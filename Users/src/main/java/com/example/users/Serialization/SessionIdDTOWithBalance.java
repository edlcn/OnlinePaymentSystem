package com.example.users.Serialization;

import com.example.users.Serialization.SessionIdDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SessionIdDTOWithBalance {

    private String sessionId;
    private Long id;
    private Double balance;

    public SessionIdDTOWithBalance(SessionIdDTO session,Double balance){
        this.sessionId = session.getSessionId();
        this.id = session.getId();
        this.balance = balance;
    }
}
