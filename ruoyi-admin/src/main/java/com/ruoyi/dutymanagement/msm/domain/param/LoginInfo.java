package com.ruoyi.dutymanagement.msm.domain.param;

import lombok.Data;

/**
 * 登陆对象
 *
 * @Author fenghan
 * @Date 2022-09-02
 */
@Data
public class LoginInfo {
    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;
}
