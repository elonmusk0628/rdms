package com.ruoyi.web.controller.roster;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;
import com.ruoyi.dutymanagement.msm.service.IHttpClientService;
import com.ruoyi.web.common.constant.BaseConstants;
import com.ruoyi.web.common.enums.ExceptionEnum;
import com.ruoyi.web.service.IRosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 值班 控制层
 *
 * @Author fenghan
 * @Date 2022-10-8
 */
@RestController
@RequestMapping("/roster/message")
public class RosterController {
    @Autowired
    private IHttpClientService rosterHttpClientService;

    @Autowired
    private IRosterService rosterService;

    /**
     * 调值班系统值班接口
     *
     * @param loginInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/doRoster")
    public AjaxResult doRoster(@RequestBody LoginInfo loginInfo) throws Exception {
        //获取token
        String token = rosterHttpClientService.getToken(loginInfo);
        //获取fAccess
        String fAccess = rosterHttpClientService.getFAccess(token);
        String str = rosterHttpClientService.doRostering(token, fAccess);
        return AjaxResult.success(str);
    }

    /**
     * 机器人语音交互
     *
     * @param strParam
     * @return
     */
    @GetMapping("/getRobotData")
    public AjaxResult getRobotData(@RequestParam String strParam) throws Exception {
        if (StringUtils.isEmpty(strParam)) {
            return AjaxResult.error(ExceptionEnum.NULL_REQUEST_PARAM.getErrorMsg());
        }
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername(BaseConstants.USER_NAME);
        loginInfo.setPassword(BaseConstants.PASS_WORD);
        //获取token
        String token = rosterHttpClientService.getToken(loginInfo);
        //获取fAccess
        String fAccess = rosterHttpClientService.getFAccess(token);
        //调取值班系统排班接口
        rosterHttpClientService.doRostering(token, fAccess);
        //语音播报信息
        String resp = rosterService.getRobotData(strParam);
        if (!StringUtils.isNotEmpty(resp)) {
            AjaxResult.error(ExceptionEnum.NULL_RESULT.getErrorMsg());
        }
        return AjaxResult.success(resp);
    }
}
