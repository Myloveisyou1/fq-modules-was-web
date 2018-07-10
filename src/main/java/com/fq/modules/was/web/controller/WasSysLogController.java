package com.fq.modules.was.web.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fq.modules.was.web.entity.WasSysLog;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.WasSysLogService;
import com.fq.modules.was.web.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 系统操作日志表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-10 10:39:15
 */
@RestController
@RequestMapping("v1/wassyslog")
public class WasSysLogController {

    @Autowired
    private WasSysLogService wasSysLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(WasSysLog wasSysLog, Pages pages) {

        Map<String, Object> map = wasSysLogService.queryPage(ResultUtil.initParams(wasSysLog,pages));

        return ResultUtil.success(map.get("result"), (Pages) map.get("pages"));
    }


    /**
     * 查询单条信息
     */
    @RequestMapping("/info/{wasId}")
    public Result info(@PathVariable("wasId") Long wasId) {

        WasSysLog wasSysLog =wasSysLogService.selectById(wasId);
        return ResultUtil.success(wasSysLog, null);
    }

    /**
     * 保存信息
     */
    @RequestMapping("/save")
    public Result save(WasSysLog wasSysLog) {

        return ResultUtil.success(wasSysLogService.insert(wasSysLog), null);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update")
    public Result update(WasSysLog wasSysLog) {

        return ResultUtil.success(wasSysLogService.updateById(wasSysLog), null);
    }

}
