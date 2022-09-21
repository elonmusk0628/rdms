package com.ruoyi.dutymanagement.fax.service.impl;

import com.ruoyi.dutymanagement.fax.domain.FaxInfoEntity;
import com.ruoyi.dutymanagement.fax.domain.param.FaxParam;
import com.ruoyi.dutymanagement.fax.domain.vo.FaxVO;
import com.ruoyi.dutymanagement.fax.mapper.FaxMessageMapper;
import com.ruoyi.dutymanagement.fax.service.IFaxMessageService;
import com.ruoyi.dutymanagement.msm.service.IHttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 传真管理服务实现层
 *
 * @Author fenghan
 */
@Service
@Transactional
public class FaxMessageServiceImpl implements IFaxMessageService {
    @Autowired
    private FaxMessageMapper faxMessageMapper;

    @Autowired
    private IHttpClientService httpPostClientService;

    /**
     * 查询传真信息列表
     *
     * @param faxParam
     * @return
     */
    @Override
    public List<FaxVO> list(FaxParam faxParam) {
        List<FaxVO> faxVOList = faxMessageMapper.list(faxParam);
        return faxVOList;
    }

    /**
     * 查询传真信息详情
     *
     * @param mainId
     * @return
     */
    @Override
    public FaxVO getFaxInfoById(String mainId) {
        //查询传真信息
        FaxVO faxVO = faxMessageMapper.getFaxById(Integer.valueOf(mainId));
        //查询传真详细信息
        List<FaxInfoEntity> faxInfoEntityList = faxMessageMapper.getFaxInfoById(Integer.valueOf(mainId));
        faxVO.setFaxInfoEntityList(faxInfoEntityList);
        return faxVO;
    }

    /**
     * 调其它系统接口
     *
     * @return
     */
    @Override
    public String getJsonObject(String status) throws Exception {
        //调取值班管理系统传真接口
        String jsonObject = httpPostClientService.doFax(status);
        return jsonObject;
    }
    /**
     * 与机器人接口
     *
     * @return
     */
    @Override
    public String getRobotData(String status) {
        List<FaxVO> faxVOList = faxMessageMapper.getFaxByStatus(status);
        String resultStr = null;
        if (faxVOList.size() ==1) {
            for (FaxVO faxVO : faxVOList) {
                //发文时间
                String draftDate = faxVO.getDraftDate();
                //传真标题
                String name = faxVO.getName();
                resultStr = draftDate + "收到一份标题为：" + name + "的传真，请及时查看！";
                //播报完成的传真修改状态为已读1
                faxMessageMapper.updateStatus(faxVO.getId());
            }
        }else if (faxVOList.size()>1){
            resultStr = "有"+faxVOList.size()+"份新传真，请及时查看！";
            for (FaxVO faxVO : faxVOList) {
                //将多份播报完成的传真修改状态为已读1
                faxMessageMapper.updateStatus(faxVO.getId());
            }
        }else {
            return null;
        }
        return resultStr;
    }
}
