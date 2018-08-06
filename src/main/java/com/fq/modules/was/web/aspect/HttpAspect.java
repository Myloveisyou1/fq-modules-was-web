package com.fq.modules.was.web.aspect;

import com.fq.modules.was.web.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.smartcardio.CommandAPDU;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

@Aspect
@Component
@Slf4j
public class HttpAspect {

    private long start = 0l;

    private Long gid;

//    @Autowired
//    private LogService logService;

    @Pointcut("execution(public * com.fq.modules.was.web.controller.*.*.*(..))" )
    public void log() {

    }

    @Before("log()" )
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("===========开始请求" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "============" );
        Date date = new Date();
        start = date.getTime();
        String stime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" ).format(date);
        log.info("===========请求时间:" + stime + "==========================" );
        //获取url
        String url = request.getRequestURL().toString();
        log.info("url={}", url);

        //获取method
        String method = request.getMethod().toString();
        log.info("method={}", method);

        //获取ip
        String ip = request.getRemoteAddr().toString();
        log.info("ip={}", ip);

        //获取类方法
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        log.info("class_method={}", classMethod);

        //获取类参数
        //log.info("args={}",joinPoint.getArgs());
        Enumeration em = request.getParameterNames();
        String names = "";
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getParameter(name);
            names += name + "=" + value + ",";
        }
        log.info("args:{}", names);
//        Logs logs = new Logs();
//        logs.setUrl(url);
//        logs.setMethod(method);
//        logs.setIp(ip);
//        logs.setClassMethod(classMethod);
//        logs.setArgs(names);
//        logs.setStartTime(stime);
//        //保存请求相关的信息
//        logService.optSaveLogs(logs);
//        gid = logs.getGid();

    }

    @After("log()" )
    public void doAfter() {
        Date end = new Date();
        String etime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" ).format(end);
        long time = (end.getTime() - start) / 1000;
//        Logs logs = new Logs();
//        logs.setGid(gid);
//        logs.setEndTime(etime);
//        logs.setTime(time);
//        logService.optUpdateLogs(logs);
        log.info("=============结束请求时间:" + etime + ",耗时:" + time + "s======================" );
    }

    /**
     * 获取返回的结果集
     *
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "log()" )
    public void doAfterReturn(Object object) {

        String back = "";
        if (CommonUtil.isNotEmpty(object)) {
            back = object.toString();
        }

//        Result result = (Result) object;
//
//        Logs logs = new Logs();
//        logs.setGid(gid);
//        logs.setRepData(JSONObject.toJSONString(result.getCode()+result.getMsg()));
//        logService.optUpdateLogs(logs);
        log.info("response={}", back);
    }

}
