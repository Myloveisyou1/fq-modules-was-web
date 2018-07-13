package com.fq.modules.was.web.mapper.setting;

import com.fq.modules.was.web.utils.SimpleInsertLangDriver;
import com.fq.modules.was.web.utils.SimpleUpdateLangDriver;
import com.fq.modules.was.web.entity.setting.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Descript:
 * @Author: liuyingjie
 * @Date: create in 2018/5/29 0029 17:37
 */
@Component
@Mapper
public interface RoleMapper {

    @Select("select gid,role_name roleName,role_name_en roleNameEn,version,create_time createTime,update_time updateTime,status from was_sys_role where 1=1")
    List<Role> findAllRole();

    @Select("select gid,role_name roleName,role_name_en roleNameEn,version,create_time createTime,update_time updateTime,status from was_sys_role where gid = #{gid}")
    Role findById(@Param("gid" ) Long roleId);

    @Select("select gid,role_name roleName,role_name_en roleNameEn,version,create_time createTime,update_time updateTime,status from was_sys_role where 1=1 and role_name like #{roleName} or role_name_en like #{roleName}")
    List<Role> findRoleByName(String roleName);

    @Update("update was_sys_role (#{role}) where gid = #{gid}")
    @Lang(SimpleUpdateLangDriver.class)
    boolean updateRole(Role role);


    @Insert("insert into was_sys_role (#{role})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true,keyProperty = "gid",keyColumn = "gid")
    void addRole(Role role);

    @Delete("delete from was_sys_role where gid = #{gid}")
    void deleteByGid(@Param(value = "gid" ) Long gid);
}
