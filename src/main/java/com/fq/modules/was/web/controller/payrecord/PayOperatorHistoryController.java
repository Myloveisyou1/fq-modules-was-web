package com.fq.modules.was.web.controller.payrecord;

import java.util.Arrays;
import java.util.Map;

import com.fq.modules.was.web.entity.payrecord.PayOperatorHistory;
import com.fq.modules.was.web.service.payrecord.PayOperatorHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.utils.ResultUtil;


/**
 * 提币失败的操作历史表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-17 09:44:07
 */
@RestController
@RequestMapping("v1/payoperatorhistory")
public class PayOperatorHistoryController {

    @Autowired
    private PayOperatorHistoryService payOperatorHistoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(PayOperatorHistory payOperatorHistory, Pages pages) {

        Map<String, Object> map = payOperatorHistoryService.queryPage(ResultUtil.initParams(payOperatorHistory,pages));

        return ResultUtil.success(map.get("result"), (Pages) map.get("pages"));
    }


    /**
     * 查询单条信息
     */
    @RequestMapping("/info/{wasId}")
    public Result info(@PathVariable("wasId") Integer wasId) {

        PayOperatorHistory payOperatorHistory =payOperatorHistoryService.selectById(wasId);
        return ResultUtil.success(payOperatorHistory, null);
    }

    /**
     * 保存信息
     */
    @RequestMapping("/save")
    public Result save(PayOperatorHistory payOperatorHistory) {

        return ResultUtil.success(payOperatorHistoryService.insert(payOperatorHistory), null);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update")
    public Result update(PayOperatorHistory payOperatorHistory) {

        return ResultUtil.success(payOperatorHistoryService.updateById(payOperatorHistory), null);
    }

    /**
     * 删除
     */
    /*
    @RequestMapping("/delete")
    public Result delete(@RequestBody Integer[]wasIds) {
            payOperatorHistoryService.deleteBatchIds(Arrays.asList(wasIds));

        return ResultUtil.success(payOperatorHistoryService.deleteBatchIds(Arrays.asList(wasIds)), null);
    }*/

}
