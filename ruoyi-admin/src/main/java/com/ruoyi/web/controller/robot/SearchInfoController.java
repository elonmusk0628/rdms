package com.ruoyi.web.controller.robot;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.common.enums.ExceptionEnum;
import com.ruoyi.web.domian.SearchResponse;
import com.ruoyi.web.service.ISearchInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchInfoController {

    private static final Logger log = LoggerFactory.getLogger(SearchInfoController.class);


    @Autowired
    private ISearchInfoService searchInfoService;


    /**
     * 查询信息请求
     *
     * @param str http请求对象
     */
    @GetMapping("/info")
    public AjaxResult SearchInfo(@RequestParam(name = "str") String str) {
        if (StringUtils.isEmpty(str)) {
            return AjaxResult.error(ExceptionEnum.NULL_REQUEST_PARAM.getErrorMsg());
        }
        SearchResponse resp = searchInfoService.selectRiverInfoByCondition(str);
        if (StringUtils.isNotEmpty(resp.getContent())) {
            return AjaxResult.success(resp);
        } else {
            return AjaxResult.error(ExceptionEnum.NULL_RESULT.getErrorMsg());
        }
    }

}


