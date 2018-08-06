package com.fq.modules.was.web.controller.addresspool;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fq.modules.was.web.entity.addresspool.WarnHistory;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.addresspool.WarnHistoryService;
import com.fq.modules.was.web.utils.ResultUtil;


/**
 * 预警提醒历史记录表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-12 18:18:34
 */
@RestController
@RequestMapping("v1/warnhistory" )
public class WarnHistoryController {

    @Autowired
    private WarnHistoryService warnHistoryService;

    /**
     * 列表
     */
    @RequestMapping("/list" )
    public Result list(WarnHistory warnHistory, Pages pages) {

        Map<String, Object> map = warnHistoryService.queryPage(ResultUtil.initParams(warnHistory, pages));

        return ResultUtil.success(map.get("result" ), (Pages) map.get("pages" ));
    }


    /**
     * 查询单条信息
     */
    @RequestMapping("/info/{wasId}" )
    public Result info(@PathVariable("wasId" ) Integer wasId) {

        WarnHistory warnHistory = warnHistoryService.selectById(wasId);
        return ResultUtil.success(warnHistory, null);
    }

    /**
     * 保存信息
     */
    @RequestMapping("/save" )
    public Result save(WarnHistory warnHistory) {

        return ResultUtil.success(warnHistoryService.insert(warnHistory), null);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update" )
    public Result update(WarnHistory warnHistory) {

        return ResultUtil.success(warnHistoryService.updateById(warnHistory), null);
    }

    /**
     * 删除
     */
    /*
    @RequestMapping("/delete")
    public Result delete(@RequestBody Integer[]wasIds) {
            warnHistoryService.deleteBatchIds(Arrays.asList(wasIds));

        return ResultUtil.success(warnHistoryService.deleteBatchIds(Arrays.asList(wasIds)), null);
    }*/

}
