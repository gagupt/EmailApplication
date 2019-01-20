package com.example.email.services;

import com.example.email.model.User;

public interface UserService {
  User getUser(String emailId);

  boolean verifyCredential(User user);
}
