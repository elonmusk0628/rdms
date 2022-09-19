package com.ruoyi.dutymanagement.mail.service;

import com.ruoyi.dutymanagement.mail.domain.param.MailParam;
import com.ruoyi.dutymanagement.mail.domain.vo.MailVO;
import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;

import java.util.List;

/**
 * 邮件管理服务层
 *
 * @Author fenghan
 */
public interface IMailMessageService {
    /**
     * 查询邮件列表
     *
     * @param mailParam
     * @return
     */
    public List<MailVO> list(MailParam mailParam);

    /**
     * 查询邮件详情
     *
     * @param mainId
     * @return
     */
    public MailVO getMailInfoById(String mainId);

    /**
     * 调其它系统接口
     *
     * @return
     */
    String getJsonObject(String status) throws Exception;

    /**
     * 机器人接口
     *
     * @param status
     * @return
     */
    public String getRobotData(String status);

    /**
     * 获取鉴权码F-Access
     *
     * @return
     */
    public String getMailFAccess(LoginInfo loginInfo) throws Exception;
}
