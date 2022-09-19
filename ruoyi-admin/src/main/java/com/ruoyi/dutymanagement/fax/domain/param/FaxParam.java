package com.ruoyi.dutymanagement.fax.domain.param;

import lombok.Data;

/**
 * 传真参数对象
 *
 * @Author fenghan
 */
@Data
public class FaxParam {
    private String name;

    private String fileNum;

    private String startDate;

    private String endDate;
}
