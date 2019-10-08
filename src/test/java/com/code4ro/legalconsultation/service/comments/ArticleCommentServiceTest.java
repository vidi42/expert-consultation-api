package com.code4ro.legalconsultation.service.comments;

import com.code4ro.legalconsultation.common.exceptions.ConflictingMetadataException;
import com.code4ro.legalconsultation.common.exceptions.ResourceNotFoundException;
import com.code4ro.legalconsultation.model.dto.CommentDto;
import com.code4ro.legalconsultation.model.persistence.Article;
import com.code4ro.legalconsultation.model.persistence.Comment;
import com.code4ro.legalconsultation.model.persistence.User;
import com.code4ro.legalconsultation.repository.ArticleRepository;
import com.code4ro.legalconsultation.repository.CommentRepository;
import com.code4ro.legalconsultation.repository.UserRepository;
import com.code4ro.legalconsultation.service.impl.comments.ArticleCommentService;
import com.code4ro.legalconsultation.util.RandomObjectFiller;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArticleCommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    private ArticleCommentService articleCommentService;

    @Before
    public void before(){
        this.articleCommentService = new ArticleCommentService(commentRepository, articleRepository, userRepository);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void createComment(){
        createCommentHappyFlow();
        createCommentMissingArticle();
        createCommentMissingUser();
    }

    private void createCommentMissingArticle() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);

        articleCommentService.create(article.getId(), owner.getId(), commentDto);
    }

    private void createCommentMissingUser() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));

        articleCommentService.create(article.getId(), owner.getId(), commentDto);
    }

    private void createCommentHappyFlow() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));

        Comment comment = articleCommentService.create(article.getId(), owner.getId(), commentDto);
        Assert.assertEquals(commentDto.getText(), comment.getText());
    }

    @Test
    public void getAllCommmentsForAGivenArticleId(){
        List<Comment> comments = new ArrayList<>();
        for (int i=0; i<= (new Random()).nextInt(); i++){
            comments.add(RandomObjectFiller.createAndFillWithBaseEntity(Comment.class));
        }
        UUID articleId = UUID.randomUUID();
        when(commentRepository.findByArticleId(articleId)).thenReturn(comments);

        Assert.assertEquals(comments, articleCommentService.fetchAll(articleId));
    }

    @Test
    public void updateCommmentHappyFlow(){
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setArticle(article);
        comment.setOwner(owner);
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        Comment savedComment = comment;
        savedComment.setText(commentDto.getText());
        when(commentRepository.save(savedComment)).thenReturn(savedComment);
        Optional<Comment> commentOptional = articleCommentService.update(article.getId(), owner.getId(), comment.getId(), commentDto);
        verify(commentRepository).save(any(Comment.class));
        Assert.assertEquals(savedComment, commentOptional.get());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateCommmentMissingUser() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setArticle(article);
        comment.setOwner(owner);
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        articleCommentService.update(article.getId(), owner.getId(), comment.getId(), commentDto);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateCommmentMissingArticle() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setArticle(article);
        comment.setOwner(owner);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        articleCommentService.update(article.getId(), owner.getId(), comment.getId(), commentDto);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateCommmentMissingOriginalComment() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());
        articleCommentService.update(article.getId(), owner.getId(), comment.getId(), commentDto);
    }

    @Test(expected = ConflictingMetadataException.class)
    public void updateCommmentDifferentUser() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setArticle(article);
        comment.setOwner(owner);
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        Comment savedComment = comment;
        savedComment.setText(commentDto.getText());
        User otherUser = RandomObjectFiller.createAndFillWithBaseEntityAndDifferentId(User.class, owner.getId());
        when(userRepository.findById(otherUser.getId())).thenReturn(Optional.of(otherUser));
        Optional<Comment> commentOptional = articleCommentService.update(article.getId(), otherUser.getId(), comment.getId(), commentDto);
        Assert.assertTrue(commentOptional.isEmpty());
    }

    @Test(expected = ConflictingMetadataException.class)
    public void updateCommmentDifferentArticle() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setArticle(article);
        comment.setOwner(owner);
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        Comment savedComment = comment;
        savedComment.setText(commentDto.getText());
        Article otherArticle = RandomObjectFiller.createAndFillWithBaseEntityAndDifferentId(Article.class, article.getId());
        when(articleRepository.findById(otherArticle.getId())).thenReturn(Optional.of(otherArticle));
        Optional<Comment> commentOptional = articleCommentService.update(otherArticle.getId(), owner.getId(), comment.getId(), commentDto);
        Assert.assertTrue(commentOptional.isEmpty());
    }

    @Test
    public void deleteCommentHappyFlow(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setArticle(article);
        comment.setOwner(owner);
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        articleCommentService.delete(article.getId(), owner.getId(), comment.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteCommentMissingOwner(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setArticle(article);
        comment.setOwner(owner);
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        articleCommentService.delete(article.getId(), owner.getId(), comment.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteCommentMissingArticle(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setArticle(article);
        comment.setOwner(owner);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        articleCommentService.delete(article.getId(), owner.getId(), comment.getId());
    }

    @Test(expected = ConflictingMetadataException.class)
    public void deleteCommentWrongUser(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setArticle(article);
        comment.setOwner(owner);
        User otherUser = RandomObjectFiller.createAndFillWithBaseEntityAndDifferentId(User.class, owner.getId());
        when(userRepository.findById(otherUser.getId())).thenReturn(Optional.of(otherUser));
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        articleCommentService.delete(article.getId(), otherUser.getId(), comment.getId());
    }

    @Test(expected = ConflictingMetadataException.class)
    public void deleteCommentWrongArticle(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setArticle(article);
        comment.setOwner(owner);
        Article otherArticle = RandomObjectFiller.createAndFillWithBaseEntityAndDifferentId(Article.class, owner.getId());
        when(articleRepository.findById(otherArticle.getId())).thenReturn(Optional.of(otherArticle));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        articleCommentService.delete(otherArticle.getId(), owner.getId(), comment.getId());
    }

    @Test
    public void fetchOneCommentHappyFlow(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setArticle(article);
        comment.setOwner(owner);
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        Comment fetchedComment = articleCommentService.fetchComment(article.getId(), owner.getId(), comment.getId());
        Assert.assertEquals(comment, fetchedComment);
    }

    @Test(expected = ConflictingMetadataException.class)
    public void fetchOneCommentWrongArticle() {
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setArticle(article);
        comment.setOwner(owner);
        Article otherArticle = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        when(articleRepository.findById(otherArticle.getId())).thenReturn(Optional.of(otherArticle));
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        articleCommentService.fetchComment(otherArticle.getId(), owner.getId(), comment.getId());
    }

    @Test(expected = ConflictingMetadataException.class)
    public void fetchOneCommentWrongUser() {
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setArticle(article);
        comment.setOwner(owner);
        User otherUser = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        when(userRepository.findById(otherUser.getId())).thenReturn(Optional.of(otherUser));
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        articleCommentService.fetchComment(article.getId(), otherUser.getId(), comment.getId());
    }
}
