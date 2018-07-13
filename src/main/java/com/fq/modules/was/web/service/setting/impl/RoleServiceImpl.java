package com.fq.modules.was.web.service.setting.impl;

import com.fq.modules.was.web.entity.logs.SysLog;
import com.fq.modules.was.web.entity.setting.Menu;
import com.fq.modules.was.web.entity.setting.MenuRole;
import com.fq.modules.was.web.entity.setting.Role;
import com.fq.modules.was.web.enums.ResultEnum;
import com.fq.modules.was.web.exception.WasWebException;
import com.fq.modules.was.web.mapper.setting.MenuMapper;
import com.fq.modules.was.web.mapper.logs.SysLogMapper;
import com.fq.modules.was.web.mapper.setting.RoleMapper;
import com.fq.modules.was.web.service.common.impl.BaseServiceImpl;
import com.fq.modules.was.web.service.setting.RoleService;
import com.fq.modules.was.web.utils.CommonUtil;
import com.fq.modules.was.web.utils.DatesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

    @Autowired
    private RoleMapper mapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * 查询所有角色
     * @return
     */
    public List<Role> findAllRole(String roleName) {

        if (CommonUtil.isNotEmpty(roleName)) {
            roleName = "%"+roleName+"%";
            return mapper.findRoleByName(roleName);
        } else {
            return mapper.findAllRole();
        }
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    public boolean updateRole(Role role) {

        Role bean = new Role();
        if (CommonUtil.isNotEmpty(role)) {
            bean = mapper.findById(role.getGid());
            String content = getUserName()+"在"+DatesUtils.time()+"修改角色"+bean.getRoleName()+"的信息,如下:";
            if (CommonUtil.isNotEmpty(bean)) {
                if (CommonUtil.isNotEmpty(role.getRoleName())) {
                    content += "roleName="+role.getRoleName();
                    bean.setRoleName(role.getRoleName());
                }
                if (CommonUtil.isNotEmpty(role.getRoleNameEn())) {
                    content += "roleEnName="+role.getRoleNameEn();
                    bean.setRoleNameEn(role.getRoleNameEn());
                }
                if (CommonUtil.isNotEmpty(role.getStatus())) {
                    if (role.getStatus() == 0) {
                        content += "启用角色";
                    } else {
                        content += "禁用角色";
                    }
                    bean.setStatus(role.getStatus());
                }
                bean.setUpdateTime(DatesUtils.time());
                bean.setVersion(bean.getVersion()+1);

                //记录系统日志
                String result = "修改成功";
                sysLogMapper.addSysLog(new SysLog(2,getUserName(),content,result));
            }
        }

        return mapper.updateRole(bean);

    }

    /**
     * 添加角色
     * @param role
     * @param menus
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean addRole(Role role, String menus) {

        List<Role> r = mapper.findRoleByName(role.getRoleName());
        String content = getUserName()+"在"+DatesUtils.time()+"新增角色"+role.getRoleName();
        if (CommonUtil.isNotEmpty(r)) {
            throw new WasWebException(ResultEnum.SAME_ROLE);
        }
        String[] menu = {};
        if (CommonUtil.isNotEmpty(menus)) {
            content += "权限信息如下:"+menus;
            menu = menus.split(",");
        }
        //保存角色
        role.setVersion(1l);
        role.setStatus(0);
        role.setCreateTime(DatesUtils.time());
        role.setUpdateTime(DatesUtils.time());

        mapper.addRole(role);

        for (int i=0;i<menu.length;i++) {
            MenuRole menuRole = new MenuRole();
            menuRole.setRoleId(role.getGid());
            if (CommonUtil.isNotEmpty(menu[i])) {
                menuRole.setMenuId(Long.parseLong(menu[i]));
            }
            menuRole.setVersion(1l);
            menuRole.setStatus(0);
            menuRole.setCreateTime(DatesUtils.time());
            menuRole.setUpdateTime(DatesUtils.time());
            menuMapper.addMenuRole(menuRole);
        }

        //记录系统日志
        String result = "新增成功";
        sysLogMapper.addSysLog(new SysLog(1,getUserName(),content,result));

        return true;
    }

    /**
     * 修改角色权限
     * @param role
     * @param ymenus
     * @param nmenus
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean updateRomeMenu(Role role, String ymenus, String nmenus) {

        List<Role> r = mapper.findRoleByName(role.getRoleName());
        if (CommonUtil.isNotEmpty(r)) {
            Role bean = r.get(0);
            //修改角色
            bean.setRoleName(role.getRoleName());
            bean.setRoleNameEn(role.getRoleNameEn());
            bean.setVersion(bean.getVersion()+1);

            mapper.updateRole(bean);

            //修改权限
            List<Menu> list = menuMapper.findMenuByRole(bean.getGid());
            String[] ymenu = ymenus.split(",");
            String[] nmenu = nmenus.split(",");
            if (CommonUtil.isNotEmpty(nmenu)) {
                //校验要保存的权限
                boolean flag = false;
                for(int j=0;j<ymenu.length;j++) {//1,3,4
                    //如果添加的权限原来不存在,则添加该权限3,4
                    if (CommonUtil.isNotEmpty(list)) {
                        for (int i=0;i<list.size();i++) {
                            if(CommonUtil.isNotEmpty(ymenu[j])) {
                                if (!list.get(i).getGid().equals(Long.parseLong(ymenu[j]))) {
                                    flag = true;
                                } else {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    } else {
                        flag = true;
                    }
                    if (flag) {
                        MenuRole menuRole = new MenuRole();
                        menuRole.setRoleId(bean.getGid());
                        menuRole.setMenuId(Long.parseLong(ymenu[j]));
                        menuRole.setVersion(1l);
                        menuRole.setStatus(0);
                        menuRole.setCreateTime(DatesUtils.time());
                        menuRole.setUpdateTime(DatesUtils.time());
                        menuMapper.addMenuRole(menuRole);
                    }
                }

                //校验要删除的权限1,2,3
                for(int m=0;m<nmenu.length;m++) {
                    //如果原来存在该权限,则删除2,3,4
                    if(CommonUtil.isNotEmpty(nmenu[m])) {
                        for (int i=0;i<list.size();i++) {
                            if (list.get(i).getGid().equals(Long.parseLong(nmenu[m]))) {
                                //根据权限和角色查询menu_role的信息

                                MenuRole menuRole = menuMapper.findInfoByRoleAndMenu(bean.getGid(),list.get(i).getGid());

                                menuMapper.deleteMenuRole(menuRole);
                            }
                        }
                    }

                }
            }

            String content = getUserName()+"在"+DatesUtils.time()+"修改角色和权限信息,修改后权限为:【"+ymenus+"】";
            //记录系统日志
            String result = "修改成功";
            sysLogMapper.addSysLog(new SysLog(2,getUserName(),content,result));
        }

        return true;
    }

    /**
     * 查询角色权限
     * @param roleId
     * @return
     */
    public List<Menu> findMenuByRole(Long roleId) {
        return menuMapper.findMenuByRole(roleId);
    }

    /**
     * 删除角色
     * @param gid
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean deleteRole(Long gid) {

        //删除权限
        boolean flag = menuMapper.deleteMenuRoleByRoleId(gid);
        //删除角色
        mapper.deleteByGid(gid);

        //记录日志
        String result = "";
        if (flag) {
            result = "删除成功";
        } else {
            result = "删除失败";
        }
        sysLogMapper.addSysLog(new SysLog(1,getUserName(),getUserName()+"在"+DatesUtils.time()+"修改角色信息,id="+gid,result));

        return flag;
    }
}
