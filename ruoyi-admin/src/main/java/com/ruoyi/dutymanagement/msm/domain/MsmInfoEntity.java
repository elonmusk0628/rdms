package com.ruoyi.dutymanagement.msm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 短信联系人实体
 *
 * @Author fenghan
 */
@Entity
@Table(name = "msm_info", schema = "rdms")
@Data
public class MsmInfoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "itemid")
    private int itemid;
    @Basic
    @Column(name = "main_id")
    private int mainId;
    @Basic
    @Column(name = "send_info_id")
    private int sendInfoId;
    @Basic
    @Column(name = "search_value")
    private int searchValue;
    @Basic
    @Column(name = "create_by")
    private String createBy;
    @Basic
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Basic
    @Column(name = "update_by")
    private String updateBy;
    @Basic
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @Basic
    @Column(name = "remark")
    private String remark;
    @Basic
    @Column(name = "user_name")
    private String userName;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "success")
    private String success;

}
