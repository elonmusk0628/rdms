package com.ruoyi.dutymanagement.msm.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.dutymanagement.fax.domain.FaxEntity;
import com.ruoyi.dutymanagement.fax.domain.FaxInfoEntity;
import com.ruoyi.dutymanagement.fax.domain.vo.FaxVO;
import com.ruoyi.dutymanagement.fax.mapper.FaxMessageMapper;
import com.ruoyi.dutymanagement.mail.domain.MailEntity;
import com.ruoyi.dutymanagement.mail.domain.MailInfoEntity;
import com.ruoyi.dutymanagement.mail.domain.vo.MailVO;
import com.ruoyi.dutymanagement.mail.mapper.MailMessageMapper;
import com.ruoyi.dutymanagement.msm.domain.MsmEntity;
import com.ruoyi.dutymanagement.msm.domain.MsmInfoEntity;
import com.ruoyi.dutymanagement.msm.domain.param.LoginInfo;
import com.ruoyi.dutymanagement.msm.domain.vo.MsmVO;
import com.ruoyi.dutymanagement.msm.mapper.ShortMessageMapper;
import com.ruoyi.dutymanagement.msm.service.IHttpClientService;
import com.ruoyi.dutymanagement.msm.util.DateUtils;
import com.ruoyi.dutymanagement.tel.domain.TelInfoEntity;
import com.ruoyi.dutymanagement.tel.domain.vo.TelInfoVO;
import com.ruoyi.dutymanagement.tel.mapper.TelMessageMapper;
import com.ruoyi.web.common.constant.BaseConstants;
import com.ruoyi.web.domian.RosterEntity;
import com.ruoyi.web.domian.RosteringEntity;
import com.ruoyi.web.domian.vo.RosteringVO;
import com.ruoyi.web.mapper.RosterMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 调外系统接口实现
 *
 * @Author fenghan
 * @Date 2022-09-02
 */
@Service
@Transactional
public class HttpClientServiceImpl implements IHttpClientService {
    @Autowired
    private ShortMessageMapper shortMessageMapper;

    @Autowired
    private MailMessageMapper mailMessageMapper;

    @Autowired
    private FaxMessageMapper faxMessageMapper;

    @Autowired
    private TelMessageMapper telMessageMapper;

    @Autowired
    private RosterMapper rosterMapper;

