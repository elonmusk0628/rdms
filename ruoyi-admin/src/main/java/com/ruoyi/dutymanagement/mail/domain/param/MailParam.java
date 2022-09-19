package com.ruoyi.dutymanagement.mail.domain.param;

import lombok.Data;

/**
 * 邮件查询参数
 *
 * @Author fenghan
 */
@Data
public class MailParam {

    private String mailName;

    private String mailType;

    private String startDate;

    private String endDate;
}
