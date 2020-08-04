package com.code4ro.legalconsultation.authentication.repository;

import com.code4ro.legalconsultation.authentication.model.persistence.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, UUID> {

    @Query("SELECT appuser FROM ApplicationUser appuser " +
            " JOIN FETCH appuser.user user" +
            " WHERE appuser.username = :usernameOrEmail OR user.email = :usernameOrEmail")
    Optional<ApplicationUser> findByUsernameOrEmail(String usernameOrEmail);

    boolean existsByUsername(String username);
}
