package com.fq.modules.was.web.controller.payrecord;

import java.util.Map;

import com.fq.modules.was.web.entity.payrecord.PayRecord;
import com.fq.modules.was.web.service.payrecord.PayRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.utils.ResultUtil;


/**
 * 钱包转币操作记录表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-16 15:53:09
 */
@RestController
@RequestMapping("v1/payrecord")
public class PayRecordController {

    @Autowired
    private PayRecordService payRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(PayRecord payRecord, Pages pages) {

        Map<String, Object> map = payRecordService.queryPage(ResultUtil.initParams(payRecord,pages));

        return ResultUtil.success(map.get("result"), (Pages) map.get("pages"));
    }


    /**
     * 查询单条信息
     */
    @RequestMapping("/info/{wasId}")
    public Result info(@PathVariable("wasId") Integer wasId) {

        PayRecord payRecord =payRecordService.selectById(wasId);
        return ResultUtil.success(payRecord, null);
    }

    /**
     * 保存信息
     */
    @RequestMapping("/save")
    public Result save(PayRecord payRecord) {

        return ResultUtil.success(payRecordService.insert(payRecord), null);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update")
    public Result update(PayRecord payRecord) {

        return ResultUtil.success(payRecordService.updateById(payRecord), null);
    }

    /**
     * 删除
     */
    /*
    @RequestMapping("/delete")
    public Result delete(@RequestBody Integer[]wasIds) {
            payRecordService.deleteBatchIds(Arrays.asList(wasIds));

        return ResultUtil.success(payRecordService.deleteBatchIds(Arrays.asList(wasIds)), null);
    }*/

    /**
     * 再次执行    从而获得交易hash或者重新请求到对应的钱包客户端
     * @param wasId
     * @param oType    1.未收到hash的情况;2.请求发送失败的情况
     * @return
     */
    @PostMapping(value = "/doAgain")
    public Result doAgain(@RequestParam("wasId") Integer wasId, @RequestParam("oType") String oType) {

        return ResultUtil.success(payRecordService.doAgain(wasId,oType),null);
    }

    /**
     * 根据wasId查询操作历史记录
     * @param wasId
     * @return
     */
    @PostMapping(value = "/findHistoryByWasId")
    public Result findHistoryByWasId(@RequestParam("wasId") Integer wasId) {

        return ResultUtil.success(payRecordService.findHistoryByWasId(wasId),null);
    }

}
