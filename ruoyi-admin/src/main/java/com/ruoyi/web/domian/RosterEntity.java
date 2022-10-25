package com.ruoyi.web.domian;

import lombok.Data;

import javax.persistence.*;

/**
 * 成员信息表实体
 *
 * @Author fenghan
 * @Date 2022-10-8
 */
@Data
@Entity
@Table(name = "roster", schema = "rdms")
public class RosterEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    /** id*/
    private int id;
    @Basic
    @Column(name = "main_id")
    /** 主id*/
    private int mainId;
    @Basic
    @Column(name = "member_id")
    /** 成员id*/
    private int memberId;
    @Basic
    @Column(name = "schedule_id")
    /** 排班id*/
    private int scheduleId;
    @Basic
    @Column(name = "user_id")
    /** 用户id*/
    private Integer userId;
    @Basic
    @Column(name = "nick_name")
    /** 昵称*/
    private String nickName;
    @Basic
    @Column(name = "schedule_type")
    /** 成员类型*/
    private String scheduleType;
    @Basic
    @Column(name = "create_time")
    /** 创建时间*/
    private String createTime;
    @Basic
    @Column(name = "update_time")
    /** 修改时间*/
    private String updateTime;
}
