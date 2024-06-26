package com.example.springdatajpa.repository;

import com.example.springdatajpa.domain.CreateUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CreateUpdateRepository extends JpaRepository<CreateUpdate, Long>, JpaSpecificationExecutor<CreateUpdate> {

    // generated by jpa buddy
    @Query("select distinct c from CreateUpdate c where upper(c.updateTime) between ?1 and ?2 order by c.updateTime DESC")
    List<CreateUpdate> findDistinctByUpdateTimeBetweenAllIgnoreCaseOrderByUpdateTimeDesc(Date updateTimeStart, Date updateTimeEnd, Pageable pageable);
    List<CreateUpdate> findByUpdaterIgnoreCaseOrderByUpdaterDesc(String updater);
    // generated by jpa buddy

    // @Query(value = "select * from create_update where creater = ?1 order by ?2", nativeQuery = true)
    @Query(value = "select * from create_update where creater = :creater order by :sort", nativeQuery = true)
    List<CreateUpdate> byCreater(@Param("creater") String creater, @Param("sort") String sort);

    @Modifying
    // @Query("update CreateUpdate cu set cu.creater = ?1 where cu.id = ?2")
    @Query("update CreateUpdate cu set cu.creater = :creater where cu.id = :id")
    int updateCreaterById(@Param("creater") String creater, @Param("id") Long id);

    @Query(value = "select cu from CreateUpdate cu where cu.creater = ?1")
    // @Query(value = "select * from create_update cu where cu.creater = ?1", nativeQuery = true)   // not work
    List<CreateUpdate> byCreaterThenSort(String creater, Sort sort);

    @Query(value = "select cu from CreateUpdate cu where cu.creater = ?1")
    // @Query(value = "select * from create_update cu where cu.creater = ?1", nativeQuery = true)
    List<CreateUpdate> byCreaterPageable(String creater, Pageable pageable);

    // byCreaterThenSort 与 byCreaterPageable 方法说明，参数中的 Sort 在 HQL 中才有效，Pageable 在 HQL 和 nativeQuery 中都有效

    // @Procedure("plus1inout")
    @Procedure(procedureName = "plus1inout")
    Integer plus1(Integer arg);

    @Procedure
    Integer plus1inout(Integer arg);

    @Procedure(name = "User.plus1")
    Integer plus1ByName(@Param("arg") Integer arg);

    Page<CreateUpdate> findAll(Pageable pageable);

    List<CreateUpdate> findByCreater(String creater);
    List<CreateUpdate> findByCreateTimeAfter(Date createTime);

    List<CreateUpdate> byCreateTime(Date time);
    List<CreateUpdate> byUpdateTime(Date time);

}
