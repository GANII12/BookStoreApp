package com.example.bookstore.Model;

import com.example.bookstore.DTO.OrderDTO;
import lombok.Data;
import lombok.ToString;
import org.hibernate.query.criteria.internal.expression.function.CurrentDateFunction;

import javax.persistence.*;
import java.time.LocalDate;
@ToString
@Data
@Entity
@Table(name = "Order_db")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OrderId")
    private int orderID;
    private LocalDate date;
    private int price;
    private int quantity;
    private String address;
    @ManyToOne
    @JoinColumn(name = "User_Id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "Book_Id")
    private Book book;
    private boolean cancel;

    public Order(OrderDTO orderDTO) {
    }


    public void updateOrder(int price ,int quantity ,String address , Book book, User user ){
        this.date = LocalDate.now();
        this.price = price;
        this.quantity = quantity;
        this.address = address;
        this.user = user;
        this.book = book;

    }
    public Order(int price ,int quantity ,String address , Book book, User user) {
        this.updateOrder(price,quantity,address,book,user);
    }
    public Order() {
    }

}
