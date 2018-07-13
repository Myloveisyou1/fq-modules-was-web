package com.fq.modules.was.web.service.setting.impl;

import com.fq.modules.was.web.entity.logs.SysLog;
import com.fq.modules.was.web.entity.setting.Icon;
import com.fq.modules.was.web.entity.setting.Menu;
import com.fq.modules.was.web.entity.setting.MenuList;
import com.fq.modules.was.web.enums.ResultEnum;
import com.fq.modules.was.web.mapper.setting.MenuMapper;
import com.fq.modules.was.web.exception.WasWebException;
import com.fq.modules.was.web.mapper.logs.SysLogMapper;
import com.fq.modules.was.web.service.common.impl.BaseServiceImpl;
import com.fq.modules.was.web.service.setting.MenuService;
import com.fq.modules.was.web.utils.BaseUtils;
import com.fq.modules.was.web.utils.CommonUtil;
import com.fq.modules.was.web.utils.DatesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {

    @Autowired
    private MenuMapper mapper;
    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * 查询所有菜单
     * @return
     */
    public List<Menu> findAllMenu() {

        List<Menu> menuList = mapper.findAllMenu();
        List<Menu> backList = new ArrayList<>();
        if (CommonUtil.isNotEmpty(menuList)) {
            if (menuList.size() > 0) {

                if (menuList != null && menuList.size() > 0) {
                    backList = BaseUtils.getMenuList(menuList);
                }
            }
        }
        return backList;
    }

    /**
     * 查询所有图标
     * @return
     */
    public List<Icon> findIconList() {

        return mapper.findIconList();
    }

    /**
     * 添加图标
     * @param name
     * @param url
     * @return
     */
    public boolean addIcon(String name,String url) {

        Icon icon = new Icon();
        icon.setName(name);
        icon.setUrl(url);
        icon.setStatus(0);
        icon.setVersion(1l);
        icon.setCreateTime(DatesUtils.time());
        icon.setUpdateTime(DatesUtils.time());
        return mapper.addIcon(icon);
    }

    /**
     * 添加菜单
     * @param menu
     * @return
     */
    public boolean addMenu(Menu menu) {

        List<Menu> menus = null;
        String content = getUserName()+"在"+DatesUtils.time();
        //校验是否重复
        if (menu.getParentCode() != 0) {//新增二级菜单
            Menu list = mapper.findMenuByUrl(menu.getUrl());
            if (CommonUtil.isNotEmpty(list)) {
                throw new WasWebException(ResultEnum.SAME_DATA);
            }
            Menu parentMenu = mapper.findById(Long.valueOf(menu.getParentCode()));
            menus = mapper.findAllChildMenu();
            if (CommonUtil.isNotEmpty(menus)) {
                Menu bean = menus.get(0);
                if (CommonUtil.isNotEmpty(menu.getParentCode())) {
                    if (CommonUtil.isNotEmpty(parentMenu)) {
                        content += "新增二级菜单"+menu.getMenuName()+",父级菜单为:"+parentMenu.getMenuName();
                        //添加子菜单
                        List<Menu> menuByParentCode = mapper.findMenuByParentCode(parentMenu.getCode());
                        if (CommonUtil.isNotEmpty(menuByParentCode)) {
                            menu.setCode(menuByParentCode.get(0).getCode()+1);
                        } else {
                            menu.setCode(1);
                        }
                        menu.setParentCode(parentMenu.getCode());
                    } else {
                        throw new WasWebException(ResultEnum.PARENT_NOT_FOUND);
                    }

                } else {
                    menu.setCode(bean.getCode()+1);
                }
            } else {
                menu.setCode(1);
            }
        } else if (menu.getParentCode() == 0){//新增一级菜单
            //查询所有的一级菜单
            menus = mapper.findMenuByParentCode(menu.getParentCode());
            content += "新增一级菜单,菜单为:"+menu.getMenuName();
            if (CommonUtil.isNotEmpty(menus)) {
                Menu bean = menus.get(0);
                menu.setCode(bean.getCode()+1);
            } else {
                menu.setCode(1);
            }
        }
        menu.setStatus(0);
        menu.setVersion(1l);
        menu.setCreateTime(DatesUtils.time());
        menu.setUpdateTime(DatesUtils.time());

        //记录系统日志
        String result = "新增成功";
        sysLogMapper.addSysLog(new SysLog(1,getUserName(),content,result));
        return mapper.addMenu(menu);
    }

    /**
     * 删除菜单
     * @param gid
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean delMenu(Long gid) {

        Menu menu = mapper.findById(gid);
        String content = getUserName()+"在"+DatesUtils.time()+"删除了菜单:"+menu.getMenuName();
        //查询子类菜单
        List<Menu> cmenu = mapper.findMenuByParentCode(menu.getCode());
        if (CommonUtil.isNotEmpty(cmenu) && cmenu.size() > 0) {
            //删除子类的权限
            content += ",删除的子菜单如下:";
            for (int i = 0; i < cmenu.size(); i++) {
                content += cmenu.get(i).getMenuName();
                mapper.deleteMenuRoleByMenuId(cmenu.get(i).getGid());
            }
        }

        //1.删除角色菜单关联表========删除本身
        mapper.deleteMenuRoleByMenuId(gid);

        //2.删除菜单

        //记录系统日志
        String result = "删除成功";
        sysLogMapper.addSysLog(new SysLog(4,getUserName(),content,result));

        return mapper.deleteMenuByMenuId(gid,menu.getCode());
    }

    /**
     * 根据id查询菜单
     * @param gid
     * @return
     */
    public Menu findById(Long gid) {

        return mapper.findById(gid);
    }

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    public boolean editMenu(Menu menu) {

        Menu saveBean = findById(menu.getGid());
        if (CommonUtil.isNotEmpty(saveBean)) {
            String content = getUserName()+"在"+DatesUtils.time()+"修改菜单"+saveBean.getMenuName()+"的信息";
            if (CommonUtil.isNotEmpty(menu.getMenuName())) {
                content += "menuName="+menu.getMenuName();
                saveBean.setMenuName(menu.getMenuName());
            }
            if (CommonUtil.isNotEmpty(menu.getUrl())) {
                content += "url="+menu.getUrl();
                saveBean.setUrl(menu.getUrl());
            }
            if (CommonUtil.isNotEmpty(menu.getIcon())) {
                content += "icon="+menu.getIcon();
                saveBean.setIcon(menu.getIcon());
            }
            if (CommonUtil.isNotEmpty(menu.getBelong())) {
                content += "belong="+menu.getBelong();
                saveBean.setBelong(menu.getBelong());
            }

            saveBean.setUpdateTime(DatesUtils.time());
            saveBean.setVersion(saveBean.getVersion()+1);

            //记录系统日志
            String result = "修改成功";
            sysLogMapper.addSysLog(new SysLog(2,getUserName(),content,result));
        }

        return mapper.updateMenu(saveBean);
    }

    /**
     * 查询子菜单
     * @param parentCode
     * @return
     */
    public List<Menu> findChildMenu(String parentCode) {

        int code = 0;
        if (CommonUtil.isNotEmpty(parentCode)) {
            Menu menu = mapper.findById(Long.valueOf(parentCode));
            if (CommonUtil.isNotEmpty(menu)) {
                code = menu.getCode();
            }
        }
        List<Menu> list = mapper.findMenuByParentCode(code);
        return list;
    }

    /**
     * 查询角色的权限
     * @param roleId
     * @return
     */
    public List<MenuList> findMenuByRole(Long roleId) {

        List<Menu> menuList = mapper.findMenuByRole(roleId);
        //最终返回的权限
        List<MenuList> backList = new ArrayList<>();
        if (menuList != null && menuList.size() > 0) {
            backList = BaseUtils.getMenuListForLogin(menuList);
        }
        return backList;
    }

    /**
     * 根据url请求地址查询请求名称
     * @param url
     * @return
     */
    @Override
    public Menu findMenuByUrl(String url) {

        if (CommonUtil.isNotEmpty(url)) {
            return mapper.findMenuByUrl(url);
        } else {
            return null;
        }
    }
}
