package com.ruoyi.web.controller.customQAInfo;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.common.enums.ExceptionEnum;
import com.ruoyi.web.domian.CustomQAInfo;
import com.ruoyi.web.domian.CustomQAInfoRequest;
import com.ruoyi.web.service.ICustomQAInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 自定义问答控制层
 *
 * @Author fengzh
 */
@RestController
@RequestMapping("/CustomQA")
public class CustomQAInfoController {

    @Autowired
    ICustomQAInfoService customQAInfoService;

    private static final Logger log = LoggerFactory.getLogger(CustomQAInfoController.class);

    /**
     * 查询自定义问答请求
     *
     * @param question 问题
     * @param answer 答案
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 页码
     * @param pageSize 页数
     */
    @GetMapping("/select")
    @PreAuthorize("@ss.hasPermi('CustomQA:select')")
    public AjaxResult selectAnswerInfo(@RequestParam(value = "question", required = false) String question,
                                       @RequestParam(value = "answer", required = false) String answer,
                                       @RequestParam(value = "start_time", required = false) String startTime,
                                       @RequestParam(value = "end_time", required = false) String endTime,
                                       @RequestParam(value = "page_num", required = false) Integer pageNum,
                                       @RequestParam(value = "page_size", required = false) Integer pageSize) {
        PageInfo<CustomQAInfo> answerInfoPage = customQAInfoService.selectAnswerInfoList(question, answer, startTime, endTime, pageNum, pageSize);
        List<CustomQAInfo> AnswerInfos = answerInfoPage.getList();
        return AjaxResult.success(answerInfoPage);
    }

    /**
     * 添加自定义问答请求
     *
     * @param req 请求体
     */
    @PostMapping("/add")
    @PreAuthorize("@ss.hasPermi('CustomQA:add')")
    public AjaxResult addAnswerInfo(@Validated @RequestBody CustomQAInfo req) {
            int i = customQAInfoService.addAnswerInfo(req);
            if (i > 0) {
                return AjaxResult.success();
            } else {
                return AjaxResult.error(ExceptionEnum.ADD_FAILED.getErrorMsg());
            }

    }

    /**
     * 修改自定义问答请求
     *
     * @param req 请求体
     */
    @PostMapping("/update")
    @PreAuthorize("@ss.hasPermi('CustomQA:update')")
    public AjaxResult updateAnswerInfo(@Validated @RequestBody CustomQAInfo req) {
            int i = customQAInfoService.updateAnswerInfo(req);
            if (i > 0) {
                return AjaxResult.success();
            } else {
                return AjaxResult.error(ExceptionEnum.UPDATE_FAILED.getErrorMsg());
            }
    }

    /**
     * 删除自定义问答请求
     *
     * @param ids id
     */
    @GetMapping("/delete")
    @PreAuthorize("@ss.hasPermi('CustomQA:delete')")
    public AjaxResult deleteAnswerInfo(@RequestParam(value = "id", required = true) List<Integer> ids) {
        int i = customQAInfoService.deleteAnswerInfo(ids);
        if (i > 0) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error(ExceptionEnum.DELETE_FAILED.getErrorMsg());
        }
    }

    /**
     * 下载自定义问答模板
     *
     * @param response response
     */
    @PostMapping("/downloadTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<CustomQAInfo> util = new ExcelUtil<CustomQAInfo>(CustomQAInfo.class);
        util.importTemplateExcel(response, "自定义问答模板");
    }

    /**
     * 批量导入自定义问答
     *
     * @param file file
     */
    @PostMapping("/importAnswerInfo")
    @PreAuthorize("@ss.hasPermi('CustomQA:importAnswerInfo')")
    public AjaxResult addAnswerInfoTemplate(MultipartFile file) throws Exception
    {
        ExcelUtil<CustomQAInfo> util = new ExcelUtil<CustomQAInfo>(CustomQAInfo.class);
        List<CustomQAInfo> answerInfoList = util.importExcel(file.getInputStream());
        String message = customQAInfoService.addAnswerTemplate(answerInfoList);
        return AjaxResult.success(message);
    }

    /**
     * 导出问答列表
     *
     * @param response response
     * @param req 自定义问答请求体
     */
    @PostMapping("/export")
    @PreAuthorize("@ss.hasPermi('CustomQA:export')")
    @Log(title = "自定义知识问答列表", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, CustomQAInfoRequest req)
    {
        PageInfo<CustomQAInfo> answerInfoPage = customQAInfoService.selectAnswerInfoList(req.getQuestion(), req.getAnswer(),
                req.getStartTime(), req.getEndTime(), req.getPageNum(), req.getPageSize());
        List<CustomQAInfo> AnswerInfosList = answerInfoPage.getList();
        ExcelUtil<CustomQAInfo> util = new ExcelUtil<CustomQAInfo>(CustomQAInfo.class);
        util.exportExcel(response, AnswerInfosList, "自定义知识问答");
    }

}