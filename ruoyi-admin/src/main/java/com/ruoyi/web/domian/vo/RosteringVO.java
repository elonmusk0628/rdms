package com.ruoyi.web.domian.vo;


import lombok.Data;

/**
 * 返回数据体
 *
 * @Author fenghan
 * @Date 2022-10-8
 */
@Data
public class RosteringVO {
    /** id */
    private int id;

    /** 排班id */
    private int scheduleId;

    /** 年月 */
    private String years;

    /** 日期 */
    private String date;

    /** 周 */
    private String week;

    /** 日类型 0：工作日 1：公休日 */
    private String dateType;

    /** 创建者 */
    private String createBy;

    /** 创建日期 */
    private String createTime;

    /** 修改者 */
    private String updateBy;

    /** 修改日期 */
    private String updateTime;
}
