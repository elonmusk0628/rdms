package com.ruoyi.dutymanagement.tel.service;

import com.ruoyi.dutymanagement.tel.domain.param.TelInfoParam;
import com.ruoyi.dutymanagement.tel.domain.vo.TelInfoVO;

import java.util.List;

/**
 * 电话管理服务层
 *
 * @Author fenghan
 * @Date 2022-09-16
 */
public interface ITelMessageService {
    /**
     * 查询通话记录信息列表
     *
     * @param telInfoParam
     * @return
     */
    List<TelInfoVO> getTelList(TelInfoParam telInfoParam);

    /**
     * 根据id查询通话记录详情
     *
     * @param id
     * @return
     */
    TelInfoVO getTelInfoById(String id);

    /**
     * 与机器人接口
     *
     * @param status
     * @return
     */
    String getRobotData(String status);

    /**
     * 当天未读新来电数
     *
     * @param telInfoParam
     * @return
     */
    int getTelCount(TelInfoParam telInfoParam);
}
