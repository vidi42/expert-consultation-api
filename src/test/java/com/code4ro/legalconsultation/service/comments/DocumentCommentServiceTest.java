package com.code4ro.legalconsultation.service.comments;

import com.code4ro.legalconsultation.common.exceptions.ConflictingMetadataException;
import com.code4ro.legalconsultation.common.exceptions.ResourceNotFoundException;
import com.code4ro.legalconsultation.model.dto.CommentDto;
import com.code4ro.legalconsultation.model.persistence.Comment;
import com.code4ro.legalconsultation.model.persistence.DocumentMetadata;
import com.code4ro.legalconsultation.model.persistence.User;
import com.code4ro.legalconsultation.repository.CommentRepository;
import com.code4ro.legalconsultation.repository.DocumentMetadataRepository;
import com.code4ro.legalconsultation.repository.UserRepository;
import com.code4ro.legalconsultation.service.impl.comments.DocumentCommentService;
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
public class DocumentCommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private DocumentMetadataRepository documentRepository;

    @Mock
    private UserRepository userRepository;

    private DocumentCommentService documentCommentService;

    @Before
    public void before(){
        this.documentCommentService = new DocumentCommentService(commentRepository, documentRepository, userRepository);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void createComment(){
        createCommentHappyFlow();
        createCommentMissingDocument();
        createCommentMissingUser();
    }

    private void createCommentMissingDocument() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);

        documentCommentService.create(document.getId(), owner.getId(), commentDto);
    }

    private void createCommentMissingUser() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        when(documentRepository.findById(document.getId())).thenReturn(Optional.of(document));

        documentCommentService.create(document.getId(), owner.getId(), commentDto);
    }

    private void createCommentHappyFlow() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        when(documentRepository.findById(document.getId())).thenReturn(Optional.of(document));
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));

        Comment comment = documentCommentService.create(document.getId(), owner.getId(), commentDto);
        Assert.assertEquals(commentDto.getText(), comment.getText());
    }

    @Test
    public void getAllCommmentsForAGivenDocumentId(){
        List<Comment> comments = new ArrayList<>();
        for (int i=0; i<= (new Random()).nextInt(); i++){
            comments.add(RandomObjectFiller.createAndFillWithBaseEntity(Comment.class));
        }
        UUID documentId = UUID.randomUUID();
        when(commentRepository.findByDocumentId(documentId)).thenReturn(comments);

        Assert.assertEquals(comments, documentCommentService.fetchAll(documentId));
    }

    @Test
    public void updateCommmentHappyFlow(){
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setDocument(document);
        comment.setOwner(owner);
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(documentRepository.findById(document.getId())).thenReturn(Optional.of(document));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        Comment savedComment = comment;
        savedComment.setText(commentDto.getText());
        when(commentRepository.save(savedComment)).thenReturn(savedComment);
        Optional<Comment> commentOptional = documentCommentService.update(document.getId(), owner.getId(), comment.getId(), commentDto);
        verify(commentRepository).save(any(Comment.class));
        Assert.assertEquals(savedComment, commentOptional.get());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateCommmentMissingUser() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setDocument(document);
        comment.setOwner(owner);
        when(documentRepository.findById(document.getId())).thenReturn(Optional.of(document));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        documentCommentService.update(document.getId(), owner.getId(), comment.getId(), commentDto);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateCommmentMissingDocument() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setDocument(document);
        comment.setOwner(owner);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        documentCommentService.update(document.getId(), owner.getId(), comment.getId(), commentDto);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateCommmentMissingOriginalComment() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());
        documentCommentService.update(document.getId(), owner.getId(), comment.getId(), commentDto);
    }

    @Test(expected = ConflictingMetadataException.class)
    public void updateCommmentDifferentUser() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setDocument(document);
        comment.setOwner(owner);
        when(documentRepository.findById(document.getId())).thenReturn(Optional.of(document));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        Comment savedComment = comment;
        savedComment.setText(commentDto.getText());
        User otherUser = RandomObjectFiller.createAndFillWithBaseEntityAndDifferentId(User.class, owner.getId());
        when(userRepository.findById(otherUser.getId())).thenReturn(Optional.of(otherUser));
        Optional<Comment> commentOptional = documentCommentService.update(document.getId(), otherUser.getId(), comment.getId(), commentDto);
        Assert.assertTrue(commentOptional.isEmpty());
    }

    @Test(expected = ConflictingMetadataException.class)
    public void updateCommmentDifferentDocument() {
        CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setDocument(document);
        comment.setOwner(owner);
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        Comment savedComment = comment;
        savedComment.setText(commentDto.getText());
        DocumentMetadata otherDocument = RandomObjectFiller.createAndFillWithBaseEntityAndDifferentId(DocumentMetadata.class, document.getId());
        when(documentRepository.findById(otherDocument.getId())).thenReturn(Optional.of(otherDocument));
        Optional<Comment> commentOptional = documentCommentService.update(otherDocument.getId(), owner.getId(), comment.getId(), commentDto);
        Assert.assertTrue(commentOptional.isEmpty());
    }

    @Test
    public void deleteCommentHappyFlow(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setDocument(document);
        comment.setOwner(owner);
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(documentRepository.findById(document.getId())).thenReturn(Optional.of(document));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        documentCommentService.delete(document.getId(), owner.getId(), comment.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteCommentMissingOwner(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setDocument(document);
        comment.setOwner(owner);
        when(documentRepository.findById(document.getId())).thenReturn(Optional.of(document));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        documentCommentService.delete(document.getId(), owner.getId(), comment.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteCommentMissingDocument(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setDocument(document);
        comment.setOwner(owner);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        documentCommentService.delete(document.getId(), owner.getId(), comment.getId());
    }

    @Test(expected = ConflictingMetadataException.class)
    public void deleteCommentWrongUser(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setDocument(document);
        comment.setOwner(owner);
        User otherUser = RandomObjectFiller.createAndFillWithBaseEntityAndDifferentId(User.class, owner.getId());
        when(userRepository.findById(otherUser.getId())).thenReturn(Optional.of(otherUser));
        when(documentRepository.findById(document.getId())).thenReturn(Optional.of(document));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        documentCommentService.delete(document.getId(), otherUser.getId(), comment.getId());
    }

    @Test(expected = ConflictingMetadataException.class)
    public void deleteCommentWrongDocument(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setDocument(document);
        comment.setOwner(owner);
        DocumentMetadata otherDocument = RandomObjectFiller.createAndFillWithBaseEntityAndDifferentId(DocumentMetadata.class, owner.getId());
        when(documentRepository.findById(otherDocument.getId())).thenReturn(Optional.of(otherDocument));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        documentCommentService.delete(otherDocument.getId(), owner.getId(), comment.getId());
    }

    @Test
    public void fetchOneCommentHappyFlow(){
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setDocument(document);
        comment.setOwner(owner);
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(documentRepository.findById(document.getId())).thenReturn(Optional.of(document));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        Comment fetchedComment = documentCommentService.fetchComment(document.getId(), owner.getId(), comment.getId());
        Assert.assertEquals(comment, fetchedComment);
    }

    @Test(expected = ConflictingMetadataException.class)
    public void fetchOneCommentWrongDocument() {
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setDocument(document);
        comment.setOwner(owner);
        DocumentMetadata otherDocument = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        when(documentRepository.findById(otherDocument.getId())).thenReturn(Optional.of(otherDocument));
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        documentCommentService.fetchComment(otherDocument.getId(), owner.getId(), comment.getId());
    }

    @Test(expected = ConflictingMetadataException.class)
    public void fetchOneCommentWrongUser() {
        User owner = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        DocumentMetadata document = RandomObjectFiller.createAndFillWithBaseEntity(DocumentMetadata.class);
        Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
        comment.setId(UUID.randomUUID());
        comment.setDocument(document);
        comment.setOwner(owner);
        User otherUser = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
        when(userRepository.findById(otherUser.getId())).thenReturn(Optional.of(otherUser));
        when(documentRepository.findById(document.getId())).thenReturn(Optional.of(document));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        documentCommentService.fetchComment(document.getId(), otherUser.getId(), comment.getId());
    }
}
