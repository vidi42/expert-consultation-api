package com.code4ro.legalconsultation.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.code4ro.legalconsultation.model.persistence.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {
  Vote findByDocumentNodeIdAndOwnerId(final UUID nodeId, final UUID ownerId);
}
