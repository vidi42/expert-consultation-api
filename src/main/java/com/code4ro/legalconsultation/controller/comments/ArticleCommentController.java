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

@AllArgsConstructor
@Controller
@RequestMapping("/api/article")
public class ArticleCommentController implements CommentControllerBase {

    @Qualifier(value = "articleCommentService")
    private CommentService articleCommentService;

    @Override
    @GetMapping("{articleId}")
    public ResponseEntity<List<Comment>> fetchAllComments(@PathVariable UUID articleId){
        return ResponseEntity.ok(articleCommentService.fetchAll(articleId));
    }

    @Override
    @GetMapping("{articleId}/user/{ownerId}/comment/{commentId}")
    public ResponseEntity<Comment> fetchComment(@PathVariable UUID articleId, @PathVariable UUID ownerId, @PathVariable UUID commentId) {
        return ResponseEntity.ok(articleCommentService.fetchComment(articleId, ownerId, commentId));
    }

    @Override
    @PostMapping("{articleId}/user/{ownerId}")
    public ResponseEntity createComment(@PathVariable UUID articleId, @PathVariable UUID ownerId, @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(articleCommentService.create(articleId, ownerId, commentDto));
    }

    @Override
    @PutMapping("{articleId}/user/{ownerId}/comment/{commentId}")
    public ResponseEntity updateComment(@PathVariable UUID articleId, @PathVariable UUID ownerId, @PathVariable UUID commentId, @RequestBody CommentDto commentDto){
        Optional<Comment> commentOptional = articleCommentService.update(articleId, ownerId, commentId, commentDto);
        if (commentOptional.isEmpty())
            throw new ResourceNotFoundException();
        return ResponseEntity.ok(commentOptional.get());
    }

    @Override
    @DeleteMapping("{articleId}/user/{ownerId}/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable UUID articleId, @PathVariable UUID ownerId, @PathVariable UUID commentId){
        articleCommentService.delete(articleId, ownerId, commentId);
        return ResponseEntity.ok().build();
    }
}
