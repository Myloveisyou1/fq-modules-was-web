package com.fq.modules.was.web.service.impl;

import com.fq.modules.was.web.entity.WasAddress;
import com.fq.modules.was.web.enums.ResultEnum;
import com.fq.modules.was.web.mapper.WasAddressListMapper;
import com.fq.modules.was.web.service.WasAddressListService;
import com.fq.modules.was.web.entity.WasAddressList;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.mapper.WasAddressListMapper;
import com.fq.modules.was.web.service.WasAddressListService;
import com.fq.modules.was.web.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("wasAddressListService")
public class WasAddressListServiceImpl implements WasAddressListService {

    @Autowired
    private WasAddressListMapper wasAddressListMapper;

    /**
    * 分页查询数据
    * @param params
    * @return
    */
    public Map<String,Object> queryPage(Map<String, Object> params) {

        Map<String,Object> map = new HashMap<>();

        //这里开始写具体的业务逻辑

        List<WasAddressList> list = wasAddressListMapper.pageQuery(params);
        Long count = wasAddressListMapper.pageQueryCount(params);
        Pages pages = (Pages) params.get("pages");
        pages.setTotalCount(count);
        long totalPage = count%pages.getPageSize()>0?(1+count/pages.getPageSize()):(count/pages.getPageSize());
        pages.setTotalPage(Integer.valueOf(totalPage+""));

        //处理返回值,主要是算出[地址总数\剩余数量\使用频率\最小剩余数\预警触发]
        /**
         * BTC	bita	5	bita下的BTC	3
         * BTC	bita	6	bita下的BTC	1
         * BTC	pizza	5	pizza下的BTC	3
         */
        if (CommonUtil.isNotEmpty(list)) {
            for (WasAddressList temp : list) {
                //根据wasType\wasSource\wasExpireTime查询剩余数量
                temp.setSurplusCount(wasAddressListMapper.queryCountByWasTypeAndSource(temp.getWasType(),temp.getWasSource(),temp.getWasExpireTime()));
                double rete = (double)(temp.getTotalCount()-temp.getSurplusCount())/temp.getTotalCount();
                if (rete <= 0.00) {
                    temp.setRate("0%");
                } else {
                    temp.setRate(String.format("%.2f",rete*100)+"%");
                }
                //查询最小剩余数
                temp.setMinSurplusCount(3l);
                if (temp.getSurplusCount() <= temp.getMinSurplusCount()) {
                    temp.setTrigger(true);
                } else {
                    temp.setTrigger(false);
                }
            }
        }
        map.put("result",list);//数据信息
        map.put("pages",pages);//分页信息

        return map;
    }

    /**
     * 根据id查询信息
     * @param wasId 主键id
     */
    public WasAddressList selectById(Integer wasId) {

        WasAddressList wasAddressList = wasAddressListMapper.selectById(wasId);

        return wasAddressList;
    }

    /**
     * 保存信息
     * @param wasAddressList 需要保存的对象
     * @return
     */
    public boolean insert(WasAddressList wasAddressList) {

        return wasAddressListMapper.insert(wasAddressList);
    }

    /**
     * 修改信息
     * @param wasAddressList 需要修改的对象
     * @return
     */
    public boolean updateById(WasAddressList wasAddressList) {

        return wasAddressListMapper.updateById(wasAddressList);
    }

    @Override
    public boolean deleteBatchIds(List<Integer> integers) {
        return false;
    }

    /**
     * 地址总数详情
     * @param stringObjectMap
     * @return
     */
    @Override
    public Map<String, Object> queryPageTotalCount(Map<String, Object> stringObjectMap,Integer type) {

        Map<String,Object> map = new HashMap<>();

        //这里开始写具体的业务逻辑
        //1.地址总数详情, 2.剩余数量详情
        List<WasAddress> list = null;
        Long count = 0l;
        if (type == 1) {
            list = wasAddressListMapper.queryPageTotalCount(stringObjectMap);
            count = wasAddressListMapper.queryPageTotalCounts(stringObjectMap);
        } else if (type == 2) {
            list = wasAddressListMapper.queryPageSurplusCount(stringObjectMap);
            count = wasAddressListMapper.queryPageSurplusCounts(stringObjectMap);
        }
        Pages pages = (Pages) stringObjectMap.get("pages");
        pages.setTotalCount(count);
        long totalPage = count%pages.getPageSize()>0?(1+count/pages.getPageSize()):(count/pages.getPageSize());
        pages.setTotalPage(Integer.valueOf(totalPage+""));

        //处理数据
        if (CommonUtil.isNotEmpty(list)) {
            for (WasAddress temp : list) {
                if (temp.getFromT().equals("1")) {//已使用的地址
                    temp.setStatus(ResultEnum.USED.getMsg());
                } else if (temp.getFromT().equals("2")) {
                    //判断是否过期
                    if (temp.getWasCreateTime().getTime()+temp.getWasExpireTime()*24*60*60*1000 < System.currentTimeMillis()) {
                        temp.setStatus(ResultEnum.EXPIRED.getMsg());
                    } else {
                        temp.setStatus(ResultEnum.NOT_USED.getMsg());
                    }
                }
                temp.setExpireTime(new Date(temp.getWasCreateTime().getTime()+temp.getWasExpireTime()*24*60*60*1000));
            }
        }
        map.put("result",list);//数据信息
        map.put("pages",pages);//分页信息

        return map;
    }

    /**
     * 废弃方法
     * 地址剩余数量详情
     * @param stringObjectMap
     * @return
     */
    @Override
    public Map<String, Object> queryPageSurplusCount(Map<String, Object> stringObjectMap) {

        /*Map<String,Object> map = new HashMap<>();

        //这里开始写具体的业务逻辑

        List<WasAddress> list = wasAddressListMapper.queryPageSurplusCount(stringObjectMap);
        Long count = wasAddressListMapper.queryPageSurplusCounts(stringObjectMap);
        Pages pages = (Pages) stringObjectMap.get("pages");
        pages.setTotalCount(count);
        long totalPage = count%pages.getPageSize()>0?(1+count/pages.getPageSize()):(count/pages.getPageSize());
        pages.setTotalPage(Integer.valueOf(totalPage+""));

        //处理数据
        if (CommonUtil.isNotEmpty(list)) {
            for (WasAddress temp : list) {
                if (temp.getFromT().equals("1")) {//已使用的地址
                    temp.setStatus(ResultEnum.USED.getMsg());
                } else if (temp.getFromT().equals("2")) {
                    //判断是否过期
                    if (temp.getWasCreateTime().getTime()+temp.getWasExpireTime()*24*60*60*1000 < System.currentTimeMillis()) {
                        temp.setStatus(ResultEnum.EXPIRED.getMsg());
                    } else {
                        temp.setStatus(ResultEnum.NOT_USED.getMsg());
                    }
                }
                temp.setExpireTime(new Date(temp.getWasCreateTime().getTime()+temp.getWasExpireTime()*24*60*60*1000));
            }
        }
        map.put("result",list);//数据信息
        map.put("pages",pages);//分页信息*/

        return null;
    }

}
