package com.example.sessionid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionIdDAO extends JpaRepository<SessionId,Long> {
    SessionId findSessionIdById(Long id);
}
