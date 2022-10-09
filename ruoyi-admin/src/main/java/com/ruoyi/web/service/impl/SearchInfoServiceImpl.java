package com.ruoyi.web.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.common.constant.BaseConstants;
import com.ruoyi.web.common.enums.ExceptionEnum;
import com.ruoyi.web.domian.SearchInfo;
import com.ruoyi.web.domian.SearchRequest;
import com.ruoyi.web.domian.SearchResponse;
import com.ruoyi.web.mapper.SearchInfoMapper;
import com.ruoyi.web.service.ISearchInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 河流信息 服务层实现类
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

    private static final Integer ATTRIBUTE  = 6;

    private static final String YEAR_PREFIX = "202";

    @Value("${yearStr.value}")
    private String YEAR_STR;

    @Autowired
    SearchInfoMapper searchInfoMapper;

    /**
     * 查询信息
     *
     * @param searchInfoStr 查询参数
     * @return 查询信息集合
     */
    @Override
    public SearchResponse selectRiverInfoByCondition(String searchInfoStr) {
        List<SearchInfo> riverList = new ArrayList<>();
        SearchResponse resp = new SearchResponse();
        try {
            // 1.请求过来的字符串进行ik分词，放进map
            Map<Integer,String> keyWordMap = new HashMap<>();
            StringReader stringReader = new StringReader(searchInfoStr.trim());
            IKSegmenter ik = new IKSegmenter(stringReader,true);
            Integer i = 0;
            Lexeme lex;
            while((lex = ik.next())!=null){
                i++;
                keyWordMap.put(i, lex.getLexemeText());
            }

            SearchRequest req = new SearchRequest();
            String yearKeyWord = "";
            String monthKeyWord = "";
            String dayKeyWord = "";
            String hourKeyWord = "";
            String name = "";
            String date = "";
            // 2.keyWordMap中获取对应参数值
            if (keyWordMap.get(YEAR_KEY).contains(YEAR_PREFIX)) {
                yearKeyWord = keyWordMap.get(YEAR_KEY);
                monthKeyWord = keyWordMap.get(MONTH_KEY);
                dayKeyWord = keyWordMap.get(DAY_KEY);
                hourKeyWord = keyWordMap.get(HOUR_KEY);
                name = keyWordMap.get(NAME_KEY);
            } else {
                yearKeyWord = YEAR_STR;
                monthKeyWord = keyWordMap.get(YEAR_KEY);
                dayKeyWord = keyWordMap.get(MONTH_KEY);
                hourKeyWord = keyWordMap.get(DAY_KEY);
                name = keyWordMap.get(HOUR_KEY);
            }
            // 3.大写小时转为int
            int hour = packageHour(hourKeyWord);

            // 4.截取日期，封装查询数据库请求体req
            date = yearKeyWord.substring(0, yearKeyWord.length() - 1) + CONNECT_SYMBOL + monthKeyWord.substring(0, monthKeyWord.length() - 1)
                    + CONNECT_SYMBOL + dayKeyWord.substring(0, dayKeyWord.length() - 1);
            req.setIDate(date);
            req.setIHour(hour);
            req.setName(name);

            // 5.校验名称是否正确
            List<String> nameList = searchInfoMapper.selectName();
            String nameStr = nameList.toString();
            if (!nameStr.contains(name)) {
                resp.setContent(ExceptionEnum.NAME_ERROR.getErrorMsg());
            } else {
                riverList = searchInfoMapper.selectRiverInfoList(req);
                // 6.封装返回体参数，添加单位
                packageParam(riverList);
                // 7.封装到返回体中
                resp = packageResp(riverList, keyWordMap);
            }

            return resp;
        } catch (Exception e) {
            return resp;
        }
    }

    /**
     * 查询所有名称
     *
     * @return 关键字结果集
     */
    @Override
    public List<String> selectName() {
       return searchInfoMapper.selectName();
    }

    private void packageParam(List<SearchInfo> list) {
        SearchInfo searchInfo = list.get(0);
        if (searchInfo.getType() == RESERVOIR_TYPE) {
            searchInfo.setReservoirIn(searchInfo.getReservoirIn() + BaseConstants.M3_S_SUFFIX);
            searchInfo.setReservoirOut(searchInfo.getReservoirOut() + BaseConstants.M3_S_SUFFIX);
        }
        if (searchInfo.getType() == RIVER_TYPE) {
            searchInfo.setRiverFlow(searchInfo.getRiverFlow() + BaseConstants.M3_S_SUFFIX);
        }
        searchInfo.setWaterLevel(searchInfo.getWaterLevel() + BaseConstants.METER_SUFFIX);
        searchInfo.setWarnLevel(searchInfo.getWarnLevel() + BaseConstants.METER_SUFFIX);
    }

    private SearchResponse packageResp(List<SearchInfo> list, Map<Integer,String> keyWordMap) {
        SearchInfo info = list.get(0);
        SearchResponse resp = new SearchResponse();
        String attribute = "";
        if (keyWordMap.get(YEAR_KEY).contains(YEAR_PREFIX)) {
            attribute = keyWordMap.get(ATTRIBUTE);
        } else {
            attribute = keyWordMap.get(5);
        }
        if (info.getType() == RESERVOIR_TYPE) {
            if (StringUtils.isEmpty(attribute)) {
                resp.setContent(info.getName() + BaseConstants.WATER_LEVEL + info.getWaterLevel() + BaseConstants.COMMA_SYMBOL
                        + BaseConstants.LIMIT_LEVEL + info.getWarnLevel() + BaseConstants.COMMA_SYMBOL + BaseConstants.RESERVOIR_IN
                        + info.getReservoirIn() + BaseConstants.COMMA_SYMBOL + BaseConstants.RESERVOIR_OUT + info.getReservoirOut()
                        + BaseConstants.COMMA_SYMBOL + BaseConstants.STATUS + info.getStatus());
            } else {

                switch (attribute) {
                    case BaseConstants.A_K_WATER_LEVEL:
                        resp.setContent(info.getName() + BaseConstants.K_WATER_LEVEL + info.getWaterLevel());
                        break;
                    case BaseConstants.A_LIMIT_LEVEL:
                        resp.setContent(info.getName() + BaseConstants.LIMIT_LEVEL + info.getWarnLevel());
                        break;
                    case BaseConstants.A_RESERVOIR_IN:
                        resp.setContent(info.getName() + BaseConstants.RESERVOIR_IN + info.getReservoirIn());
                        break;
                    case BaseConstants.A_RESERVOIR_OUT:
                        resp.setContent(info.getName() + BaseConstants.RESERVOIR_OUT + info.getReservoirOut());
                        break;
                    case BaseConstants.A_STATUS:
                        resp.setContent(info.getName() + BaseConstants.STATUS + info.getStatus());
                        break;
                    default:
                        resp.setContent(info.getName() + BaseConstants.WATER_LEVEL + info.getWaterLevel() + BaseConstants.COMMA_SYMBOL
                                + BaseConstants.LIMIT_LEVEL + info.getWarnLevel() + BaseConstants.COMMA_SYMBOL + BaseConstants.RESERVOIR_IN
                                + info.getReservoirIn() + BaseConstants.COMMA_SYMBOL + BaseConstants.RESERVOIR_OUT + info.getReservoirOut()
                                + BaseConstants.COMMA_SYMBOL + BaseConstants.STATUS + info.getStatus());
                }
            }
        }
        if (info.getType() == RIVER_TYPE) {
            if (StringUtils.isEmpty(attribute)) {
                resp.setContent(info.getName() + BaseConstants.WATER_LEVEL + info.getWaterLevel() + BaseConstants.COMMA_SYMBOL
                        + BaseConstants.WARN_LEVEL + info.getWarnLevel() + BaseConstants.COMMA_SYMBOL + BaseConstants.RIVER_FLOW
                        + info.getRiverFlow() + BaseConstants.COMMA_SYMBOL + BaseConstants.WATER_POTENTIAL + info.getWaterPotential()
                        + BaseConstants.COMMA_SYMBOL + BaseConstants.STATUS + info.getStatus());
            } else {
                switch (attribute) {
                    case BaseConstants.A_WATER_LEVEL:
                        resp.setContent(info.getName() + BaseConstants.WATER_LEVEL + info.getWaterLevel());
                        break;
                    case BaseConstants.A_WARN_LEVEL:
                        resp.setContent(info.getName() + BaseConstants.WARN_LEVEL + info.getWarnLevel());
                        break;
                    case BaseConstants.A_RIVER_FLOW:
                        resp.setContent(info.getName() + BaseConstants.RIVER_FLOW + info.getRiverFlow());
                        break;
                    case BaseConstants.A_WATER_POTENTIAL:
                        resp.setContent(info.getName() + BaseConstants.WATER_POTENTIAL + info.getWaterPotential());
                        break;
                    case BaseConstants.A_STATUS:
                        resp.setContent(info.getName() + BaseConstants.STATUS + info.getStatus());
                        break;
                    default:
                        resp.setContent(info.getName() + BaseConstants.WATER_LEVEL + info.getWaterLevel() + BaseConstants.COMMA_SYMBOL
                                + BaseConstants.WARN_LEVEL + info.getWarnLevel() + BaseConstants.COMMA_SYMBOL + BaseConstants.RIVER_FLOW
                                + info.getRiverFlow() + BaseConstants.COMMA_SYMBOL + BaseConstants.WATER_POTENTIAL + info.getWaterPotential()
                                + BaseConstants.COMMA_SYMBOL + BaseConstants.STATUS + info.getStatus());
                }
            }
        }
        return resp;
    }

    /**
     * 时间转换方法
     * @param hourStr 汉字小时参数
     * @return int类型小时数值
     */
    private int packageHour(String hourStr) {
        String hour = hourStr.substring(0, hourStr.length() - 1);
        switch (hour) {
            case BaseConstants.ZERO_OCLOCK:
                return 0;
            case BaseConstants.ONE_OCLOCK:
                return 1;
            case BaseConstants.TWO_OCLOCK:
                return 2;
            case BaseConstants.THREE_OCLOCK:
                return 3;
            case BaseConstants.FOUR_OCLOCK:
                return 4;
            case BaseConstants.FIVE_OCLOCK:
                return 5;
            case BaseConstants.SIX_OCLOCK:
                return 6;
            case BaseConstants.SEVEN_OCLOCK:
                return 7;
            case BaseConstants.EIGHT_OCLOCK:
                return 8;
            case BaseConstants.NINE_OCLOCK:
                return 9;
            case BaseConstants.TEN_OCLOCK:
                return 10;
            default:
                return Integer.parseInt(hour);
        }
    }

}
