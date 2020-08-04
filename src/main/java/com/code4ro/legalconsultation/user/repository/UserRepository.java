package com.code4ro.legalconsultation.user.repository;

import com.code4ro.legalconsultation.user.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllByEmailIn(Collection<String> emails);

    Optional<User> findByEmail(final String email);
}
