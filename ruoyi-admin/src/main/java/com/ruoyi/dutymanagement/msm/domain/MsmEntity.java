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
    private Integer id;
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
    @Column(name = "ageing")
    private String ageing;
    @Basic
    @Column(name = "send_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
    @Basic
    @Column(name = "business_type")
    private String businessType;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "send_count")
    private Integer sendCount;
    @Basic
    @Column(name = "fail_count")
    private Integer failCount;
    @Basic
    @Column(name = "dhsms_id")
    private String dhsmsId;
    @Basic
    @Column(name = "signa_ture")
    private String signaTure;
    @Basic
    @Column(name = "success")
    private String success;
    @Basic
    @Column(name = "user_name")
    private String userName;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "task_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskTime;
    @Basic
    @Column(name = "task_time_str")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskTimeStr;

}
