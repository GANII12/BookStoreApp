package com.example.bookstore.controllers;

import com.example.bookstore.DTO.OrderDTO;
import com.example.bookstore.DTO.ResponseOrderDTO;
import com.example.bookstore.Model.Cart;
import com.example.bookstore.Model.Order;
import com.example.bookstore.exceptions.BookStoreException;
import com.example.bookstore.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("Order")
@Slf4j
public class OrderController {
    @Autowired
    private IOrderService orderService;

    //get all orders
    @GetMapping(value = {"","/","/get"})
    public ResponseEntity<ResponseOrderDTO> getAll(){
        return orderService.getAll();
    }

    //get order by id
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseOrderDTO> getById(@PathVariable int id ){
        return orderService.getById(id);
    }

    //create order
    @PostMapping("/create")
    public ResponseEntity<ResponseOrderDTO> addCart(@Valid @RequestBody OrderDTO orderDTO){
        Order order =  orderService.addOrderItem(orderDTO);
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO("Added Successfully" ,order);
        return new ResponseEntity<ResponseOrderDTO>(responseOrderDTO, HttpStatus.OK);
    }

    //update order by id
    @PutMapping("/update/{token}")
    public ResponseEntity<ResponseOrderDTO> updateOrder(@Valid @RequestBody OrderDTO orderDTO , @RequestParam(name = "token") String token){
        return orderService.updateId(orderDTO, token);
    }

    //delete order
    @DeleteMapping("/delete/{token}")
    public ResponseEntity<ResponseOrderDTO> deleteById(@PathVariable String token) throws BookStoreException {
        orderService.deleteOrder(token);
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO("Deleted Successfully" , "Deleted Id :",token);
        return new ResponseEntity<ResponseOrderDTO>(responseOrderDTO, HttpStatus.OK);
    }

    //cancel order
    @GetMapping("/cancel/{orderId}")
    public ResponseEntity<ResponseOrderDTO> cancelOrder(@PathVariable int orderId , int userId){
        Order order =orderService.cancelOrder(orderId,userId);
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO("Cancelled Successfully",order);
        return new ResponseEntity<ResponseOrderDTO>(responseOrderDTO, HttpStatus.OK);
    }
}
