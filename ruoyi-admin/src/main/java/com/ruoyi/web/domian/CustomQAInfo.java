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
 * 对应customqa_info表
 *
 * @author fengzh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CustomQAInfo {

    /** 主键id */
    private Integer id;

    /** 问题类型 */
    @JsonProperty(value = "type")
    private String type;

    /** 自定义问题内容 */
    @NotBlank(message = "question param cant be empty")
    @JsonProperty(value = "question")
    @Excel(name = "自定义问题内容", cellType = ColumnType.STRING)
    private String question;

    /** 自定义回答内容 */
    @NotBlank(message = "answer param cant be empty")
    @JsonProperty(value = "answer")
    @Excel(name = "自定义回答内容", cellType = ColumnType.STRING)
    private String answer;

    /** 创建人 */
    @JsonProperty(value = "creator")
    private String creator;

    /** 创建时间 */
    @JsonProperty(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /** 更新时间 */
    @JsonProperty(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
