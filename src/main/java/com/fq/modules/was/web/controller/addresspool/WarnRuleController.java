package com.fq.modules.was.web.controller.addresspool;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fq.modules.was.web.entity.addresspool.WarnRule;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.addresspool.WarnRuleService;
import com.fq.modules.was.web.utils.ResultUtil;


/**
 * 预警提醒规则
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-12 17:15:52
 */
@RestController
@RequestMapping("v1/warnrule" )
public class WarnRuleController {

    @Autowired
    private WarnRuleService warnRuleService;

    /**
     * 列表
     */
    @RequestMapping("/list" )
    public Result list(WarnRule warnRule, Pages pages) {

        Map<String, Object> map = warnRuleService.queryPage(ResultUtil.initParams(warnRule, pages));

        return ResultUtil.success(map.get("result" ), (Pages) map.get("pages" ));
    }


    /**
     * 查询单条信息
     */
    @RequestMapping("/info/{wasId}" )
    public Result info(@PathVariable("wasId" ) Integer wasId) {

        WarnRule warnRule = warnRuleService.selectById(wasId);
        return ResultUtil.success(warnRule, null);
    }

    /**
     * 查询单条信息
     */
    @RequestMapping("/selectByTypeAndSource/{wasType}/{wasSource}" )
    public Result info(@PathVariable("wasType" ) String wasType, @PathVariable("wasSource" ) String wasSource) {

        WarnRule warnRule = warnRuleService.selectByTypeAndSource(wasType, wasSource);
        return ResultUtil.success(warnRule, null);
    }

    /**
     * 保存信息
     */
    @RequestMapping("/save" )
    public Result save(WarnRule warnRule) {

        return ResultUtil.success(warnRuleService.insert(warnRule), null);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update" )
    public Result update(WarnRule warnRule) {

        return ResultUtil.success(warnRuleService.updateById(warnRule), null);
    }

    /**
     * 删除
     */
    /*
    @RequestMapping("/delete")
    public Result delete(@RequestBody Integer[]wasIds) {
            warnRuleService.deleteBatchIds(Arrays.asList(wasIds));

        return ResultUtil.success(warnRuleService.deleteBatchIds(Arrays.asList(wasIds)), null);
    }*/

}
