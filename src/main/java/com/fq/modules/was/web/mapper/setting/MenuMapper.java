package com.fq.modules.was.web.mapper.setting;

import com.fq.modules.was.web.entity.setting.Icon;
import com.fq.modules.was.web.entity.setting.Menu;
import com.fq.modules.was.web.entity.setting.MenuRole;
import com.fq.modules.was.web.utils.SimpleInsertLangDriver;
import com.fq.modules.was.web.utils.SimpleUpdateLangDriver;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Descript:
 * @Author: liuyingjie
 * @Date: create in 2018/5/25 0025 15:37
 */
@Component
@Mapper
public interface MenuMapper {

    /**
     * 查询角色的菜单/权限
     *
     * @param roleId
     * @return
     */
    @Select("select m.gid,m.menu_type menuType,m.icon,m.menu_name menuName,m.url,m.parent_code parentCode,m.`code`,m.belong,m.version,m.create_time createTime,m.update_time updateTime,m.`status`,m.sort sort,p.project_name projectName " +
            "from was_sys_menu m inner join was_sys_menu_role r on m.gid=r.menu_id inner JOIN was_sys_project p on m.belong=p.gid and m.status=0 and r.role_id = #{roleId} order by m.sort" )
    List<Menu> findMenuByRole(Long roleId);

    /**
     * 查询所有的菜单/权限
     *
     * @return
     */
    @Select("select m.gid,m.menu_type menuType,m.icon,m.menu_name menuName,m.url,m.parent_code parentCode,m.`code`,m.belong,m.version,m.create_time createTime,m.update_time updateTime,m.`status`,m.sort sort,p.project_name projectName " +
            "from was_sys_menu m left join was_sys_project p on m.belong=p.gid order by m.sort asc" )
    List<Menu> findAllMenu();

    /**
     * 增加菜单/权限
     *
     * @param menuRole
     */
    @Insert("insert into was_sys_menu_role (#{menuRole})" )
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "gid", keyColumn = "gid" )
    void addMenuRole(MenuRole menuRole);

    @Select("select gid,role_id roleId,menu_id menuId,version,create_time createTime,update_time updateTime,status from was_sys_menu_role where role_id=#{roleId} and menu_id=#{menuId}" )
    MenuRole findInfoByRoleAndMenu(@Param(value = "roleId" ) Long roleId, @Param(value = "menuId" ) Long menuId);

    @Delete("delete from was_sys_menu_role where gid = #{gid}" )
    boolean deleteMenuRole(MenuRole menuRole);

    @Update("update was_sys_menu set status = #{status} where belong = #{belong}" )
    boolean updateMenuByProject(Menu menu);

    @Delete("delete from was_sys_menu_role where role_id = #{roleId}" )
    boolean deleteMenuRoleByRoleId(@Param(value = "roleId" ) Long roleId);

    /**
     * 根据项目删除菜单
     *
     * @param gid
     * @return
     */
    @Delete("delete from was_sys_menu where belong = #{gid}" )
    boolean deleteMenuByProject(@Param(value = "gid" ) Long gid);

    /**
     * 根据菜单删除权限关联表
     *
     * @param gid
     * @return
     */
    @Delete("delete from was_sys_menu_role where menu_id = #{gid}" )
    boolean deleteMenuRoleByMenuId(@Param(value = "gid" ) Long gid);

    /**
     * 根据菜单删除父类和子类
     *
     * @param gid
     * @return
     */
    @Delete("delete from was_sys_menu where gid = #{gid} or parent_code = #{code}" )
    boolean deleteMenuByMenuId(@Param(value = "gid" ) Long gid, @Param(value = "code" ) Integer code);

    @Select("select gid,name,url,version,create_time createTime,update_time updateTime,status from was_sys_icon" )
    List<Icon> findIconList();


    @Insert("insert into was_sys_icon (#{icon})" )
    @Lang(SimpleInsertLangDriver.class)
    boolean addIcon(Icon icon);

    @Insert("insert into was_sys_menu (#{menu})" )
    @Lang(SimpleInsertLangDriver.class)
    boolean addMenu(Menu menu);

    @Select("select gid,menu_type menuType,icon,menu_name menuName,url,parent_code parentCode,code,sort,version,belong from was_sys_menu where gid = #{gid}" )
    Menu findById(Long gid);

    @Select("select m.gid,m.menu_type menuType,m.icon,m.menu_name menuName,m.url,m.parent_code parentCode,m.`code`,m.belong,m.version,m.create_time createTime,m.update_time updateTime,m.`status`,m.sort sort,p.project_name projectName " +
            "from was_sys_menu m inner join was_sys_project p on m.belong=p.gid and m.parent_code = #{code} order by m.menu_type,m.sort asc" )
    List<Menu> findMenuByParentCode(Integer code);

    @Update("update was_sys_menu (#{saveBean}) where gid = #{gid}" )
    @Lang(SimpleUpdateLangDriver.class)
    boolean updateMenu(Menu saveBean);

    /**
     * 根据url查询菜单
     *
     * @param checkUrl
     * @return
     */
    @Select("select gid,menu_type menuType,icon,menu_name menuName,url,parent_code parentCode,code,version,belong from was_sys_menu where url=#{url}" )
    Menu findMenuByUrl(@Param(value = "url" ) String checkUrl);

    @Select("select m.gid,m.menu_type menuType,m.icon,m.menu_name menuName,m.url,m.parent_code parentCode,m.`code`,m.belong,m.version,m.create_time createTime,m.update_time updateTime,m.`status`,p.project_name projectName " +
            "from was_sys_menu m inner join was_sys_project p on m.belong=p.gid and m.parent_code != 0 order by gid desc" )
    List<Menu> findAllChildMenu();
}
