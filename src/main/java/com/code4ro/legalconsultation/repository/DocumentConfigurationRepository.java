package com.code4ro.legalconsultation.repository;

import com.code4ro.legalconsultation.model.persistence.DocumentConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentConfigurationRepository extends JpaRepository<DocumentConfiguration, UUID> {
}
