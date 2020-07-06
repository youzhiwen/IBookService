package com.ibook.repository;

import com.ibook.entity.User;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByEmail(String email);
    void deleteByEmail(String email);
    List<User> findByUserName(String userName);
    User findByUserNameOrEmail(String userName, String email);
}
