package com.example.bookstore.repository;

import com.example.bookstore.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM User where Email=:email",nativeQuery = true)
    public Optional<User> findByEmailId(String email);

}
