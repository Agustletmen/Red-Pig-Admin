package com.redpig.aspect;

import com.alibaba.fastjson.JSON;
import com.redpig.entity.ExceptionLog;
import com.redpig.entity.OperationLog;
import com.redpig.entity.User;
import com.redpig.service.IExceptionLogService;
import com.redpig.service.IOperationLogService;
import com.redpig.util.RedPigTools;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zqd
 *
 * @since 2023年7月24日16:37:46
 */
@Slf4j
@Aspect
@Component
public class OperLogAspect {

    @Autowired
    IExceptionLogService exceptionLogService;

    @Autowired
    IOperationLogService operationLogService;

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(io.swagger.v3.oas.annotations.Operation)")
    public void operLogPoinCut() {
        log.info("operLogPoinCut()。。。。");
    }

    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     * * 任意返回值类型 com... 扫描的包路径 .. 当前包及子包 * 所有类名 .*（..） 任意方法 任意参数类型
     */
    @Pointcut("execution(* com.redpig.controller..*.*(..))")
    public void operExceptionLogPoinCut() {
        log.info("operExceptionLogPoinCut。。。");
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "keys")
    public void saveOperLog(JoinPoint joinPoint, Object keys) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();


        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        }

        OperationLog operlog = new OperationLog();
        // 主键ID

        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();

        // 获取操作
        Operation opLog = method.getAnnotation(Operation.class);
        if (opLog != null) {
            String operModul = opLog.description();

            String operDesc = opLog.description();
            // 操作模块
            operlog.setOperModul(operModul);

            // 操作类型

            // 操作描述
            operlog.setOperDesc(operDesc);
        }

        // 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();

        // 获取请求的方法名
        String methodName = method.getName();

        methodName = className + "." + methodName;

        // 请求方法
        operlog.setOperMethod(methodName);

        // 请求的参数
        Map<String, String> rtnMap = null;
        if (request != null) {
            rtnMap = converMap(request.getParameterMap());
        }

        // 将参数所在的数组转换成json
        String params = JSON.toJSONString(rtnMap);
        // 请求参数
        operlog.setOperRequParam(params);
        // 返回结果
        String result = JSON.toJSONString(keys);
        operlog.setOperRespParam(result.length()>10000?result.substring(0,10000):result);

        // 请求用户ID
        User user = RedPigTools.getUser();
        if(user!=null){
            operlog.setOperUserId(user.getId().toString());
            // 请求用户名称
            operlog.setOperUserName(user.getUsername());
        }

        // 请求IP
        if (request != null) {
            //operlog.setOperIp(ServletUtil.getClientIP(request));
        }
        // 请求URI
        if (request != null) {
            operlog.setOperUri(request.getRequestURI());
        }
        // 操作版本

        //这里测试就只打印下日志类容 实际应该是使用service或者mapper进行入库 或者 使用队列推送给专门处理日志的服务
        log.info(JSON.toJSONString(operlog));
        operationLogService.save(operlog);
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "operExceptionLogPoinCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            request = (HttpServletRequest) requestAttributes
                    .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        }

        ExceptionLog excepLog = new ExceptionLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();

            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求的参数
            Map<String, String> rtnMap = null;
            if (request != null) {
                rtnMap = converMap(request.getParameterMap());
            }
            // 将参数所在的数组转换成json
            String params = JSON.toJSONString(rtnMap);
            // 请求参数
            excepLog.setExcRequParam(params);
            // 请求方法名
            excepLog.setOperMethod(methodName);
            // 异常名称
            excepLog.setExcName(e.getClass().getName());
            // 异常信息
            excepLog.setExcMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
            // 操作员ID
            excepLog.setOperUserId("test");
            // 操作员名称
            excepLog.setOperUserName("测试用户");
            if (request != null) {
                // 操作URI
                excepLog.setOperUri(request.getRequestURI());
            }
            // 操作员IP
            if (request != null) {
                //excepLog.setOperIp(ServletUtil.getClientIP(request));
            }
            // 操作版本号

            // 发生异常时间
            excepLog.setOperCreateTime(new Date());

            log.info(JSON.toJSONString(excepLog));
            exceptionLogService.save(excepLog);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        //默认HashMap的初始化容量为16 这里不是size size指的是元素个数 存放的元素个数是可以自动扩充的
        Map<String, String> rtnMap = new HashMap<>(16);
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        //StringBuffer 线程安全 StringBuilder 速度快
        StringBuilder strbuff = new StringBuilder();
        for (StackTraceElement stet : elements) {
            //为什么不连在一起append 因为 连在一起会多出一个字符串 多出内存开销
            strbuff.append(stet);
            strbuff.append("\n");
        }
        return exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
    }
}
