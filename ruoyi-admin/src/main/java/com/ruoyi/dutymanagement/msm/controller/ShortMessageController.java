package com.ruoyi.dutymanagement.msm.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;
import com.ruoyi.dutymanagement.msm.domain.param.MsmParam;
import com.ruoyi.dutymanagement.msm.domain.vo.MsmVO;
import com.ruoyi.dutymanagement.msm.service.IHttpClientService;
import com.ruoyi.dutymanagement.msm.service.IShortMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * 短信管理
 *
 * @Author fenghan
 * @Date 2022-09-02
 */
@RestController
@RequestMapping("/msm/message")
public class ShortMessageController extends BaseController {

    @Autowired
    private IShortMessageService shortMessageService;

    @Autowired
    private IHttpClientService msmHttpPostClientService;

    /**
     * 查询短信列表
     *
     * @param msmParam
     * @return
     */
    @PreAuthorize("@ss.hasPermi('msm:message:list')")
    @GetMapping("/getMsmList")
    public TableDataInfo getMsmList(MsmParam msmParam) {
        startPage();
        List<MsmVO> msmVOList = shortMessageService.getMsmList(msmParam);
        return getDataTable(msmVOList);
    }

    /**
     * 查询短信详情
     *
     * @param sendInfoId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('msm:message:getInfoById')")
    @GetMapping("/getInfoById/{sendInfoId}")
    public AjaxResult getInfoById(@PathVariable String sendInfoId) {
        if (sendInfoId == null || "".equals(sendInfoId)) {
            return AjaxResult.error("sendInfoId不能为空！");
        }
        MsmVO msmVOList = shortMessageService.getInfoById(sendInfoId);
        return AjaxResult.success(msmVOList);
    }

    /**
     * 调其它系统接口
     *
     * @return
     */
    @PostMapping("/getJsonObject")
    public AjaxResult getJsonObject(@RequestBody LoginInfo loginInfo) throws Exception {
        //获取token
        String token = msmHttpPostClientService.getToken(loginInfo);
        String jsonObject = msmHttpPostClientService.doMsm(token);
        return AjaxResult.success(jsonObject);
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
        String messageContent = shortMessageService.getRobotData(status);
        if (null == messageContent) {
            AjaxResult.error("没有新消息！");
        }
        return AjaxResult.success(messageContent);
    }

    /**
     * 测试新增
     *
     * @param param
     * @return
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody MsmParam param) throws ParseException {
        shortMessageService.add(param);
        return AjaxResult.success("新增成功");
    }

    /**
     * 获取token
     *
     * @param
     * @return
     */
    @PostMapping("/getToken")
    public AjaxResult getToken(@RequestBody LoginInfo loginInfo) throws Exception {
        String token = shortMessageService.getToken(loginInfo);
        return AjaxResult.success(token);
    }
    /**
     * 当天未读新短信数
     * @param msmParam
     * @return
     */
    @GetMapping("/getMsmCount")
    public AjaxResult getMsmCount(MsmParam msmParam) {
        if (msmParam.getStatus() == null) {
            return AjaxResult.error("status参数不能为空！");
        }
        int msmCount = shortMessageService.getMsmCount(msmParam);
        return AjaxResult.success(msmCount);
    }
}
