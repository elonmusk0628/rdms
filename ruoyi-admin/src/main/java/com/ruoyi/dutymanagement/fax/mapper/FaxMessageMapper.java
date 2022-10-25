package com.ruoyi.dutymanagement.fax.mapper;

import com.ruoyi.dutymanagement.fax.domain.FaxEntity;
import com.ruoyi.dutymanagement.fax.domain.FaxInfoEntity;
import com.ruoyi.dutymanagement.fax.domain.param.FaxParam;
import com.ruoyi.dutymanagement.fax.domain.vo.FaxVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 传真管理数据交互层
 *
 * @Author fenghan
 */
@Repository
public interface FaxMessageMapper {
    /**
     * 查询传真信息列表
     *
     * @param faxParam
     * @return
     */
    List<FaxVO> getFaxList(FaxParam faxParam);

    /**
     * 查询传真信息
     *
     * @param mainId
     * @return
     */
    FaxVO getFaxById(Integer mainId);

    /**
     * 查询传真信息
     *
     * @param fileManageId
     * @return
     */
    FaxVO getFaxByManageId(String fileManageId);

    /**
     * 查询传真信息详情
     *
     * @param mainId
     * @return
     */
    List<FaxInfoEntity> getFaxInfoById(Integer mainId);

    /**
     * 新增传真信息
     *
     * @param faxEntity
     */
    int addFaxInfo(FaxEntity faxEntity);

    /**
     * 新增传真子信息
     *
     * @param faxInfoEntity
     */
    void addItem(FaxInfoEntity faxInfoEntity);

    /**
     * 查询播报传真信息
     *
     * @param status
     * @return
     */
    List<FaxVO> getFaxByStatus(String status);

    /**
     * 修改传真状态为已读 0未读1已读
     *
     * @param id
     */
    void updateStatus(Integer id);

    /**
     * 当天未读新传真数
     * @param faxParam
     * @return
     */
    int getFaxCount(FaxParam faxParam);
}
