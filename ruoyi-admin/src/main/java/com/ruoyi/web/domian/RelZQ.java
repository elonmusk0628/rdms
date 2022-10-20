package com.ruoyi.web.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 对应rel_z_q表
 *
 * @author fengzh
 */
@Data
@NoArgsConstructor
@Component
public class RelZQ {

    /** 测站代码 */
    private String stcd;

    /** 曲线名称 */
    private String curvNm;

    /** 点序号 */
    private String ptNo;

    /** 水位 单位：m */
    private BigDecimal z;

    /** 流量 单位：m3/s */
    private BigDecimal q;

    /** 启用时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date enabTm;

    /** 备注 */
    private String nT;

}
