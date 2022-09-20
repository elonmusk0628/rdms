package com.ruoyi.web.controller.keyword;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.common.enums.ExceptionEnum;
import com.ruoyi.web.domian.KeyWordInfo;
import com.ruoyi.web.service.IKeyWordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/key/word")
public class KeyWordController {

    @Autowired
    IKeyWordService keyWordService;

    private static final Logger log = LoggerFactory.getLogger(KeyWordController.class);

    /**
     * 查询关键字请求
     *
     * @param keyWord 关键字
     * @param type 类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    @GetMapping("/select")
    public AjaxResult selectKeyword(@RequestParam(value = "key_word", required = false) String keyWord,
                                    @RequestParam(value = "type", required = false) Integer type,
                                    @RequestParam(value = "start_time", required = false) String startTime,
                                    @RequestParam(value = "end_time", required = false) String endTime,
                                    @RequestParam(value = "page_num", required = false) Integer pageNum,
                                    @RequestParam(value = "page_size", required = false) Integer pageSize) {
        PageInfo<KeyWordInfo> keyWordInfoPageInfo = keyWordService.selectKeyWordInfoList(keyWord, type, startTime, endTime, pageNum, pageSize);
        List<KeyWordInfo> keyWordInfos = keyWordInfoPageInfo.getList();
        if (CollectionUtils.isEmpty(keyWordInfos)) {
            return AjaxResult.error(ExceptionEnum.NULL_RESULT.getErrorMsg());
        } else {
            return AjaxResult.success(keyWordInfoPageInfo);
        }
    }

    /**
     * 添加关键字请求
     *
     * @param req 请求体
     */
    @PostMapping("/add")
    public AjaxResult addKeyword(@Validated @RequestBody KeyWordInfo req) {
        try {
            int i = keyWordService.addKeyWordInfo(req);
            if (i > 0) {
                return AjaxResult.success();
            } else {
                return AjaxResult.error(ExceptionEnum.ADD_FAILED.getErrorMsg());
            }
        } catch (IOException e) {
            log.error(ExceptionEnum.WRITE_TO_DOCUMENT_FAILED.getErrorMsg(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改关键字请求
     *
     * @param req 请求体
     */
    @PostMapping("/update")
    public AjaxResult updateKeyword(@Validated @RequestBody KeyWordInfo req) {
            int i = keyWordService.updateKeyWordInfo(req);
            if (i > 0) {
                return AjaxResult.success();
            } else {
                return AjaxResult.error(ExceptionEnum.UPDATE_FAILED.getErrorMsg());
            }
    }

    /**
     * 删除关键字请求
     *
     * @param id id
     */
    @GetMapping("/delete")
    public AjaxResult deleteKeyWord(@RequestParam(value = "id", required = true) Integer id) {
        int i = keyWordService.deleteKeyWordInfo(id);
        if (i > 0) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error(ExceptionEnum.DELETE_FAILED.getErrorMsg());
        }
    }

}