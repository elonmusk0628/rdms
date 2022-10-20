package com.ruoyi.web.mapper;


import com.ruoyi.web.domian.SearchRequest;
import com.ruoyi.web.domian.WrStatB;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 河道项目 数据层
 *
 */
@Repository
@MapperScan
public interface WrStatBMapper {

    /**
     * 条件查询
     *
     * @param req 查询参数
     * @return 信息集合
     */
    public List<WrStatB> selectWadiInfoList(SearchRequest req);

}
