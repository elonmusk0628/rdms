package com.ruoyi.web.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 对应wr_stat_b表
 *
 * @author fengzh
 */
@Data
@NoArgsConstructor
@Component
public class WrStatB {

    /** 测站代码 */
    private String stcd;

    /** 测站名称 */
    private String stNm;

    /** 测站类别
     MM 气象站 PP 雨量站
     BB 蒸发站 ZZ 河道水位站
     DD 堰闸水文站 ZQ 河道水文站
     TT 潮位站 RR 水库水文站
     DP 泵站水文站 ZG 地下水站
     SS 墒情站 ZB 分洪水位站
     DC 地面沉降量测站 WQ 水质站
     EL 其他测站 */
    private String stTp;

    /** 所在地 */
    private String loc;

    /** 测站岸别:0 左岸 1 右岸 2 不在河流上 */
    private String stbk;

    /** 水流流向:1 单向 2 双向 3 无 */
    private String flowDir;

    /** 基面类型
     01 1985年国家高程基准 02 1954年黄海高程系 03 1956年黄海高程系
     04 榆林 05 吴淞基面 06 珠江高程系
     07 大沽高程系 08 大连高程系 09 波罗的海水准
     10 渤海高程系 11 海防高程系 12 海口秀英港
     13 废黄河 14 康斯坦丁 15 新兵团水文
     16 广州 17 安庆 18 坎门
     99 其他 */
    private String datTp;

    /** 基面高程 */
    private String datElev;

    /** 修正基值 */
    private String modBasVal;

    /**  修正参数 */
    private String modPara;

    /** 监测方式 A:1 自动 2 人工 3 自动与人工相结合 */
    private String monTpA;

    /** 监测方式 B:4 驻测 5 巡测 6 驻测与巡测相结合 */
    private String monTpB;

    /** 设站年月 */
    private String estStYm;

    /** 运行状况:1 在用良好（工程处于良好运行）2 在用故障（工程带故障运行）3 停用（工程已废弃、或其它原因停用） */
    private String runCond;

    /** 管理单位代码 */
    private String engManCd;

    /** 拼音码 */
    private String pinYinCd;

    /** 时间戳 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tS;

    /** 备注 */
    private String nT;

    /** 水位信息 */
    private List<WrStZR> z;

    /** 流量信息 */
    private List<WrStMqR> q;

    /** 蓄水量信息 */
    private List<WrStWR> w;

    /** 蓄水量信息 */
    private List<WrStatItemB> itemB;

}
