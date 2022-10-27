package com.ruoyi.dutymanagement.msm.domain.vo;

import com.ruoyi.dutymanagement.msm.domain.MsmInfoEntity;
import lombok.Data;


import java.sql.Timestamp;
import java.util.List;

/**
 * 短信返回VO
 *
 * @Author fenghan
 * @Date 2022-09-02
 */
@Data
public class MsmVO {
    /** id */
    private int id;

    /** 发送信息ID */
    private int sendInfoId;

    /** 搜索值 */
    private int searchValue;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 收件人 */
    private String remark;

    /** 时效 */
    private String ageing;

    /** 发送时间 */
    private Timestamp sendTime;

    /** 业务类型 */
    private String businessType;

    /** 状态 */
    private String status;

    /** 发送内容 */
    private String content;

    /** 发送人数 */
    private Integer sendCount;

    /** 失败人数 */
    private Integer failCount;

    /** 标识 */
    private String dhsmsId;

    /** 署名 */
    private String signaTure;

    /** 发送状态  0发送成功，1发送失败 */
    private String success;

    /** 用户名 */
    private String userName;

    /** 电话 */
    private String phone;

    /** 任务时间 */
    private Timestamp taskTime;

    /** 任务 */
    private Timestamp taskTimeStr;

    /** 附件列表 */
    private List<MsmInfoEntity> MsmInfoList;

}
