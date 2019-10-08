package com.code4ro.legalconsultation.controller.comments.templates;

import com.code4ro.legalconsultation.model.dto.CommentDto;
import com.code4ro.legalconsultation.model.persistence.Comment;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CommentControllerBase {
    ResponseEntity<List<Comment>> fetchAllComments(UUID elementId);

    ResponseEntity<Comment> fetchComment(UUID elementId, UUID ownerId, UUID commentId);

    ResponseEntity createComment(UUID elementId, UUID ownerId, CommentDto commentDto);

    ResponseEntity updateComment(UUID elementId, UUID ownerId, UUID commentId, CommentDto commentDto);

    ResponseEntity deleteComment(UUID elementId, UUID ownerId, UUID commentID);
}
