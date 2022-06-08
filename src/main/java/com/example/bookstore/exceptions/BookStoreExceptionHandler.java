package com.example.bookstore.exceptions;

import com.example.bookstore.DTO.ResponseBookDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class BookStoreExceptionHandler {
    private static final String message = "Exception while processing REST Request";
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseBookDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        List<String> errMsg = errorList.stream()
                .map(objErr -> objErr.getDefaultMessage())
                .collect(Collectors.toList());
        ResponseBookDTO responseDTO = new ResponseBookDTO(message , errMsg);
        return new ResponseEntity<ResponseBookDTO>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookStoreException.class)
    public ResponseEntity<ResponseBookDTO> handleEmployeePayrollException(BookStoreException exception){
        ResponseBookDTO responseDTO = new ResponseBookDTO(message, exception.getMessage());
        return new ResponseEntity<ResponseBookDTO>(responseDTO , HttpStatus.BAD_REQUEST);
    }
}
