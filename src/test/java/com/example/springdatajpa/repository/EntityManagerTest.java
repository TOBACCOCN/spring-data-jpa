package com.example.springdatajpa.repository;

import com.example.springdatajpa.domain.CreateUpdate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

@SpringBootTest
@Slf4j
public class EntityManagerTest {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    @Rollback(false)
    public void persist() {
        CreateUpdate createUpdate = new CreateUpdate();
        createUpdate.setCreater("creater_1");
        createUpdate.setUpdater("updater_1");
        // createUpdate.setId(10L);    // persist，不能指定 id，否则抛异常
        entityManager.persist(createUpdate);
        createUpdate.setUpdater("uuu"); // 插入数据时，该字段会保存最新值 uuu
        log.info(">>>>> createUpdate: [{}]", createUpdate);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void merge() {
        CreateUpdate createUpdate = new CreateUpdate();
        createUpdate.setCreater("creater_1");
        createUpdate.setUpdater("updater_1");

        // merge 可以指定 id；没有主键时插入，有主键时会从数据库根据主键查询，存在数据的话执行更新（不同于 Repository 里面的根据实体类的 DynamicUpdate 注解按需更新，这里是全量更新），
        // 不存在的话执行插入（但主键使用指定的策略生成，而不是 setId 的值）

        // 1.不设置主键，插入数据

        // 2.设置主键但是数据库不存在对应的数据，执行插入
        // createUpdate.setId(10L);

        // 3.设置主键数据库存在对应的数据，执行更新
        // createUpdate.setCreateTime(new Date());
        // createUpdate.setUpdateTime(new Date());
        // createUpdate.setId(103L);

        CreateUpdate createUpdate1 = entityManager.merge(createUpdate);
        entityManager.flush();      // 触发执行 sql
        createUpdate1.setUpdater("ccc");
        log.info(">>>>> createUpdate1: [{}]", createUpdate1);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void remove() {
        CreateUpdate createUpdate = new CreateUpdate();
        // 1.没有主键时不执行删除操作
        // 2.该主键在数据库没有对应数据时，报错 java.lang.IllegalArgumentException: Removing a detached instance
        // createUpdate.setId(10L);
        // 3.该主键在数据库有对应数据时执行删除操作
        createUpdate = entityManager.find(CreateUpdate.class, 103L);
         entityManager.remove(createUpdate);
    }

    @Test
    public void find() {
        CreateUpdate createUpdate = entityManager.find(CreateUpdate.class, 103L);
        log.info(">>>>> createUpdate: [{}]", createUpdate);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void getReference() {
        // getReference 是懒加载的，如果不加事务，执行完方法 getReference，entityManager就关闭了，后面用到查询到的 createUpdate 时会触发查询 sql，由于 entityManager 关闭会报错
        // 但是不知道问什么不输出查询的 sql
        CreateUpdate createUpdate = entityManager.getReference(CreateUpdate.class, 106L);
        log.info(">>>>> --------");
        log.info(">>>>> createUpdate: [{}]", createUpdate);
        log.info(">>>>> updater: [{}]", createUpdate.getUpdater());
    }

    @Test
    public void isOpen() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // entityManager.close();
        boolean open = entityManager.isOpen();
        log.info(">>>>> open: [{}]", open);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void flush() {
        CreateUpdate createUpdate = entityManager.find(CreateUpdate.class, 103L);
        createUpdate.setCreater("test");
        entityManager.flush();      // flush: 刷新 entityManager管理的实体到数据库，会执行 sql
        log.info(">>>>>");
    }

    @Test
    @Transactional
    public void refresh() {
        CreateUpdate createUpdate = entityManager.find(CreateUpdate.class, 103L);
        entityManager.refresh(createUpdate);      // refresh: 同步数据库数据到实例（测试时在find后refresh前手动更改数据库中数据）
        log.info(">>>>> createUpdate: [{}]", createUpdate);
    }

}
