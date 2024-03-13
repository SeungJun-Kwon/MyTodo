package com.sparta.mytodo.domain.user.repository;

import static com.sparta.mytodo.domain.user.entity.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.mytodo.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public User findByUserName(String userName) {
        return queryFactory.selectFrom(user).where(user.userName.eq(userName)).fetchFirst();
    }
}
