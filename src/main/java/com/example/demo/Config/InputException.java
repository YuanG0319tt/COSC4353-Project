package com.example.demo.Config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputException extends RuntimeException{
    Integer errorCode;
    String errorMessage;
}
