package com.ruoyi.web.domian;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询信息请求体
 *
 * @author fengzh
 */
@Data
@NoArgsConstructor
public class QuestionAndAnswerRequest {

    private static final long serialVersionUID = 6154604954980315349L;

    /** 问题 */
    private String question;

    /** 答案 */
    private String answer;

    /** 类型 */
    private String type;

    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 页码 */
    private Integer pageNum;

    /** 每页条数 */
    private Integer pageSize;

}
