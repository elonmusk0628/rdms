package com.ruoyi.dutymanagement.msm.service;

import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;
import com.ruoyi.dutymanagement.msm.domain.param.MsmParam;
import com.ruoyi.dutymanagement.msm.domain.vo.MsmVO;

import java.text.ParseException;
import java.util.List;

/**
 * 短信管理服务层
 *
 * @Author fenghan
 * @Date 2022-09-02
 */
public interface IShortMessageService {
    /**
     * 查询短信列表
     *
     * @param msmParam
     * @return
     */
    List<MsmVO> getMsmList(MsmParam msmParam);

    /**
     * 查询短信详情
     *
     * @param sendInfoId
     * @return
     */
    MsmVO getInfoById(String sendInfoId);

    /**
     * 测试方法
     *
     * @param status
     * @return
     */
    String getRobotData(String status);

    /**
     * 测试新增接口
     *
     * @param msmParam
     * @throws ParseException
     */
    void add(MsmParam msmParam) throws ParseException;

    /**
     * 获取token
     *
     * @return
     */
    String getToken(LoginInfo loginInfo) throws Exception;

    /**
     * 当天未读新短信数
     *
     * @param msmParam
     * @return
     */
    int getMsmCount(MsmParam msmParam);
}
