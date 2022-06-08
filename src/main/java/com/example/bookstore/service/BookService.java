package com.example.bookstore.service;

import com.example.bookstore.DTO.BookDTO;
import com.example.bookstore.DTO.ResponseBookDTO;
import com.example.bookstore.Model.Book;
import com.example.bookstore.exceptions.BookStoreException;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.util.EmailSenderService;
import com.example.bookstore.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.prefs.BackingStoreException;

@Service
@Slf4j
public class BookService implements IBookService{
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private EmailSenderService sender;

    @Override
    public ResponseEntity<ResponseBookDTO> getBookData() {
        log.info("Fetching all Book Details...");
        List<Book> books = bookRepository.findAll();
        ResponseBookDTO respBookDTO = new ResponseBookDTO("All Book Details",books);
        return new ResponseEntity<>(respBookDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseBookDTO> getBookDataById(Optional<String> id , String token) {
        log.info("Retrieving information from the DB");
        int tokenId = tokenUtil.decodeToken(token);
        ResponseBookDTO respBookDTO;
        Optional<Book> tokenBook = bookRepository.findById(Math.toIntExact(tokenId));
        if(tokenBook.isEmpty()){
            respBookDTO = new ResponseBookDTO("Error :This is not an authorized book!",null,token);
            return new ResponseEntity<ResponseBookDTO>(respBookDTO,HttpStatus.UNAUTHORIZED);
        }
        Optional<Book> books = bookRepository.findById(Math.toIntExact(Long.parseLong(id.get())));
        Book book = books.orElse(null);

        if (books.isPresent()){
            respBookDTO = new ResponseBookDTO("Found the Book",book,null);
            return new ResponseEntity<ResponseBookDTO>(respBookDTO,HttpStatus.OK);
        }
        return null;
    }

    @Override
    public ResponseEntity<ResponseBookDTO> createBook(BookDTO bookDTO) {
        Book book = new Book(bookDTO);
        log.debug("Book Data: " +book.toString());
        bookRepository.save(book);
        String token = tokenUtil.createToken(book.getId());
        sender.sendEmail("ganeshmoturu1467@gmail.com","Test Email","Book SuccessFully");
        ResponseBookDTO respBookDTO = new ResponseBookDTO("Book created",book,token);
        return new  ResponseEntity<>(respBookDTO,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseBookDTO> updateBook(BookDTO bookDTO, String token) {
        Integer id = tokenUtil.decodeToken(token);
        Optional<Book> books = bookRepository.findById(id);
        if(books.isEmpty()){
           throw new BookStoreException("Book details for given Id is not found");
        }
        Book book = new Book(bookDTO);
        book.setId(id);
        Book book1 = bookRepository.save(book);
        ResponseBookDTO respBookDTO = new ResponseBookDTO("Book Updated",book1,token);
        return new ResponseEntity<>(respBookDTO,HttpStatus.OK);
    }

    @Override
    public Book updateQuantity(int id , int quantity) {
        Optional<Book> newBook = bookRepository.findById(id);
        if (newBook.isEmpty()){
            throw new BookStoreException("Book doesnt exists");
        }else{
            newBook.get().setQuantity(quantity);
            bookRepository.save(newBook.get());
            return newBook.get();
        }
    }

    @Override
    public List<Book> searchBookByName(String bookName) {
        return bookRepository.searchBookByName(bookName);
    }

    @Override
    public void deleteBook(String token) {
        Integer id = tokenUtil.decodeToken(token);
        Optional<Book> books = bookRepository.findById(id);
        if (books.isEmpty()){
            throw new BookStoreException("Book Details not Found");
        }else {
            bookRepository.deleteById(id);
            sender.sendEmail("ganeshmoturu1467@gmail.com","Test Email","Book Deleted SuccessFully"+token);
        }
        System.out.println(books.get());
    }

    @Override
    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

    @Override
    public List<Book> sortByAsc() {
        return bookRepository.sortByAsc();
    }

    @Override
    public List<Book> sortByDsc() {
        return bookRepository.sortByDsc();
    }
}
