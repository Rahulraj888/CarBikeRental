package com.groupb.rental.dao;

import com.groupb.rental.model.User;

public interface UserDAOInterface {
    boolean registerUser(User user);
    User login(String username, String password);
    User getUserById(int id);
}
