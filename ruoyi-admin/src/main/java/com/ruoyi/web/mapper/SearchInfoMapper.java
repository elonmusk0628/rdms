package com.ruoyi.web.mapper;


import com.ruoyi.web.domian.SearchInfo;
import com.ruoyi.web.domian.SearchRequest;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 河流信息 数据层
 *
 */
@Repository
@MapperScan
public interface SearchInfoMapper {

    /**
     * 条件查询
     *
     * @param req 查询参数
     * @return 信息集合
     */
    public List<SearchInfo> selectRiverInfoList(SearchRequest req);
}
