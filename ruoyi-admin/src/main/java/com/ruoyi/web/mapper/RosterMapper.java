package com.ruoyi.web.mapper;

import com.ruoyi.web.domian.RosterEntity;
import com.ruoyi.web.domian.RosteringEntity;
import com.ruoyi.web.domian.vo.RosteringVO;
import org.springframework.stereotype.Repository;

/**
 * 值班 数据交互层
 *
 * @Author fenghan
 */
@Repository
public interface RosterMapper {
    /**
     * 根据排班id查询排班信息
     *
     * @param scheduleId
     * @return
     */
    RosteringVO getRosterByScheduleId(Integer scheduleId);

    /**
     * 新增值班信息
     *
     * @param rosteringEntity
     * @return
     */
    int addRosteringInfo(RosteringEntity rosteringEntity);

    /**
     * 新增成员信息
     *
     * @param rosterEntity
     * @return
     */
    int addRosterInfo(RosterEntity rosterEntity);

    /**
     * 根据时间查询值班信息表
     *
     * @param timeParam
     * @return
     */
    RosteringVO getRosterByTime(String timeParam);

    /**
     * 根据排班id和排班人员类型查询值班人员信息
     *
     * @param scheduleId
     * @param scheduleType
     * @return
     */
    RosterEntity getRosterByTypeAndId(int scheduleId, String scheduleType);
}
