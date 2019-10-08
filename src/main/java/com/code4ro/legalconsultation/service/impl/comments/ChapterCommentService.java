package com.code4ro.legalconsultation.service.impl.comments;

import com.code4ro.legalconsultation.common.exceptions.ConflictingMetadataException;
import com.code4ro.legalconsultation.common.exceptions.ResourceNotFoundException;
import com.code4ro.legalconsultation.model.dto.CommentDto;
import com.code4ro.legalconsultation.model.persistence.Chapter;
import com.code4ro.legalconsultation.model.persistence.Comment;
import com.code4ro.legalconsultation.model.persistence.User;
import com.code4ro.legalconsultation.repository.ChapterRepository;
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
public class ChapterCommentService implements CommentService {
    private final CommentRepository commentRepository;
    private final ChapterRepository chapterRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChapterCommentService(final CommentRepository commentRepository, final ChapterRepository chapterRepository, final UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.chapterRepository = chapterRepository;
        this.userRepository  = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> fetchAll(UUID chapterId) {
        return commentRepository.findByChapterId(chapterId);
    }

    @Override
    @Transactional
    public Optional<Comment> update(UUID chapterId, UUID ownerId, UUID commentId, CommentDto commentDto) {
        Comment comment = getCommentFromDB(commentId);
        if (isCorrectChapterAndOwner(comment, chapterId, ownerId)){
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

    private boolean isCorrectChapterAndOwner(Comment comment, UUID chapterId, UUID ownerId) {
        Chapter chapter = getChapterFromDB(chapterId);
        User owner = getUserFromDB(ownerId);
        if (comment == null || chapter ==null || owner == null) throw new ResourceNotFoundException();
        return chapter.equals(comment.getChapter()) && owner.equals(comment.getOwner());
    }

    private User getUserFromDB(UUID ownerId) {
        Optional<User> userOptional = userRepository.findById(ownerId);
        if (userOptional.isEmpty()) throw new ResourceNotFoundException();
        return userOptional.get();
    }

    private Chapter getChapterFromDB(UUID chapterId) {
        Optional<Chapter> chapterOptional = chapterRepository.findById(chapterId);
        if (chapterOptional.isEmpty()) throw new ResourceNotFoundException();
        return chapterOptional.get();
    }

    @Override
    @Transactional
    public Comment create(UUID chapterId, UUID ownerId, CommentDto commentDto) {
        Chapter chapter = getChapterFromDB(chapterId);
        User owner = getUserFromDB(ownerId);
        if (chapter == null || owner == null) throw new ResourceNotFoundException();
        return createAndSaveCommentEntity(commentDto, chapter, owner);
    }

    private Comment createAndSaveCommentEntity(CommentDto commentDto, Chapter chapter, User owner) {
        Comment comment = createCommentEntity(commentDto, chapter, owner);
        commentRepository.save(comment);
        return comment;
    }

    private Comment createCommentEntity(CommentDto commentDto, Chapter chapter, User owner) {
        Comment comment = new Comment();
        comment.setChapter(chapter);
        comment.setLastEditDateTime(Calendar.getInstance().getTime());
        comment.setOwner(owner);
        comment.setText(commentDto.getText());
        return comment;
    }

    @Override
    @Transactional
    public void delete(UUID chapterId, UUID ownerId, UUID commentId) {
        Comment comment = getCommentFromDB(commentId);
        if (isCorrectChapterAndOwner(comment, chapterId, ownerId)) commentRepository.delete(comment);
        else throw new ConflictingMetadataException();
    }

    @Override
    public Comment fetchComment(UUID chapterId, UUID ownerId, UUID commentId) {
        Comment comment = getCommentFromDB(commentId);
        if (isCorrectChapterAndOwner(comment, chapterId, ownerId)) return comment;
        else throw new ConflictingMetadataException();
    }
}
