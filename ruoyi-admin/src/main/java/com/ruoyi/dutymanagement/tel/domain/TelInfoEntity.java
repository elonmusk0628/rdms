package com.ruoyi.dutymanagement.tel.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 电话记录实体
 *
 * @Author fenghan
 * @Date 2022-09-16
 */
@Entity
@Table(name = "tel_info", schema = "rdms")
@Data
public class TelInfoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    /** id*/
    private int id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    @Column(name = "tel_record_id")
    /** 通话记录id*/
    private int telRecordId;
    @Basic
    @Column(name = "the_electricity_unit")
    /** 来电单位*/
    private String theElectricityUnit;
    @Basic
    @Column(name = "tel")
    /** 来电单位电话*/
    private String tel;
    @Basic
    @Column(name = "user_name")
    /** 来电人姓名*/
    private String userName;
    @Basic
    @Column(name = "tel_time")
    /** 来电时间*/
    private String telTime;
    @Basic
    @Column(name = "answer_people")
    /** 接听人*/
    private String answerPeople;
    @Basic
    @Column(name = "phone")
    /** 接听人电话*/
    private String phone;
    @Basic
    @Column(name = "title")
    /** 通话标题*/
    private String title;
    @Basic
    @Column(name = "content")
    /** 通话内容*/
    private String content;
    @Basic
    @Column(name = "suggestion")
    /** 处理意见*/
    private String suggestion;
    @Basic
    @Column(name = "create_time")
    /** 创建日期*/
    private Date createTime;
    @Basic
    @Column(name = "create_by")
    /** 创建人*/
    private String createBy;
    @Basic
    @Column(name = "update_by")
    /** 修改人*/
    private String updateBy;
    @Basic
    @Column(name = "update_time")
    /** 修改日期*/
    private Date updateTime;
    @Basic
    @Column(name = "remark")
    /** 备注*/
    private String remark;
    @Basic
    @Column(name = "status")
    /** 状态*/
    private String status;
}
