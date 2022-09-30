package com.ruoyi.dutymanagement.mail.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 邮件附件实体类
 *
 * @Author fenghan
 */
@Entity
@Table(name = "mail_attachment", schema = "rdms")
@Data
public class MailInfoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "item_id")
    private Integer itemId;
    @Basic
    @Column(name = "main_id")
    private String mainId;
    @Basic
    @Column(name = "file_id")
    private String fileId;
    @Basic
    @Column(name = "mail_attachment_id")
    private Integer mailAttachmentId;
    @Basic
    @Column(name = "file_name")
    private String fileName;
    @Basic
    @Column(name = "file_type")
    private String fileType;
    @Basic
    @Column(name = "file_path")
    private String filePath;
    @Basic
    @Column(name = "app_code")
    private String appCode;
    @Basic
    @Column(name = "file_size")
    private Integer fileSize;
    @Basic
    @Column(name = "search_value")
    private String searchValue;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateTime;
    @Basic
    @Column(name = "delflag")
    private String delflag;
    @Basic
    @Column(name = "remark")
    private String remark;

}
