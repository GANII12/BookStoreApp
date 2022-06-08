package com.example.bookstore.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResponseBookDTO {
    private String message;
    private Object data;
    private String token;

    public ResponseBookDTO(String message, Object data, String token) {
        this.message = message;
        this.data = data;
        this.token =token;
    }

    public ResponseBookDTO(String message) {
        this.message = message;
    }

    public ResponseBookDTO(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
