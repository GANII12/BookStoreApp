package com.example.bookstore.service;

import com.example.bookstore.DTO.OrderDTO;
import com.example.bookstore.DTO.ResponseOrderDTO;
import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.Order;
import com.example.bookstore.Model.User;
import com.example.bookstore.exceptions.BookStoreException;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.util.EmailSenderService;
import com.example.bookstore.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService implements IOrderService{
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private EmailSenderService sender;

    @Override
    public ResponseEntity<ResponseOrderDTO> getAll() {
        log.info("Fetching all Book Details...");
        List<Order> orders = orderRepository.findAll();
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO("All Book Details",orders);
        return new ResponseEntity<>(responseOrderDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseOrderDTO> getById(int id) {
        log.info("Retrieving information from the DB");
//        int tokenId = tokenUtil.decodeToken(token);
        ResponseOrderDTO responseOrderDTO;
        Optional<Order> tokenOrder = orderRepository.findById(id);
        if(tokenOrder.isEmpty()){
            responseOrderDTO = new ResponseOrderDTO("Error :This is not an authorized Order!",null);
            return new ResponseEntity<ResponseOrderDTO>(responseOrderDTO,HttpStatus.UNAUTHORIZED);
        }
        Optional<Order> order = orderRepository.findById(id);
        Order order1 = order.orElse(null);

        if (order.isPresent()){
            responseOrderDTO = new ResponseOrderDTO("Found the Book",order1,null);
            return new ResponseEntity<ResponseOrderDTO>(responseOrderDTO,HttpStatus.OK);
        }
        return null;
    }

    @Override
    public ResponseEntity<ResponseOrderDTO> updateId(OrderDTO orderDTO, String token) {
        Integer id = tokenUtil.decodeToken(token);
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()){
            throw new BookStoreException("Order details for given Id is not found");
        }
        Order order1 = new Order(orderDTO);
        order1.setOrderID(id);
        Order order2 = orderRepository.save(order1);
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO("Order Updated",order2,token);
        return new ResponseEntity<>(responseOrderDTO,HttpStatus.OK);
    }

    @Override
    public Order addOrderItem(OrderDTO orderDTO) {
        Optional<Book> book = bookRepository.findById(orderDTO.getBookId());
        Optional<User> user = userRepository.findById(orderDTO.getUserId());
        if (book.isPresent() && user.isPresent()){
            if (orderDTO.getQuantity() <=book.get().getQuantity()){
                Order newOrder = new Order(orderDTO.price,orderDTO.quantity,orderDTO.address,book.get(),user.get());
                orderRepository.save(newOrder);
                log.info("Order record inserted Successfully");
                book.get().setQuantity(book.get().getQuantity()-orderDTO.getQuantity());
                return newOrder;
            }else {
                throw new BookStoreException("Requested quantity is not available");
            }
        }else {
            throw new BookStoreException("Book or User Doesnt exists");
        }

    }

    @Override
    public void deleteOrder(String token) {
        Integer id = tokenUtil.decodeToken(token);
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()){
            throw new BookStoreException("Cart Details not Found");
        }else {
            orderRepository.deleteById(id);
            sender.sendEmail("ganeshmoturu1467@gmail.com","Test Email","Order Deleted SuccessFully"+token);
        }
        System.out.println(order.get());
    }

    @Override
    public Order cancelOrder(int orderId , int userId) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.get().setCancel(true);
        return  orderRepository.save(order.get());
    }
}
