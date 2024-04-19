package com.example.springdatajpa.repository;

import com.example.springdatajpa.domain.CreateUpdate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
class CreateUpdateRepositoryTest {

    @Resource
    private CreateUpdateRepository createUpdateRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void findAll() {
        // Pageable pageable = Pageable.ofSize(10).withPage(0);    // page 从 0 开始
        Sort.TypedSort<CreateUpdate> typedSort = Sort.TypedSort.sort(CreateUpdate.class);
        Sort sort = typedSort.by(CreateUpdate::getCreater).descending()
                .and(typedSort.by(CreateUpdate::getId).descending());
        Pageable pageable = PageRequest.of(0, 10, sort);
        List<CreateUpdate> createUpdates = createUpdateRepository.findAll(pageable).getContent();
        log.info(">>>>> CREATE_UPDATES: [{}]", createUpdates);

        // Example 用法
        // CreateUpdate createUpdate = new CreateUpdate();
        // createUpdate.setId(103L);
        // List<CreateUpdate> list = createUpdateRepository.findAll(Example.of(createUpdate));
        // log.info(">>>>> LIST: [{}]", list);

        // ExampleMatcher 用法
        CreateUpdate createUpdate = new CreateUpdate();
        createUpdate.setCreater("1");
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("creater", ExampleMatcher.GenericPropertyMatchers.endsWith());
        List<CreateUpdate> list = createUpdateRepository.findAll(Example.of(createUpdate, matcher));
        log.info(">>>>> LIST: [{}]", list);

        // Specification(CriteriaQuery, CriteriaBuilder, Predicate)
        List<CreateUpdate> createUpdateList = createUpdateRepository.findAll((Specification<CreateUpdate>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("creater"), "creater_1");
            Predicate predicate1 = criteriaBuilder.isNull(root.get("updateTime"));
            Predicate predicate2 = criteriaBuilder.greaterThan(root.get("id"), 0);
            return criteriaBuilder.and(criteriaBuilder.and(criteriaBuilder.or(predicate, predicate1), predicate2));
        });
        log.info(">>>>> CREATE_UPDATE_LIST: [{}]", createUpdateList);
    }

    @Test
    public void findByCreater() {
        List<CreateUpdate> list = createUpdateRepository.findByCreater("creater_3");
        log.info(">>>>> LIST: [{}]", list);
    }

    @Test
    public void byCreater() {
        List<CreateUpdate> list = createUpdateRepository.byCreater("creater_2", "id");
        log.info(">>>>> LIST: [{}]", list);
    }

    @Test
    public void byCreaterThenSort() {
        List<CreateUpdate> list = createUpdateRepository.byCreaterThenSort("creater_1", Sort.by(Sort.Direction.DESC, "id"));
        log.info(">>>>> LIST: [{}]", list);
    }

    @Test
    public void byCreaterPageable() {
        List<CreateUpdate> list = createUpdateRepository.byCreaterPageable("creater_1", Pageable.ofSize(2).withPage(2));
        log.info(">>>>> LIST: [{}]", list);
    }

    @Test
    @Rollback(false)
    public void plus1() {
        // Integer res = createUpdateRepository.plus1(100);
        // Integer res = createUpdateRepository.plus1inout(100);
        Integer res = createUpdateRepository.plus1ByName(100);  // not work
        log.info(">>>>> RES: [{}]", res);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void updateCreaterById() {
        int update = createUpdateRepository.updateCreaterById("creater_11", 103L);
        log.info(">>>>>> UPDATE: [{}]", update);
    }

    @Test
    public void findByCreateTimeAfter() {
        String date = "2023-01-15 21:22:51";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime zonedDateTime = LocalDateTime.parse(date, formatter).atZone(ZoneId.systemDefault());
        List<CreateUpdate> list = createUpdateRepository.findByCreateTimeAfter(Date.from(zonedDateTime.toInstant()));
        log.info(">>>>> LIST: [{}]", list);
    }

    @Test
    public void byCreateTime() {
        String date = "2023-01-15 21:22:51";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime zonedDateTime = LocalDateTime.parse(date, formatter).atZone(ZoneId.systemDefault());
        List<CreateUpdate> list = createUpdateRepository.byCreateTime(Date.from(zonedDateTime.toInstant()));
        log.info(">>>>> LIST: [{}]", list);
    }

    @Test
    void insert() {
        CreateUpdate createUpdate = new CreateUpdate();
        createUpdate.setCreater("creater_1");
        createUpdate.setUpdater("updater_1");
        createUpdate.setId(102L);
        createUpdateRepository.save(createUpdate);  // save：没有主键时根据规则设置新主键值插入数据，有主键时会根据主键查询数据库是否存在对应数据，不存在时插入数据，存在时更新数据
    }

    @Test
    void update() {
        Optional<CreateUpdate> optional = createUpdateRepository.findById(106L);
        optional.ifPresent(e -> {
            e.setCreater("CREATer_11");
            createUpdateRepository.save(e);
        });
    }

    @Test
    void delete() {
        CreateUpdate createUpdate = new CreateUpdate();
        createUpdate.setId(102L);
        createUpdateRepository.delete(createUpdate);
    }

}