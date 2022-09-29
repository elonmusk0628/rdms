package com.ruoyi.web.domian;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 关键字请求体
 *
 * @author fengzh
 */
@Data
@NoArgsConstructor
@Component
public class KeyWordInfoRequest {

    /** 主键id */
    @JsonProperty(value = "id")
    private int id;

    /** 类型(1:水库；2:河道；3:机构) */
    @JsonProperty(value = "type")
    private int type;

    /** 关键字 */
    @JsonProperty(value = "key_word")
    private String keyWord;

    /** 开始时间 */
    @JsonProperty(value = "start_time")
    private String startTime;

    /** 结束时间 */
    @JsonProperty(value = "end_time")
    private String endTime;

}
