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
    private Integer id;
    @Column(name = "message_Id")
    private String messageId;
    @Basic
    @Column(name = "mail_attachment_id")
    private Integer mailAttachmentId;
    @Basic
    @Column(name = "mail_name")
    private String mailName;
    @Basic
    @Column(name = "personal")
    private String personal;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "received_date")
    private String receivedDate;
    @Basic
    @Column(name = "mail_type")
    private String mailType;
    @Basic
    @Column(name = "file_size")
    private Integer fileSize;
    @Basic
    @Column(name = "search_value")
    private String searchValue;
    @Basic
    @Column(name = "createby")
    private String createby;
    @Basic
    @Column(name = "create_time")
    private String createTime;
    @Basic
    @Column(name = "update_by")
    private String updateBy;
    @Basic
    @Column(name = "update_time")
    private String updateTime;
    @Basic
    @Column(name = "app_code")
    private String appCode;
    @Basic
    @Column(name = "remark")
    private String remark;
    @Basic
    @Column(name = "status")
    private String status;
}
