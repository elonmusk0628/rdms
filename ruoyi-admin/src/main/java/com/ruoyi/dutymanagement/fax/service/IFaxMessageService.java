package com.ruoyi.dutymanagement.fax.service;

import com.ruoyi.dutymanagement.fax.domain.param.FaxParam;
import com.ruoyi.dutymanagement.fax.domain.vo.FaxVO;

import java.util.List;

/**
 * 传真管理服务层
 *
 * @Author fenghan
 */
public interface IFaxMessageService {
    /**
     * 查询传真信息列表
     *
     * @param faxParam
     * @return
     */
    List<FaxVO> getFaxList(FaxParam faxParam);

    /**
     * 查询传真信息详情
     *
     * @param mainId
     * @return
     */
    FaxVO getFaxInfoById(String mainId);

    /**
     * 调其它系统接口
     *
     * @return
     */
    String getJsonObject(String token,String fAccess) throws Exception;

    /**
     * 机器人接口
     *
     * @param status
     * @return
     */
    String getRobotData(String status);
    /**
     * 当天未读新传真数
     * @param faxParam
     * @return
     */
    int getFaxCount(FaxParam faxParam);

}
