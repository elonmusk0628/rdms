package com.ruoyi.dutymanagement.task;

import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;
import com.ruoyi.dutymanagement.msm.service.IHttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {

    @Autowired
    private IHttpClientService taskHttpClientService;

    //3.添加定时任务
    @Scheduled(cron = "0/1500 * * * * ?")
    private void configureTasks() throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername("admin");
        loginInfo.setPassword("Fyc@87117781");
        //获取token
        String token = taskHttpClientService.getToken(loginInfo);
        //获取fAccess码
        String fAccess = taskHttpClientService.getFAccess(token);
        //定时调取短信接口
        System.err.println("调取短信接口开始.........");
        taskHttpClientService.doMsm(token);
        //定时调取电话接口
        System.err.println("调取电话接口开始.........");
        taskHttpClientService.doTel(token, fAccess);
        //定时调取邮件接口
        System.err.println("调取邮件接口开始.........");
        taskHttpClientService.doMail(token, fAccess);
        //定时调取传真接口
        System.err.println("调取传真接口开始.........");
        taskHttpClientService.doFax(token, fAccess);
//        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }
}
