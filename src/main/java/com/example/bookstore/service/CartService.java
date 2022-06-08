package com.example.bookstore.service;

import com.example.bookstore.DTO.CartDTO;
import com.example.bookstore.DTO.ResponseCartDTO;
import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.Cart;
import com.example.bookstore.Model.User;
import com.example.bookstore.exceptions.BookStoreException;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CartRepository;
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
public class CartService implements ICartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private EmailSenderService sender;

    @Override
    public ResponseEntity<ResponseCartDTO> getCartData() {
        log.info("Fetching all Book Details...");
        List<Cart> carts = cartRepository.findAll();
        ResponseCartDTO respCartDTO = new ResponseCartDTO("All Book Details",carts);
        return new ResponseEntity<>(respCartDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseCartDTO> getCartDataById(Optional<String> id, String token) {
        log.info("Retrieving information from the DB");
        int tokenId = tokenUtil.decodeToken(token);
        ResponseCartDTO responseCartDTO;
        Optional<Cart> tokenCart = cartRepository.findById(Math.toIntExact(tokenId));
        if(tokenCart.isEmpty()){
            responseCartDTO = new ResponseCartDTO("Error :This is not an authorized Cart!",null,token);
            return new ResponseEntity<ResponseCartDTO>(responseCartDTO,HttpStatus.UNAUTHORIZED);
        }
        Optional<Cart> cart = cartRepository.findById(Math.toIntExact(Long.parseLong(id.get())));
        Cart cart1 = cart.orElse(null);

        if (cart.isPresent()){
            responseCartDTO = new ResponseCartDTO("Found the Book",cart,null);
            return new ResponseEntity<ResponseCartDTO>(responseCartDTO,HttpStatus.OK);
        }
        return null;
    }

    @Override
    public Cart createCart(CartDTO cartDTO) {
        Cart cart = new Cart(cartDTO);
        log.debug("Cart Data: " +cart.toString());
        Optional<Book> book = bookRepository.findById(cartDTO.getBookId());
        Optional<User> user = userRepository.findById(cartDTO.getUserId());
        if (book.isPresent() && user.isPresent()){
            if (cartDTO.getQuantity() <= book.get().getQuantity()){
                Cart newCart = new Cart(cartDTO.getQuantity(),book.get(),user.get());
                cartRepository.save(newCart);
                log.info("Cart record inserted successfully");
                //book.get().setQuantity(book.get().getQuantity()- cartDTO.getQuantity());
                bookRepository.save(book.get());
                return newCart;
            }else {
                throw new BookStoreException("Requested Quantity is not available");
            }
        }else {
            throw new BookStoreException("Book or User doesnt exists");
        }
    }

    @Override
    public ResponseEntity<ResponseCartDTO> updateCartById( CartDTO cartDTO, String token) {
        Integer id = tokenUtil.decodeToken(token);
        Optional<Cart> cart = cartRepository.findById(id);
        if(cart.isEmpty()){
            throw new BookStoreException("Cart details for given Id is not found");
        }
        Cart cart1 = new Cart(cartDTO);
        cart1.setCartID(id);
        Cart cart2 = cartRepository.save(cart1);
        ResponseCartDTO respCartDTO = new ResponseCartDTO("Cart Updated",cart2,token);
        return new ResponseEntity<>(respCartDTO,HttpStatus.OK);
    }

    @Override
    public Cart updateQuantity(int id, int quantity) {
        Optional<Cart> newCart = cartRepository.findById(id);
        if (newCart.isEmpty()){
            throw new BookStoreException("Cart doesnt exists");
        }else{
            newCart.get().setQuantity(quantity);
            cartRepository.save(newCart.get());
            return newCart.get();
        }
    }

    @Override
    public void deleteCart(String token) {
        Integer id = tokenUtil.decodeToken(token);
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isEmpty()){
            throw new BookStoreException("Cart Details not Found");
        }else {
            cartRepository.deleteById(id);
            sender.sendEmail("ganeshmoturu1467@gmail.com","Test Email","Cart Deleted SuccessFully"+token);
        }
        System.out.println(cart.get());
    }
}
