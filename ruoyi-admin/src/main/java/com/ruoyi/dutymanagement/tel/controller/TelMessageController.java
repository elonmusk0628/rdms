package com.ruoyi.dutymanagement.tel.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.dutymanagement.msm.service.IHttpClientService;
import com.ruoyi.dutymanagement.tel.domain.param.TelInfoParam;
import com.ruoyi.dutymanagement.tel.domain.vo.TelInfoVO;
import com.ruoyi.dutymanagement.tel.service.ITelMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 电话管理
 *
 * @Author fenghan
 */
@RestController
@RequestMapping("/tel/message")
public class TelMessageController extends BaseController {

    @Autowired
    private ITelMessageService telMessageService;

    @Autowired
    private IHttpClientService httpClientService;

    /**
     * 查询通话记录信息列表
     *
     * @param telInfoParam
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(TelInfoParam telInfoParam) {
        startPage();
        List<TelInfoVO> telVOList = telMessageService.list(telInfoParam);
        return getDataTable(telVOList);
    }

    /**
     * 根据id查询通话记录详情
     *
     * @param id
     * @return
     */
    @GetMapping("/getTelInfoById/{id}")
    public AjaxResult getTelInfoById(@PathVariable String id) {
        TelInfoVO telInfoVO = telMessageService.getTelInfoById(id);
        return AjaxResult.success(telInfoVO);
    }

    /**
     * 调值班管理系统电话接口
     *
     * @param status
     * @return
     * @throws Exception
     */
    @GetMapping("/doTel")
    public AjaxResult doTel(@RequestParam String status) throws Exception {
        String jsonStr = httpClientService.doTel(status);
        return AjaxResult.success(jsonStr);
    }
    /**
     * 与机器人接口
     *
     * @return
     */
    @GetMapping("/getRobotData")
    public AjaxResult getRobotData(@RequestParam String status) {
        if (status == null || "".equals(status)) {
            return AjaxResult.error("status参数不能为空！");
        }
        String messageContent = telMessageService.getRobotData(status);
        if (null == messageContent) {
            AjaxResult.error("没有新消息！");
        }
        return AjaxResult.success(messageContent);
    }
}
