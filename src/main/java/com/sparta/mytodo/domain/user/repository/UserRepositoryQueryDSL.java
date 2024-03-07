package com.sparta.mytodo.domain.user.repository;

import com.sparta.mytodo.domain.user.entity.User;

public interface UserRepositoryQueryDSL {

    User findByUserName(String userName);
}
