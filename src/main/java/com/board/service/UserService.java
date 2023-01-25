package com.board.service;

import com.board.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> login(User user);

}
