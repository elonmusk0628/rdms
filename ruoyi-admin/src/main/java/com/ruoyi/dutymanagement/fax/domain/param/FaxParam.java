package com.ruoyi.dutymanagement.fax.domain.param;

import lombok.Data;

/**
 * 传真参数对象
 *
 * @Author fenghan
 */
@Data
public class FaxParam {

    /** 标题 */
    private String name;

    /** 编号 */
    private String fileNum;

    /** 开始日期 */
    private String startDate;

    /** 结束日期 */
    private String endDate;
}
