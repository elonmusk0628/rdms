package com.ruoyi.web.mapper;

import com.ruoyi.web.domian.RosterEntity;
import com.ruoyi.web.domian.RosteringEntity;
import com.ruoyi.web.domian.vo.RosteringVO;
import org.springframework.stereotype.Repository;

@Repository
public interface RosterMapper {

    RosteringVO getRosterByScheduleId(Integer scheduleId);

    int addRosteringInfo(RosteringEntity rosteringEntity);

    int addRosterInfo(RosterEntity rosterEntity);
}
