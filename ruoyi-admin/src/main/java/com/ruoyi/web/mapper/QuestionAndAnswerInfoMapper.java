package com.ruoyi.web.mapper;


import com.ruoyi.web.domian.QuestionAndAnswerInfo;
import com.ruoyi.web.domian.QuestionAndAnswerRequest;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 知识问答 数据层
 *
 */
@Repository
@MapperScan
public interface QuestionAndAnswerInfoMapper {

    /**
     * 模糊匹配问答信息（机器人端）
     *
     * @param req 查询参数
     * @return 回答返回体
     */
    public List<QuestionAndAnswerInfo> selectAnswerInfoList(QuestionAndAnswerRequest req);

    /**
     * 自定义问答查询
     *
     * @param question 问题
     * @param answer 答案
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    public List<QuestionAndAnswerInfo> selectAnswerList(@Param("question")String question, @Param("answer")String answer,
                                                   @Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 自定义问答新增
     *
     * @param req 请求体
     * @return 问答信息集合
     */
    public int addAnswerInfo(QuestionAndAnswerInfo req);

    /**
     * 自定义问答修改
     *
     * @param req 请求体
     * @return 结果行数
     */
    public int updateAnswerInfo(QuestionAndAnswerInfo req);

    /**
     * 自定义问答删除
     *
     * @param id id
     * @return 结果行数
     */
    public int deleteAnswerInfo(Integer id);

    /**
     * 查询问答信息
     *
     * @param question keyWord
     * @return 结果行数
     */
    public QuestionAndAnswerInfo selectByQuestion(@Param("question")String question);
}
