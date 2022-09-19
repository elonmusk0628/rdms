package com.ruoyi.dutymanagement.tel.mapper;

import com.ruoyi.dutymanagement.tel.domain.TelInfoEntity;
import com.ruoyi.dutymanagement.tel.domain.param.TelInfoParam;
import com.ruoyi.dutymanagement.tel.domain.vo.TelInfoVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 电话管理数据交互层
 *
 * @Author fenghan
 */
@Repository
public interface TelMessageMapper {
    /**
     * 查询通话记录信息列表
     *
     * @param telInfoParam
     * @return
     */
    List<TelInfoVO> list(TelInfoParam telInfoParam);

    /**
     * 根据id查询通话记录详情
     *
     * @param id
     * @return
     */
    TelInfoVO getTelInfoById(Integer id);

    /**
     * 根据telRecordId查询电话记录
     *
     * @param telRecordId
     * @return
     */
    TelInfoVO getTelByTelRecordId(Integer telRecordId);

    /**
     * 新增电话记录信息
     *
     * @param telInfoEntity
     * @return
     */
    int add(TelInfoEntity telInfoEntity);

    /**
     * 查询id最大的一条数据
     *
     * @return
     */
    TelInfoVO getTelInfoMaxId();

    /**
     * 与机器人接口
     *
     * @param status
     * @return
     */
    List<TelInfoVO> getRobotData(String status);

    /**
     * 根据id修改来电状态
     *
     * @param id
     */
    void updateTelStatus(Integer id);
}
