package com.example.sessionid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionIdDTO {

    private Long id;
    private String sessionId;

    public SessionIdDTO(SessionId sessionId){
        this.id = sessionId.getId();
        this.sessionId = sessionId.getSessionId();
    }

}
