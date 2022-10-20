package com.ruoyi.web.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 对应wr_stat_item_b表
 *
 * @author fengzh
 */
@Data
@NoArgsConstructor
@Component
public class WrStatItemB {

    /** 测站代码 */
    private String stcd;

    /** 降水项目 */
    private int itemP;

    /** 蒸发及蒸发辅助项目 */
    private int itemE;

    /** 水位项目:1、河道水位 2、湖库水位 3、堰闸水位 4、泵站水位 5、地下水位 6、潮水位 9、其他 */
    private String itemZ;

    /** 流量项目:1、河道流量 2、湖库进出水流量 3、泵站流量 4、堰闸流量 5、取水流量 6、排水流量 7、泉涌流量 9、其他 */
    private String itemQ;

    /** 地下水埋深项目 */
    private int itemGwDep;

    /** 水质项目 */
    private int itemWq;

    /** 地面沉降量项目 */
    private int itemLandSubs;

    /** 悬移泥沙项目 */
    private int itemVsand;

    /** 推移泥沙项目 */
    private int itemHsand;

    /** 冰凌项目 */
    private int itemIcesl;

    /** 潮汐项目 */
    private int itemTide;

    /** 其他项目 */
    private String itemOthe;

    /** 时间戳 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tS;

    /** 备注 */
    private String nT;

}
