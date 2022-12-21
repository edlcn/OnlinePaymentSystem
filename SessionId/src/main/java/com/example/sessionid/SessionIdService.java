package com.example.sessionid;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class SessionIdService {

    private final SessionIdDAO sessionIdDAO;

    public SessionIdService(SessionIdDAO sessionIdDAO) {
        this.sessionIdDAO = sessionIdDAO;
    }

    public SessionIdDTO createSessionId(Long uid) {
        if (sessionIdDAO.existsById(uid)) throw new IllegalStateException("USER ALREADY LOGGED IN!");
        SessionId sessionId = new SessionId(uid);
        sessionIdDAO.save(sessionId);
        return new SessionIdDTO(sessionId);
    }

    // Erroru burda mı dönmeli yoksa okey mi?
    // todo: error kodlarını sayılarla ayarla.
    @Transactional
    public Integer sessionIdValidityCheck(SessionIdDTO sessionId) {
        SessionId sessionIdDb = sessionIdDAO.findSessionIdById(sessionId.getId());
        if (sessionIdDb == null) return 0;
        if (!sessionIdDb.getSessionId().equals(sessionId.getSessionId())) return 0;
        Date currentTime = new Date(System.currentTimeMillis());
        if (currentTime.compareTo(sessionIdDb.getExpiryDate()) > 0 ) {
            System.out.print(currentTime.compareTo(sessionIdDb.getExpiryDate()));
            sessionIdDAO.delete(sessionIdDb);
            return -1;
        }
        return 1;
    }

    @Transactional
    public Integer deleteSession(SessionIdDTO sessionId) {
        if (!sessionIdDAO.existsById(sessionId.getId())) return 0; //validity check yapıldığı için kontrole gerek yok.
        sessionIdDAO.deleteById(sessionId.getId());
        return 1;
    }
}
