package com.fq.modules.was.web.interceptor;

import com.fq.modules.was.web.entity.Menu;
import com.fq.modules.was.web.entity.WasSysLog;
import com.fq.modules.was.web.enums.ResultEnum;
import com.fq.modules.was.web.service.LoginService;
import com.fq.modules.was.web.entity.User;
import com.fq.modules.was.web.enums.ResultEnum;
import com.fq.modules.was.web.exception.WasWebException;
import com.fq.modules.was.web.service.LoginService;
import com.fq.modules.was.web.service.MenuService;
import com.fq.modules.was.web.service.WasSysLogService;
import com.fq.modules.was.web.utils.CommonUtil;
import com.fq.modules.was.web.utils.DatesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Descript: 登陆拦截器
 * @Author: liuyingjie
 * @Date: create in 2017/12/28 0028 15:52
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService service;
    @Autowired
    private MenuService menuService;
    @Autowired
    private WasSysLogService wasSysLogService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.info("===========进入拦截器==============");

        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute("user");
        if (CommonUtil.isNotEmpty(user)) {
            String url = httpServletRequest.getRequestURL().toString();
            //校验是否拥有权限
            if(service.checkPermission(user,url)) {
                session.setAttribute("user",user);
                return true;
            } else {
                //检验权限
                String[] str = url.split(":")[2].split("/");//   8081/consume/findConsume
                String checkUrl = "";
                for (int i=0;i<str.length;i++) {
                    if (i != 0) {
                        if (i != str.length -1) {
                            checkUrl += str[i]+"/";
                        } else {
                            checkUrl += str[i];
                        }
                    }
                }
                Menu menu = menuService.findMenuByUrl(checkUrl);
                if (CommonUtil.isNotEmpty(menu)) {
                    //记录日志信息
                    String content = user.getUserName()+"在"+DatesUtils.time()+"尝试进行["+menu.getMenuName()+"]操作,因权限不足失败!";
                    String result = "操作失败,权限不足!";

                    WasSysLog wasSysLog = new WasSysLog(2,user.getUserName(),content,result);
                    wasSysLogService.insert(wasSysLog);
                }
                throw new WasWebException(ResultEnum.NO_PERMISSION);
            }
        } else {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

        log.info(">>>MyInterceptor1>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");

        if(httpServletResponse.getStatus()==500){
//            modelAndView.setViewName("/errorpage/500");
            throw new WasWebException(ResultEnum.UNKNOW_ERROR);
        }else if(httpServletResponse.getStatus()==404){
//            modelAndView.setViewName("/errorpage/404");
            throw new WasWebException(ResultEnum.ERROR_PATH);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
