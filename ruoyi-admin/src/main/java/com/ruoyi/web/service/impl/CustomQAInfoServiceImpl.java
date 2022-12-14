package com.ruoyi.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.common.constant.BaseConstants;
import com.ruoyi.web.domian.CustomQAInfo;
import com.ruoyi.web.domian.CustomQAInfoRequest;
import com.ruoyi.web.domian.SearchResponse;
import com.ruoyi.web.mapper.CustomQAInfoMapper;
import com.ruoyi.web.service.ICustomQAInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 知识问答 服务层实现类
 *
 * @author fengZh
 */
@Service
public class CustomQAInfoServiceImpl implements ICustomQAInfoService {

    @Autowired
    CustomQAInfoMapper qAndAInfoMapper;

    private static final Logger log = LoggerFactory.getLogger(CustomQAInfoServiceImpl.class);


    /**
     * 机器人知识问答
     *
     * @param qAndAInfoStr 查询参数
     * @return 信息集合返回体
     */
    @Override
    public SearchResponse selectAnswerByCondition(String qAndAInfoStr) {
        SearchResponse resp = new SearchResponse();
        CustomQAInfoRequest req = new CustomQAInfoRequest();
        // 去除机器人入参末尾的句号
        String newQAStr = qAndAInfoStr.substring(0, qAndAInfoStr.length() - 1);
        // 如果入参有数字，将大写汉字转为数字
        String replaceStr = packageHour(newQAStr);
        req.setQuestion(replaceStr);
        List<CustomQAInfo> customQAInfo = qAndAInfoMapper.selectAnswerInfoList(req);
        if (CollectionUtils.isNotEmpty(customQAInfo)) {
            resp.setContent(customQAInfo.get(0).getAnswer());
        }
        return resp;
    }

    /**
     * 自定义问答查询
     *
     * @param question 问题
     * @param answer 答案
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    @Override
    public PageInfo<CustomQAInfo> selectAnswerInfoList(String question, String answer, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CustomQAInfo> AnswerInfos = qAndAInfoMapper.selectAnswerList(question, answer, startTime, endTime);
        return new PageInfo<CustomQAInfo>(AnswerInfos);
    }

    /**
     * 自定义问答新增
     *
     * @param req 请求体
     * @return 关键字信息集合
     */
    @Override
    public int addAnswerInfo(CustomQAInfo req) {
        // 1.校验问题是否录入
        CustomQAInfo info = qAndAInfoMapper.selectByQuestion(req.getQuestion());
        if (info == null) {
            // 2.写入数据库
            req.setCreateTime(new Date());
            req.setUpdateTime(new Date());
            int i = qAndAInfoMapper.addAnswerInfo(req);
            return i;
        } else {
            return 0;
        }
    }

    /**
     * 自定义问答修改
     *
     * @param req 请求体
     * @return 结果行数
     */
    @Override
    public int updateAnswerInfo(CustomQAInfo req) {
            req.setUpdateTime(new Date());
            int i = qAndAInfoMapper.updateAnswerInfo(req);
            return i;
    }

    /**
     * 自定义问答删除
     *
     * @param ids ids
     * @return 结果行数
     */
    @Override
    public int deleteAnswerInfo(List<Integer> ids) {
        int i = 0;
        for (Integer id : ids) {
            i = qAndAInfoMapper.deleteAnswerInfo(id);
        }
        return i;
    }

    /**
     * 自定义问答批量新增
     *
     * @param AnswerInfoList list
     * @return String
     */
    @Override
    public String addAnswerTemplate(List<CustomQAInfo> AnswerInfoList) {
        if (StringUtils.isNull(AnswerInfoList) || AnswerInfoList.size() == 0)
        {
            throw new ServiceException("导入问答数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (CustomQAInfo info : AnswerInfoList)
        {
            try {
                if (StringUtils.isNotEmpty(info.getQuestion()) && StringUtils.isNotEmpty(info.getAnswer())) {
                        // 校验该问题是否录入
                    CustomQAInfo answer = qAndAInfoMapper.selectByQuestion(info.getQuestion());
                        if (answer == null) {
                            successNum++;
                            // 不存在写入数据库
                            info.setCreateTime(new Date());
                            info.setUpdateTime(new Date());
                            qAndAInfoMapper.addAnswerInfo(info);
                            successMsg.append("<br/>" + successNum + ",自定义问答 " + info.getQuestion() + " 导入成功");
                        } else {
                            failureNum++;
                            failureMsg.append("<br/>" + failureNum + ",自定义问答 " + info.getQuestion() + " 已存在,请重新导入");
                        }
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + ",自定义问答 " + info.getQuestion() + ",问题或答案内容不能为空");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + ",自定义问答 " + info.getQuestion() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 汉字转阿拉伯柱数字
     *
     * @param qAStr 自定义问答字符串
     * @ture String 转换后的字符串
     */
    private String packageHour(String qAStr) {
        String replaceStr = "";
        if (qAStr.contains(BaseConstants.ZERO_CLOCK)) {
            replaceStr = qAStr.replace(BaseConstants.ZERO_CLOCK,"0");
            return replaceStr;
        }
        if (qAStr.contains(BaseConstants.ONE_CLOCK)) {
            replaceStr = qAStr.replace(BaseConstants.ONE_CLOCK,"1");
            return replaceStr;
        }
        if (qAStr.contains(BaseConstants.TWO_NUMBER) || qAStr.contains(BaseConstants.TWO_CLOCK) ) {
            replaceStr = qAStr.replace(BaseConstants.TWO_NUMBER,"2");
            replaceStr = replaceStr.replace(BaseConstants.TWO_CLOCK,"2");
            return replaceStr;
        }
        if (qAStr.contains(BaseConstants.THREE_CLOCK)) {
            replaceStr =  qAStr.replace(BaseConstants.THREE_CLOCK, "3");
            return replaceStr;
        }
        if (qAStr.contains(BaseConstants.FOUR_CLOCK)) {
            replaceStr = qAStr.replace(BaseConstants.FOUR_CLOCK,"4");
            return replaceStr;
        }
        if (qAStr.contains(BaseConstants.FIVE_CLOCK)) {
            replaceStr = qAStr.replace(BaseConstants.FIVE_CLOCK,"5");
            return replaceStr;
        }
        if (qAStr.contains(BaseConstants.SIX_CLOCK)) {
            replaceStr = qAStr.replace(BaseConstants.SIX_CLOCK,"6");
            return replaceStr;
        }
        if (qAStr.contains(BaseConstants.SEVEN_CLOCK)) {
            replaceStr = qAStr.replace(BaseConstants.SEVEN_CLOCK,"7");
            return replaceStr;
        }
        if (qAStr.contains(BaseConstants.EIGHT_CLOCK)) {
            replaceStr = qAStr.replace(BaseConstants.EIGHT_CLOCK,"8");
            return replaceStr;
        }
        if (qAStr.contains(BaseConstants.NINE_CLOCK)) {
            replaceStr = qAStr.replace(BaseConstants.NINE_CLOCK,"9");
            return replaceStr;
        }
        if (qAStr.contains(BaseConstants.TEN_CLOCK)) {
            replaceStr = qAStr.replace(BaseConstants.TEN_CLOCK,"10");
            return replaceStr;
        }
        return qAStr;
    }

}
