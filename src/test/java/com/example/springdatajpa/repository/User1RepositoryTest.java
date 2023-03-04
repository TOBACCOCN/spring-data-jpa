package com.example.springdatajpa.repository;

import com.example.springdatajpa.domain.Gender;
import com.example.springdatajpa.domain.NameAddressKey;
import com.example.springdatajpa.domain.User1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
class User1RepositoryTest {

    @Resource
    private User1Repository user1Repository;

    @Test
    public void findById() {
        Optional<User1> optional = user1Repository.findById(new NameAddressKey("1", "1"));
        optional.ifPresent(e-> log.info(">>>>> USER1: [{}]", e));
    }

    @Test
    public void findByGender() {
        List<User1> list = user1Repository.findByGender(Gender.FEMALE);
        log.info(">>>>> LIST: [{}]", list);
    }

}