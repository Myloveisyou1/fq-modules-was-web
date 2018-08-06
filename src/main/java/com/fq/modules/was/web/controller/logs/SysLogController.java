package com.fq.modules.was.web.controller.logs;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fq.modules.was.web.entity.logs.SysLog;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.logs.SysLogService;
import com.fq.modules.was.web.utils.ResultUtil;


/**
 * 系统操作日志表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-11 19:01:03
 */
@RestController
@RequestMapping("v1/syslog" )
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     */
    @RequestMapping("/list" )
    public Result list(SysLog sysLog, Pages pages) {

        Map<String, Object> map = sysLogService.queryPage(ResultUtil.initParams(sysLog, pages));

        return ResultUtil.success(map.get("result" ), (Pages) map.get("pages" ));
    }


    /**
     * 查询单条信息
     */
    @RequestMapping("/info/{wasId}" )
    public Result info(@PathVariable("wasId" ) Long wasId) {

        SysLog sysLog = sysLogService.selectById(wasId);
        return ResultUtil.success(sysLog, null);
    }

    /**
     * 保存信息
     */
    @RequestMapping("/save" )
    public Result save(SysLog sysLog) {

        return ResultUtil.success(sysLogService.insert(sysLog), null);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update" )
    public Result update(SysLog sysLog) {

        return ResultUtil.success(sysLogService.updateById(sysLog), null);
    }

    /**
     * 删除
     */
    /*
    @RequestMapping("/delete")
    public Result delete(@RequestBody Long[]wasIds) {
        sysLogService.deleteBatchIds(Arrays.asList(wasIds));

        return ResultUtil.success(sysLogService.deleteBatchIds(Arrays.asList(wasIds)), null);
    }*/

}
