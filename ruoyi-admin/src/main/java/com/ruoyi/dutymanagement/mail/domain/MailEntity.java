package com.ruoyi.dutymanagement.mail.domain;

import lombok.Data;

import javax.persistence.*;
/**
 * 邮件实体类
 *
 * @Author fenghan
 */
@Entity
@Table(name = "mail", schema = "rdms")
@Data
public class MailEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    /** id*/
    private Integer id;
    @Column(name = "message_Id")
    /** 邮件id*/
    private String messageId;
    @Basic
    @Column(name = "mail_attachment_id")
    /** 邮件附件id*/
    private Integer mailAttachmentId;
    @Basic
    @Column(name = "mail_name")
    /** 标题*/
    private String mailName;
    @Basic
    @Column(name = "personal")
    /** 邮件发送人*/
    private String personal;
    @Basic
    @Column(name = "address")
    /** 邮件发送地址*/
    private String address;
    @Basic
    @Column(name = "received_date")
    /** 接受时间*/
    private String receivedDate;
    @Basic
    @Column(name = "mail_type")
    /** 邮件类型*/
    private String mailType;
    @Basic
    @Column(name = "file_size")
    /** 附件数量*/
    private Integer fileSize;
    @Basic
    @Column(name = "search_value")
    /** 搜索值*/
    private String searchValue;
    @Basic
    @Column(name = "createby")
    /** 创建者*/
    private String createby;
    @Basic
    @Column(name = "create_time")
    /** 创建日期*/
    private String createTime;
    @Basic
    @Column(name = "update_by")
    /** 修改者*/
    private String updateBy;
    @Basic
    @Column(name = "update_time")
    /** 修改日期*/
    private String updateTime;
    @Basic
    @Column(name = "app_code")
    /** 应用号*/
    private String appCode;
    @Basic
    @Column(name = "remark")
    /** 备注*/
    private String remark;
    @Basic
    @Column(name = "status")
    /** 状态*/
    private String status;
}
