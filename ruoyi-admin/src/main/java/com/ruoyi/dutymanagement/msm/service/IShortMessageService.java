package com.ruoyi.dutymanagement.msm.service;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.dutymanagement.msm.domain.MsmEntity;
import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;
import com.ruoyi.dutymanagement.msm.domain.param.MsmParam;
import com.ruoyi.dutymanagement.msm.domain.vo.MsmVO;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * 短信管理服务层
 *
 * @Author fenghan
 */
public interface IShortMessageService {
    /**
     * 查询短信列表
     *
     * @param msmParam
     * @return
     */
    List<MsmVO> list(MsmParam msmParam);

    /**
     * 查询短信详情
     *
     * @param sendInfoId
     * @return
     */
    MsmVO getInfoById(String sendInfoId);

    /**
     * 调其它系统接口
     *
     * @return
     */
    String getJsonObject(String status) throws Exception;

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
}
