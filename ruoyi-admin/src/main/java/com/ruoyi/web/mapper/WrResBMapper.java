package com.ruoyi.web.mapper;


import com.ruoyi.web.domian.SearchRequest;
import com.ruoyi.web.domian.WrResB;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 水库项目 数据层
 *
 */
@Repository
@MapperScan
public interface WrResBMapper {

    /**
     * 条件查询
     *
     * @param req 查询参数
     * @return 信息集合
     */
    public List<WrResB> selectInfoList(SearchRequest req);

    /**
     * 查询所有名称
     *
     * @return 关键字结果集
     */
    public List<String> selectName();
}
