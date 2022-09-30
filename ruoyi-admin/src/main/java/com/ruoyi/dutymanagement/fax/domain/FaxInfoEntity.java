package com.ruoyi.dutymanagement.fax.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 传真附件实体类
 *
 * @Author fenghan
 */
@Entity
@Table(name = "fax_attachment", schema = "rdms")
@Data
public class FaxInfoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "main_id")
    private Integer mainId;
    @Basic
    @Column(name = "file_id")
    private String fileId;
    @Basic
    @Column(name = "file_name")
    private String fileName;
    @Basic
    @Column(name = "file_path")
    private String filePath;
    @Basic
    @Column(name = "file_type")
    private String fileType;
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
    @Column(name = "del_flag")
    private String delFlag;
}
