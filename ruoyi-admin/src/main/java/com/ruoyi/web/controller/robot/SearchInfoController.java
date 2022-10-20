package com.ruoyi.web.controller.robot;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.common.enums.ExceptionEnum;
import com.ruoyi.web.domian.SearchResponse;
import com.ruoyi.web.service.ICustomQAInfoService;
import com.ruoyi.web.service.ISearchInfoService;
import com.ruoyi.web.service.IWrResBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机器人查询信息控制层
 *
 * @Author fengzh
 */
@RestController
@RequestMapping("/search")
public class SearchInfoController {

    private static final Logger log = LoggerFactory.getLogger(SearchInfoController.class);

    @Autowired
    private ISearchInfoService searchInfoService;

    @Autowired
    private ICustomQAInfoService customQAInfoService;

    @Autowired
    private IWrResBService wrResBService;


    /**
     * 查询信息请求
     *
     * @param searchInfoStr searchInfoStr
     */
    @GetMapping("/info")
    public AjaxResult SearchInfo(@RequestParam(name = "str") String searchInfoStr) {
        if (StringUtils.isEmpty(searchInfoStr)) {
            return AjaxResult.error(ExceptionEnum.NULL_REQUEST_PARAM.getErrorMsg());
        }
        SearchResponse resp = searchInfoService.selectRiverInfoByCondition(searchInfoStr);
        if (!StringUtils.isNotEmpty(resp.getContent())) {
             resp.setContent(ExceptionEnum.NULL_RESULT.getErrorMsg());
        }
        return AjaxResult.success(resp);
    }

    /**
     * 知识问答请求
     *
     * @param qAndAInfoStr qAndAInfoStr
     */
    @GetMapping("/answer")
    public AjaxResult QAndAInfo(@RequestParam(name = "str") String qAndAInfoStr) {
        if (StringUtils.isEmpty(qAndAInfoStr)) {
            return AjaxResult.error(ExceptionEnum.NULL_REQUEST_PARAM.getErrorMsg());
        }
        SearchResponse resp = customQAInfoService.selectAnswerByCondition(qAndAInfoStr);
        if (!StringUtils.isNotEmpty(resp.getContent())) {
            resp.setContent(ExceptionEnum.NULL_RESULT.getErrorMsg());
        }
        return AjaxResult.success(resp);
    }

    /**
     * 机器人查询信息请求
     *
     * @param searchInfoStr searchInfoStr
     */
    @GetMapping("/info/v2")
    public AjaxResult SearchInfoV2(@RequestParam(name = "str") String searchInfoStr) {
        if (StringUtils.isEmpty(searchInfoStr)) {
            return AjaxResult.error(ExceptionEnum.NULL_REQUEST_PARAM.getErrorMsg());
        }
        SearchResponse resp = wrResBService.selectInfo(searchInfoStr);
        if (!StringUtils.isNotEmpty(resp.getContent())) {
            resp.setContent(ExceptionEnum.NULL_RESULT.getErrorMsg());
        }
        return AjaxResult.success(resp);
    }

}



