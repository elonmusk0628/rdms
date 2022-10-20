package com.ruoyi.web.service;

import com.github.pagehelper.PageInfo;
import com.ruoyi.web.domian.CustomQAInfo;
import com.ruoyi.web.domian.SearchResponse;

import javax.jws.WebService;
import java.util.List;

/**
 * 知识问答 服务层
 *
 */
@WebService
public interface ICustomQAInfoService {

    /**
     * 知识问答信息
     *
     * @param qAndAInfoStr 前台请求的字符串
     * @return 回答返回体
     */
    public SearchResponse selectAnswerByCondition(String qAndAInfoStr);

    /**
     * 自定义问答查询
     *
     * @param question 问题
     * @param answer 答案
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    PageInfo<CustomQAInfo> selectAnswerInfoList(String question, String answer,
                                                          String startTime, String endTime,
                                                          Integer pageNum, Integer pageSize);

    /**
     * 自定义问答新增
     *
     * @param req 请求体
     * @return 关键字信息集合
     */
    int addAnswerInfo(CustomQAInfo req);

    /**
     * 自定义问答修改
     *
     * @param req 请求体
     * @return 结果行数
     */
    int updateAnswerInfo(CustomQAInfo req);

    /**
     * 自定义问答删除
     *
     * @param ids ids
     * @return 结果行数
     */
    int deleteAnswerInfo(List<Integer> ids);

    /**
     * 自定义问答批量新增
     *
     * @param list list
     * @return String
     */
    String addAnswerTemplate(List<CustomQAInfo> list);

}
