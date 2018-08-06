package com.fq.modules.was.web.controller.datadictionary;


import java.util.Map;

import com.fq.modules.was.web.entity.datadictionary.DataDictionary;
import com.fq.modules.was.web.service.datadictionary.DataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.utils.ResultUtil;


/**
 * 数据字典表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-17 14:16:38
 */
@RestController
@RequestMapping("v1/datadictionary" )
public class DataDictionaryController {

    @Autowired
    private DataDictionaryService dataDictionaryService;

    /**
     * 列表
     */
    @RequestMapping("/list" )
    public Result list(DataDictionary dataDictionary, Pages pages) {

        Map<String, Object> map = dataDictionaryService.queryPage(ResultUtil.initParams(dataDictionary, pages));

        return ResultUtil.success(map.get("result" ), (Pages) map.get("pages" ));
    }


    /**
     * 查询单条信息
     */
    @RequestMapping("/info/{wasId}" )
    public Result info(@PathVariable("wasId" ) Integer wasId) {

        DataDictionary dataDictionary = dataDictionaryService.selectById(wasId);
        return ResultUtil.success(dataDictionary, null);
    }

    /**
     * 保存信息
     */
    @RequestMapping("/save" )
    public Result save(DataDictionary dataDictionary) {

        return ResultUtil.success(dataDictionaryService.insert(dataDictionary), null);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update" )
    public Result update(DataDictionary dataDictionary) {

        return ResultUtil.success(dataDictionaryService.updateById(dataDictionary), null);
    }

    /**
     * 删除
     */
    /*
    @RequestMapping("/delete")
    public Result delete(@RequestBody Integer[]wasIds) {
            dataDictionaryService.deleteBatchIds(Arrays.asList(wasIds));

        return ResultUtil.success(dataDictionaryService.deleteBatchIds(Arrays.asList(wasIds)), null);
    }*/

    /**
     * 禁用/启用全部币种
     *
     * @return
     */
    @RequestMapping(value = "/disabledAll" )
    public Result disabledAll(@RequestParam("way" ) String way) {

        return ResultUtil.success(dataDictionaryService.disabledAll(way), null);

    }

    /**
     * 获取最新区块高度
     *
     * @param wasType
     * @return
     */
    @RequestMapping(value = "/getNewHeight" )
    public Result getNewHeight(@RequestParam("wasType" ) String wasType) {

        return ResultUtil.success(dataDictionaryService.getNewHeight(wasType), null);
    }

    /**
     * 编辑/保存信息
     */
    @RequestMapping("/saveOrUpdate" )
    public Result save(DataDictionary wasDataDictionary, @RequestParam("saveOrUpdate" ) String saveOrUpdate) {

        if (saveOrUpdate.equals("1" )) {
            return ResultUtil.success(dataDictionaryService.insert(wasDataDictionary), null);
        } else if (saveOrUpdate.equals("2" )) {
            return ResultUtil.success(dataDictionaryService.updateById(wasDataDictionary), null);
        }
        return null;
    }

}
