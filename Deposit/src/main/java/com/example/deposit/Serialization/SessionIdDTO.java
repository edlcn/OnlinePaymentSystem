package com.example.deposit.Serialization;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionIdDTO {

    private Long id;
    private String sessionId;
}
