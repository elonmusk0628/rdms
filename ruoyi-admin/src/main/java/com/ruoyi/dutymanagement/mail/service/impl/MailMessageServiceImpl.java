package com.ruoyi.dutymanagement.mail.service.impl;

import com.ruoyi.dutymanagement.mail.domain.MailInfoEntity;
import com.ruoyi.dutymanagement.mail.domain.param.MailParam;
import com.ruoyi.dutymanagement.mail.domain.vo.MailVO;
import com.ruoyi.dutymanagement.mail.mapper.MailMessageMapper;
import com.ruoyi.dutymanagement.mail.service.IMailMessageService;
import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;
import com.ruoyi.dutymanagement.msm.service.IHttpClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 邮件管理业务逻辑层
 *
 * @Author fenghan
 */
@Service
@Transactional
public class MailMessageServiceImpl implements IMailMessageService {
    private static final Logger log = LoggerFactory.getLogger(MailMessageServiceImpl.class);
    @Autowired
    private MailMessageMapper mailMessageMapper;

    @Autowired
    private IHttpClientService mailHttpPostClientService;

    /**
     * 查询邮件列表
     *
     * @param mailParam
     * @return
     */
    @Override
    public List<MailVO> getMailList(MailParam mailParam) {
        List<MailVO> mailVOList = mailMessageMapper.getMailList(mailParam);
        return mailVOList;
    }

    /**
     * 查询邮件详情
     *
     * @param mainId
     * @return
     */
    @Override
    public MailVO getMailInfoById(String mainId) {
        //查询邮件信息
        MailVO mailVO = mailMessageMapper.getMailById(Integer.valueOf(mainId));
        //查询邮件详情信息
        List<MailInfoEntity> mailInfoList = mailMessageMapper.getMailInfoById(mainId);
        mailVO.setMailInfoList(mailInfoList);
        return mailVO;
    }

    /**
     * 调其它系统接口
     *
     * @return
     */
    @Override
    public String getJsonObject(String token,String fAccess) throws Exception {
        //调取值班管理系统短信接口
        String jsonObject = mailHttpPostClientService.doMail(token,fAccess);
        return jsonObject;
    }

    @Override
    public String getRobotData(String status) {
        //查询未读邮件
        List<MailVO> mailVOList = mailMessageMapper.getMailByStatus(status);
        String mailMessage = null;
        if (mailVOList.size() == 1) {
            for (MailVO mailVO : mailVOList) {
                //邮件接收时间
                String receivedDate = mailVO.getReceivedDate();
                //邮件标题
                String mailName = mailVO.getMailName();
                mailMessage = receivedDate + "收到标题为：" + mailName + "邮件，请及时查看！";
                if (receivedDate != null || mailName != null) {
                    //将邮件状态改为1
                    mailMessageMapper.updateMailStatus(mailVO.getId());
                }
            }
        } else if (mailVOList.size() > 1) {
            mailMessage = "有" + mailVOList.size() + "封新邮件，请及时查看！";
            for (MailVO mailVO : mailVOList) {
                //将邮件状态改为1
                mailMessageMapper.updateMailStatus(mailVO.getId());
            }
        } else {
            return null;
        }

        return mailMessage;
    }

    /**
     * 获取鉴权码F-Access
     *
     * @return
     */
    @Override
    public String getMailFAccess(LoginInfo loginInfo) throws Exception {
        String token = mailHttpPostClientService.getToken(loginInfo);
        String fAccess = mailHttpPostClientService.getFAccess(token);
        return fAccess;
    }
}
