package com.ruoyi.dutymanagement.fax.domain.vo;

import com.ruoyi.dutymanagement.fax.domain.FaxInfoEntity;
import lombok.Data;

import java.util.List;

/**
 * 传真返回VO
 *
 * @Author fenghan
 * @Date 2022-09-12
 */
@Data
public class FaxVO {
    /**
     * id
     */
    private Integer id;
    /**
     * 文件id
     */
    private String fileManageId;
    /**
     * 标题名
     */
    private String name;
    /**
     * 文件编号
     */
    private String fileNum;
    /**
     * 发文日期
     */
    private String draftDate;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 应用号
     */
    private String appCode;
    /**
     * 类别
     */
    private String category;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private List<FaxInfoEntity> faxInfoEntityList;
}
