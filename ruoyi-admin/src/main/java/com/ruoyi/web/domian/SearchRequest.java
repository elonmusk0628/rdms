package com.ruoyi.web.domian;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchRequest {

    private static final long serialVersionUID = -5755711372164839113L;

    /** 名称 */
    private String name;

    /** 类型 */
    private String type;

    /** 状态 */
    private String status;

    /** 日期 */
    private String iDate;

    /** 小时 */
    private int iHour;

}
