package com.ruoyi.web.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 对应wr_st_z_r表
 *
 * @author fengzh
 */
@Data
@NoArgsConstructor
@Component
public class WrStZR {

    /** 测站代码 */
    private String stcd;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tM;

    /** 水位 单位：m */
    private String z;

}
