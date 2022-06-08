package com.example.bookstore.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResponseCartDTO {
    private String message;
    private Object data;
    private String token;

    public ResponseCartDTO(String message, Object data, String token) {
        this.message = message;
        this.data = data;
        this.token =token;
    }

    public ResponseCartDTO(String message) {
        this.message = message;
    }

    public ResponseCartDTO(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
