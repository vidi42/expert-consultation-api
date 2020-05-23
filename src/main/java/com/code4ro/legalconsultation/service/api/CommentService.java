package com.code4ro.legalconsultation.service.api;

import com.code4ro.legalconsultation.model.dto.CommentDto;
import com.code4ro.legalconsultation.model.persistence.Comment;
import com.code4ro.legalconsultation.model.persistence.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.UUID;

public interface CommentService {
    CommentDto update(UUID nodeId, UUID id, CommentDto commentDto);

    Comment create(UUID nodeId, CommentDto commentDto);

    CommentDto createReply(UUID parentId, CommentDto commentDto);

    void delete(UUID id);

    Page<Comment> findAll(UUID nodeId, Pageable pageable);

    Page<Comment> findAllReplies(UUID parentId, Pageable pageable);

    BigInteger count(UUID nodeId);

    CommentDto setStatus(UUID commentId, CommentStatus approved);
}
