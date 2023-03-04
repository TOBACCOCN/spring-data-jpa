package com.example.springdatajpa.web;

import com.example.springdatajpa.domain.CreateUpdate;
import com.example.springdatajpa.repository.CreateUpdateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DefaultController {

    private final CreateUpdateRepository createUpdateRepository;

    @PostMapping("/add")
    public void add(@RequestBody CreateUpdate createUpdate) {
        CreateUpdate save = createUpdateRepository.save(createUpdate);
        log.info(">>>>> SAVE: [{}]", save);
    }

}
