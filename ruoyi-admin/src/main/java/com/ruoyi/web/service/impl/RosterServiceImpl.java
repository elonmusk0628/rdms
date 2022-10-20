package com.ruoyi.web.service.impl;

import com.ruoyi.dutymanagement.msm.util.DateUtils;
import com.ruoyi.web.common.constant.BaseConstants;
import com.ruoyi.web.common.enums.ExceptionEnum;
import com.ruoyi.web.domian.RosterEntity;
import com.ruoyi.web.domian.vo.RosteringVO;
import com.ruoyi.web.mapper.RosterMapper;
import com.ruoyi.web.service.IRosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 值班 业务逻辑层
 *
 * @Author fenghan
 */
@Service
public class RosterServiceImpl implements IRosterService {

    @Autowired
    private RosterMapper rosterMapper;

    /**
     * 机器人语音交互
     *
     * @param strParam
     * @return
     */
    @Override
    public String getRobotData(String strParam) {
        String timeParam = null;
        try {
            Map<Integer, String> esMap = ikAnalyzer(strParam);
            String strValue = esMap.get(1);
            if (BaseConstants.TODAY.equals(strValue)) {
                //今天
                timeParam = DateUtils.dateRurnStrFormat(new Date());
                //根据时间查询值班信息表
                RosteringVO rostering = rosterMapper.getRosterByTime(timeParam);
                if(rostering!=null){
                    /**根据排班id和排班人员类型查询值班人员信息 0:带班，1：值班，2：副班*/
                    //查询带班人员信息
                    RosterEntity leadShifInfo = rosterMapper.getRosterByTypeAndId(rostering.getScheduleId(), BaseConstants.ZERO);
                    //查询值班人员信息
                    RosterEntity beOnDutyInfo = rosterMapper.getRosterByTypeAndId(rostering.getScheduleId(), BaseConstants.ONE);
                    //查询副班人员信息
                    RosterEntity deputyShiftInfo = rosterMapper.getRosterByTypeAndId(rostering.getScheduleId(), BaseConstants.TWO);
                    //带班人昵称
                    String leadShifName = leadShifInfo.getNickName();
                    //值班人昵称
                    String beOnDutyName = beOnDutyInfo.getNickName();
                    //副班人昵称
                    String deputyShiftName = deputyShiftInfo.getNickName();
                    return "今日带班：" + leadShifName + ",值班：" + beOnDutyName + ",副班：" + deputyShiftName;
                }else {
                    return ExceptionEnum.NULL_RESULT.getErrorMsg();
                }
            } else if (BaseConstants.TOMORROW.equals(strValue)) {
                //明天
                timeParam = DateUtils.getTomorrow();
                //根据时间查询值班信息表
                RosteringVO rosterVO = rosterMapper.getRosterByTime(timeParam);
                if(rosterVO!=null){
                    /**根据排班id和排班人员类型查询值班人员信息 0:带班，1：值班，2：副班*/
                    //查询带班人员信息
                    RosterEntity leadShifInfo = rosterMapper.getRosterByTypeAndId(rosterVO.getScheduleId(), BaseConstants.ZERO);
                    //查询值班人员信息
                    RosterEntity beOnDutyInfo = rosterMapper.getRosterByTypeAndId(rosterVO.getScheduleId(), BaseConstants.ONE);
                    //查询副班人员信息
                    RosterEntity deputyShiftInfo = rosterMapper.getRosterByTypeAndId(rosterVO.getScheduleId(), BaseConstants.TWO);
                    //带班人昵称
                    String leadShifName = leadShifInfo.getNickName();
                    //值班人昵称
                    String beOnDutyName = beOnDutyInfo.getNickName();
                    //副班人昵称
                    String deputyShiftName = deputyShiftInfo.getNickName();
                    return "明日带班：" + leadShifName + ",值班：" + beOnDutyName + ",副班：" + deputyShiftName;
                }else {
                    return ExceptionEnum.NULL_RESULT.getErrorMsg();
                }
            } else if (BaseConstants.YESTER_DAY.equals(strValue)) {
                //昨天
                timeParam = DateUtils.getYesterDay();
                //根据时间查询值班信息表
                RosteringVO rosteringVO = rosterMapper.getRosterByTime(timeParam);
                if(rosteringVO!=null){
                    /**根据排班id和排班人员类型查询值班人员信息 0:带班，1：值班，2：副班*/
                    //查询带班人员信息
                    RosterEntity leadShifInfo = rosterMapper.getRosterByTypeAndId(rosteringVO.getScheduleId(), BaseConstants.ZERO);
                    //查询值班人员信息
                    RosterEntity beOnDutyInfo = rosterMapper.getRosterByTypeAndId(rosteringVO.getScheduleId(), BaseConstants.ONE);
                    //查询副班人员信息
                    RosterEntity deputyShiftInfo = rosterMapper.getRosterByTypeAndId(rosteringVO.getScheduleId(), BaseConstants.TWO);
                    //带班人昵称
                    String leadShifName = leadShifInfo.getNickName();
                    //值班人昵称
                    String beOnDutyName = beOnDutyInfo.getNickName();
                    //副班人昵称
                    String deputyShiftName = deputyShiftInfo.getNickName();
                    return "昨日带班：" + leadShifName + ",值班：" + beOnDutyName + ",副班：" + deputyShiftName;
                }else {
                    return ExceptionEnum.NULL_RESULT.getErrorMsg();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 解词分词
     *
     * @param searchInfoStr
     * @return
     * @throws IOException
     */
    public Map<Integer, String> ikAnalyzer(String searchInfoStr) throws IOException {
        Map<Integer, String> keyWordMap = new HashMap<>();
        StringReader stringReader = new StringReader(searchInfoStr.trim());
        IKSegmenter ik = new IKSegmenter(stringReader, true);
        Integer i = 0;
        Lexeme lex;
        while ((lex = ik.next()) != null) {
            i++;
            keyWordMap.put(i, lex.getLexemeText());
        }
        return keyWordMap;
    }
}
