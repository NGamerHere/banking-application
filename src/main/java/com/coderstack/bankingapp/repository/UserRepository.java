package com.coderstack.bankingapp.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;
import com.coderstack.bankingapp.Entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

     Optional<User> findByEmail(String email);
}
