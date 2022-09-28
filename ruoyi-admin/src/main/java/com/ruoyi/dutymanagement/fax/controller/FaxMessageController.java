package com.ruoyi.dutymanagement.fax.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.dutymanagement.fax.domain.param.FaxParam;
import com.ruoyi.dutymanagement.fax.domain.vo.FaxVO;
import com.ruoyi.dutymanagement.fax.service.IFaxMessageService;
import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;
import com.ruoyi.dutymanagement.msm.service.IHttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 传真管理控制层
 *
 * @Author fenghan
 */
@RestController()
@RequestMapping("/fax/message")
public class FaxMessageController extends BaseController {

    @Autowired
    private IFaxMessageService faxMessageService;

    @Autowired
    private IHttpClientService httpClientService;


    /**
     * 查询传真信息列表
     *
     * @param faxParam
     * @return
     */
    @PreAuthorize("@ss.hasPermi('fax:message:list')")
        @GetMapping("/list")
    public TableDataInfo list(FaxParam faxParam) {
        startPage();
        List<FaxVO> faxVOList = faxMessageService.list(faxParam);
        return getDataTable(faxVOList);
    }

    /**
     * 查询传真信息详情
     *
     * @param mainId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('fax:message:getFaxInfoById')")
    @GetMapping("/getFaxInfoById/{mainId}")
    public AjaxResult getFaxInfoById(@PathVariable String mainId) {
        if (mainId == null || "".equals(mainId)) {
            AjaxResult.error("mainId参数不能为空！");
        }
        FaxVO faxVO = faxMessageService.getFaxInfoById(mainId);
        return AjaxResult.success(faxVO);
    }

    /**
     * 调其它系统接口
     *
     * @return
     */
    @GetMapping("/getJsonObject")
    public AjaxResult getJsonObject(LoginInfo loginInfo) throws Exception {
        //获取token
        String token = httpClientService.getToken(loginInfo);
        //获取fAccess
        String fAccess = httpClientService.getFAccess(token);
        String jsonObject = faxMessageService.getJsonObject(token,fAccess);
        return AjaxResult.success(jsonObject);
    }

    /**
     * 与机器人接口
     *
     * @return
     */
    @GetMapping("/getRobotData")
    public AjaxResult getRobotData(@RequestParam String status) {
        if ((status == null) || "".equals(status)) {
            return AjaxResult.error("status参数不能为空！");
        }
        String messageContent = faxMessageService.getRobotData(status);
        if (null == messageContent) {
            AjaxResult.error("没有新消息！");
        }
        return AjaxResult.success(messageContent);
    }
}
