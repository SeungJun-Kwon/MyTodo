package com.sparta.mytodo.domain.user.repository;

import com.sparta.mytodo.domain.user.entity.User;

public interface UserRepositoryCustom {

    User findByUserName(String userName);
}
