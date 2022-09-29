package com.ruoyi.dutymanagement.msm.service;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * 调外系统接口
 */
@Component
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
    String doMail(String token,String fAccess) throws Exception;

    /**
     * 调值班管理系统传真接口
     *
     * @param token,fAccess
     * @return
     */
    String doFax(String token,String fAccess) throws Exception;

    /**
     * 调值班管理系统电话接口
     *
     * @param token,fAccess
     * @return
     */
    String doTel(String token,String fAccess) throws Exception;

    /**
     * 获取token
     *
     * @param loginInfo
     * @return
     */
    String getToken(LoginInfo loginInfo) throws Exception;

    /**
     * 获取鉴权码F-Access
     * @param token
     * @return
     */
    String getFAccess(String token) throws Exception;

}
