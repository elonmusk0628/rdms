package com.ruoyi.web.domian;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关键字返回体
 *
 * @author fengzh
 */
@Data
@NoArgsConstructor
public class KeyWordInfoResponse {

    private static final long serialVersionUID = -8001189064434349840L;

    /** 内容 */
    private String content;

    /** 总数 */
    private int total;

}
