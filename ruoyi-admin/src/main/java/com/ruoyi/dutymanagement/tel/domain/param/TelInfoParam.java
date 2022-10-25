package com.ruoyi.dutymanagement.tel.domain.param;

import lombok.Data;

/**
 * 电话查询参数
 *
 * @Author fenghan
 */
@Data
public class TelInfoParam {
    //来电单位
    private String theElectricityUnit;
    //来电电话
    private String tel;
    //来点姓名
    private String userName;
    //接听人
    private String answerPeople;
    //来电开始时间
    private String startDate;
    //来电结束时间
    private String endDate;
    //状态
    private String status;
}
