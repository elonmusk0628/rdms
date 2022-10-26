package com.ruoyi.dutymanagement.msm.service;

import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;

/**
 * 调外系统接口
 *
 * @Author fenghan
 * @Date 2022-09-02
 */
public interface IHttpClientService {
    /**
     * 调值班管理系统短信接口
     *
     * @param token
     * @return
     */
    String doMsm(String token) throws Exception;

    /**
     * 调其它系统邮件接口
     *
     * @return
     */
    String doMail(String token, String fAccess) throws Exception;

    /**
     * 调值班管理系统传真接口
     *
     * @param token,fAccess
     * @return
     */
    String doFax(String token, String fAccess) throws Exception;

    /**
     * 调值班管理系统电话接口
     *
     * @param token,fAccess
     * @return
     */
    String doTel(String token, String fAccess) throws Exception;

    /**
     * 调值班管理系统排班接口
     *
     * @param token
     * @param fAccess
     * @return
     * @throws Exception
     */
    String doRostering(String token, String fAccess) throws Exception;

    /**
     * 获取token
     *
     * @param loginInfo
     * @return
     */
    String getToken(LoginInfo loginInfo) throws Exception;

    /**
     * 获取鉴权码F-Access
     *
     * @param token
     * @return
     */
    String getFAccess(String token) throws Exception;

}
