package com.ruoyi.dutymanagement.tel.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 电话返回VO
 *
 * @Author fenghan
 * @Date 2022-09-16
 */
@Data
public class TelInfoVO {
    /** id */
    private int id;

    /** 通话记录id */
    private int telRecordId;

    /** 来电单位 */
    private String theElectricityUnit;

    /** 来电单位电话 */
    private String tel;

    /** 来电人姓名 */
    private String userName;

    /** 来电时间 */
    private String telTime;

    /** 接听人 */
    private String answerPeople;

    /** 接听人电话 */
    private String phone;

    /** 通话标题 */
    private String title;

    /** 通话内容 */
    private String content;

    /** 处理意见 */
    private String suggestion;

    /** 创建时间 */
    private Date createTime;

    /** 创建人 */
    private String createBy;

    /** 修改人 */
    private String updateBy;

    /** 修改时间 */
    private Date updateTime;

    /** 备注 */
    private String remark;

    /** 状态 0已读1未读 */
    private String status;
}
