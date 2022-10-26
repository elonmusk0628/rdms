package com.ruoyi.web.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.common.constant.BaseConstants;
import com.ruoyi.web.common.enums.ConstantEnum;
import com.ruoyi.web.common.enums.ExceptionEnum;
import com.ruoyi.web.domian.*;
import com.ruoyi.web.domian.vo.WrStatBVO;
import com.ruoyi.web.mapper.WrResBMapper;
import com.ruoyi.web.mapper.WrStatBMapper;
import com.ruoyi.web.service.IWrResBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 河流信息 服务层实现类
 *
 * @author fengZh
 */
@Service
public class WrResBServiceImpl implements IWrResBService {

    private static final String CONNECT_SYMBOL = "-";

    private static final String TIME_CONNECT = ":";

    private static final Integer NAME_KEY = 4;

    private static final Integer YEAR_KEY = 1;

    private static final Integer MONTH_KEY = 2;

    private static final Integer DAY_KEY = 3;

    private static final Integer RESERVOIR_TYPE = 1;

    private static final Integer RIVER_TYPE = 2;

    private static final Integer ATTRIBUTE = 5;

    private static final String YEAR_PREFIX = "202";

    @Value("${yearStr.value}")
    private String YEAR_STR;

    @Autowired
    WrResBMapper wrResBMapper;

    @Autowired
    WrStatBMapper wrStatBMapper;

    /**
     * 查询信息(水库，河道)
     *
     * @param searchInfoStr 查询参数
     * @return 查询信息集合
     */
    @Override
    public SearchResponse selectInfo(String searchInfoStr) {
        SearchResponse searchResponse;
        // 判断查询水库还是河道信息
        if (searchInfoStr.contains(BaseConstants.RESERVOIR)) {
            // 水库信息
            searchResponse = selectInfoByCondition(searchInfoStr);
        } else {
            // 河道信息
            searchResponse = selectWadiByCondition(searchInfoStr);
        }
        return searchResponse;
    }

    /**
     * 查询河道信息
     *
     * @param searchInfoStr 查询参数
     * @return 查询信息集合
     */
    public SearchResponse selectWadiByCondition(String searchInfoStr) {
        List<WrStatB> wrStatBList = new ArrayList<>();
        SearchResponse resp = new SearchResponse();
        WrStatBVO statBVO = new WrStatBVO();
        Map<Integer, String> keyWordMap = new HashMap<>();
        try {
            // 1.ik分词方法
            SearchRequest req = ikAnalyzer(searchInfoStr, keyWordMap);

            wrStatBList = wrStatBMapper.selectWadiInfoList(req);
            // 2.封装返回体参数，添加单位
            packageParamWrStatB(wrStatBList, statBVO);
            // 3.封装到返回体中
            resp = packageRespWrStatB(statBVO, keyWordMap);
            return resp;
        } catch (Exception e) {
            return resp;
        }

    }

