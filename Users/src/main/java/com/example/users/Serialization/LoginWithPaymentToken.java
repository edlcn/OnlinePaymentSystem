package com.example.users.Serialization;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginWithPaymentToken {
    private String mail;
    private String password;
    private String token;
}
