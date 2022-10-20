package com.ruoyi.dutymanagement.msm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 短信信息实体
 *
 * @Author fenghan
 */
@Entity
@Table(name = "msm", schema = "rdms")
@Data
public class MsmEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    /** id*/
    private Integer id;
    @Column(name = "send_info_id")
    /** 发送信息ID*/
    private int sendInfoId;
    @Column(name = "send_time")
    /** 发送时间*/
    private String sendTime;
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
    @Column(name = "ageing")
    /** 时效*/
    private String ageing;
    @Basic
    @Column(name = "business_type")
    /** 业务类型*/
    private String businessType;
    @Basic
    @Column(name = "status")
    /** 状态*/
    private String status;
    @Basic
    @Column(name = "content")
    /** 发送内容*/
    private String content;
    @Basic
    @Column(name = "send_count")
    /** 发送人数*/
    private Integer sendCount;
    @Basic
    @Column(name = "fail_count")
    /** 失败人数*/
    private Integer failCount;
    @Basic
    @Column(name = "dhsms_id")
    /** 标识*/
    private String dhsmsId;
    @Basic
    @Column(name = "signa_ture")
    /** 署名*/
    private String signaTure;
    @Basic
    @Column(name = "success")
    /** 发送状态*/
    private String success;
    @Basic
    @Column(name = "user_name")
    /** 用户名*/
    private String userName;
    @Basic
    @Column(name = "phone")
    /** 电话*/
    private String phone;
    @Basic
    @Column(name = "task_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /** 任务时间*/
    private Date taskTime;
    @Basic
    @Column(name = "task_time_str")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /** task_time_str*/
    private Date taskTimeStr;

}
