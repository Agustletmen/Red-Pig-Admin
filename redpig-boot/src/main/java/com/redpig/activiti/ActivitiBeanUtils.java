package com.redpig.activiti;

import org.activiti.engine.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ActivitiBeanUtils {
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    public void init(){
        ProcessEngineConfiguration config = ProcessEngineConfiguration
                .createStandaloneInMemProcessEngineConfiguration();

        // 连接数据库的配置
        config.setJdbcDriver(driver);
        config.setJdbcUrl(url);
        config.setJdbcUsername(username);
        config.setJdbcPassword(password);

        config.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //工作流的核心对象，ProcessEnginee对象
        ProcessEngine processEngine = config.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        HistoryService historyService = processEngine.getHistoryService();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        ApplicationContextUtil.registerSingletonBean("processEngine",processEngine);
        ApplicationContextUtil.registerSingletonBean("taskService",taskService);
        ApplicationContextUtil.registerSingletonBean("historyService",historyService);
        ApplicationContextUtil.registerSingletonBean("repositoryService",repositoryService);
        ApplicationContextUtil.registerSingletonBean("runtimeService",runtimeService);
    }
}
