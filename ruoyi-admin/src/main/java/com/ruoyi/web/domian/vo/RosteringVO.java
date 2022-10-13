package com.ruoyi.web.domian.vo;


import lombok.Data;

/**
 * 返回数据体
 *
 * @Author fenghan
 */
@Data
public class RosteringVO {

    private int id;

    private int scheduleId;

    private String years;

    private String date;

    private String week;

    private String dateType;

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;
}
