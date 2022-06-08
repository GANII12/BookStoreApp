package com.example.bookstore.repository;

import com.example.bookstore.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT * FROM Book_db ORDER BY Book_Name asc ",nativeQuery = true)
    List<Book> sortByAsc();
    @Query(value = "SELECT * FROM Book_db ORDER BY Book_Name desc",nativeQuery = true)
    List<Book> sortByDsc();

    @Query(value = "SELECT * FROM Book_db where Book_Name is bookName ",nativeQuery = true)
    List<Book> searchBookByName(String bookName);
}
