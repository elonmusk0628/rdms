package com.ruoyi.dutymanagement.fax.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;

/**
 * 传真信息实体类
 *
 * @Author fenghan
 * @Date 2022-09-12
 */
@Entity
@Table(name = "fax", schema = "rdms")
@Data
public class FaxEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    /** id*/
    private int id;
    @Basic
    @Column(name = "file_manage_id")
    /** 文件id*/
    private String fileManageId;
    @Basic
    @Column(name = "name")
    /** 标题名*/
    private String name;
    @Basic
    @Column(name = "file_num")
    /** 文件编号*/
    private String fileNum;
    @Basic
    @Column(name = "draft_date")
    /** 发文日期*/
    private String draftDate;
    @Basic
    @Column(name = "file_name")
    /** 文件名*/
    private String fileName;
    @Basic
    @Column(name = "file_path")
    /** 文件路径*/
    private String filePath;
    @Basic
    @Column(name = "app_code")
    /** 应用号*/
    private String appCode;
    @Basic
    @Column(name = "category")
    /** 类别*/
    private String category;
    @Basic
    @Column(name = "dept_name")
    /** 部门名称*/
    private String deptName;
    @Basic
    @Column(name = "create_by")
    /** 创建人*/
    private String createBy;
    @Basic
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    private String updateTime;
    @Basic
    @Column(name = "remark")
    /** 备注*/
    private String remark;
    @Basic
    @Column(name = "status")
    /** 状态*/
    private String status;
}
