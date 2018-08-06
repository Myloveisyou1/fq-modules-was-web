package com.fq.modules.was.web.service.datadictionary.impl;

import com.alibaba.fastjson.JSONObject;
import com.fq.modules.was.web.entity.common.Constant;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.datadictionary.DataDictionary;
import com.fq.modules.was.web.entity.logs.SysLog;
import com.fq.modules.was.web.mapper.datadictionary.DataDictionaryMapper;
import com.fq.modules.was.web.mapper.logs.SysLogMapper;
import com.fq.modules.was.web.service.common.impl.BaseServiceImpl;
import com.fq.modules.was.web.service.datadictionary.DataDictionaryService;
import com.fq.modules.was.web.utils.CommonUtil;
import com.fq.modules.was.web.utils.DatesUtils;
import com.fq.modules.was.web.utils.ExcelUtils;
import com.fq.modules.was.web.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DataDictionaryServiceImpl extends BaseServiceImpl implements DataDictionaryService {

    @Autowired
    private DataDictionaryMapper dataDictionaryMapper;

    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * 分页查询数据
     *
     * @param params
     * @return
     */
    public Map<String, Object> queryPage(Map<String, Object> params) {

        Map<String, Object> map = new HashMap<>();

        //这里开始写具体的业务逻辑
        List<DataDictionary> list = dataDictionaryMapper.pageQueryList(params);
        Long count = dataDictionaryMapper.pageQueryCount(params);

        Pages pages = (Pages) params.get("pages" );
        pages.setTotalCount(count);
        long totalPage = count % pages.getPageSize() > 0 ? (1 + count / pages.getPageSize()) : (count / pages.getPageSize());
        pages.setTotalPage(Integer.valueOf(totalPage + "" ));

        //处理数据
        if (CommonUtil.isNotEmpty(list)) {
            for (DataDictionary temp : list) {
                if (CommonUtil.isEmpty(temp.getWasCoinIntroduceUrl())) {
                    temp.setWasCoinIntroduceUrl("" );
                }
                if (CommonUtil.isEmpty(temp.getWasBlockBrowsersUrl())) {
                    temp.setWasBlockBrowsersUrl("" );
                }
            }
        }
        map.put("result", list);//数据信息
        map.put("pages", pages);//分页信息

        return map;
    }

    /**
     * 根据主键查询信息
     */
    public DataDictionary selectById(Integer wasId) {

        return dataDictionaryMapper.selectById(wasId);
    }

    /**
     * 保存信息
     *
     * @param dataDictionary 需要保存的对象
     * @return
     */
    public boolean insert(DataDictionary dataDictionary) {

        boolean flag = dataDictionaryMapper.addDataDictionary(dataDictionary);
        //记录日志信息
        String content = getUserName() + "在" + DatesUtils.time() + "新增了【】的信息";
        String result = "新增成功";
        if (!flag) {
            result = "新增失败";
        }
        SysLog sysLog = new SysLog(1, getUserName(), content, result);
        sysLogMapper.addSysLog(sysLog);

        return flag;
    }

    /**
     * 修改信息
     *
     * @param dataDictionary 需要修改的对象
     * @return
     */
    public boolean updateById(DataDictionary dataDictionary) {

        boolean flag = dataDictionaryMapper.optUpdateDataDictionary(dataDictionary);
        //记录日志信息
        String content = getUserName() + "在" + DatesUtils.time() + "修改了【】的信息";
        String result = "修改成功";
        if (!flag) {
            result = "修改失败";
        }
        SysLog sysLog = new SysLog(2, getUserName(), content, result);
        sysLogMapper.addSysLog(sysLog);
        return flag;
    }

    /**
     * 根据id删除信息
     *
     * @return
     */
    public int deleteById(Integer wasId) {

        int ret = dataDictionaryMapper.deleteById(wasId);

        //记录日志信息
        String content = getUserName() + "在" + DatesUtils.time() + "删除了id=【wasId】的信息";
        String result = "删除成功";
        if (ret == 0) {
            result = "删除失败";
        }
        SysLog sysLog = new SysLog(4, getUserName(), content, result);
        sysLogMapper.addSysLog(sysLog);

        return ret;
    }

    @Override
    public int disabledAll(String way) {
        //way 对应操作类型  0:一键禁用,1:一键启用
        String type = "";
        if (way.equals("0" )) {
            type = "一键禁用";
        } else {
            type = "一键启用";
        }

        //系统日志
        sysLogMapper.addSysLog(new SysLog(2, getUserName(), getUserName() + "在" + DatesUtils.time() + type + "数字货币", "操作成功" ));

        return dataDictionaryMapper.disabledAll(way);
    }

    @Override
    public Map<String, Object> getNewHeight(String wasType) {
        Map<String, Object> map = new HashMap<>();
        map.put("wasType", wasType);
        String ret = HttpClientUtils.doPost(Constant.URL_DATA_DICTIONARY, map);

        JSONObject jsonObject = JSONObject.parseObject(ret);

        //是否需要把最新区块高度更新到数据库
        Integer blockNumber = jsonObject.getJSONObject("data" ).getInteger("block_number" );
        int a = dataDictionaryMapper.updateBlockNumByWasType(wasType, blockNumber);
        Map<String, Object> result = new HashMap<>(2);
        if (a > 0) {
            result.put("msg", jsonObject.getString("msg" ));
            result.put("blockNumber", blockNumber);
        } else {
            result.put("msg", "获取失败,数据更新失败!" );
        }
        return result;
    }

    /**
     * 查询信息列表
     *
     * @param params
     * @return
     */
    public String findAll(Map<String, Object> params) {
        String path = "";
        String[] title = {"序号", "执行方法", "币种类型", "代币合约地址", "最小确认数", "区块高度", "轮询位数", "网关", "精度", "归零价格", "归零限制", "转账价格", "转账限制", "货币介绍URL", "区块浏览器URL", "状态", "备注"};
        String excelName = "数字货币管理";
        List<DataDictionary> list = dataDictionaryMapper.findAll(params);
        if (CommonUtil.isNotEmpty(list)) {
            String[][] content = new String[list.size()][title.length];
            Map<String, Object> map = new HashMap<>();
            map.put("time", DatesUtils.time());
            //处理返回值
            for (int i = 0; i < list.size(); i++) {
                content[i][0] = i + 1 + "";
                content[i][1] = list.get(i).getWasBaseCurrency();
                content[i][2] = list.get(i).getWasType();
                content[i][3] = list.get(i).getWasTokenAddress();
                content[i][4] = list.get(i).getWasMinConfirm() + "";
                content[i][5] = list.get(i).getWasBeginBlock();
                content[i][6] = list.get(i).getWasBlockNum();
                content[i][7] = list.get(i).getWasGateWay();
                content[i][8] = list.get(i).getWasPrecision() + "";
                content[i][9] = list.get(i).getWasZeroGasPrice();
                content[i][10] = list.get(i).getWasZeroGasLimit();
                content[i][11] = list.get(i).getWasTransferGasPrice();
                content[i][12] = list.get(i).getWasTransferGasLimit();
                content[i][13] = list.get(i).getWasCoinIntroduceUrl();
                content[i][14] = list.get(i).getWasBlockBrowsersUrl();
                String status = "";
                switch (list.get(i).getWasStatus()) {
                    case 0:
                        status = "禁用";
                        break;
                    case 1:
                        status = "启用";
                        break;
                    case 2:
                        status = "禁止提币";
                        break;
                    case 3:
                        status = "开放提币";
                        break;
                    default:
                        status = "";
                }
                content[i][15] = status;
                content[i][16] = list.get(i).getWasRemark();
            }
            path = ExcelUtils.excel(map, title, excelName, content);


            //记录日志
            sysLogMapper.addSysLog(new SysLog(3, getUserName(), getUserName() + "在" + DatesUtils.time() + "导出数字货币列表到Excel", "导出Excel成功!" ));

        }
        return path;
    }
}
