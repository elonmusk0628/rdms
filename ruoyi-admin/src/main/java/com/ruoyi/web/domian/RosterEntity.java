package com.ruoyi.web.domian;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "roster", schema = "rdms")
public class RosterEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "main_id")
    private int mainId;
    @Basic
    @Column(name = "member_id")
    private int memberId;
    @Basic
    @Column(name = "schedule_id")
    private int scheduleId;
    @Basic
    @Column(name = "user_id")
    private Integer userId;
    @Basic
    @Column(name = "nick_name")
    private String nickName;
    @Basic
    @Column(name = "schedule_type")
    private String scheduleType;
    @Basic
    @Column(name = "create_time")
    private String createTime;
    @Basic
    @Column(name = "update_time")
    private String updateTime;
}
