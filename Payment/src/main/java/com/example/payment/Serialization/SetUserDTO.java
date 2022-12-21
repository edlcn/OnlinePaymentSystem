package com.example.payment.Serialization;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetUserDTO {

    private Long uid;
    private String token;

}
