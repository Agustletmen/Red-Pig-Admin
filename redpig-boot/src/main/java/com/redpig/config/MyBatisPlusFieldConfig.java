package com.redpig.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.redpig.util.RedPigTools;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyBatisPlusFieldConfig implements MetaObjectHandler {

    /**
     * 使用mp做添加操作时候，这个方法执行
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //设置属性值
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
        this.setFieldValByName("deleteStatus",1,metaObject);
        if(RedPigTools.getUsername()!=null){
            this.setFieldValByName("createBy", RedPigTools.getUsername(),metaObject);
            this.setFieldValByName("updateBy", RedPigTools.getUsername(),metaObject);
        }
    }

    /**
     * 使用mp做修改操作时候，这个方法执行
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",new Date(),metaObject);
        this.setFieldValByName("updateBy", RedPigTools.getUsername(),metaObject);
    }
}
