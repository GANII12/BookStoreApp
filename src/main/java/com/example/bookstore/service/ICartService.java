package com.example.bookstore.service;

import com.example.bookstore.DTO.CartDTO;
import com.example.bookstore.DTO.ResponseCartDTO;
import com.example.bookstore.Model.Cart;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ICartService {
    ResponseEntity<ResponseCartDTO> getCartData();
    ResponseEntity<ResponseCartDTO> getCartDataById(Optional<String> id, String token);
    Cart createCart(CartDTO cartDTO);
    ResponseEntity<ResponseCartDTO> updateCartById( CartDTO cartDTO, String token );
    Cart updateQuantity(int id , int quantity);
    void deleteCart(String token);
}
