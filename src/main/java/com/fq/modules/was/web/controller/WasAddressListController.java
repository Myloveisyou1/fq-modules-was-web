package com.fq.modules.was.web.controller;

import com.fq.modules.was.web.entity.WasAddress;
import com.fq.modules.was.web.entity.WasAddressList;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.WasAddressListService;
import com.fq.modules.was.web.service.WasAddressService;
import com.fq.modules.was.web.utils.ResultUtil;
import com.fq.modules.was.web.entity.WasAddressList;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.WasAddressListService;
import com.fq.modules.was.web.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * 钱包地址列表（运维生成）
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-06 16:05:19
 */
@RestController
@RequestMapping("v1/wasaddresslist")
public class WasAddressListController {

    @Autowired
    private WasAddressListService wasAddressListService;
    @Autowired
    private WasAddressService wasAddressService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(WasAddressList wasAddressList, Pages pages){

        Map<String,Object> params = new HashMap<>();
        params.put("bean",wasAddressList);
        params.put("pages",pages);
        Map<String,Object> map = wasAddressListService.queryPage(ResultUtil.initParams(wasAddressList,pages));

        return ResultUtil.success(map.get("result"),(Pages)map.get("pages"));
    }

    /**
     * 获取地址详情
     * @param wasAddress
     * @param pages
     * @param identify   1.地址总数详情, 2.剩余数量详情
     * @return
     */
    @RequestMapping(value = "/getDetails")
    public Result getDetails(WasAddress wasAddress, Pages pages, String identify) {
        Map<String,Object> map = null;
        if (identify.equals("1")) {//总数    未使用和已使用的合集
            map = wasAddressListService.queryPageTotalCount(ResultUtil.initParams(wasAddress,pages),1);
        } else {                    //剩余数量   未使用的数量
            map = wasAddressListService.queryPageTotalCount(ResultUtil.initParams(wasAddress,pages),2);
        }

        return ResultUtil.success(map.get("result"),(Pages)map.get("pages"));
    }

    /**
     * 查询单条信息
     */
    @RequestMapping("/info/{wasId}")
    public Result info(@PathVariable("wasId") Integer wasId){

		WasAddressList wasAddressList = wasAddressListService.selectById(wasId);
        return ResultUtil.success(wasAddressList,null);
    }

    /**
     * 保存信息
     */
    @RequestMapping("/save")
    public Result save(WasAddressList wasAddressList){

        return ResultUtil.success(wasAddressListService.insert(wasAddressList),null);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update")
    public Result update(WasAddressList wasAddressList){

        return ResultUtil.success(wasAddressListService.updateById(wasAddressList),null);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public Result delete(@RequestBody Integer[] wasIds){
			wasAddressListService.deleteBatchIds(Arrays.asList(wasIds));

        return ResultUtil.success(wasAddressListService.deleteBatchIds(Arrays.asList(wasIds)),null);
    }

}
