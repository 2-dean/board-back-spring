package com.board.service.impl;

import com.board.domain.User;
import com.board.mapper.UserMapper;
import com.board.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Optional<User> login(User user) {
        return Optional.ofNullable(userMapper.findUser(user));
    }
}
