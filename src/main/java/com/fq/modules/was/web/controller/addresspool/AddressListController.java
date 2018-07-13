package com.fq.modules.was.web.controller.addresspool;

import java.text.ParseException;
import java.util.Map;

import com.fq.modules.was.web.vo.AddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fq.modules.was.web.entity.addresspool.AddressList;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.addresspool.AddressListService;
import com.fq.modules.was.web.utils.ResultUtil;


/**
 * 钱包地址列表（运维生成）
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-12 10:06:21
 */
@RestController
@RequestMapping("v1/addresslist")
public class AddressListController {

    @Autowired
    private AddressListService addressListService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(AddressVo vo, Pages pages) {

        Map<String, Object> map = addressListService.queryPage(ResultUtil.initParams(vo,pages));

        return ResultUtil.success(map.get("result"), (Pages) map.get("pages"));
    }


    /**
     * 查询单条信息
     */
    @RequestMapping("/info/{wasId}")
    public Result info(@PathVariable("wasId") Integer wasId) {

        AddressList addressList =addressListService.selectById(wasId);
        return ResultUtil.success(addressList, null);
    }

    /**
     * 保存信息
     */
    @RequestMapping("/save")
    public Result save(AddressList addressList) {

        return ResultUtil.success(addressListService.insert(addressList), null);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update")
    public Result update(AddressList addressList) {

        return ResultUtil.success(addressListService.updateById(addressList), null);
    }

    /**
     * 删除
     */
    /*
    @RequestMapping("/delete")
    public Result delete(@RequestBody Integer[]wasIds) {
            addressListService.deleteBatchIds(Arrays.asList(wasIds));

        return ResultUtil.success(addressListService.deleteBatchIds(Arrays.asList(wasIds)), null);
    }*/

    /**
     * 获取地址明细
     * @param vo
     * @param pages
     * @return
     */
    @RequestMapping(value = "/getDetails")
    public Result getDetails(AddressVo vo, Pages pages) throws ParseException {

        Map<String, Object> map = addressListService.queryPageDetails(ResultUtil.initParams(vo,pages));

        return ResultUtil.success(map.get("result"),(Pages)map.get("pages"));
    }

}
