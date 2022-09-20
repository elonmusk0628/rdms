package com.ruoyi.web.service;

import com.github.pagehelper.PageInfo;
import com.ruoyi.web.domian.KeyWordInfo;

import javax.jws.WebService;
import java.io.IOException;
import java.util.List;

/**
 * 关键字 服务层
 *
 */
@WebService
public interface IKeyWordService {

    /**
     * 查询关键字请求
     *
     * @param keyWord 关键字
     * @param type 类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    public PageInfo<KeyWordInfo> selectKeyWordInfoList(String keyWord, Integer type, String startTime, String endTime, Integer pageNum, Integer pageSize);

    /**
     * 新增关键字信息
     *
     * @param req 请求体
     * @return 关键字信息集合
     */
    public int addKeyWordInfo(KeyWordInfo req) throws IOException;

    /**
     * 修改关键字信息
     *
     * @param req 请求体
     * @return 结果行数
     */
    public int updateKeyWordInfo(KeyWordInfo req);

    /**
     * 删除关键字请求
     *
     * @param id id
     * @return 结果行数
     */
    public int deleteKeyWordInfo(Integer id);

}
