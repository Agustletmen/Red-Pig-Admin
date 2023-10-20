package com.redpig.generator.vo;

import lombok.Data;

@Data
public class FieldVO {

    /** 列名 **/
    private String columnName;

    /** 字段名 **/
    private String fieldName;

    /** java字段类型 **/
    private String fieldType;

    /** 数据库字段类型 **/
    private String columnType;

    /** JS的类型:boolean、null、undefined、number、string、Symbol **/
    private String jsType;

    /** 字段注释 **/
    private String comments;

    /** get方法名字 */
    private String getMethodName;

    /** set方法名字 */
    private String setMethodName;

}
