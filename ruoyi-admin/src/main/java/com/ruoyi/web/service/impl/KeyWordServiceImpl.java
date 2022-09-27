package com.ruoyi.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.common.constant.BaseConstants;
import com.ruoyi.web.domian.KeyWordInfo;
import com.ruoyi.web.mapper.KeyWordMapper;
import com.ruoyi.web.service.IKeyWordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${keyword.filePath}")
    private String FILE_PATH;

    private static final Logger log = LoggerFactory.getLogger(KeyWordServiceImpl.class);


    /**
     * 查询关键字请求
     *
     * @param keyWord 关键字
     * @param type 类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    @Override
    public PageInfo<KeyWordInfo> selectKeyWordInfoList(String keyWord, String type, String startTime, String endTime, Integer pageNum, Integer pageSize) {
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
        // 1.校验关键字是否存在
        KeyWordInfo info = keyWordMapper.selectByKeyWord(req.getKeyWord(), req.getType());
        if (info == null) {
            // 2.写入数据库
            req.setCreateTime(new Date());
            req.setUpdateTime(new Date());
            int i = keyWordMapper.addKeyWordInfo(req);

            // 3.写入关键字词典ext.dic
            String keyWord = req.getKeyWord();
            writeToDocument(FILE_PATH, keyWord);
            return i;
        } else {
            return 0;
        }

    }

    /**
     * 修改关键字请求
     *
     * @param req 关键字请求体
     */
    @Override
    public int updateKeyWordInfo(KeyWordInfo req) {
        KeyWordInfo info = keyWordMapper.selectByKeyWord(req.getKeyWord(), req.getType());
        if (info == null) {
            req.setUpdateTime(new Date());
            int i = keyWordMapper.updateKeyWordInfo(req);
            return i;
        } else {
            return 0;
        }
    }

    /**
     * 删除关键字请求
     *
     * @param ids 关键字id集合
     */
    @Override
    public int deleteKeyWordInfo(List<Integer> ids) {
        int i = 0;
        for (Integer id : ids) {
            i = keyWordMapper.deleteWordInfo(id);
        }
        return i;
    }

    /**
     * 批量新增关键字
     *
     * @param keyWordInfoList keyWordInfoList
     * @return String
     */
    @Override
    @Transactional(rollbackFor = IOException.class)
    public String addKeyWordTemplate(List<KeyWordInfo> keyWordInfoList) throws IOException {
        if (StringUtils.isNull(keyWordInfoList) || keyWordInfoList.size() == 0)
        {
            throw new ServiceException("导入关键字数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (KeyWordInfo info : keyWordInfoList)
        {
            try {
                if (StringUtils.isNotEmpty(info.getType()) && StringUtils.isNotEmpty(info.getKeyWord())) {
                    if (BaseConstants.SHUI_KU.equals(info.getType()) || BaseConstants.HE_DAO.equals(info.getType())
                            || BaseConstants.JI_GOU.equals(info.getType())) {

                        // 验证该关键字是否存在
                        KeyWordInfo keyInfo = keyWordMapper.selectByKeyWord(info.getKeyWord(), info.getType());
                        if (keyInfo == null) {
                            successNum++;
                            // 1.不存在写入数据库
                            info.setCreateTime(new Date());
                            info.setUpdateTime(new Date());
                            keyWordMapper.addKeyWordInfo(info);
                            // 2.写入字典
                            writeToDocument(FILE_PATH, info.getKeyWord());
                            successMsg.append("<br/>" + successNum + ",关键字 " + info.getKeyWord() + " 导入成功");
                        } else {
                            failureNum++;
                            failureMsg.append("<br/>" + failureNum + ",关键字 " + info.getKeyWord() + " 已存在,请重新导入");
                        }
                    } else {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + ",关键字 " + info.getKeyWord() + ",关键字类型错误");
                    }
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + ",关键字 " + info.getKeyWord() + ",关键字类型或关键字不能为空");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + ",关键字 " + info.getKeyWord() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public String refreshKeyWords()  {
        try {
            // 1.先清空文档
            clearDocument();
            List<KeyWordInfo> keyWordInfoList = keyWordMapper.selectAll();

            // 2.循环写入文档
            for (KeyWordInfo info : keyWordInfoList) {
                String keyWord = info.getKeyWord();
                writeToDocument(FILE_PATH, keyWord);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
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

    public void clearDocument() throws IOException {
        File fi = new File(FILE_PATH);
        FileWriter fileWriter = new FileWriter(fi);
        fileWriter.write("");
        fileWriter.flush();
        fileWriter.close();
    }

}
