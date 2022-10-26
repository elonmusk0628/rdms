package com.ruoyi.dutymanagement.mail.domain.vo;

import com.ruoyi.dutymanagement.mail.domain.MailInfoEntity;
import lombok.Data;

import java.util.List;

/**
 * 邮件返回对象
 *
 * @Author fenghan
 * @Date 2022-09-08
 */
@Data
public class MailVO {
    /**
     * id
     */
    private int id;
    /**
     * 邮件id
     */
    private String messageId;
    /**
     * 邮件附件id
     */
    private Integer mailAttachmentId;
    /**
     * 标题
     */
    private String mailName;
    /**
     * 邮件发送人
     */
    private String personal;
    /**
     * 邮件发送地址
     */
    private String address;
    /**
     * 接收时间
     */
    private String receivedDate;
    /**
     * 邮件类型
     */
    private String mailType;
    /**
     * 附件数量
     */
    private Integer fileSize;
    /**
     * 搜索值
     */
    private String searchValue;
    /**
     * 创建人
     */
    private String createby;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 应用号
     */
    private String appCode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态0未读1已读
     */
    private String status;
    /**
     * 附件列表
     */
    private List<MailInfoEntity> mailInfoList;
}
