package com.ruoyi.dutymanagement.tel.service.impl;

import com.ruoyi.dutymanagement.msm.util.DateUtils;
import com.ruoyi.dutymanagement.tel.domain.param.TelInfoParam;
import com.ruoyi.dutymanagement.tel.domain.vo.TelInfoVO;
import com.ruoyi.dutymanagement.tel.mapper.TelMessageMapper;
import com.ruoyi.dutymanagement.tel.service.ITelMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 电话服务业务逻辑层
 *
 * @Author fenghan
 */
@Service
@Transactional
public class TelMessageServiceImpl implements ITelMessageService {
    @Autowired
    private TelMessageMapper telMessageMapper;

    /**
     * 查询通话记录信息列表
     *
     * @param telInfoParam
     * @return
     */
    @Override
    public List<TelInfoVO> getTelList(TelInfoParam telInfoParam) {
        List<TelInfoVO> telInfoVOList = telMessageMapper.getTelList(telInfoParam);
        return telInfoVOList;
    }

    /**
     * 根据id查询通话记录详情
     *
     * @param id
     * @return
     */
    @Override
    public TelInfoVO getTelInfoById(String id) {
        TelInfoVO telInfoVO = telMessageMapper.getTelInfoById(Integer.valueOf(id));
        return telInfoVO;
    }

    /**
     * 与机器人接口
     *
     * @return
     */
    @Override
    public String getRobotData(String status) {
        String resultStr = null;
        //根据状态查询出未读来电记录
        List<TelInfoVO> telInfoVOList = telMessageMapper.getRobotData(status);
        if (telInfoVOList.size() == 1) {
            for (TelInfoVO telInfoVO : telInfoVOList) {
                String theTelTime = DateUtils.dateRurnString(telInfoVO.getTelTime());
                String theElectricity = telInfoVO.getTheElectricityUnit();
                String theUserName = telInfoVO.getUserName();
                String theTel = telInfoVO.getTel();
                resultStr = theTelTime + theElectricity + "的" + theUserName + "来电，来电号码为：" + theTel + "请及时查看！";
                //将播报的来电状态修改为已读1
                telMessageMapper.updateTelStatus(telInfoVO.getId());
            }
        } else if (telInfoVOList.size() > 1) {
            resultStr = "有" + telInfoVOList.size() + "条来电记录，请及时查看";
            for (TelInfoVO telInfoVO : telInfoVOList) {
                //将多条播报的来电状态修改为已读1
                telMessageMapper.updateTelStatus(telInfoVO.getId());
            }
        } else {
            return null;
        }
        return resultStr;
    }
}
