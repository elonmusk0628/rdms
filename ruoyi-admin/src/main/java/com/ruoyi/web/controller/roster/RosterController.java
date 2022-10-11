package com.ruoyi.web.controller.roster;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;
import com.ruoyi.dutymanagement.msm.service.IHttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roster/message")
public class RosterController {
    @Autowired
    private IHttpClientService rosterHttpClientService;

    /**
     * 调值班系统值班接口
     * @param loginInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/doRoster")
    public AjaxResult doRoster(@RequestBody LoginInfo loginInfo) throws Exception{
        //获取token
        String token = rosterHttpClientService.getToken(loginInfo);
        //获取fAccess
        String fAccess = rosterHttpClientService.getFAccess(token);
        String str = rosterHttpClientService.doRostering(token,fAccess);
        return AjaxResult.success(str);
    }

}
