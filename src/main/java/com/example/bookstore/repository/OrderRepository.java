package com.example.bookstore.repository;

import com.example.bookstore.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT * FROM order_db where order_id is id",nativeQuery = true)
    void findById(Optional<Order> id);
}