    /**
     * 调值班管理系统短信接口
     *
     * @param token
     * @return
     */
    @Override
    public String doMsm(String token) throws Exception {
        // 1. 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2. 创建HttpGet对象
        HttpGet httpGet = new HttpGet(BaseConstants.IP + ":" + BaseConstants.PORT + "/zwfxzb/fxb/sendinfo/getMore");
        //Get请求中添加token
        httpGet.addHeader("Authorization", token);
        //入库对象
        MsmEntity msmEntity = new MsmEntity();
        //
        MsmInfoEntity msmInfoEntity = new MsmInfoEntity();
        // 4. 执行请求并处理响应
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                entity = new BufferedHttpEntity(entity);
                // 从响应模型中获取响应实体
                byte[] bytes = EntityUtils.toByteArray(entity);
                if (bytes != null) {
                    String resultStr = new String(bytes, "UTF-8");
                    JSONObject jsonObject = JSONObject.parseObject(resultStr);
                    JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("rows").toString());
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        //类型转换
                        String ageing = String.valueOf(object.get("ageing"));
                        int sendInfoId = Integer.parseInt(String.valueOf(object.get("sendInfoId")));
                        Date dateTime = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(object.get("sendTime")));
                        String sendTime = DateUtils.dateRurnStrFormat(dateTime);
                        Date createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(object.get("createTime")));
                        String createBy = String.valueOf(object.get("createBy"));
                        String content = String.valueOf(object.get("content"));
                        int sendCount = Integer.parseInt(String.valueOf(object.get("sendCount")));
                        String signature = String.valueOf(object.get("signature"));
                        String remark = String.valueOf(object.get("remark"));
                        //数据组装
                        msmEntity.setAgeing(ageing);
                        msmEntity.setSendInfoId(sendInfoId);
                        msmEntity.setCreateBy(createBy);
                        msmEntity.setCreateTime(createTime);
                        msmEntity.setRemark(remark);
                        msmEntity.setSendTime(sendTime);
                        msmEntity.setContent(content);
                        msmEntity.setSendCount(sendCount);
                        msmEntity.setSignaTure(signature);
                        msmEntity.setSuccess("0");
                        msmEntity.setStatus("0");
                        //根据发送id查询是否已查询出
                        MsmVO msmVO = shortMessageMapper.getShortMessageById(sendInfoId);
                        if (msmVO == null) {
                            //短信主信息入库
                            int count = shortMessageMapper.addMsmInfo(msmEntity);
                            int mainId = msmEntity.getId();
                            Object receiverList = object.get("receiverList");
                            if (receiverList == null) {
                                continue;
                            } else {
                                String objectStr = receiverList.toString();
                                JSONArray jsonArrayItem = JSONArray.parseArray(objectStr);
                                for (int j = 0; j < jsonArrayItem.size(); j++) {
                                    JSONObject objectItem = (JSONObject) jsonArrayItem.get(j);
                                    String userName = String.valueOf(objectItem.get("userName"));
                                    String phone = String.valueOf(objectItem.get("phone"));
                                    msmInfoEntity.setMainId(mainId);
                                    msmInfoEntity.setSendInfoId(sendInfoId);
                                    msmInfoEntity.setUserName(userName);
                                    msmInfoEntity.setPhone(phone);
                                    shortMessageMapper.addItem(msmInfoEntity);
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                    return jsonObject.toString();
                }
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            // 5. 释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 调其它系统接口
     *
     * @param token fAccess
     * @return
     */
    @Override
    public String doMail(String token, String fAccess) throws Exception {
        // 1. 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2. 创建HttpGet对象
        HttpGet httpGet = new HttpGet(BaseConstants.IP + ":" + BaseConstants.PORT + "/zwfxzb/baseFile/mailMsg/list");
        //Get请求中添加token
        httpGet.addHeader("Authorization", token);
        //Gtt请求中添加鉴权码F-Access
        httpGet.addHeader("F-Access", fAccess);
        //入库对象
        MailEntity mailEntity = new MailEntity();
        MailInfoEntity mailInfoEntity = new MailInfoEntity();
        // 4. 执行请求并处理响应
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                entity = new BufferedHttpEntity(entity);
                // 从响应模型中获取响应实体
                byte[] bytes = EntityUtils.toByteArray(entity);
                if (bytes != null) {
                    String resultStr = new String(bytes, "UTF-8");
                    JSONObject jsonObject = JSONObject.parseObject(resultStr);
                    JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("rows").toString());
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        //类型转换
                        String createTime = String.valueOf(object.get("createTime"));
                        int mailAttachmentId = Integer.parseInt(String.valueOf(object.get("mailAttachmentId")));
                        String mailName = String.valueOf(object.get("mailName"));
                        String address = String.valueOf(object.get("address"));
                        String receivedDate = String.valueOf(object.get("receivedDate"));
                        String messageId = String.valueOf(object.get("messageId"));
                        String appCode = String.valueOf(object.get("appCode"));
                        String mailType = String.valueOf(object.get("mailType"));
                        int fileSize = Integer.parseInt(String.valueOf(object.get("fileSize")));
                        //数据组装
                        mailEntity.setCreateTime(createTime);
                        mailEntity.setMailAttachmentId(mailAttachmentId);
                        mailEntity.setMailName(mailName);
                        mailEntity.setAddress(address);
                        mailEntity.setReceivedDate(receivedDate);
                        mailEntity.setMessageId(messageId);
                        mailEntity.setAppCode(appCode);
                        mailEntity.setMailType(mailType);
                        mailEntity.setFileSize(fileSize);
                        mailEntity.setStatus("0");
                        //根据messageId查询短信
                        MailVO mailVO = mailMessageMapper.getMailByMessageId(messageId);
                        if (mailVO == null) {
                            //邮件信息入库
                            int count = mailMessageMapper.addMialInfo(mailEntity);
                            int mainId = mailEntity.getId();
                            Object fileInfoList = object.get("fileInfoList");
                            if (fileInfoList == null) {
                                continue;
                            } else {
                                String objectStr = fileInfoList.toString();
                                JSONArray jsonArrayItem = JSONArray.parseArray(objectStr);
                                for (int j = 0; j < jsonArrayItem.size(); j++) {
                                    JSONObject objectItem = (JSONObject) jsonArrayItem.get(j);
                                    String fileId = String.valueOf(objectItem.get("fileId"));
                                    String fileName = String.valueOf(objectItem.get("fileName"));
                                    mailInfoEntity.setMainId(String.valueOf(mainId));
                                    mailInfoEntity.setMailAttachmentId(mailAttachmentId);
                                    mailInfoEntity.setFileId(fileId);
                                    mailInfoEntity.setFileName(fileName);
                                    //附件入库
                                    mailMessageMapper.addMailAttachMent(mailInfoEntity);
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                    return jsonObject.toString();
                }
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 5. 释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 调值班管理系统传真接口
     *
     * @param token,fAccess
     * @return
     */
    @Override
    public String doFax(String token, String fAccess) throws Exception {
        // 1. 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2. 创建HttpGet对象
        HttpGet httpGet = new HttpGet(BaseConstants.IP + ":" + BaseConstants.PORT + "/zwfxzb/baseFile/filemanage/list?category=3");
        //Get请求中添加token
        httpGet.addHeader("Authorization", token);
        //Gtt请求中添加鉴权码F-Access
        httpGet.addHeader("F-Access", fAccess);
        //入库对象
        FaxEntity faxEntity = new FaxEntity();
        //入库子表对象
        FaxInfoEntity faxInfoEntity = new FaxInfoEntity();
        // 4. 执行请求并处理响应
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                entity = new BufferedHttpEntity(entity);
                // 从响应模型中获取响应实体
                byte[] bytes = EntityUtils.toByteArray(entity);
                if (bytes != null) {
                    String resultStr = new String(bytes, "UTF-8");
                    JSONObject jsonObject = JSONObject.parseObject(resultStr);
                    JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("rows").toString());
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        //类型转换
                        String fileManageId = String.valueOf(object.get("fileManageId"));
                        String name = String.valueOf(object.get("name"));
                        String fileNum = String.valueOf(object.get("fileNum"));
                        String draftDate = String.valueOf(object.get("draftDate"));
                        String category = String.valueOf(object.get("category"));
                        String createTime = String.valueOf(object.get("createTime"));
                        //数据组装
                        faxEntity.setFileManageId(fileManageId);
                        faxEntity.setName(name);
                        faxEntity.setFileNum(fileNum);
                        faxEntity.setDraftDate(draftDate);
                        faxEntity.setCategory(category);
                        faxEntity.setCreateTime(createTime);
                        faxEntity.setStatus("0");
                        //根据fileManageId查询传真
                        FaxVO faxVO = faxMessageMapper.getFaxByManageId(fileManageId);
                        if (faxVO == null) {
                            //短信信息入库
                            int count = faxMessageMapper.addFaxInfo(faxEntity);
                            Object fileInfoList = object.get("fileInfoList");
                            if (fileInfoList == null) {
                                continue;
                            } else {
                                String objectStr = fileInfoList.toString();
                                JSONArray jsonArrayItem = JSONArray.parseArray(objectStr);
                                for (int j = 0; j < jsonArrayItem.size(); j++) {
                                    JSONObject objectItem = (JSONObject) jsonArrayItem.get(j);
                                    String fileId = String.valueOf(objectItem.get("fileId"));
                                    String fileType = String.valueOf(objectItem.get("fileType"));
                                    String fileName = String.valueOf(objectItem.get("fileName"));
                                    String filePath = String.valueOf(objectItem.get("filePath"));
                                    faxInfoEntity.setMainId(faxEntity.getId());
                                    faxInfoEntity.setFileId(fileId);
                                    faxInfoEntity.setFileType(fileType);
                                    faxInfoEntity.setFileName(fileName);
                                    faxInfoEntity.setFilePath(filePath);
                                    //附件入库
                                    faxMessageMapper.addItem(faxInfoEntity);
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                    return jsonObject.toString();
                }
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 5. 释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 调值班管理系统电话接口
     *
     * @param token,fAccess
     * @return
     */
    @Override
    public String doTel(String token, String fAccess) throws Exception {
        // 1. 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2. 创建HttpGet对象
        HttpGet httpGet = new HttpGet(BaseConstants.IP + ":" + BaseConstants.PORT + "/zwfxzb/fxb/telrecord/list");
        //Get请求中添加token
        httpGet.addHeader("Authorization", token);
        //Gtt请求中添加鉴权码F-Access
        httpGet.addHeader("F-Access", fAccess);
        //入库对象
        TelInfoEntity telInfoEntity = new TelInfoEntity();
        // 4. 执行请求并处理响应
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                entity = new BufferedHttpEntity(entity);
                // 从响应模型中获取响应实体
                byte[] bytes = EntityUtils.toByteArray(entity);
                if (bytes != null) {
                    String resultStr = new String(bytes, "UTF-8");
                    JSONObject jsonObject = JSONObject.parseObject(resultStr);
                    JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("rows").toString());
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        //类型转换
                        String createTime = String.valueOf(object.get("createTime"));
                        String telRecordId = String.valueOf(object.get("telRecordId"));
                        String theElectricityUnit = String.valueOf(object.get("theElectricityUnit"));
                        String tel = String.valueOf(object.get("tel"));
                        String userName = String.valueOf(object.get("userName"));
                        Date telTime = DateUtils.stringTurnDate(String.valueOf(object.get("telTime")));
                        String answerPeople = String.valueOf(object.get("answerPeople"));
                        String phone = String.valueOf(object.get("phone"));
                        String title = String.valueOf(object.get("title"));
                        String content = String.valueOf(object.get("content"));
                        String suggestion = String.valueOf(object.get("suggestion"));
                        //数据组装
                        telInfoEntity.setCreateTime(DateUtils.stringTurnDate(createTime));
                        telInfoEntity.setTelRecordId(Integer.parseInt(telRecordId));
                        telInfoEntity.setTheElectricityUnit(theElectricityUnit);
                        telInfoEntity.setTel(tel);
                        telInfoEntity.setUserName(userName);
                        telInfoEntity.setTelTime(DateUtils.dateRurnStrFormat(telTime));
                        telInfoEntity.setAnswerPeople(answerPeople);
                        telInfoEntity.setPhone(phone);
                        telInfoEntity.setTitle(title);
                        telInfoEntity.setContent(content);
                        telInfoEntity.setSuggestion(suggestion);
                        telInfoEntity.setStatus("0");
                        //根据telRecordId查询电话记录
                        TelInfoVO telInfoVO = telMessageMapper.getTelByTelRecordId(Integer.parseInt(telRecordId));
                        if (telInfoVO == null) {
                            //短信信息入库
                            int count = telMessageMapper.addTelInfo(telInfoEntity);
                            return String.valueOf(count);
                        } else {
                            continue;
                        }
                    }
                }
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 5. 释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 调值班管理系统排班接口
     *
     * @param token
     * @param fAccess
     * @return
     * @throws Exception
     */
    @Override
    public String doRostering(String token, String fAccess) throws Exception {
        // 1. 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2. 创建HttpGet对象
        HttpGet httpGet = new HttpGet(BaseConstants.IP + ":" + BaseConstants.PORT + "/zwfxzb/schedule/info/V2/list");
        //Get请求中添加token
        httpGet.addHeader("Authorization", token);
        //Gtt请求中添加鉴权码F-Access
        httpGet.addHeader("F-Access", fAccess);
        //入库对象
        RosteringEntity rosteringEntity = new RosteringEntity();
        RosterEntity rosterEntity = new RosterEntity();
        // 4. 执行请求并处理响应
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                entity = new BufferedHttpEntity(entity);
                // 从响应模型中获取响应实体
                byte[] bytes = EntityUtils.toByteArray(entity);
                if (bytes != null) {
                    String resultStr = new String(bytes, "UTF-8");
                    JSONObject jsonObject = JSONObject.parseObject(resultStr);
                    JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("rows").toString());
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        //类型转换
                        String createBy = String.valueOf(object.get("createBy"));
                        String createTime = String.valueOf(object.get("createTime"));
                        int scheduleInfoV2Id = Integer.parseInt(String.valueOf(object.get("scheduleInfoV2Id")));
                        String yyyyMm = String.valueOf(object.get("yyyyMm"));
                        String date = String.valueOf(object.get("date"));
                        String week = String.valueOf(object.get("week"));
                        String dateType = String.valueOf(object.get("dateType"));
                        //值班领导列表
                        Object dutyHeader = object.get("dutyHeader");
                        //值班人列表
                        Object onDutier1 = object.get("onDutier1");
                        //副班人列表
                        Object onDutier2 = object.get("onDutier2");
                        //数据组装
                        if (createBy == null || "".equals(createBy)) {
                            rosteringEntity.setCreateBy("admin");
                        } else {
                            rosteringEntity.setCreateBy(createBy);
                        }
                        if (createTime != null && !"".equals(createTime) && !"null".equals(createTime)) {
                            rosteringEntity.setCreateTime(createTime);
                        } else {
                            rosteringEntity.setCreateTime(date);
                        }
                        rosteringEntity.setScheduleId(scheduleInfoV2Id);
                        rosteringEntity.setYears(yyyyMm);
                        rosteringEntity.setDate(date);
                        rosteringEntity.setWeek(week);
                        rosteringEntity.setDateType(dateType);
                        //根据ScheduleId查询排班信息
                        RosteringVO rosteringVO = rosterMapper.getRosterByScheduleId(scheduleInfoV2Id);
                        if (rosteringVO == null) {
                            //排班信息入库
                            int count = rosterMapper.addRosteringInfo(rosteringEntity);
                            //解析值班领导列表
                            if (dutyHeader == null) {
                                continue;
                            } else {
                                String objectStr = dutyHeader.toString();
                                JSONArray jsonArrayItem = JSONArray.parseArray(objectStr);
                                for (int j = 0; j < jsonArrayItem.size(); j++) {
                                    JSONObject objectItem = (JSONObject) jsonArrayItem.get(j);
                                    int scheduleId = Integer.parseInt(String.valueOf(objectItem.get("scheduleInfoV2Id")));
                                    int memberId = Integer.parseInt(String.valueOf(objectItem.get("scheduleInfoV2MemberId")));
                                    String scheduleType = String.valueOf(objectItem.get("scheduleType"));
                                    int userId = Integer.parseInt(String.valueOf(objectItem.get("userId")));
                                    String nickName = String.valueOf(objectItem.get("nickName"));
                                    rosterEntity.setScheduleId(scheduleId);
                                    rosterEntity.setMemberId(memberId);
                                    //领班0
                                    rosterEntity.setScheduleType("0");
                                    rosterEntity.setUserId(userId);
                                    rosterEntity.setNickName(nickName);
                                    rosterEntity.setMainId(rosteringEntity.getId());
                                    rosterEntity.setCreateTime(DateUtils.dateRurnString(new Date()));
                                    rosterMapper.addRosterInfo(rosterEntity);
                                }
                            }
                            //解析值班人列表
                            if (onDutier1 == null) {
                                continue;
                            } else {
                                String objectStr = onDutier1.toString();
                                JSONArray jsonArrayItem = JSONArray.parseArray(objectStr);
                                for (int k = 0; k < jsonArrayItem.size(); k++) {
                                    JSONObject objectItem = (JSONObject) jsonArrayItem.get(k);
                                    int scheduleId = Integer.parseInt(String.valueOf(objectItem.get("scheduleInfoV2Id")));
                                    int memberId = Integer.parseInt(String.valueOf(objectItem.get("scheduleInfoV2MemberId")));
                                    String scheduleType = String.valueOf(objectItem.get("scheduleType"));
                                    int userId = Integer.parseInt(String.valueOf(objectItem.get("userId")));
                                    String nickName = String.valueOf(objectItem.get("nickName"));
                                    rosterEntity.setScheduleId(scheduleId);
                                    rosterEntity.setMemberId(memberId);
                                    //值班
                                    rosterEntity.setScheduleType("1");
                                    rosterEntity.setUserId(userId);
                                    rosterEntity.setNickName(nickName);
                                    rosterEntity.setMainId(rosteringEntity.getId());
                                    rosterEntity.setCreateTime(DateUtils.dateRurnString(new Date()));
                                    rosterMapper.addRosterInfo(rosterEntity);
                                }
                            }
                            //解析副班人列表
                            if (onDutier2 == null) {
                                continue;
                            } else {
                                String objectStr = onDutier2.toString();
                                JSONArray jsonArrayItem = JSONArray.parseArray(objectStr);
                                for (int z = 0; z < jsonArrayItem.size(); z++) {
                                    JSONObject objectItem = (JSONObject) jsonArrayItem.get(z);
                                    int scheduleId = Integer.parseInt(String.valueOf(objectItem.get("scheduleInfoV2Id")));
                                    int memberId = Integer.parseInt(String.valueOf(objectItem.get("scheduleInfoV2MemberId")));
                                    String scheduleType = String.valueOf(objectItem.get("scheduleType"));
                                    String userIdStr = String.valueOf(objectItem.get("userId"));
                                    int userId = 0;
                                    if (userIdStr != "null") {
                                        userId = Integer.parseInt(userIdStr);
                                    }
                                    String nickName = String.valueOf(objectItem.get("nickName"));
                                    rosterEntity.setScheduleId(scheduleId);
                                    rosterEntity.setMemberId(memberId);
                                    rosterEntity.setScheduleType("2");
                                    rosterEntity.setUserId(userId);
                                    rosterEntity.setNickName(nickName);
                                    rosterEntity.setMainId(rosteringEntity.getId());
                                    rosterEntity.setCreateTime(DateUtils.dateRurnString(new Date()));
                                    rosterMapper.addRosterInfo(rosterEntity);
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                    return jsonObject.toString();
                }
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 5. 释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取token
     *
     * @param loginInfo
     * @return
     */
    @Override
    public String getToken(LoginInfo loginInfo) throws Exception {
        String jsonString = JSON.toJSONString(loginInfo);
        // 1. 创建HttpClient对象
        CloseableHttpClient client = HttpClientBuilder.create().build();
        // 2. 创建HttpPost对象
        HttpPost post = new HttpPost(BaseConstants.IP + ":" + BaseConstants.PORT + "/zwfxzb/login");
        String token = "";
        HttpResponse res = null;
        try {
            StringEntity stringEntity = new StringEntity(jsonString.toString(), "UTF-8");
            stringEntity.setContentType("application/json");
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json; charset=utf-8");
            post.setEntity(stringEntity);
            res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                if (entity != null) {
                    entity = new BufferedHttpEntity(entity);
                    // 从响应模型中获取响应实体
                    byte[] bytes = EntityUtils.toByteArray(entity);
                    if (bytes != null) {
                        String resultStr = new String(bytes, "UTF-8");
                        JSONObject jsonObject = JSONObject.parseObject(resultStr);
                        token = jsonObject.getString("token");
                        return token;
                    }
                }
            }
        } catch (Exception e) {
            if (res == null) {
                return "HttpResponse 为 null!";
            }
            throw new RuntimeException(e);
        }
        if (res == null || res.getStatusLine() == null) {
            return "无响应";
        }
        return token;
    }

    /**
     * 获取鉴权码F-Access
     *
     * @return
     */
    @Override
    public String getFAccess(String token) throws Exception {
        //鉴权码F-Access
        String fAccess = null;
        // 1. 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(BaseConstants.IP + ":" + BaseConstants.PORT + "/zwfxzb/fxb/file/fileAuth");
        HttpResponse res = null;
        try {
            //Get请求中添加token
            httpGet.addHeader("Authorization", token);
            res = httpClient.execute(httpGet);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                if (entity != null) {
                    entity = new BufferedHttpEntity(entity);
                    // 从响应模型中获取响应实体
                    byte[] bytes = EntityUtils.toByteArray(entity);
                    if (bytes != null) {
                        String resultStr = new String(bytes, "UTF-8");
                        JSONObject jsonObject = JSONObject.parseObject(resultStr);
                        fAccess = jsonObject.getString("msg");
                        return fAccess;
                    }
                }
            }
        } catch (Exception e) {
            if (res == null) {
                return "HttpResponse 为 null!";
            }
            throw new RuntimeException(e);
        }
        if (res == null || res.getStatusLine() == null) {
            return "无响应";
        }
        return fAccess;
    }

    /**
     * 获取http请求数据
     *
     * @param sign
     * @return
     * @throws Exception
     */
    public JSONObject getHttpClientService(String sign) throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername(BaseConstants.USER_NAME);
        loginInfo.setPassword(BaseConstants.PASS_WORD);
        HttpGet httpGet = null;
        //获取token
        String token = getToken(loginInfo);
        //获取鉴权码FAccess
        String fAccess = getFAccess(token);
        // 1. 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2. 创建HttpGet对象
        if ("FAX".equals(sign)) {
            httpGet = new HttpGet(BaseConstants.IP + ":" + BaseConstants.PORT + "/zwfxzb/baseFile/filemanage/list?category=3");
        } else if ("MAIL".equals(sign)) {
            httpGet = new HttpGet(BaseConstants.IP + ":" + BaseConstants.PORT + "/zwfxzb/baseFile/mailMsg/list");
        } else if ("MSM".equals(sign)) {
            httpGet = new HttpGet(BaseConstants.IP + ":" + BaseConstants.PORT + "/zwfxzb/fxb/sendinfo/getMore");
        } else if ("TEL".equals(sign)) {
            httpGet = new HttpGet(BaseConstants.IP + ":" + BaseConstants.PORT + "/zwfxzb/fxb/telrecord/list");
        }
        //Get请求中添加token
        httpGet.addHeader("Authorization", token);
        //Gtt请求中添加鉴权码F-Access
        httpGet.addHeader("F-Access", fAccess);

        JSONObject object = null;
        // 4. 执行请求并处理响应
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                entity = new BufferedHttpEntity(entity);
                System.out.println("响应内容：");
                System.out.println(EntityUtils.toString(entity));
                // 从响应模型中获取响应实体
                byte[] bytes = EntityUtils.toByteArray(entity);
                if (bytes != null) {
                    String resultStr = new String(bytes, "UTF-8");
                    JSONObject jsonObject = JSONObject.parseObject(resultStr);
                    JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("rows").toString());
                    for (int i = 0; i < jsonArray.size(); i++) {
                        object = (JSONObject) jsonArray.get(i);
                    }
                    return object;
                }
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 5. 释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
