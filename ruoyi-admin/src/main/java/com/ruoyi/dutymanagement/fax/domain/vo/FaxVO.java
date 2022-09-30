package com.ruoyi.dutymanagement.fax.domain.vo;

import com.ruoyi.dutymanagement.fax.domain.FaxInfoEntity;
import lombok.Data;

import java.util.List;

/**
 * 传真返回VO
 *
 * @Author fenghan
 */
@Data
public class FaxVO {

    private Integer id;

    private String fileManageId;

    private String name;

    private String fileNum;

    private String draftDate;

    private String fileName;

    private String filePath;

    private String appCode;

    private String category;

    private String deptName;

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;

    private String remark;

    private List<FaxInfoEntity> faxInfoEntityList;
}
