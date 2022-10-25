package com.ruoyi.dutymanagement.msm.mapper;

import com.ruoyi.dutymanagement.msm.domain.MsmEntity;
import com.ruoyi.dutymanagement.msm.domain.MsmInfoEntity;
import com.ruoyi.dutymanagement.msm.domain.param.MsmParam;
import com.ruoyi.dutymanagement.msm.domain.vo.MsmVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 短信管理数据交互层
 *
 * @Author fenghan
 */
@Repository
public interface ShortMessageMapper {
    /**
     * 查询短信列表
     *
     * @param msmParam
     * @return
     */
    List<MsmVO> getMsmList(MsmParam msmParam);

    /**
     * 根据id查询短信信息
     *
     * @param sendInfoId
     * @return
     */
    MsmVO getShortMessageById(int sendInfoId);

    /**
     * 查询短信详情
     *
     * @param sendInfoId
     * @return
     */
    List<MsmInfoEntity> getInfoById(int sendInfoId);

    /**
     * 新增短信主信息
     *
     * @param msmEntity
     */
    int addMsmInfo(MsmEntity msmEntity);

    /**
     * 新增短信子信息
     *
     * @param msmInfoEntity
     */
    void addItem(MsmInfoEntity msmInfoEntity);

    /**
     * 测试机器人接口
     *
     * @param status
     * @return
     */
    List<MsmVO> getRobotData(String status);

    /**
     * 根据id修改短信状态
     *
     * @param id
     */
    void updatMsmStatus(Integer id);
    /**
     * 当天未读新短信数
     * @param msmParam
     * @return
     */
    int getMsmCount(MsmParam msmParam);
}
