package com.fq.modules.was.web.controller.setting;

import com.fq.modules.was.web.entity.setting.SysConfig;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.setting.SysConfigService;
import com.fq.modules.was.web.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-06 16:54:47
 */
@RestController
@RequestMapping("v1/sysconfig" )
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list" )
    public Result list(SysConfig sysConfig, Pages pages) {

        Map<String, Object> params = new HashMap<>();
        params.put("bean", sysConfig);
        params.put("pages", pages);
        Map<String, Object> map = sysConfigService.queryPage(params);

        return ResultUtil.success(map.get("result" ), (Pages) map.get("pages" ));
    }

    /**
     * 添加配置
     *
     * @param sysConfig
     * @return
     */
    @RequestMapping(value = "/add" )
    public Result add(SysConfig sysConfig) {

        return ResultUtil.success(sysConfigService.insert(sysConfig), null);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update" )
    public Result update(SysConfig sysConfig) {

        return ResultUtil.success(sysConfigService.updateById(sysConfig), null);
    }

    /**
     * 查询单条信息
     */
    @RequestMapping("/info/{wasType}/{wasSource}" )
    public Result info(@PathVariable("wasType" ) String wasType, @PathVariable("wasSource" ) String wasSource) {

        Map<String, Object> sysConfig = sysConfigService.selectByTypeAndSource(wasType, wasSource);
        return ResultUtil.success(sysConfig, null);
    }

}
