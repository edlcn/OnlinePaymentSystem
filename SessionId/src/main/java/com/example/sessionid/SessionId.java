package com.example.sessionid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Random;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SessionId {

    @Id
    private Long id;

    private String sessionId;

    private Date expiryDate;

    public SessionId(Long id){
        this.id = id;
        this.sessionId = generateSessionId();
        this.expiryDate = new Date(System.currentTimeMillis() + 1000 * 60 * 5);
    }



    private String generateSessionId(){
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890?*";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 21;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return String.valueOf(sb);
    }


}
