package com.example.bookstore.controllers;

import com.example.bookstore.DTO.BookDTO;
import com.example.bookstore.DTO.ResponseBookDTO;
import com.example.bookstore.Model.Book;
import com.example.bookstore.exceptions.BookStoreException;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("Book")
@Slf4j
public class BookController {
    @Autowired
    private IBookService bookService;


    //get all books
    @GetMapping(value = {"","/","/get"})
    public ResponseEntity<ResponseBookDTO> getBookData(){
        return bookService.getBookData();
    }

    //get book by Id
    @GetMapping("/get/{token}")
    public ResponseEntity<ResponseBookDTO> getBookById(@PathVariable Optional<String> id , @RequestParam(name = "token") String token){
        return bookService.getBookDataById(id, token);
    }

    //create book
    @PostMapping("/create")
    public ResponseEntity<ResponseBookDTO> addBook(@Valid @RequestBody BookDTO bookDTO){
        return bookService.createBook(bookDTO);
    }

    //update book by Id
    @PutMapping("/update/{token}")
    public ResponseEntity<ResponseBookDTO> updateBook(@Valid @RequestBody BookDTO bookDTO , @RequestParam(name = "token") String token){
        return bookService.updateBook(bookDTO, token);
    }

    //update quantity
    @PutMapping("/updateQuantity/{id}")
    public ResponseEntity<ResponseBookDTO> updateQuantity(@PathVariable int id ,@RequestParam int quantity){
        Book newBook = bookService.updateQuantity(id,quantity);
        ResponseBookDTO responseBookDTO =new ResponseBookDTO("Quantity of Book Updated",newBook);
        return new ResponseEntity<>(responseBookDTO,HttpStatus.OK);
    }

    //delete book
    @DeleteMapping("/delete/{token}")
    public ResponseEntity<ResponseBookDTO> deleteBookById(@PathVariable String token) throws BookStoreException{
        bookService.deleteBook(token);
        ResponseBookDTO responseBookDTO = new ResponseBookDTO("Deleted Successfully" , "Deleted Id :",token);
        return new ResponseEntity<ResponseBookDTO>(responseBookDTO, HttpStatus.OK);
    }

    //delete all books
    @DeleteMapping("/deleteAll")
    public ResponseEntity<ResponseBookDTO> deleteAllBooks(){
        bookService.deleteAllBooks();
        ResponseBookDTO responseBookDTO = new ResponseBookDTO("All Books Deleted Successfully");
        return new ResponseEntity<ResponseBookDTO>(responseBookDTO,HttpStatus.OK);
    }

    //sorting books by ascending order
    @GetMapping("/sortByAsc")
    public ResponseEntity<ResponseBookDTO> sortByAsc(){
        List<Book> bookList = null;
        bookList =bookService.sortByAsc();
        ResponseBookDTO responseBookDTO = new ResponseBookDTO("All Books are sorted by Ascending order",bookList);
        return new ResponseEntity<ResponseBookDTO>(responseBookDTO,HttpStatus.OK);
    }

    //sorting books by descending order
    @GetMapping("/sortByDsc")
    public ResponseEntity<ResponseBookDTO> sortByDsc(){
        List<Book> bookList = null;
        bookList =bookService.sortByDsc();
        ResponseBookDTO responseBookDTO = new ResponseBookDTO("All Books are sorted by Descending order",bookList);
        return new ResponseEntity<ResponseBookDTO>(responseBookDTO,HttpStatus.OK);
    }

    //search book by name
    @GetMapping("/searchByName/{bookName}")
    public ResponseEntity<ResponseBookDTO> searchBookByName(@PathVariable String  bookName){
        List<Book> books = null;
        books = bookService.searchBookByName(bookName);
        ResponseBookDTO responseBookDTO = new ResponseBookDTO(" Book Founded!!!",books);
        return new ResponseEntity<ResponseBookDTO>(responseBookDTO,HttpStatus.OK);
    }
}
