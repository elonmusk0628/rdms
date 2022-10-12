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

}
