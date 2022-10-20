package com.ruoyi.web.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;

/**
 * 对应wr_st_cq_r表
 *
 * @author fengzh
 */
@Data
@NoArgsConstructor
@Component
public class WrStCqR {

    /** 测站代码 */
    private String stcd;

    /** 时间 */
    @JsonFormat(pattern = "HH:MM:SS")
    private Time tM;

    /** 流量 单位：m3/s */
    private String q;

}
