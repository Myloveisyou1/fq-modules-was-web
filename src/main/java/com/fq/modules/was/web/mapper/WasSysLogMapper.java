package com.fq.modules.was.web.mapper;

import com.fq.modules.was.web.entity.WasSysLog;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.provider.BaseProvider;
import com.fq.modules.was.web.utils.SimpleInsertLangDriver;
import com.fq.modules.was.web.utils.SimpleUpdateLangDriver;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 系统操作日志表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-10 10:39:15
 */
@Component
@Mapper
public interface WasSysLogMapper {

    @Select("select "
            + "was_id wasId,"
            + "was_log_type wasLogType,"
            + "was_log_operator wasLogOperator,"
            + "was_log_content wasLogContent,"
            + "was_log_result wasLogResult,"
            + "was_log_time wasLogTime,"
            + " from was_sys_log where was_id=#{wasId}")
        WasSysLog selectById(Long wasId);

    @Insert("insert into was_sys_log (#{wasSysLog})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "wasId", keyColumn = "was_id")
    boolean insert(WasSysLog wasSysLog);

    @Update("update was_sys_log (#{wasSysLog}) where was_id = #{wasId}")
    @Lang(SimpleUpdateLangDriver.class)
    boolean updateById(WasSysLog wasSysLog);

    @SelectProvider(type = BaseProvider.class, method = "pageQueryWasSysLogList")
    List<WasSysLog> pageQuery(Map<String,Object> map);

    @SelectProvider(type = BaseProvider.class, method = "pageQueryWasSysLogCount")
    Long pageQueryCount(Map<String,Object> map);
}
