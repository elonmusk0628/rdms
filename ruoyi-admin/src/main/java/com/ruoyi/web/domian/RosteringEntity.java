package com.ruoyi.web.domian;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
@Data
@Entity
@Table(name = "rostering", schema = "rdms")
public class RosteringEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "schedule_id")
    private int scheduleId;
    @Basic
    @Column(name = "years")
    private String years;
    @Basic
    @Column(name = "date")
    private String date;
    @Basic
    @Column(name = "week")
    private String week;
    @Basic
    @Column(name = "date_type")
    private String dateType;
    @Basic
    @Column(name = "create_by")
    private String createBy;
    @Basic
    @Column(name = "create_time")
    private String createTime;
    @Basic
    @Column(name = "update_by")
    private String updateBy;
    @Basic
    @Column(name = "update_time")
    private String updateTime;

}
