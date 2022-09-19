package com.ruoyi.dutymanagement.tel.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 电话返回VO
 *
 * @Author fenghan
 */
@Data
public class TelInfoVO {
    private int id;

    private int telRecordId;

    private String theElectricityUnit;

    private String tel;

    private String userName;

    private Date telTime;

    private String answerPeople;

    private String phone;

    private String title;

    private String content;

    private String suggestion;

    private Date createTime;

    private String createBy;

    private String updateBy;

    private Date updateTime;

    private String remark;

    private String status;
}
