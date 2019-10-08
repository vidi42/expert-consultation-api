package com.code4ro.legalconsultation.service.comments;

import com.code4ro.legalconsultation.common.exceptions.ConflictingMetadataException;
import com.code4ro.legalconsultation.common.exceptions.ResourceNotFoundException;
import com.code4ro.legalconsultation.model.dto.CommentDto;
import com.code4ro.legalconsultation.model.persistence.Chapter;
import com.code4ro.legalconsultation.model.persistence.Comment;
import com.code4ro.legalconsultation.model.persistence.User;
import com.code4ro.legalconsultation.repository.ChapterRepository;
import com.code4ro.legalconsultation.repository.CommentRepository;
import com.code4ro.legalconsultation.repository.UserRepository;
import com.code4ro.legalconsultation.service.impl.comments.ChapterCommentService;
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
public class ChapterCommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private UserRepository userRepository;

    private ChapterCommentService chapterCommentService;

    @Before
    public void before(){
        this.chapterCommentService = new ChapterCommentService(commentRepository, chapterRepository, userRepository);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void createComment(){
        createCommentHappyFlow();
        createCommentMissingChapter();
        createCommentMissingUser();
    }

    private void createCommentMissingChapter() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);

        chapterCommentService.create(chapter.getId(), owner.getId(), commentDto);
    }

    private void createCommentMissingUser() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        when(chapterRepository.findById(chapter.getId())).thenReturn(Optional.of(chapter));

        chapterCommentService.create(chapter.getId(), owner.getId(), commentDto);
    }

    private void createCommentHappyFlow() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        when(chapterRepository.findById(chapter.getId())).thenReturn(Optional.of(chapter));
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));

        Comment comment = chapterCommentService.create(chapter.getId(), owner.getId(), commentDto);
        Assert.assertEquals(commentDto.getText(), comment.getText());
    }

    @Test
    public void getAllCommmentsForAGivenChapterId(){
        List<Comment> comments = new ArrayList<>();
        for (int i=0; i<= (new Random()).nextInt(); i++){
            comments.add(RandomObjectFiller.createAndFillWithBaseEntity(Comment.class));
        }
        UUID chapterId = UUID.randomUUID();
        when(commentRepository.findByChapterId(chapterId)).thenReturn(comments);

        Assert.assertEquals(comments, chapterCommentService.fetchAll(chapterId));
    }

    @Test
    public void updateCommmentHappyFlow(){
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setChapter(chapter);
        comment.setOwner(owner);
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(chapterRepository.findById(chapter.getId())).thenReturn(Optional.of(chapter));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        Comment savedComment = comment;
        savedComment.setText(commentDto.getText());
        when(commentRepository.save(savedComment)).thenReturn(savedComment);
        Optional<Comment> commentOptional = chapterCommentService.update(chapter.getId(), owner.getId(), comment.getId(), commentDto);
        verify(commentRepository).save(any(Comment.class));
        Assert.assertEquals(savedComment, commentOptional.get());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateCommmentMissingUser() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setChapter(chapter);
        comment.setOwner(owner);
        when(chapterRepository.findById(chapter.getId())).thenReturn(Optional.of(chapter));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        chapterCommentService.update(chapter.getId(), owner.getId(), comment.getId(), commentDto);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateCommmentMissingChapter() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setChapter(chapter);
        comment.setOwner(owner);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        chapterCommentService.update(chapter.getId(), owner.getId(), comment.getId(), commentDto);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateCommmentMissingOriginalComment() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());
        chapterCommentService.update(chapter.getId(), owner.getId(), comment.getId(), commentDto);
    }

    @Test(expected = ConflictingMetadataException.class)
    public void updateCommmentDifferentUser() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setChapter(chapter);
        comment.setOwner(owner);
        when(chapterRepository.findById(chapter.getId())).thenReturn(Optional.of(chapter));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        Comment savedComment = comment;
        savedComment.setText(commentDto.getText());
        User otherUser = RandomObjectFiller.createAndFillWithBaseEntityAndDifferentId(User.class, owner.getId());
        when(userRepository.findById(otherUser.getId())).thenReturn(Optional.of(otherUser));
        Optional<Comment> commentOptional = chapterCommentService.update(chapter.getId(), otherUser.getId(), comment.getId(), commentDto);
        Assert.assertTrue(commentOptional.isEmpty());
    }

    @Test(expected = ConflictingMetadataException.class)
    public void updateCommmentDifferentChapter() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setChapter(chapter);
        comment.setOwner(owner);
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        Comment savedComment = comment;
        savedComment.setText(commentDto.getText());
        Chapter otherChapter = RandomObjectFiller.createAndFillWithBaseEntityAndDifferentId(Chapter.class, chapter.getId());
        when(chapterRepository.findById(otherChapter.getId())).thenReturn(Optional.of(otherChapter));
        Optional<Comment> commentOptional = chapterCommentService.update(otherChapter.getId(), owner.getId(), comment.getId(), commentDto);
        Assert.assertTrue(commentOptional.isEmpty());
    }

    @Test
    public void deleteCommentHappyFlow(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setChapter(chapter);
        comment.setOwner(owner);
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(chapterRepository.findById(chapter.getId())).thenReturn(Optional.of(chapter));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        chapterCommentService.delete(chapter.getId(), owner.getId(), comment.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteCommentMissingOwner(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setChapter(chapter);
        comment.setOwner(owner);
        when(chapterRepository.findById(chapter.getId())).thenReturn(Optional.of(chapter));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        chapterCommentService.delete(chapter.getId(), owner.getId(), comment.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteCommentMissingChapter(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setChapter(chapter);
        comment.setOwner(owner);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        chapterCommentService.delete(chapter.getId(), owner.getId(), comment.getId());
    }

    @Test(expected = ConflictingMetadataException.class)
    public void deleteCommentWrongUser(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setChapter(chapter);
        comment.setOwner(owner);
        User otherUser = RandomObjectFiller.createAndFillWithBaseEntityAndDifferentId(User.class, owner.getId());
        when(userRepository.findById(otherUser.getId())).thenReturn(Optional.of(otherUser));
        when(chapterRepository.findById(chapter.getId())).thenReturn(Optional.of(chapter));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        chapterCommentService.delete(chapter.getId(), otherUser.getId(), comment.getId());
    }

    @Test(expected = ConflictingMetadataException.class)
    public void deleteCommentWrongChapter(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setChapter(chapter);
        comment.setOwner(owner);
        Chapter otherChapter = RandomObjectFiller.createAndFillWithBaseEntityAndDifferentId(Chapter.class, owner.getId());
        when(chapterRepository.findById(otherChapter.getId())).thenReturn(Optional.of(otherChapter));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        chapterCommentService.delete(otherChapter.getId(), owner.getId(), comment.getId());
    }

    @Test
    public void fetchOneCommentHappyFlow(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setChapter(chapter);
        comment.setOwner(owner);
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(chapterRepository.findById(chapter.getId())).thenReturn(Optional.of(chapter));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        Comment fetchedComment = chapterCommentService.fetchComment(chapter.getId(), owner.getId(), comment.getId());
        Assert.assertEquals(comment, fetchedComment);
    }

    @Test(expected = ConflictingMetadataException.class)
    public void fetchOneCommentWrongChapter() {
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setChapter(chapter);
        comment.setOwner(owner);
        Chapter otherChapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        when(chapterRepository.findById(otherChapter.getId())).thenReturn(Optional.of(otherChapter));
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        chapterCommentService.fetchComment(otherChapter.getId(), owner.getId(), comment.getId());
    }

    @Test(expected = ConflictingMetadataException.class)
    public void fetchOneCommentWrongUser() {
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        Chapter chapter = RandomObjectFiller.createAndFillWithBaseEntity(Chapter.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setChapter(chapter);
        comment.setOwner(owner);
        User otherUser = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        when(userRepository.findById(otherUser.getId())).thenReturn(Optional.of(otherUser));
        when(chapterRepository.findById(chapter.getId())).thenReturn(Optional.of(chapter));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        chapterCommentService.fetchComment(chapter.getId(), otherUser.getId(), comment.getId());
    }
}
