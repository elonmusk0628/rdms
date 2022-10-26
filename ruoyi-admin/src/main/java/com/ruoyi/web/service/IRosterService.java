package com.ruoyi.web.service;

import java.io.IOException;

/**
 * 值班 服务层
 *
 * @Author fenghan
 * @Date 2022-10-8
 */
public interface IRosterService {
    /**
     * 机器人语音交互
     *
     * @param strParam
     * @return
     */
    String getRobotData(String strParam) throws IOException;
}
