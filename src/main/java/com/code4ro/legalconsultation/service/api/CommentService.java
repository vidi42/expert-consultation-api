package com.code4ro.legalconsultation.service.api;

import com.code4ro.legalconsultation.model.dto.CommentDto;
import com.code4ro.legalconsultation.model.persistence.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentService {
    List<Comment> fetchAll(UUID articleId);
    Optional<Comment> update(UUID elementId, UUID ownerId, UUID commentId, CommentDto commentDto);
    Comment create(UUID elementId, UUID ownerId, CommentDto commentDto);
    void delete(UUID articleId, UUID ownerId, UUID commentId);
    Comment fetchComment(UUID articleId, UUID ownerId, UUID commentId);
}
