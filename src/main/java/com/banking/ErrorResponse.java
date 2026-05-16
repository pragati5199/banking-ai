package com.banking;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    int status;
    String message;
    LocalDateTime timeStamp;

}