    /**
     * 查询水库信息
     *
     * @param searchInfoStr 查询参数
     * @return 查询信息集合
     */
    @Override
    public SearchResponse selectInfoByCondition(String searchInfoStr) {
        List<WrResB> wrResBList = new ArrayList<>();
        SearchResponse resp = new SearchResponse();
        Map<Integer, String> keyWordMap = new HashMap<>();
        try {
            // 1.ik分词方法
            SearchRequest req = ikAnalyzer(searchInfoStr, keyWordMap);

            // 2.校验名称是否正确
            List<String> nameList = wrResBMapper.selectName();
            String nameStr = nameList.toString();
            if (!nameStr.contains(req.getName())) {
                resp.setContent(ExceptionEnum.NAME_ERROR.getErrorMsg());
            } else {
                wrResBList = wrResBMapper.selectInfoList(req);
                // 3.封装返回体参数，添加单位
                packageParamWrResB(wrResBList);
                // 4.封装到返回体中
                resp = packageRespWrResB(wrResBList, keyWordMap);
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
        return wrResBMapper.selectName();
    }

    /**
     * 封装水库返回体
     *
     * @param list 水库信息集合
     */
    private void packageParamWrResB(List<WrResB> list) {
        WrResB resB = list.get(0);
        if (resB.getRunCond().equals(ConstantEnum.STATUS_NORMAL.getCode())) {
            resB.setRunCond(ConstantEnum.STATUS_NORMAL.getMsg());
        }
        if (resB.getRunCond().equals(ConstantEnum.STATUS_FAULT.getCode())) {
            resB.setRunCond(ConstantEnum.STATUS_FAULT.getMsg());
        }
        if (resB.getRunCond().equals(ConstantEnum.STATUS_DISABLE.getCode())) {
            resB.setRunCond(ConstantEnum.STATUS_DISABLE.getMsg());
        }
        resB.setCatA(resB.getCatA() + BaseConstants.CAT_A);
        resB.setDesFz(resB.getDesFz() + BaseConstants.CAT_A);
        resB.setTotV(resB.getTotV() + BaseConstants.TOT_V);
        resB.setFrscV(resB.getFrscV() + BaseConstants.TOT_V);
        resB.setNormWz(resB.getNormWz() + BaseConstants.METER_SUFFIX);
        resB.setCfZ(resB.getCfZ() + BaseConstants.METER_SUFFIX);
        resB.setUtilV(resB.getUtilV() + BaseConstants.TOT_V);
        resB.setFsZ(resB.getFsZ() + BaseConstants.METER_SUFFIX);
        resB.setFsZV(resB.getFsZV() + BaseConstants.TOT_V);
        resB.setFsZV(resB.getFsZV() + BaseConstants.TOT_V);
        resB.setDeadZ(resB.getDeadZ() + BaseConstants.METER_SUFFIX);
        resB.setDeadV(resB.getDeadV() + BaseConstants.TOT_V);
        resB.setMinDisc(resB.getMinDisc() + BaseConstants.M3_S_SUFFIX);
        resB.setStEndLen(resB.getStEndLen() + BaseConstants.METER_SUFFIX);
    }

    /**
     * 封装水库返回体
     *
     * @param list 水库信息集合
     * @param keyWordMap 关键字集合
     * @return 水库信息返回体
     */
    private SearchResponse packageRespWrResB(List<WrResB> list, Map<Integer, String> keyWordMap) {
        WrResB resB = list.get(0);
        SearchResponse resp = new SearchResponse();
        String attribute = "";
        if (keyWordMap.get(YEAR_KEY).contains(YEAR_PREFIX)) {
            attribute = keyWordMap.get(ATTRIBUTE);
        } else {
            attribute = keyWordMap.get(4);
        }

        if (StringUtils.isEmpty(attribute)) {
            resp.setContent(resB.getResNm() + BaseConstants.LOCAL + resB.getLoc() + BaseConstants.COMMA_SYMBOL
                    + BaseConstants.WATER_AREA + resB.getCatA() + BaseConstants.COMMA_SYMBOL + BaseConstants.DESIGN_FLOOD_LEVEL + resB.getDesFz()
                    + BaseConstants.COMMA_SYMBOL + BaseConstants.TOTAL_CAPACITY + resB.getTotV() + BaseConstants.COMMA_SYMBOL
                    + BaseConstants.FLOOD_CAPACITY + resB.getFrscV() + BaseConstants.COMMA_SYMBOL + BaseConstants.NORMAL_LEVEL
                    + resB.getNormWz() + BaseConstants.COMMA_SYMBOL + BaseConstants.PROSPER_CAPACITY + resB.getUtilV()
                    + BaseConstants.COMMA_SYMBOL + BaseConstants.FLOOD_LIMIT_LEVEL + resB.getFsZ() + BaseConstants.COMMA_SYMBOL
                    + BaseConstants.DEAD_LEVEL + resB.getDeadZ() + BaseConstants.COMMA_SYMBOL + BaseConstants.DEAD_CAPACITY + resB.getDeadV()
                    + BaseConstants.COMMA_SYMBOL + BaseConstants.MIN_LEAK_FLOW + resB.getMinDisc()
                    + BaseConstants.COMMA_SYMBOL + BaseConstants.RUN_STATUS + resB.getRunCond()
            );
        } else {
            switch (attribute) {
                case BaseConstants.A_LOCAL:
                    resp.setContent(resB.getResNm() + BaseConstants.LOCAL + resB.getLoc());
                    break;
                case BaseConstants.A_WATER_AREA:
                    resp.setContent(resB.getResNm() + BaseConstants.WATER_AREA + resB.getCatA());
                    break;
                case BaseConstants.A_DESIGN_FLOOD_LEVEL:
                    resp.setContent(resB.getResNm() + BaseConstants.DESIGN_FLOOD_LEVEL + resB.getDesFz());
                    break;
                case BaseConstants.A_TOTAL_CAPACITY:
                    resp.setContent(resB.getResNm() + BaseConstants.TOTAL_CAPACITY + resB.getTotV());
                    break;
                case BaseConstants.A_FLOOD_CAPACITY:
                    resp.setContent(resB.getResNm() + BaseConstants.FLOOD_CAPACITY + resB.getFrscV());
                    break;
                case BaseConstants.A_NORMAL_LEVEL:
                    resp.setContent(resB.getResNm() + BaseConstants.NORMAL_LEVEL + resB.getNormWz());
                    break;
                case BaseConstants.A_PROSPER_CAPACITY:
                    resp.setContent(resB.getResNm() + BaseConstants.PROSPER_CAPACITY + resB.getUtilV());
                    break;
                case BaseConstants.A_FLOOD_LIMIT_LEVEL:
                    resp.setContent(resB.getResNm() + BaseConstants.FLOOD_LIMIT_LEVEL + resB.getFsZ());
                    break;
                case BaseConstants.A_DEAD_LEVEL:
                    resp.setContent(resB.getResNm() + BaseConstants.DEAD_LEVEL + resB.getDeadZ());
                    break;
                case BaseConstants.A_DEAD_CAPACITY:
                    resp.setContent(resB.getResNm() + BaseConstants.DEAD_CAPACITY + resB.getDeadV());
                    break;
                case BaseConstants.A_MIN_LEAK_FLOW:
                    resp.setContent(resB.getResNm() + BaseConstants.MIN_LEAK_FLOW + resB.getMinDisc());
                    break;
                case BaseConstants.A_RUN_STATUS:
                    resp.setContent(resB.getResNm() + BaseConstants.RUN_STATUS + resB.getRunCond());
                    break;
                default:
                    resp.setContent(resB.getResNm() + BaseConstants.LOCAL + resB.getLoc() + BaseConstants.COMMA_SYMBOL
                            + BaseConstants.WATER_AREA + resB.getCatA() + BaseConstants.COMMA_SYMBOL + BaseConstants.DESIGN_FLOOD_LEVEL + resB.getDesFz()
                            + BaseConstants.COMMA_SYMBOL + BaseConstants.TOTAL_CAPACITY + resB.getTotV() + BaseConstants.COMMA_SYMBOL
                            + BaseConstants.FLOOD_CAPACITY + resB.getFrscV() + BaseConstants.COMMA_SYMBOL + BaseConstants.NORMAL_LEVEL
                            + resB.getNormWz() + BaseConstants.COMMA_SYMBOL + BaseConstants.PROSPER_CAPACITY + resB.getUtilV()
                            + BaseConstants.COMMA_SYMBOL + BaseConstants.FLOOD_LIMIT_LEVEL + resB.getFsZ() + BaseConstants.COMMA_SYMBOL
                            + BaseConstants.DEAD_LEVEL + resB.getDeadZ() + BaseConstants.COMMA_SYMBOL + BaseConstants.DEAD_CAPACITY + resB.getDeadV()
                            + BaseConstants.COMMA_SYMBOL + BaseConstants.MIN_LEAK_FLOW + resB.getMinDisc()
                            + BaseConstants.COMMA_SYMBOL + BaseConstants.RUN_STATUS + resB.getRunCond());
            }
        }
        return resp;
    }

    /**
     * 时间数字转换
     *
     * @param hourStr 汉字小时参数
     * @return int类型小时数值
     */
    private int packageHour(String hourStr) {
        String hour = hourStr.substring(0, hourStr.length() - 1);
        switch (hour) {
            case BaseConstants.ZERO_CLOCK:
                return 0;
            case BaseConstants.ONE_CLOCK:
                return 1;
            case BaseConstants.TWO_CLOCK:
                return 2;
            case BaseConstants.THREE_CLOCK:
                return 3;
            case BaseConstants.FOUR_CLOCK:
                return 4;
            case BaseConstants.FIVE_CLOCK:
                return 5;
            case BaseConstants.SIX_CLOCK:
                return 6;
            case BaseConstants.SEVEN_CLOCK:
                return 7;
            case BaseConstants.EIGHT_CLOCK:
                return 8;
            case BaseConstants.NINE_CLOCK:
                return 9;
            case BaseConstants.TEN_CLOCK:
                return 10;
            default:
                return Integer.parseInt(hour);
        }
    }

    /**
     * 查询河道信息
     *
     * @param searchInfoStr 查询参数
     * @param keyWordMap 关键字map
     * @return 查询参数请求体
     */
    public SearchRequest ikAnalyzer(String searchInfoStr, Map<Integer, String> keyWordMap) throws IOException {
        // 1.请求过来的字符串进行ik分词，放进map
        StringReader stringReader = new StringReader(searchInfoStr.trim());
        IKSegmenter ik = new IKSegmenter(stringReader, true);
        Integer i = 0;
        Lexeme lex;
        while ((lex = ik.next()) != null) {
            i++;
            keyWordMap.put(i, lex.getLexemeText());
        }

        // 2.keyWordMap中获取对应参数值
        SearchRequest req = new SearchRequest();
        String yearKeyWord = "";
        String monthKeyWord = "";
        String dayKeyWord = "";
        String resName = "";
        String ts = "";
        if (keyWordMap.get(YEAR_KEY).contains(YEAR_PREFIX)) {
            yearKeyWord = keyWordMap.get(YEAR_KEY);
            monthKeyWord = keyWordMap.get(MONTH_KEY);
            dayKeyWord = keyWordMap.get(DAY_KEY);
            resName = keyWordMap.get(NAME_KEY);
        } else {
            yearKeyWord = YEAR_STR;
            monthKeyWord = keyWordMap.get(YEAR_KEY);
            dayKeyWord = keyWordMap.get(MONTH_KEY);
            resName = keyWordMap.get(DAY_KEY);
        }

        // 3.截取日期，封装查询数据库请求体req
        ts = yearKeyWord.substring(0, yearKeyWord.length() - 1) + CONNECT_SYMBOL + monthKeyWord.substring(0, monthKeyWord.length() - 1)
                + CONNECT_SYMBOL + dayKeyWord.substring(0, dayKeyWord.length() - 1);
        req.setTS(ts);
        req.setName(resName);

        return req;
    }

    /**
     * 封装河道返回体
     *
     * @param list 水库信息集合
     * @param statBVO 河道信息vo
     */
    private void packageParamWrStatB(List<WrStatB> list, WrStatBVO statBVO) {
        WrStatB wrStatB = list.get(0);
        if (wrStatB.getRunCond().equals(ConstantEnum.STATUS_NORMAL.getCode())) {
            statBVO.setRunCond(ConstantEnum.STATUS_NORMAL.getMsg());
        }
        if (wrStatB.getRunCond().equals(ConstantEnum.STATUS_FAULT.getCode())) {
            statBVO.setRunCond(ConstantEnum.STATUS_FAULT.getMsg());
        }
        if (wrStatB.getRunCond().equals(ConstantEnum.STATUS_DISABLE.getCode())) {
            statBVO.setRunCond(ConstantEnum.STATUS_DISABLE.getMsg());
        }
        statBVO.setStNm(wrStatB.getStNm());
        statBVO.setLoc(wrStatB.getLoc());
        statBVO.setDatElev(wrStatB.getDatElev() + BaseConstants.METER_SUFFIX);
        statBVO.setZ(wrStatB.getZ().get(0).getZ() + BaseConstants.METER_SUFFIX);
        statBVO.setQ(wrStatB.getQ().get(0).getQ() + BaseConstants.M3_S_SUFFIX);
        statBVO.setW(wrStatB.getW().get(0).getW() + BaseConstants.TOT_V);
    }

    /**
     * 封装河道返回体
     *
     * @param keyWordMap 关键字集合
     * @param statBVO 河道信息vo
     * @return 河道信息返回体
     */
    private SearchResponse packageRespWrStatB(WrStatBVO statBVO, Map<Integer, String> keyWordMap) {
        SearchResponse resp = new SearchResponse();
        String attribute = "";
        if (keyWordMap.get(YEAR_KEY).contains(YEAR_PREFIX)) {
            attribute = keyWordMap.get(ATTRIBUTE);
        } else {
            attribute = keyWordMap.get(4);
        }

        if (StringUtils.isEmpty(attribute)) {
            resp.setContent(statBVO.getStNm() + BaseConstants.LOCAL + statBVO.getLoc() + BaseConstants.COMMA_SYMBOL
                    + BaseConstants.BASE_HIGH + statBVO.getDatElev() + BaseConstants.COMMA_SYMBOL + BaseConstants.WATER_LEVEL
                    + statBVO.getZ() + BaseConstants.COMMA_SYMBOL + BaseConstants.RIVER_FLOW + statBVO.getQ()
                    + BaseConstants.COMMA_SYMBOL + BaseConstants.WATER_CAPACITY + statBVO.getW() + BaseConstants.COMMA_SYMBOL
                    + BaseConstants.RUN_STATUS + statBVO.getRunCond() + BaseConstants.COMMA_SYMBOL
                    + BaseConstants.WADI_ITEM + BaseConstants.COMMA_SYMBOL + BaseConstants.FLOW_ITEM);
        } else {
            switch (attribute) {
                case BaseConstants.A_LOCAL:
                    resp.setContent(statBVO.getStNm() + BaseConstants.LOCAL + statBVO.getLoc());
                    break;
                case BaseConstants.A_BASE_HIGH:
                    resp.setContent(statBVO.getStNm() + BaseConstants.BASE_HIGH + statBVO.getDatElev());
                    break;
                case BaseConstants.A_WATER_LEVEL:
                    resp.setContent(statBVO.getStNm() + BaseConstants.WATER_LEVEL + statBVO.getZ());
                    break;
                case BaseConstants.A_RIVER_FLOW:
                    resp.setContent(statBVO.getStNm() + BaseConstants.RIVER_FLOW + statBVO.getQ());
                    break;
                case BaseConstants.A_WATER_CAPACITY:
                    resp.setContent(statBVO.getStNm() + BaseConstants.WATER_CAPACITY + statBVO.getW());
                    break;
                default:
                    resp.setContent(statBVO.getStNm() + BaseConstants.LOCAL + statBVO.getLoc() + BaseConstants.COMMA_SYMBOL
                            + BaseConstants.BASE_HIGH + statBVO.getDatElev() + BaseConstants.COMMA_SYMBOL + BaseConstants.WATER_LEVEL
                            + statBVO.getZ() + BaseConstants.COMMA_SYMBOL + BaseConstants.RIVER_FLOW + statBVO.getQ()
                            + BaseConstants.COMMA_SYMBOL + BaseConstants.WATER_CAPACITY + statBVO.getW() + BaseConstants.COMMA_SYMBOL
                            + BaseConstants.RUN_STATUS + statBVO.getRunCond() + BaseConstants.COMMA_SYMBOL
                            + BaseConstants.WADI_ITEM + BaseConstants.COMMA_SYMBOL + BaseConstants.FLOW_ITEM);
            }
        }
        return resp;
    }
}
