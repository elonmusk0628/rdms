package com.ruoyi.dutymanagement.task;

import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;
import com.ruoyi.dutymanagement.msm.service.IHttpClientService;
import com.ruoyi.web.common.constant.BaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduleTask {

    @Autowired
    private IHttpClientService taskHttpClientService;

    //3.添加定时任务
    @Scheduled(cron = "0/1500 * * * * ?")
    private void configureTasks() throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername(BaseConstants.USER_NAME);
        loginInfo.setPassword(BaseConstants.PASS_WORD);
        //获取token
        String token = taskHttpClientService.getToken(loginInfo);
        //获取fAccess码
        String fAccess = taskHttpClientService.getFAccess(token);
        //定时调取短信接口
        taskHttpClientService.doMsm(token);
        //定时调取电话接口
        taskHttpClientService.doTel(token, fAccess);
        //定时调取邮件接口
        taskHttpClientService.doMail(token, fAccess);
        //定时调取传真接口
        taskHttpClientService.doFax(token, fAccess);
    }
}
