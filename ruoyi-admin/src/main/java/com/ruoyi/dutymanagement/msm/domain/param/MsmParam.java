package com.ruoyi.dutymanagement.msm.domain.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 短息查询参数
 *
 * @Author fenghan
 */
@Data
public class MsmParam {

    private String signaTure;

    private String content;

    private String success;

    private String ageing;

    private String sendTime;

    private String userName;

    private String businessType;

    private int sendCount;

    private int failCount;

    private String status;

    private String phone;

    private int searchValue;

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;

    private int sendInfoId;

    private String startDate;

    private String endDate;
}
