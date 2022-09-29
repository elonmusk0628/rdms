package com.ruoyi.web.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 对应river_info表
 *
 * @author fengzh
 */
@Data
@NoArgsConstructor
@Component
public class SearchInfo {

    /** 主键id */
    private int id;

    /** 类型(1:水库；2:河道；3:机构) */
    private int type;

    /** 状态 */
    private String status;

    /** 河道名称 */
    private String name;

    /** 水位 */
    private String waterLevel;

    /** 警戒水位(汛限水位) */
    private String warnLevel;

    /** 河道流量(type=2时，必填) */
    private String riverFlow;

    /** 水势(type=2时，必填) */
    private String waterPotential;

    /** 水文类型(type=2时，必填) */
    private String waterType;

    /** 入库(type=1时，必填) */
    private String reservoirIn;

    /** 出库(type=1时，必填) */
    private String reservoirOut;

    /** 备注信息 */
    private String markContent;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date iDate;

    /** 小时 */
    private int iHour;

}
