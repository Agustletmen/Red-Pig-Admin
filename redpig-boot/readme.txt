后端技术栈:
    JDK17【LTS:长期支持版本】
    SpringBoot3.x
    MyBatis-Plus3.5.3
    MySql8.x
    SpringSecurity6/Shiro/Sa-Token/不用权限框架
    knife4j:封装的swagger文档
    Jwt
    代码生成器:velocity/freemarker
    activiti7:工作流引擎
        web设计器:activiti6的设计器是支持7的
        以后工作流可能都不会提供自己的设计器 bpmnjs
    quartz:定时器

前端技术栈:
vue3
pinia
typescript
前端模板:vue-pure-admin https://gitee.com/yiming_chang/vue-pure-admin
    vue3+element-plus

系统环境
JDK17
IDEA2021/eclipse
vscode

启动
服务端:启动APP
客户端

登录、注册功能的开发
登录接口是使用SpringSecurity+过滤器
自己封装过滤器可以随意控制接口名称、请求类型

跨域:什么叫跨域 不同的域名
    需要设置跨域的地方
    SpringBoot
    SpringSecurity
    Vue中的vite

前后端分离 目前发展到现在 角色、权限 几乎已经大家都是公认的【设计方式】

面试中 关于权限:控制到按钮级别、数据级别【shiro、SpringSecurity 有个注解 过滤的】


SpringBoot3不用spi
