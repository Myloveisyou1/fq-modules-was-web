package com.fq.modules.was.web.controller;

import com.fq.modules.was.web.entity.WasDataDictionary;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.WasDataDictionaryService;
import com.fq.modules.was.web.utils.ResultUtil;
import com.fq.modules.was.web.entity.WasDataDictionary;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.WasDataDictionaryService;
import com.fq.modules.was.web.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * 数据字典表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-05 10:12:41
 */
@RestController
@RequestMapping("generator/wasdatadictionary")
public class WasDataDictionaryController {

    @Autowired
    private WasDataDictionaryService wasDataDictionaryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(WasDataDictionary wasDataDictionary, Pages pages){

        Map<String,Object> params = new HashMap<>();
        params.put("bean",wasDataDictionary);
        params.put("pages",pages);
        Map<String,Object> map = wasDataDictionaryService.queryPage(params);

        return ResultUtil.success(map.get("result"),(Pages) map.get("pages"));
    }


    /**
     * 查询单条信息
     */
    @RequestMapping("/info/{wasId}")
    public Result info(@PathVariable("wasId") Integer wasId){

		WasDataDictionary wasDataDictionary = wasDataDictionaryService.selectById(wasId);
        return ResultUtil.success(wasDataDictionary,null);
    }

    /**
     * 保存信息
     */
    @RequestMapping("/saveOrUpdate")
    public Result save(WasDataDictionary wasDataDictionary,@RequestParam("saveOrUpdate") String saveOrUpdate){

        if (saveOrUpdate.equals("1")) {
            return ResultUtil.success(wasDataDictionaryService.insert(wasDataDictionary),null);
        } else if (saveOrUpdate.equals("2")) {
            return ResultUtil.success(wasDataDictionaryService.updateById(wasDataDictionary),null);
        }
        return null;
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update")
    public Result update(WasDataDictionary wasDataDictionary){

        return ResultUtil.success(wasDataDictionaryService.updateById(wasDataDictionary),null);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public Result delete(@RequestBody Integer[] wasIds){
			wasDataDictionaryService.deleteBatchIds(Arrays.asList(wasIds));

        return ResultUtil.success(wasDataDictionaryService.deleteBatchIds(Arrays.asList(wasIds)),null);
    }

    /**
     * 禁用/启用全部币种
     * @return
     */
    @RequestMapping(value = "/disabledAll")
    public Result disabledAll(@RequestParam("way") String way) {

        return ResultUtil.success(wasDataDictionaryService.disabledAll(way),null);

    }

    @RequestMapping(value = "/getNewHeight")
    public Result getNewHeight(@RequestParam("wasType") String wasType) {

        return ResultUtil.success(wasDataDictionaryService.getNewHeight(wasType),null);
    }

}
