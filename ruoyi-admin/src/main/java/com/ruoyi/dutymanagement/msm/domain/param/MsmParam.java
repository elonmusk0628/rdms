package com.ruoyi.dutymanagement.msm.domain.param;

import lombok.Data;


/**
 * 短息查询参数
 *
 * @Author fenghan
 * @Date 2022-09-02
 */
@Data
public class MsmParam {
    /** 署名 */
    private String signaTure;

    /** 发送内容 */
    private String content;

    /** 发送状态  0发送成功，1发送失败 */
    private String success;

    /** 时效 */
    private String ageing;

    /** 发送时间 */
    private String sendTime;

    /** 用户名 */
    private String userName;

    /** 业务类型 */
    private String businessType;

    /** 发送人数 */
    private int sendCount;

    /** 失败人数 */
    private int failCount;

    /** 状态 */
    private String status;

    /** 电话 */
    private String phone;

    /** 收件人 */
    private String remark;

    /** 创建时间 */
    private String createTime;

    /** 修改人 */
    private String updateBy;

    /** 修改时间 */
    private String updateTime;

    /** 发送信息ID */
    private int sendInfoId;

    /** 开始时间 */
    private String startDate;

    /** 结束时间 */
    private String endDate;
}
