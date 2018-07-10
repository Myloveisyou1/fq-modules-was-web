package com.fq.modules.was.web.service.impl;

import com.fq.modules.was.web.entity.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseServiceImpl {

    public String getUserName() {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        return user.getUserName();
    }
}
