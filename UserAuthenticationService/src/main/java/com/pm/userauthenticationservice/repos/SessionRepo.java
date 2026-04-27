package com.pm.userauthenticationservice.repos;

import com.pm.userauthenticationservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepo extends JpaRepository<Session, Long> {
    Session save(Session session);
    Optional<Session> findByTokenAndUser_Id(String token, Long userId);
}
