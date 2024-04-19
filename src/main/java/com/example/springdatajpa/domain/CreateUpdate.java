package com.example.springdatajpa.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Data
@NamedQueries(value = {@NamedQuery(name = "CreateUpdate.byCreateTime", query = "select cu from CreateUpdate cu where createTime = :time"),
    @NamedQuery(name = "CreateUpdate.byUpdateTime", query = "select cu from CreateUpdate cu where updateTime = :time")})
@NamedStoredProcedureQuery(name = "User.plus1", procedureName = "plus1inout", parameters = {@StoredProcedureParameter(mode = ParameterMode.IN, name = "arg", type = Integer.class),
    @StoredProcedureParameter(mode = ParameterMode.OUT, name = "res", type = Integer.class)})
public class CreateUpdate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String creater;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private String updater;

    private Date updateTime;

    private transient String test;

}
