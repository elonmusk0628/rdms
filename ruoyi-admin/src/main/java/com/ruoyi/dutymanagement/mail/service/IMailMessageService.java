package com.ruoyi.dutymanagement.mail.service;

import com.ruoyi.dutymanagement.mail.domain.param.MailParam;
import com.ruoyi.dutymanagement.mail.domain.vo.MailVO;
import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;

import java.util.List;

/**
 * 邮件管理服务层
 *
 * @Author fenghan
 * @Date 2022-09-08
 */
public interface IMailMessageService {
    /**
     * 查询邮件列表
     *
     * @param mailParam
     * @return
     */
    List<MailVO> getMailList(MailParam mailParam);

    /**
     * 查询邮件详情
     *
     * @param mainId
     * @return
     */
    MailVO getMailInfoById(String mainId);

    /**
     * 调其它系统接口
     *
     * @return
     */
    String getJsonObject(String token, String fAccess) throws Exception;

    /**
     * 机器人接口
     *
     * @param status
     * @return
     */
    String getRobotData(String status);

    /**
     * 获取鉴权码F-Access
     *
     * @return
     */
    String getMailFAccess(LoginInfo loginInfo) throws Exception;

    /**
     * 获取当天未读新邮件数
     *
     * @param mailParam
     * @return
     */
    int getMailCount(MailParam mailParam);
}
