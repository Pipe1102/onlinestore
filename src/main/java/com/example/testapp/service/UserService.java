package com.example.testapp.service;

import com.example.testapp.entity.User;
import com.example.testapp.exception.EmailExistException;
import com.example.testapp.exception.UserNotFoundException;
import com.example.testapp.exception.UsernameExistException;

import java.util.List;

public interface UserService {

    User register(String firstName,String lastname,String password,String username,String email) throws UserNotFoundException, EmailExistException, UsernameExistException;

    List<User> getUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);


}
