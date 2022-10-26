package com.ruoyi.web.domian;

import lombok.Data;

import javax.persistence.*;

/**
 * 排班信息表实体类
 *
 * @Author fenghan
 * @Date 2022-10-8
 */
@Data
@Entity
@Table(name = "rostering", schema = "rdms")
public class RosteringEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    /** id*/
    private int id;
    @Basic
    @Column(name = "schedule_id")
    /** 排班id*/
    private int scheduleId;
    @Basic
    @Column(name = "years")
    /** 年份*/
    private String years;
    @Basic
    @Column(name = "date")
    /** 日期*/
    private String date;
    @Basic
    @Column(name = "week")
    /** 周*/
    private String week;
    @Basic
    @Column(name = "date_type")
    /** 日期类型*/
    private String dateType;
    @Basic
    @Column(name = "create_by")
    /** 创建者*/
    private String createBy;
    @Basic
    @Column(name = "create_time")
    /** 创建时间*/
    private String createTime;
    @Basic
    @Column(name = "update_by")
    /** 修改者*/
    private String updateBy;
    @Basic
    @Column(name = "update_time")
    /** 修改时间*/
    private String updateTime;

}
