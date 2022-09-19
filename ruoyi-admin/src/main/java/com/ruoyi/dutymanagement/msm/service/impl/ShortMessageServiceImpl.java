package com.ruoyi.dutymanagement.msm.service.impl;

import com.ruoyi.dutymanagement.msm.domain.MsmEntity;
import com.ruoyi.dutymanagement.msm.domain.MsmInfoEntity;
import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;
import com.ruoyi.dutymanagement.msm.domain.param.MsmParam;
import com.ruoyi.dutymanagement.msm.domain.vo.MsmVO;
import com.ruoyi.dutymanagement.msm.mapper.ShortMessageMapper;
import com.ruoyi.dutymanagement.msm.service.IHttpClientService;
import com.ruoyi.dutymanagement.msm.service.IShortMessageService;
import com.ruoyi.dutymanagement.msm.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 短信管理服务实现层
 *
 * @Author fenghan
 */
@Service
@Transactional
public class ShortMessageServiceImpl implements IShortMessageService {

    @Autowired
    private ShortMessageMapper shortMessageMapper;

    @Autowired
    private IHttpClientService httpPostClientService;

    /**
     * 查询短信列表
     *
     * @param msmParam
     * @return
     */
    @Override
    public List<MsmVO> list(MsmParam msmParam) {
        List<MsmVO> msmVOList = shortMessageMapper.list(msmParam);
        return msmVOList;
    }

    /**
     * 查询短信详情
     *
     * @param sendInfoId
     * @return
     */
    @Override
    public MsmVO getInfoById(String sendInfoId) {
        int mainId = Integer.valueOf(sendInfoId);
        //根据sendInfoId查询短信信息
        MsmVO msmVO = shortMessageMapper.getShortMessageById(mainId);
        //根据sendInfoId查询短信详情
        List<MsmInfoEntity> msmInfoEntityList = shortMessageMapper.getInfoById(mainId);
        msmVO.setMsmInfoList(msmInfoEntityList);
        return msmVO;
    }

    /**
     * 与机器人接口
     *
     * @return
     */
    @Override
    public String getJsonObject(String status) throws Exception {
        //调取值班管理系统短信接口
        String jsonObject = httpPostClientService.doMsm(status);
        return jsonObject;
    }

    /**
     * 机器人接口
     *
     * @param status
     * @return
     */
    @Override
    public String getRobotData(String status) {
        List<MsmVO> msmVOList = shortMessageMapper.getRobotData(status);
        String messageContent = null;
        if (msmVOList.size() == 1) {
            for (MsmVO msmVO : msmVOList) {
                //发送时间
                String sendTime = DateUtils.dateRurnString(msmVO.getSendTime());
                //署名
                String signaTure = msmVO.getSignaTure();
                messageContent = sendTime + signaTure + "来了一条新短消息,请注意查收！";
                //修改已播报信息状态为已读1
                shortMessageMapper.updatMsmStatus(msmVO.getId());
            }
        } else if (msmVOList.size() > 1) {
            messageContent = "有" + msmVOList.size() + "新短消息，请及时查收！";
            for (MsmVO msmVO : msmVOList) {
                //修改多条已播报信息状态为已读1
                shortMessageMapper.updatMsmStatus(msmVO.getId());
            }
        } else {
            return null;
        }

        return messageContent;
    }

    @Override
    public void add(MsmParam msmParam) throws ParseException {
        MsmEntity msmEntity = new MsmEntity();
        msmEntity.setAgeing(msmParam.getAgeing());
        Date sendTime = DateUtils.stringTurnDate(msmParam.getSendTime());
        msmEntity.setSendTime(sendTime);
        msmEntity.setBusinessType(msmParam.getBusinessType());
        msmEntity.setStatus(msmParam.getStatus());
        msmEntity.setContent(msmParam.getContent());
        msmEntity.setSendCount(msmParam.getSendCount());
        msmEntity.setFailCount(msmParam.getFailCount());
        msmEntity.setSignaTure(msmParam.getSignaTure());
        msmEntity.setSuccess(msmParam.getSuccess());
        shortMessageMapper.add(msmEntity);
    }

    /**
     * 获取token
     *
     * @param loginInfo
     * @return
     */
    @Override
    public String getToken(LoginInfo loginInfo) throws Exception {
        String token = httpPostClientService.getToken(loginInfo);
        return token;
    }
}
