package com.fq.modules.was.web.mapper.setting;

import com.fq.modules.was.web.entity.setting.Project;
import com.fq.modules.was.web.utils.SimpleInsertLangDriver;
import com.fq.modules.was.web.utils.SimpleUpdateLangDriver;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Descript:
 * @Author: liuyingjie
 * @Date: create in 2018/6/1 0001 10:37
 */
@Component
@Mapper
public interface ProjectMapper {


    @Select("select gid,project_name projectName,name_en nameEn,version,create_time createTime,update_time updateTime,status from was_sys_project where 1=1 AND project_name like #{projectName} or name_en like #{projectName}" )
    List<Project> findProjectByName(@Param(value = "projectName" ) String projectName);

    @Select("select gid,project_name projectName,name_en nameEn,version,create_time createTime,update_time updateTime,status from was_sys_project where 1=1" )
    List<Project> findAllProject();

    @Select("select gid,project_name projectName,name_en nameEn,version,create_time createTime,update_time updateTime,status from was_sys_project where gid = #{gid}" )
    Project findById(@Param(value = "gid" ) Long gid);

    @Update("update was_sys_project (#{bean}) where gid = #{gid}" )
    @Lang(SimpleUpdateLangDriver.class)
    boolean updateProject(Project bean);

    @Delete("delete from was_sys_project where gid = #{gid}" )
    void deleteProjectById(@Param(value = "gid" ) Long gid);

    @Insert("insert into was_sys_project (#{project})" )
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "gid", keyColumn = "gid" )
    int addProject(Project project);
}
