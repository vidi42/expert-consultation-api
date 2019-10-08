package com.code4ro.legalconsultation.service.impl.comments;

import com.code4ro.legalconsultation.common.exceptions.ConflictingMetadataException;
import com.code4ro.legalconsultation.common.exceptions.ResourceNotFoundException;
import com.code4ro.legalconsultation.model.dto.CommentDto;
import com.code4ro.legalconsultation.model.persistence.Article;
import com.code4ro.legalconsultation.model.persistence.Comment;
import com.code4ro.legalconsultation.model.persistence.User;
import com.code4ro.legalconsultation.repository.ArticleRepository;
import com.code4ro.legalconsultation.repository.CommentRepository;
import com.code4ro.legalconsultation.repository.UserRepository;
import com.code4ro.legalconsultation.service.api.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleCommentService implements CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Autowired
    public ArticleCommentService(final CommentRepository commentRepository, final ArticleRepository articleRepository, final UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.userRepository  = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> fetchAll(UUID articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    @Override
    @Transactional
    public Optional<Comment> update(UUID articleId, UUID ownerId, UUID commentId, CommentDto commentDto) {
        Comment comment = getCommentFromDB(commentId);
        if (isCorrectArticleAndOwner(comment, articleId, ownerId)){
            comment.setText(commentDto.getText());
            comment = commentRepository.save(comment);
            return Optional.of(comment);
        } else throw new ConflictingMetadataException();
    }

    private Comment getCommentFromDB(UUID commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isEmpty()) throw new ResourceNotFoundException();
        return commentOptional.get();
    }

    private boolean isCorrectArticleAndOwner(Comment comment, UUID articleId, UUID ownerId) {
        Article article = getArticleFromDB(articleId);
        User owner = getUserFromDB(ownerId);
        if (comment == null || article ==null || owner == null) throw new ResourceNotFoundException();
        return article.equals(comment.getArticle()) && owner.equals(comment.getOwner());
    }

    private User getUserFromDB(UUID ownerId) {
        Optional<User> userOptional = userRepository.findById(ownerId);
        if (userOptional.isEmpty()) throw new ResourceNotFoundException();
        return userOptional.get();
    }

    private Article getArticleFromDB(UUID articleId) {
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        if (articleOptional.isEmpty()) throw new ResourceNotFoundException();
        return articleOptional.get();
    }

    @Override
    @Transactional
    public Comment create(UUID articleId, UUID ownerId, CommentDto commentDto) {
        Article article = getArticleFromDB(articleId);
        User owner = getUserFromDB(ownerId);
        if (article == null || owner == null) throw new ResourceNotFoundException();
        return createAndSaveCommentEntity(commentDto, article, owner);
    }

    private Comment createAndSaveCommentEntity(CommentDto commentDto, Article article, User owner) {
        Comment comment = createCommentEntity(commentDto, article, owner);
        commentRepository.save(comment);
        return comment;
    }

    private Comment createCommentEntity(CommentDto commentDto, Article article, User owner) {
        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setLastEditDateTime(Calendar.getInstance().getTime());
        comment.setOwner(owner);
        comment.setText(commentDto.getText());
        return comment;
    }

    @Override
    @Transactional
    public void delete(UUID articleId, UUID ownerId, UUID commentId) {
        Comment comment = getCommentFromDB(commentId);
        if (isCorrectArticleAndOwner(comment, articleId, ownerId)) commentRepository.delete(comment);
        else throw new ConflictingMetadataException();
    }

    @Override
    public Comment fetchComment(UUID articleId, UUID ownerId, UUID commentId) {
        Comment comment = getCommentFromDB(commentId);
        if (isCorrectArticleAndOwner(comment, articleId, ownerId)) return comment;
        else throw new ConflictingMetadataException();
    }
}
