package com.fq.modules.was.web.controller.basic;

import com.fq.modules.was.web.entity.basic.SysConfig;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.basic.SysConfigService;
import com.fq.modules.was.web.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 系统参数配置表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-18 14:18:40
 */
@RestController
@RequestMapping("v1/sysconfig")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(SysConfig sysConfig, Pages pages) {

        Map<String, Object> map = sysConfigService.queryPage(ResultUtil.initParams(sysConfig,pages));

        return ResultUtil.success(map.get("result"), (Pages) map.get("pages"));
    }


    /**
     * 查询单条信息
     */
    @RequestMapping("/info/{wasId}")
    public Result info(@PathVariable("wasId") Long wasId) {

        SysConfig sysConfig =sysConfigService.selectById(wasId);
        return ResultUtil.success(sysConfig, null);
    }

    /**
     * 新增或者修改配置信息
     * @param sysConfig
     * @param obj
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    public Result saveOrUpdate(SysConfig sysConfig, @RequestParam("obj") String obj) {
        if (obj.equals("0")) {//新增
            return save(sysConfig);
        } else if (obj.equals("1")) {//编辑
            return update(sysConfig);
        }
        return null;
    }

    /**
     * 保存信息
     */
    @RequestMapping("/save")
    public Result save(SysConfig sysConfig) {

        return ResultUtil.success(sysConfigService.insert(sysConfig), null);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update")
    public Result update(SysConfig sysConfig) {

        return ResultUtil.success(sysConfigService.updateById(sysConfig), null);
    }

    /**
     * 删除
     */
    /*
    @RequestMapping("/delete")
    public Result delete(@RequestBody Long[]wasIds) {
            sysConfigService.deleteBatchIds(Arrays.asList(wasIds));

        return ResultUtil.success(sysConfigService.deleteBatchIds(Arrays.asList(wasIds)), null);
    }*/

}
