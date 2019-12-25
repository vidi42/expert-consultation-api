package com.code4ro.legalconsultation.service.api;

import java.util.UUID;

import com.code4ro.legalconsultation.model.dto.VoteDto;

public interface VoteService {
  VoteDto vote(UUID nodeId, VoteDto voteDto);
}
