package com.ruoyi.web.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Time;

/**
 * 对应rel_z_v表
 *
 * @author fengzh
 */
@Data
@NoArgsConstructor
@Component
public class RelZV {

    /** 测站代码 */
    private String stcd;

    /** 水面面积  km^2 */
    private BigDecimal watA;

    /** 点序号 */
    private String ptNo;

    /** 水位 单位：m */
    private BigDecimal z;

    /** 蓄水量  单位：10^6m^3 */
    private BigDecimal W;

    /** 率定时间 */
    @JsonFormat(pattern = "HH:MM:SS")
    private Time calTm;

    /** 备注 */
    private String nT;

}
