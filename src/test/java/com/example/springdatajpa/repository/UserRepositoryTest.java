package com.example.springdatajpa.repository;

import com.example.springdatajpa.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@Slf4j
class UserRepositoryTest {

    @Resource
    private UserRepository userRepository;

    @Test
    void findAll() {
        Pageable pageable = Pageable.ofSize(10).withPage(1);
        List<User> users = userRepository.findAll(pageable).getContent();
        log.info(">>>>> USERS: [{}]", users);
    }

    @Test
    @Transactional
    @Rollback(false)
    void updateName() {
        User user = new User();
        user.setId(0L);
        user.setName("1");
        int update = userRepository.updateName(user);
        log.info(">>>>> UPDATE: [{}]", update);
    }

}