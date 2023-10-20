package com.redpig.generator.vo;

import lombok.Data;

@Data
public class ClassVO {
    /** 包名 **/
    private String basePackageName;

    /** 文件夹 **/
    private String packageFolder;

    /** model包名 **/
    private String modelPackageName;

    /** 表名 **/
    private String tableName;

    /** 类名 **/
    private String className;

    /** className的首字母小写 **/
    private String entityName;

    /** 日期 **/
    private String date;

    /** 作者 **/
    private String author;

    /** 表注释 **/
    private String comment;

    /** 软件版本 **/
    private String version;

    /** Copyright **/
    private String copyright;

    /** 公司网址 **/
    private String webSite;

}
