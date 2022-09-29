package com.ruoyi.dutymanagement.mail.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.dutymanagement.mail.domain.param.MailParam;
import com.ruoyi.dutymanagement.mail.domain.vo.MailVO;
import com.ruoyi.dutymanagement.mail.service.IMailMessageService;
import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;
import com.ruoyi.dutymanagement.msm.service.IHttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 邮件管理
 *
 * @Author fenghan
 */
@RestController
@RequestMapping("/mail/message")
public class MailMessageController extends BaseController {

    @Autowired
    private IMailMessageService mailMessageService;

    @Autowired
    private IHttpClientService httpClientService;

    /**
     * 查询邮件列表
     *
     * @param mailParam
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mail:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(MailParam mailParam) {
        startPage();
        List<MailVO> mailVOList = mailMessageService.list(mailParam);
        return getDataTable(mailVOList);
    }

    /**
     * 查询邮件详情
     *
     * @param mainId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mail:message:getMailInfoById')")
    @GetMapping("/getMailInfoById/{mainId}")
    public AjaxResult getMailInfoById(@PathVariable String mainId) {
        MailVO mailInfo = mailMessageService.getMailInfoById(mainId);
        return AjaxResult.success(mailInfo);
    }

    /**
     * 调其它系统接口
     *
     * @return
     */
    @GetMapping("/getJsonObject")
    public AjaxResult getJsonObject(@RequestParam String status) throws Exception {
        if (status == null || "".equals(status)) {
            return AjaxResult.error("success参数不能为空！");
        }
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername("admin");
        loginInfo.setPassword("Fyc@87117781");
        //获取token
        String token = httpClientService.getToken(loginInfo);
        //获取fAccess
        String fAccess = httpClientService.getFAccess(token);
        String jsonObject = mailMessageService.getJsonObject(token,fAccess);
        return AjaxResult.success(jsonObject);
    }

    /**
     * 与机器人接口
     *
     * @param status
     * @return
     */
    @GetMapping("/getRobotData")
    public AjaxResult getMailStatus(@RequestParam String status) {
        if (status == null || "".equals(status)) {
            return AjaxResult.error("status参数不能为空！");
        }
        String mailMessage = mailMessageService.getRobotData(status);
        if (mailMessage == null || "".equals(mailMessage)) {
            AjaxResult.success("没有新邮件！");
        }
        return AjaxResult.success(mailMessage);
    }

    /**
     * 调其它系统接口
     *
     * @return
     */
    @GetMapping("/doMail")
    public AjaxResult doMail(@RequestParam String status) throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername("admin");
        loginInfo.setPassword("Fyc@87117781");
        //获取token
        String token = httpClientService.getToken(loginInfo);
        //获取fAccess
        String fAccess = httpClientService.getFAccess(token);
        String jsonObject = httpClientService.doMail(token,fAccess);
        return AjaxResult.success(jsonObject);
    }

    /**
     * 获取鉴权码F-Access
     *
     * @return
     */
    @GetMapping("/getMailFAccess")
    public AjaxResult getMailFAccess(LoginInfo loginInfo) throws Exception {
        String fAccess = mailMessageService.getMailFAccess(loginInfo);
        return AjaxResult.success(fAccess);
    }
}
