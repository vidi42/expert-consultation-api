package com.code4ro.legalconsultation.repository;

import com.code4ro.legalconsultation.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllByEmailIn(Collection<String> emails);
}
