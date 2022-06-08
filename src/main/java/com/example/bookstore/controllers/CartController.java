package com.example.bookstore.controllers;

import com.example.bookstore.DTO.BookDTO;
import com.example.bookstore.DTO.CartDTO;
import com.example.bookstore.DTO.ResponseBookDTO;
import com.example.bookstore.DTO.ResponseCartDTO;
import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.Cart;
import com.example.bookstore.exceptions.BookStoreException;
import com.example.bookstore.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("Cart")
@Slf4j
public class CartController {
    @Autowired
    private ICartService cartService;

    //get all cart data
    @GetMapping(value = {"","/","/get"})
    public ResponseEntity<ResponseCartDTO> getCartData(){
        return cartService.getCartData();
    }

    //get cart by id
    @GetMapping("/get/{token}")
    public ResponseEntity<ResponseCartDTO> getCartById(@PathVariable Optional<String> id , @RequestParam(name = "token") String token){
        return cartService.getCartDataById(id, token);
    }

    //create cart
    @PostMapping("/create")
    public Cart addCart(@Valid @RequestBody CartDTO cartDTO){
        return cartService.createCart(cartDTO);
    }

    //update cart
    @PutMapping("/update/{token}")
    public ResponseEntity<ResponseCartDTO> updateBook(@Valid @RequestBody CartDTO cartDTO , @RequestParam(name = "token") String token){
        return cartService.updateCartById(cartDTO, token);
    }

    //update quantity of cart
    @PutMapping("/updateQuantity/{id}")
    public ResponseEntity<ResponseCartDTO> updateQuantity(@PathVariable int id ,@RequestParam int quantity){
        Cart newCart = cartService.updateQuantity(id,quantity);
        ResponseCartDTO responseCartDTO =new ResponseCartDTO("Quantity of Cart Updated",newCart);
        return new ResponseEntity<>(responseCartDTO, HttpStatus.OK);
    }

    //delete cart by Id
    @DeleteMapping("/delete/{token}")
    public ResponseEntity<ResponseCartDTO> deleteCartById(@PathVariable String token) throws BookStoreException {
        cartService.deleteCart(token);
        ResponseCartDTO responseCartDTO = new ResponseCartDTO("Deleted Successfully" , "Deleted Id :",token);
        return new ResponseEntity<ResponseCartDTO>(responseCartDTO, HttpStatus.OK);
    }
}
