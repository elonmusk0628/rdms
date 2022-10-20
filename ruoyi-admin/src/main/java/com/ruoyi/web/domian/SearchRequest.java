package com.ruoyi.web.domian;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询信息请求体
 *
 * @author fengzh
 */
@Data
@NoArgsConstructor
public class SearchRequest {

    private static final long serialVersionUID = -5755711372164839113L;

    /** 名称 */
    private String name;

    /** wr_res_b表水库名称 */
    private String resNm;

    /** wr_stat_b表测站名称 */
    private String stNm;

    /** 类型 */
    private String type;

    /** 状态 */
    private String status;

    /** 日期 */
    private String iDate;

    /** 对应表日期 */
    private String tS;

    /** 小时 */
    private int iHour;

}
