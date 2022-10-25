package com.ruoyi.dutymanagement.fax.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 传真附件实体类
 *
 * @Author fenghan
 * @Date 2022-09-12
 */
@Entity
@Table(name = "fax_attachment", schema = "rdms")
@Data
public class FaxInfoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    /** id*/
    private Integer id;
    @Basic
    @Column(name = "main_id")
    /** 主表id*/
    private Integer mainId;
    @Basic
    @Column(name = "file_id")
    /** 文件id*/
    private String fileId;
    @Basic
    @Column(name = "file_name")
    /** 文件名*/
    private String fileName;
    @Basic
    @Column(name = "file_path")
    /** 文件路径*/
    private String filePath;
    @Basic
    @Column(name = "file_type")
    /** 文件类型*/
    private String fileType;
    @Basic
    @Column(name = "create_by")
    /** 创建人*/
    private String createBy;
    @Basic
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /** 创建日期*/
    private Date createTime;
    @Basic
    @Column(name = "update_by")
    /** 修改人*/
    private String updateBy;
    @Basic
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /** 修改日期*/
    private Date updateTime;
    @Basic
    @Column(name = "remark")
    /** 备注*/
    private String remark;
    @Basic
    @Column(name = "del_flag")
    /** 删除标志*/
    private String delFlag;
}
