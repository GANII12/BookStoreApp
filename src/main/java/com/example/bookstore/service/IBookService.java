package com.example.bookstore.service;

import com.example.bookstore.DTO.BookDTO;
import com.example.bookstore.DTO.ResponseBookDTO;
import com.example.bookstore.Model.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    ResponseEntity<ResponseBookDTO> getBookData();
    ResponseEntity<ResponseBookDTO> getBookDataById(Optional<String> id, String token);
    ResponseEntity<ResponseBookDTO> createBook(BookDTO bookDTO);
    ResponseEntity<ResponseBookDTO> updateBook(BookDTO bookDTO, String token);
    Book updateQuantity(int id ,int quantity);
    List<Book> searchBookByName(String bookName);
    void deleteBook(String token);
    void deleteAllBooks();
    List<Book> sortByAsc();
    List<Book> sortByDsc();
}
