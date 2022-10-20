package com.ruoyi.web.domian.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 返回数据vo
 *
 * @author fengzh
 */
@Data
@NoArgsConstructor
@Component
public class WrStatBVO {

    /** 测站代码 */
    private String stcd;

    /** 测站名称 */
    private String stNm;

    /** 所在地 */
    private String loc;

    /** 基面高程 单位：m */
    private String datElev;

    /** 水位 单位：m */
    private String z;

    /** 流量 单位：m3/s */
    private String q;

    /** 蓄水量 单位：10^6m^3 */
    private String w;

    /** 水位项目:1、河道水位 2、湖库水位 3、堰闸水位 4、泵站水位 5、地下水位 6、潮水位 9、其他 */
    private String itemZ;

    /** 流量项目:1、河道流量 2、湖库进出水流量 3、泵站流量 4、堰闸流量 5、取水流量 6、排水流量 7、泉涌流量 9、其他 */
    private String itemQ;

    /** 运行状况:1 在用良好（工程处于良好运行）2 在用故障（工程带故障运行）3 停用（工程已废弃、或其它原因停用） */
    private String runCond;

    /** 时间戳 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tS;

    /** 备注 */
    private String nT;

}
