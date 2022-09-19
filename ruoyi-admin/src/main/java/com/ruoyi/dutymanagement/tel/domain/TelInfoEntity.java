package com.ruoyi.dutymanagement.tel.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 电话记录实体
 *
 * @Author fenghan
 */
@Entity
@Table(name = "tel_info", schema = "rdms")
@Data
public class TelInfoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    @Column(name = "tel_record_id")
    private int telRecordId;
    @Basic
    @Column(name = "the_electricity_unit")
    private String theElectricityUnit;
    @Basic
    @Column(name = "tel")
    private String tel;
    @Basic
    @Column(name = "user_name")
    private String userName;
    @Basic
    @Column(name = "tel_time")
    private Date telTime;
    @Basic
    @Column(name = "answer_people")
    private String answerPeople;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "suggestion")
    private String suggestion;
    @Basic
    @Column(name = "create_time")
    private Date createTime;
    @Basic
    @Column(name = "create_by")
    private String createBy;
    @Basic
    @Column(name = "update_by")
    private String updateBy;
    @Basic
    @Column(name = "update_time")
    private Date updateTime;
    @Basic
    @Column(name = "remark")
    private String remark;
    @Basic
    @Column(name = "status")
    private String status;
}
