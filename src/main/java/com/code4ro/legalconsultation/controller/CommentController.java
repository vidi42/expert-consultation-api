package com.code4ro.legalconsultation.controller;

import com.code4ro.legalconsultation.model.dto.CommentDto;
import com.code4ro.legalconsultation.model.dto.CommentIdentificationDto;
import com.code4ro.legalconsultation.model.persistence.Comment;
import com.code4ro.legalconsultation.service.api.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpHeaders;
import java.util.UUID;

import static com.code4ro.legalconsultation.model.persistence.CommentStatus.APPROVED;
import static com.code4ro.legalconsultation.model.persistence.CommentStatus.REJECTED;

@Controller
@RequestMapping("/api/documentnodes/{nodeId}/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> create(@PathVariable final UUID nodeId,
                                          @RequestBody final CommentDto commentDto) {
        Comment comment = commentService.create(nodeId, commentDto);
        return ResponseEntity.created(URI.create("/api/documentnodes/" + nodeId + "/comments/" + comment.getId())).body(comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable final UUID nodeId,
                                             @PathVariable final UUID id,
                                             @RequestBody final CommentDto commentDto) {
        return ResponseEntity.ok(commentService.update(nodeId, id, commentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final UUID nodeId,
                                       @PathVariable final UUID id) {
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<CommentIdentificationDto>> findAll(@PathVariable UUID nodeId,
                                                                  final Pageable pageable) {
        return ResponseEntity.ok(commentService.findAll(nodeId, pageable));
    }

    @GetMapping("/{commentId}/approve")
    public ResponseEntity<CommentDto> approve(@PathVariable UUID commentId){
        return ResponseEntity.ok(commentService.setStatus(commentId, APPROVED));
    }

    @GetMapping("/{commentId}/reject")
    public ResponseEntity<CommentDto> reject(@PathVariable UUID commentId){
        return ResponseEntity.ok(commentService.setStatus(commentId, REJECTED));
    }
}
