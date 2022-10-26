package com.ruoyi.dutymanagement.msm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 短信联系人实体
 *
 * @Author fenghan
 * @Date 2022-09-02
 */
@Entity
@Table(name = "msm_info", schema = "rdms")
@Data
public class MsmInfoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "itemid")
    /** id*/
    private int itemid;
    @Basic
    @Column(name = "main_id")
    /** 主表id*/
    private int mainId;
    @Basic
    @Column(name = "send_info_id")
    /** 发送id*/
    private int sendInfoId;
    @Basic
    @Column(name = "search_value")
    /** 搜索值*/
    private int searchValue;
    @Basic
    @Column(name = "create_by")
    /** 创建者*/
    private String createBy;
    @Basic
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /** 创建时间*/
    private Date createTime;
    @Basic
    @Column(name = "update_by")
    /** 修改者*/
    private String updateBy;
    @Basic
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /** 修改时间*/
    private Date updateTime;
    @Basic
    @Column(name = "remark")
    /** 收件人*/
    private String remark;
    @Basic
    @Column(name = "user_name")
    /** 用户名*/
    private String userName;
    @Basic
    @Column(name = "phone")
    /** 电话*/
    private String phone;
    @Basic
    @Column(name = "success")
    /** 发送状态*/
    private String success;

}
