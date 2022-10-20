package com.ruoyi.web.service;

import com.ruoyi.web.domian.SearchResponse;

import javax.jws.WebService;
import java.util.List;

/**
 * 水库项目 服务层
 *
 */
@WebService
public interface IWrResBService {

    /**
     * 查询参数配置信息
     *
     * @param str 前台请求的字符串
     * @return 河道信息集合
     */
    public SearchResponse selectInfoByCondition(String str);

    /**
     * 查询所有名称
     *
     * @return 关键字结果集
     */
    public List<String> selectName();

    /**
     * 查询信息(水库，河道)
     *
     * @param str 前台请求的字符串
     * @return 河道水库等信息
     */
    public SearchResponse selectInfo(String str);



}
