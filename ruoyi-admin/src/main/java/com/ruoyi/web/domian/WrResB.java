package com.ruoyi.web.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 对应wr_res_b表
 *
 * @author fengzh
 */
@Data
@NoArgsConstructor
@Component
public class WrResB {

    /** 水库代码 */
    private String resCd;

    /** 水库名称 */
    private String resNm;

    /** 所在地 */
    private String loc;

    /** 建成年月 */
    private String compYm;

    /** 工程规模 */
    private String projScal;

    /** 基面类型 */
    private String datTp;

    /** 集水面积 单位：km2 */
    private String catA;

    /** 设计洪水位 单位：m */
    private String desFz;

    /** 总库容  单位：10^6m^3 */
    private String totV;

    /** 调洪库容  单位：10^6m^3 */
    private String frscV;

    /** 正常蓄水位 单位：m */
    private String normWz;

    /** 校核洪水位  单位：m */
    private String cfZ;

    /** 兴利库容  单位：10^6m^3 */
    private String utilV;

    /** 防洪限制水位 单位：m */
    private String fsZ;

    /** 防洪限制水位库容  单位：10^6m^3 */
    private String fsZV;

    /** 死水位  单位：m */
    private String deadZ;

    /** 死库容  单位：10^6m^3 */
    private String deadV;

    /** 水库调节方式 */
    private String resRegTp;

    /** 最小下泄流量  单位：m3/s */
    private String minDisc;

    /** 发电引水口至尾水口河道长度   单位：m */
    private String stEndLen;

    /** 水库枢纽建筑物组成 */
    private String rhcc;

    /** 运行状况 */
    private String runCond;

    /** 管理单位代码 */
    private String engManCd;

    /** 时间戳 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tS;

    /** 备注 */
    private String nT;

}
