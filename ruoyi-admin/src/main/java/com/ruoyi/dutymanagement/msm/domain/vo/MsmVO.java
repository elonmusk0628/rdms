package com.ruoyi.dutymanagement.msm.domain.vo;

import com.ruoyi.dutymanagement.msm.domain.MsmInfoEntity;
import lombok.Data;


import java.sql.Timestamp;
import java.util.List;

/**
 * 短信返回VO
 *
 * @Author fenghan
 */
@Data
public class MsmVO {
    private int id;

    private int sendInfoId;

    private int searchValue;

    private String createBy;

    private Timestamp createTime;

    private String updateBy;

    private Timestamp updateTime;

    private String remark;

    private String ageing;

    private Timestamp sendTime;

    private String businessType;

    private String status;

    private String content;

    private Integer sendCount;

    private Integer failCount;

    private String dhsmsId;

    private String signaTure;

    private String success;

    private String userName;

    private String phone;

    private Timestamp taskTime;

    private Timestamp taskTimeStr;

    private List<MsmInfoEntity> MsmInfoList;

}
