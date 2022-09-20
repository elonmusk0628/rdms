package com.ruoyi.web.service.impl;

import com.ruoyi.web.common.constant.BaseConstants;
import com.ruoyi.web.domian.SearchInfo;
import com.ruoyi.web.domian.SearchRequest;
import com.ruoyi.web.domian.SearchResponse;
import com.ruoyi.web.mapper.SearchInfoMapper;
import com.ruoyi.web.service.ISearchInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 河流信息 服务层
 *
 * @author ruoyi
 */
@Service
public class SearchInfoServiceImpl implements ISearchInfoService {

    private static final String CONNECT_SYMBOL = "-";

    private static final Integer NAME_KEY = 5;

    private static final Integer YEAR_KEY = 1;

    private static final Integer MONTH_KEY = 2;

    private static final Integer DAY_KEY = 3;

    private static final Integer HOUR_KEY = 4;

    private static final Integer RESERVOIR_TYPE  = 1;

    private static final Integer RIVER_TYPE  = 2;

    @Autowired
    SearchInfoMapper searchInfoMapper;

    /**
     * 查询信息
     *
     * @param str 查询参数
     * @return 河道信息集合
     */
    @Override
    public SearchResponse selectRiverInfoByCondition(String str) {
        List<SearchInfo> riverList = new ArrayList<>();
        SearchResponse resp = new SearchResponse();
        try {
            // 1.请求过来的字符串进行ik分词
            Map<Integer,String> keyWordMap = new HashMap<>();
            StringReader stringReader = new StringReader(str.trim());
            IKSegmenter ik = new IKSegmenter(stringReader,true);

            Integer i = 0;
            Lexeme lex;
            while((lex = ik.next())!=null){
                i++;
                keyWordMap.put(i,lex.getLexemeText());
            }

            SearchRequest req = new SearchRequest();
            // 2.keyWordMap中获取对应参数值
            String yearKeyWord = keyWordMap.get(YEAR_KEY);
            String monthKeyWord = keyWordMap.get(MONTH_KEY);
            String dayKeyWord = keyWordMap.get(DAY_KEY);
            String hourKeyWord = keyWordMap.get(HOUR_KEY);
            // 3.拼接日期，封装查询数据库请求体req
            String date = yearKeyWord.substring(0, yearKeyWord.length()-1) + CONNECT_SYMBOL + monthKeyWord.substring(0, monthKeyWord.length()-1)
                    + CONNECT_SYMBOL + dayKeyWord.substring(0, dayKeyWord.length()-1);
            req.setIDate(date);
            req.setIHour(Integer.parseInt(hourKeyWord.substring(0, hourKeyWord.length() - 1)));
            req.setName(keyWordMap.get(NAME_KEY));
            riverList = searchInfoMapper.selectRiverInfoList(req);

            // 4.封装返回体参数，添加单位
            packageParam(riverList);
            // 5.封装到返回体中
            resp = packageResp(riverList);
            return resp;
        } catch (Exception e) {
            return resp;
        }
    }

    private void packageParam(List<SearchInfo> list) {
        SearchInfo searchInfo = list.get(0);
        if (searchInfo.getType() == RESERVOIR_TYPE) {
            searchInfo.setReservoirIn(searchInfo.getReservoirIn() + BaseConstants.M2_S_SUFFIX);
            searchInfo.setReservoirOut(searchInfo.getReservoirOut() + BaseConstants.M2_S_SUFFIX);
        }
        if (searchInfo.getType() == RIVER_TYPE) {
            searchInfo.setRiverFlow(searchInfo.getRiverFlow() + BaseConstants.M2_S_SUFFIX);
        }
        searchInfo.setWaterLevel(searchInfo.getWaterLevel() + BaseConstants.METER_SUFFIX);
        searchInfo.setWarnLevel(searchInfo.getWarnLevel() + BaseConstants.METER_SUFFIX);
    }

    private SearchResponse packageResp(List<SearchInfo> list) {
        SearchInfo info = list.get(0);
        SearchResponse resp = new SearchResponse();
        if (info.getType() == RESERVOIR_TYPE) {
            resp.setContent(info.getName()+BaseConstants.WATER_LEVEL+info.getWaterLevel()+BaseConstants.COMMA_SYMBOL
                    +BaseConstants.WARN_LEVEL+info.getWarnLevel()+BaseConstants.COMMA_SYMBOL+BaseConstants.RESERVOIR_IN
                    +info.getReservoirIn()+BaseConstants.COMMA_SYMBOL+BaseConstants.RESERVOIR_OUT+info.getReservoirOut()
                    +BaseConstants.COMMA_SYMBOL+BaseConstants.STATUS+info.getStatus()
            );
        }
        if (info.getType() == RIVER_TYPE) {
            resp.setContent(info.getName()+BaseConstants.WATER_LEVEL+info.getWaterLevel()+BaseConstants.COMMA_SYMBOL
                    +BaseConstants.WARN_LEVEL+info.getWarnLevel()+BaseConstants.COMMA_SYMBOL+BaseConstants.RIVER_FLOW
                    +info.getRiverFlow()+BaseConstants.COMMA_SYMBOL+BaseConstants.WATER_POTENTIAL+info.getWaterPotential()
                    +BaseConstants.COMMA_SYMBOL+BaseConstants.STATUS+info.getStatus()
            );
        }
        return resp;
    }

}
