package com.ruoyi.dutymanagement.mail.mapper;

import com.ruoyi.dutymanagement.mail.domain.MailEntity;
import com.ruoyi.dutymanagement.mail.domain.MailInfoEntity;
import com.ruoyi.dutymanagement.mail.domain.param.MailParam;
import com.ruoyi.dutymanagement.mail.domain.vo.MailVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 邮件管理数据交互层
 *
 * @Author fenghan
 */
@Repository
public interface MailMessageMapper {
    /**
     * 查询邮件列表
     *
     * @param mailParam
     * @return
     */
    List<MailVO> getMailList(MailParam mailParam);

    /**
     * 根据邮件附件id查询邮件
     *
     * @param mainId
     * @return
     */
    MailVO getMailById(int mainId);

    /**
     * 根据邮件附件messageId查询邮件
     *
     * @param messageId
     * @return
     */
    MailVO getMailByMessageId(String messageId);

    /**
     * 查询邮件详情
     *
     * @param mainId
     * @return
     */
    List<MailInfoEntity> getMailInfoById(String mainId);

    /**
     * 查询播报邮件
     *
     * @param status
     * @return
     */
    List<MailVO> getMailByStatus(String status);

    /**
     * 新增邮件信息
     *
     * @param mailEntity
     */
    int addMialInfo(MailEntity mailEntity);

    /**
     * 修改邮件状态
     *
     * @param id
     */
    void updateMailStatus(int id);

    void addMailInfo(MailInfoEntity mailInfoEntity);
}
