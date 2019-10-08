package com.code4ro.legalconsultation.controller.comments;

import com.code4ro.legalconsultation.common.exceptions.ResourceNotFoundException;
import com.code4ro.legalconsultation.controller.comments.templates.CommentControllerBase;
import com.code4ro.legalconsultation.model.dto.CommentDto;
import com.code4ro.legalconsultation.model.persistence.Comment;
import com.code4ro.legalconsultation.service.api.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/documents")
@AllArgsConstructor
public class DocumentCommentController implements CommentControllerBase {
    @Qualifier(value = "documentCommentService")
    private CommentService documentCommentService;

    @Override
    @GetMapping("{documentId}")
    public ResponseEntity<List<Comment>> fetchAllComments(@PathVariable UUID documentId){
        return ResponseEntity.ok(documentCommentService.fetchAll(documentId));
    }

    @Override
    @GetMapping("{documentId}/user/{ownerId}/comment/{commentId}")
    public ResponseEntity<Comment> fetchComment(@PathVariable UUID documentId, @PathVariable UUID ownerId, @PathVariable UUID commentId) {
        return ResponseEntity.ok(documentCommentService.fetchComment(documentId, ownerId, commentId));
    }

    @Override
    @PostMapping("{documentId}/user/{ownerId}")
    public ResponseEntity createComment(@PathVariable UUID documentId, @PathVariable UUID ownerId, @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(documentCommentService.create(documentId, ownerId, commentDto));
    }

    @Override
    @PutMapping("{documentId}/user/{ownerId}/comment/{commentId}")
    public ResponseEntity updateComment(@PathVariable UUID documentId, @PathVariable UUID ownerId, @PathVariable UUID commentId, @RequestBody CommentDto commentDto){
        Optional<Comment> commentOptional = documentCommentService.update(documentId, ownerId, commentId, commentDto);
        if (commentOptional.isEmpty())
            throw new ResourceNotFoundException();
        return ResponseEntity.ok(commentOptional.get());
    }

    @Override
    @DeleteMapping("{documentId}/user/{ownerId}/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable UUID documentId, @PathVariable UUID ownerId, @PathVariable UUID commentId){
        documentCommentService.delete(documentId, ownerId, commentId);
        return ResponseEntity.ok().build();
    }
}
