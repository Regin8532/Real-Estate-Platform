package com.example.realestate.service;

import com.example.realestate.entity.User;

public interface UserService {
    User register(User user);
    User getByEmail(String email);
}
