package com.code4ro.legalconsultation.service.impl.comments;

import com.code4ro.legalconsultation.common.exceptions.ConflictingMetadataException;
import com.code4ro.legalconsultation.common.exceptions.ResourceNotFoundException;
import com.code4ro.legalconsultation.model.dto.CommentDto;
import com.code4ro.legalconsultation.model.persistence.Comment;
import com.code4ro.legalconsultation.model.persistence.DocumentMetadata;
import com.code4ro.legalconsultation.model.persistence.User;
import com.code4ro.legalconsultation.repository.CommentRepository;
import com.code4ro.legalconsultation.repository.DocumentMetadataRepository;
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
public class DocumentCommentService implements CommentService {
    private final CommentRepository commentRepository;
    private final DocumentMetadataRepository documentMetadataRepository;
    private final UserRepository userRepository;

    @Autowired
    public DocumentCommentService(final CommentRepository commentRepository, final DocumentMetadataRepository documentMetadataRepository, final UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.documentMetadataRepository = documentMetadataRepository;
        this.userRepository  = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> fetchAll(UUID documentId) {
        return commentRepository.findByDocumentId(documentId);
    }

    @Override
    @Transactional
    public Optional<Comment> update(UUID documentId, UUID ownerId, UUID commentId, CommentDto commentDto) {
        Comment comment = getCommentFromDB(commentId);
        if (isCorrectDocumentAndOwner(comment, documentId, ownerId)){
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

    private boolean isCorrectDocumentAndOwner(Comment comment, UUID documentId, UUID ownerId) {
        DocumentMetadata document = getDocumentFromDB(documentId);
        User owner = getUserFromDB(ownerId);
        if (comment == null || document ==null || owner == null) throw new ResourceNotFoundException();
        return document.equals(comment.getDocument()) && owner.equals(comment.getOwner());
    }

    private User getUserFromDB(UUID ownerId) {
        Optional<User> userOptional = userRepository.findById(ownerId);
        if (userOptional.isEmpty()) throw new ResourceNotFoundException();
        return userOptional.get();
    }

    private DocumentMetadata getDocumentFromDB(UUID documentId) {
        Optional<DocumentMetadata> documentOptional = documentMetadataRepository.findById(documentId);
        if (documentOptional.isEmpty()) throw new ResourceNotFoundException();
        return documentOptional.get();
    }

    @Override
    @Transactional
    public Comment create(UUID documentId, UUID ownerId, CommentDto commentDto) {
        DocumentMetadata document = getDocumentFromDB(documentId);
        User owner = getUserFromDB(ownerId);
        if (document == null || owner == null) throw new ResourceNotFoundException();
        return createAndSaveCommentEntity(commentDto, document, owner);
    }

    private Comment createAndSaveCommentEntity(CommentDto commentDto, DocumentMetadata document, User owner) {
        Comment comment = createCommentEntity(commentDto, document, owner);
        commentRepository.save(comment);
        return comment;
    }

    private Comment createCommentEntity(CommentDto commentDto, DocumentMetadata document, User owner) {
        Comment comment = new Comment();
        comment.setDocument(document);
        comment.setLastEditDateTime(Calendar.getInstance().getTime());
        comment.setOwner(owner);
        comment.setText(commentDto.getText());
        return comment;
    }

    @Override
    @Transactional
    public void delete(UUID documentId, UUID ownerId, UUID commentId) {
        Comment comment = getCommentFromDB(commentId);
        if (isCorrectDocumentAndOwner(comment, documentId, ownerId)) commentRepository.delete(comment);
        else throw new ConflictingMetadataException();
    }

    @Override
    public Comment fetchComment(UUID documentId, UUID ownerId, UUID commentId) {
        Comment comment = getCommentFromDB(commentId);
        if (isCorrectDocumentAndOwner(comment, documentId, ownerId)) return comment;
        else throw new ConflictingMetadataException();
    }
}
