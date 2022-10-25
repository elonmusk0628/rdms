package com.ruoyi.dutymanagement.mail.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 邮件附件实体类
 *
 * @Author fenghan
 * @Date 2022-09-08
 */
@Entity
@Table(name = "mail_attachment", schema = "rdms")
@Data
public class MailInfoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "item_id")
    /** id*/
    private Integer itemId;
    @Basic
    @Column(name = "main_id")
    /** 主表id*/
    private String mainId;
    @Basic
    @Column(name = "file_id")
    /** 文件id*/
    private String fileId;
    @Basic
    @Column(name = "mail_attachment_id")
    /** 邮箱附件id*/
    private Integer mailAttachmentId;
    @Basic
    @Column(name = "file_name")
    /** 文件名*/
    private String fileName;
    @Basic
    @Column(name = "file_type")
    /** 文件类型*/
    private String fileType;
    @Basic
    @Column(name = "file_path")
    /** 文件路径*/
    private String filePath;
    @Basic
    @Column(name = "app_code")
    /** 应用号*/
    private String appCode;
    @Basic
    @Column(name = "file_size")
    /** 文件数*/
    private Integer fileSize;
    @Basic
    @Column(name = "search_value")
    /** 搜索值*/
    private String searchValue;
    @Basic
    @Column(name = "create_by")
    /** 创建人*/
    private String createBy;
    @Basic
    @Column(name = "create_time")
    /** 创建日期*/
    private String createTime;
    @Basic
    @Column(name = "update_by")
    /** 修改人*/
    private String updateBy;
    @Basic
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /** 修改日期*/
    private Timestamp updateTime;
    @Basic
    @Column(name = "delflag")
    /** 删除标志*/
    private String delflag;
    @Basic
    @Column(name = "remark")
    /** 备注*/
    private String remark;

}
