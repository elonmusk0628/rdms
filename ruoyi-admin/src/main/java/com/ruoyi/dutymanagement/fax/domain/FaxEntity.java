package com.ruoyi.dutymanagement.fax.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
/**
 * 传真信息实体类
 *
 * @Author fenghan
 */
@Entity
@Table(name = "fax", schema = "rdms")
@Data
public class FaxEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "file_manage_id")
    private String fileManageId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "file_num")
    private String fileNum;
    @Basic
    @Column(name = "draft_date")
    private String draftDate;
    @Basic
    @Column(name = "file_name")
    private String fileName;
    @Basic
    @Column(name = "file_path")
    private String filePath;
    @Basic
    @Column(name = "app_code")
    private String appCode;
    @Basic
    @Column(name = "category")
    private String category;
    @Basic
    @Column(name = "dept_name")
    private String deptName;
    @Basic
    @Column(name = "create_by")
    private String createBy;
    @Basic
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
    @Basic
    @Column(name = "update_by")
    private String updateBy;
    @Basic
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;
    @Basic
    @Column(name = "remark")
    private String remark;
    @Basic
    @Column(name = "status")
    private String status;
}
