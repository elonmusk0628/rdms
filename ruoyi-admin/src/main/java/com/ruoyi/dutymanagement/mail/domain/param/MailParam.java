package com.ruoyi.dutymanagement.mail.domain.param;

import lombok.Data;

/**
 * 邮件查询参数
 *
 * @Author fenghan
 */
@Data
public class MailParam {

    /** 标题 */
    private String mailName;

    /** 邮箱类型 */
    private String mailType;

    /** 开始日期 */
    private String startDate;

    /**结束日期 */
    private String endDate;
}
