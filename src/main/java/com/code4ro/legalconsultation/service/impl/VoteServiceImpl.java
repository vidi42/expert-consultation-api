package com.code4ro.legalconsultation.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.code4ro.legalconsultation.config.security.CurrentUserService;
import com.code4ro.legalconsultation.model.dto.VoteDto;
import com.code4ro.legalconsultation.model.persistence.ApplicationUser;
import com.code4ro.legalconsultation.model.persistence.DocumentNode;
import com.code4ro.legalconsultation.model.persistence.Vote;
import com.code4ro.legalconsultation.repository.VoteRepository;
import com.code4ro.legalconsultation.service.api.DocumentNodeService;
import com.code4ro.legalconsultation.service.api.VoteService;

@Service
public class VoteServiceImpl implements VoteService {
  private final VoteRepository voteRepository;
  private final CurrentUserService currentUserService;
  private final DocumentNodeService documentNodeService;

  public VoteServiceImpl(VoteRepository voteRepository, CurrentUserService currentUserService,
      DocumentNodeService documentNodeService) {
    this.voteRepository = voteRepository;
    this.currentUserService = currentUserService;
    this.documentNodeService = documentNodeService;
  }

  @Transactional
  @Override
  public VoteDto vote(UUID nodeId, VoteDto voteDto) {
    // get the current user and the document...
    final ApplicationUser currentUser = currentUserService.getCurrentUser();
    final DocumentNode node = documentNodeService.getEntity(nodeId);

    Vote vote = voteRepository.findByDocumentNodeIdAndOwnerId(nodeId, currentUser.getId());
    // if there is no vote for that section and user combination, create one.
    // Otherwise update...
    if (vote == null) {
      // just return if the user attempts to cast a null vote on something that he
      // didn't vote already
      if (voteDto.getVoteUp() == null)
        return null;
      vote = new Vote();
      vote.setDocumentNode(node);
      vote.setOwner(currentUser);
      vote.setLastEditDateTime(new Date());
    }

    if (voteDto.getVoteUp() != null) {
      if (voteDto.getVoteUp()) {
        vote.setVoteUp(true);
        vote.setVoteDown(false);
      } else {
        vote.setVoteUp(false);
        vote.setVoteDown(true);
      }
    } else {
      vote.setVoteUp(null);
      vote.setVoteDown(null);
    }

    voteRepository.save(vote);

    return voteDto;
  }

}
