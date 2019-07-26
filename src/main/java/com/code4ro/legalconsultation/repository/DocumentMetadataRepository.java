package com.code4ro.legalconsultation.repository;

import com.code4ro.legalconsultation.model.persistence.DocumentMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentMetadataRepository extends JpaRepository<DocumentMetadata, UUID> {
}
