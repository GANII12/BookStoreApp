package com.example.bookstore.service;

import com.example.bookstore.DTO.OrderDTO;
import com.example.bookstore.DTO.ResponseOrderDTO;
import com.example.bookstore.Model.Order;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IOrderService {
    ResponseEntity<ResponseOrderDTO> getAll();
    ResponseEntity<ResponseOrderDTO> getById(int id);
    ResponseEntity<ResponseOrderDTO> updateId(OrderDTO orderDTO,String token);
    Order addOrderItem(OrderDTO orderDTO);
    void deleteOrder(String token);
    Order cancelOrder(int orderId , int userId);
}
