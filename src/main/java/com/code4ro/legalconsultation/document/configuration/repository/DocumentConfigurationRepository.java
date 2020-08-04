package com.code4ro.legalconsultation.document.configuration.repository;

import com.code4ro.legalconsultation.document.configuration.model.persistence.DocumentConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentConfigurationRepository extends JpaRepository<DocumentConfiguration, UUID> {
}
