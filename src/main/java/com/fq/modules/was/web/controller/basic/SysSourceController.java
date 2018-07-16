package com.fq.modules.was.web.controller.basic;

import java.util.Map;

import com.fq.modules.was.web.entity.basic.SysSource;
import com.fq.modules.was.web.service.basic.SysSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.utils.ResultUtil;


/**
 * 业务平台表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-16 09:54:44
 */
@RestController
@RequestMapping("v1/syssource")
public class SysSourceController {

    @Autowired
    private SysSourceService sysSourceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(SysSource sysSource, Pages pages) {

        Map<String, Object> map = sysSourceService.queryPage(ResultUtil.initParams(sysSource,pages));

        return ResultUtil.success(map.get("result"), (Pages) map.get("pages"));
    }


    /**
     * 查询单条信息
     */
    @RequestMapping("/info/{wasId}")
    public Result info(@PathVariable("wasId") Integer wasId) {

        SysSource sysSource =sysSourceService.selectById(wasId);
        return ResultUtil.success(sysSource, null);
    }

    /**
     * 保存信息
     */
    @RequestMapping("/save")
    public Result save(SysSource sysSource) {

        return ResultUtil.success(sysSourceService.insert(sysSource), null);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update")
    public Result update(SysSource sysSource) {

        return ResultUtil.success(sysSourceService.updateById(sysSource), null);
    }

    /**
     * 删除
     */
    /*
    @RequestMapping("/delete")
    public Result delete(@RequestBody Integer[]wasIds) {
            sysSourceService.deleteBatchIds(Arrays.asList(wasIds));

        return ResultUtil.success(sysSourceService.deleteBatchIds(Arrays.asList(wasIds)), null);
    }*/

    /**
     * 查询所有平台
     * @return
     */
    @RequestMapping(value = "/findAll")
    public Result findAll() {

        return ResultUtil.success(sysSourceService.findAll(),null);
    }

}
