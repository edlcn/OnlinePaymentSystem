package com.example.payment.Serialization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralHttpResponse<T> {
    private String status;
    private T returnObject;  // for GET calls.


}

