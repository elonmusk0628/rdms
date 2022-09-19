package com.ruoyi.dutymanagement.mail.domain.vo;

import com.ruoyi.dutymanagement.mail.domain.MailInfoEntity;
import lombok.Data;

import java.util.List;

/**
 * 邮件返回对象
 *
 * @Author fenghan
 */
@Data
public class MailVO {

    private int id;

    private String messageId;

    private Integer mailAttachmentId;

    private String mailName;

    private String personal;

    private String address;

    private String receivedDate;

    private String mailType;

    private Integer fileSize;

    private String searchValue;

    private String createby;

    private String createTime;

    private String updateBy;

    private String updateTime;

    private String appCode;

    private String remark;

    private List<MailInfoEntity> mailInfoList;
}
