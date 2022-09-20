package com.ruoyi.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.web.domian.KeyWordInfo;
import com.ruoyi.web.mapper.KeyWordMapper;
import com.ruoyi.web.service.IKeyWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * 关键字 服务层
 *
 * @author ruoyi
 */
@Service
public class KeyWordServiceImpl implements IKeyWordService {

    @Autowired
    KeyWordMapper keyWordMapper;

    private static final String FAIL_PATH = "D:/CodeResource/rdms/rdms/ruoyi-admin/src/main/resources/ik_analyzer/ext.dic";


    /**
     * 查询关键字请求
     *
     * @param keyWord 关键字
     * @param type 类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    @Override
    public PageInfo<KeyWordInfo> selectKeyWordInfoList(String keyWord, Integer type, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<KeyWordInfo> keyWordInfos = keyWordMapper.selectKeyWordInfoList(keyWord, type, startTime, endTime);
        return new PageInfo<KeyWordInfo>(keyWordInfos);
    }

    /**
     * 新增关键字请求
     *
     * @param req 关键字请求体
     */
    @Override
    @Transactional(rollbackFor = IOException.class)
    public int addKeyWordInfo(KeyWordInfo req) throws IOException {
        // 1.写入数据库
        req.setCreateTime(new Date());
        int i = keyWordMapper.addKeyWordInfo(req);

        // 2.写入关键字词典ext.dic
        String keyWord = req.getKeyWord();
        writeToDocument(FAIL_PATH, keyWord);
        return i;
    }

    /**
     * 修改关键字请求
     *
     * @param req 关键字请求体
     */
    @Override
    public int updateKeyWordInfo(KeyWordInfo req) {
        req.setUpdateTime(new Date());
        int i = keyWordMapper.updateKeyWordInfo(req);
        return i;
    }

    /**
     * 删除关键字请求
     *
     * @param id 关键字请求体
     */
    @Override
    public int deleteKeyWordInfo(Integer id) {
        return keyWordMapper.deleteWordInfo(id);
    }

    public void writeToDocument(String path, String content) throws IOException {
        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("\r\n");
        bufferedWriter.write(content);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

}
