package com.coderstack.bankingapp.repository;

import org.springframework.data.repository.CrudRepository;
import com.coderstack.bankingapp.Entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
