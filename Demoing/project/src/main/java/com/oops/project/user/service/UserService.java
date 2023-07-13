package com.oops.project.user.service;

import com.oops.project.user.model.User;

import java.util.List;

public interface UserService {
    public void saveUser(User user);
    public List<Object> isUserPresent(User user);
}
