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
@RequestMapping("/api/chapter")
@AllArgsConstructor
public class ChapterCommentController implements CommentControllerBase {
    @Qualifier(value = "chapterCommentService")
    private CommentService chapterCommentService;

    @Override
    @GetMapping("{chapterId}")
    public ResponseEntity<List<Comment>> fetchAllComments(@PathVariable UUID chapterId){
        return ResponseEntity.ok(chapterCommentService.fetchAll(chapterId));
    }

    @Override
    @GetMapping("{chapterId}/user/{ownerId}/comment/{commentId}")
    public ResponseEntity<Comment> fetchComment(@PathVariable UUID chapterId, @PathVariable UUID ownerId, @PathVariable UUID commentId) {
        return ResponseEntity.ok(chapterCommentService.fetchComment(chapterId, ownerId, commentId));
    }

    @Override
    @PostMapping("{chapterId}/user/{ownerId}")
    public ResponseEntity createComment(@PathVariable UUID chapterId, @PathVariable UUID ownerId, @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(chapterCommentService.create(chapterId, ownerId, commentDto));
    }

    @Override
    @PutMapping("{chapterId}/user/{ownerId}/comment/{commentId}")
    public ResponseEntity updateComment(@PathVariable UUID chapterId, @PathVariable UUID ownerId, @PathVariable UUID commentId, @RequestBody CommentDto commentDto){
        Optional<Comment> commentOptional = chapterCommentService.update(chapterId, ownerId, commentId, commentDto);
        if (commentOptional.isEmpty())
            throw new ResourceNotFoundException();
        return ResponseEntity.ok(commentOptional.get());
    }

    @Override
    @DeleteMapping("{chapterId}/user/{ownerId}/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable UUID chapterId, @PathVariable UUID ownerId, @PathVariable UUID commentId){
        chapterCommentService.delete(chapterId, ownerId, commentId);
        return ResponseEntity.ok().build();
    }
}
