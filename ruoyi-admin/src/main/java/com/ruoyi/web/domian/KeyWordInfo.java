package com.ruoyi.web.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 对应key_word_info表
 *
 * @author fengzh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class KeyWordInfo {

    /** 主键id */
    private Integer id;

    /** 类型(1:水库；2:河道；3:机构) */
    @JsonProperty(value = "type")
    @Excel(name = "关键字类型", cellType = ColumnType.STRING)
    private String type;

    /** 关键字 */
    @NotBlank(message = "key word param cant be empty")
    @JsonProperty(value = "key_word")
    @Excel(name = "关键字", cellType = ColumnType.STRING)
    private String keyWord;

    /** 创建时间 */
    @JsonProperty(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /** 更新时间 */
    @JsonProperty(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
