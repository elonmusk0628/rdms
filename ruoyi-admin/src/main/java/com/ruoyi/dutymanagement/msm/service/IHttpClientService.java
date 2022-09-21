package com.ruoyi.dutymanagement.msm.service;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * 调外系统接口
 */
public interface IHttpClientService {
    /**
     * 调值班管理系统短信接口
     *
     * @param status
     * @return
     */
    String doMsm(String status) throws Exception;

    /**
     * 调其它系统邮件接口
     *
     * @return
     */
    String doMail(String status) throws Exception;

    /**
     * 调值班管理系统传真接口
     *
     * @param status
     * @return
     */
    String doFax(String status) throws Exception;

    /**
     * 调值班管理系统电话接口
     *
     * @param status
     * @return
     */
    String doTel(String status) throws Exception;

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
     * @return
     */
    String getMailFAccess(LoginInfo loginInfo) throws Exception;

}
