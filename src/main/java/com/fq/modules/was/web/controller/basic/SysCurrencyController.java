package com.fq.modules.was.web.controller.basic;

import java.util.Map;

import com.fq.modules.was.web.entity.basic.SysCurrency;
import com.fq.modules.was.web.service.basic.SysCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.utils.ResultUtil;


/**
 * 币种类型表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-16 09:54:44
 */
@RestController
@RequestMapping("v1/syscurrency")
public class SysCurrencyController {

    @Autowired
    private SysCurrencyService sysCurrencyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(SysCurrency sysCurrency, Pages pages) {

        Map<String, Object> map = sysCurrencyService.queryPage(ResultUtil.initParams(sysCurrency,pages));

        return ResultUtil.success(map.get("result"), (Pages) map.get("pages"));
    }


    /**
     * 查询单条信息
     */
    @RequestMapping("/info/{wasId}")
    public Result info(@PathVariable("wasId") Integer wasId) {

        SysCurrency sysCurrency =sysCurrencyService.selectById(wasId);
        return ResultUtil.success(sysCurrency, null);
    }

    /**
     * 保存信息
     */
    @RequestMapping("/save")
    public Result save(SysCurrency sysCurrency) {

        return ResultUtil.success(sysCurrencyService.insert(sysCurrency), null);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update")
    public Result update(SysCurrency sysCurrency) {

        return ResultUtil.success(sysCurrencyService.updateById(sysCurrency), null);
    }

    /**
     * 删除
     */
    /*
    @RequestMapping("/delete")
    public Result delete(@RequestBody Integer[]wasIds) {
            sysCurrencyService.deleteBatchIds(Arrays.asList(wasIds));

        return ResultUtil.success(sysCurrencyService.deleteBatchIds(Arrays.asList(wasIds)), null);
    }*/

    /**
     * 查询所有币种
     * @return
     */
    @RequestMapping(value = "/findAll")
    public Result findAll() {

        return ResultUtil.success(sysCurrencyService.findAll(),null);
    }

}
