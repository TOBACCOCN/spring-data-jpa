package com.example.springdatajpa.repository;

import com.example.springdatajpa.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends Repository<User, Long> {

    Page<User> findAll(Pageable pageable);

    @Modifying
    // @Query(value = "update user set name = :#{#user.getName()} where id = :#{#user.getId()}", nativeQuery = true)
    @Query(value = "update User set name = :#{#user.getName()} where id = :#{#user.getId()}")
    int updateName(@Param("user") User user);

}
