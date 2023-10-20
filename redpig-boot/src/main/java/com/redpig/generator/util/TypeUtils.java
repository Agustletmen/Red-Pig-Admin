package com.redpig.generator.util;

import java.util.HashMap;
import java.util.Map;

public class TypeUtils {

    public static Map<String,String> mysqlToJavaTypes = new HashMap<>();

    public static Map<String,String> mysqlToJavaScriptTypes = new HashMap<>();

    static {
        /** 数值类型 **/
        mysqlToJavaTypes.put("TINYINT","Integer");
        mysqlToJavaTypes.put("SMALLINT","Integer");
        mysqlToJavaTypes.put("MEDIUMINT","Integer");
        mysqlToJavaTypes.put("INT","Integer");
        mysqlToJavaTypes.put("INTEGER","Integer");
        mysqlToJavaTypes.put("BIGINT","Long");
        mysqlToJavaTypes.put("FLOAT","Float");
        mysqlToJavaTypes.put("DOUBLE","Double");
        mysqlToJavaTypes.put("DECIMAL","java.math.BigDecimal");

        /** 日期和时间类型 **/
        mysqlToJavaTypes.put("DATE","java.util.Date");
        mysqlToJavaTypes.put("TIME","java.util.Date");
        mysqlToJavaTypes.put("YEAR","java.util.Date");
        mysqlToJavaTypes.put("DATETIME","java.util.Date");
        mysqlToJavaTypes.put("TIMESTAMP","java.util.Date");

        /** 字符串类型 **/
        mysqlToJavaTypes.put("CHAR","String");
        mysqlToJavaTypes.put("VARCHAR","String");
        /** mysqlToJavaTypes.put("TINYBLOB","byte[]"); **/
        /** mysqlToJavaTypes.put("MEDIUMBLOB","byte[]"); **/
        /** mysqlToJavaTypes.put("LONGBLOB","byte[]"); **/
        /** mysqlToJavaTypes.put("BLOB","byte[]"); **/
        /** mysqlToJavaTypes.put("TINYTEXT","String"); **/

        mysqlToJavaTypes.put("TEXT","String");
        mysqlToJavaTypes.put("MEDIUMTEXT","String");
        mysqlToJavaTypes.put("LONGTEXT","String");

        mysqlToJavaTypes.put("BIT","Boolean");

        mysqlToJavaTypes.put("ID","Long");

    }

    static {
        /** 数值类型 **/
        mysqlToJavaScriptTypes.put("TINYINT","number");
        mysqlToJavaScriptTypes.put("SMALLINT","number");
        mysqlToJavaScriptTypes.put("MEDIUMINT","number");
        mysqlToJavaScriptTypes.put("INT","number");
        mysqlToJavaScriptTypes.put("INTEGER","number");
        mysqlToJavaScriptTypes.put("BIGINT","number");
        mysqlToJavaScriptTypes.put("FLOAT","number");
        mysqlToJavaScriptTypes.put("DOUBLE","number");
        mysqlToJavaScriptTypes.put("DECIMAL","string");

        /** 日期和时间类型 **/
        mysqlToJavaScriptTypes.put("DATE","string");
        mysqlToJavaScriptTypes.put("TIME","string");
        mysqlToJavaScriptTypes.put("YEAR","string");
        mysqlToJavaScriptTypes.put("DATETIME","string");
        mysqlToJavaScriptTypes.put("TIMESTAMP","string");

        /** 字符串类型 **/
        mysqlToJavaScriptTypes.put("CHAR","string");
        mysqlToJavaScriptTypes.put("VARCHAR","string");

        mysqlToJavaScriptTypes.put("TEXT","string");
        mysqlToJavaScriptTypes.put("MEDIUMTEXT","string");
        mysqlToJavaScriptTypes.put("LONGTEXT","string");

        mysqlToJavaScriptTypes.put("BIT","boolean");

        mysqlToJavaScriptTypes.put("ID","number");

    }

    public static String getJavaTypeByMysqlType(String mysqlType){
        return mysqlToJavaTypes.get(mysqlType);
    }

    public static String getJavaScriptTypeByMysqlType(String mysqlType){
        return mysqlToJavaScriptTypes.get(mysqlType);
    }

    /**
     * mysql中有些类型 比如INT 在mybatis中要写成INTEGER
     * @return
     */
    public static String getMySqlType(String mysqlType){
        if(mysqlType.equalsIgnoreCase("int")){
            return "INTEGER";
        }
        if(mysqlType.equalsIgnoreCase("DATETIME")){
            return "TIMESTAMP";
        }
        return mysqlType;
    }

    public static void main(String[] args) {
        mysqlToJavaTypes.values().stream().forEach(e->{
            System.out.println("\""+e+"\""+",");
        });

    }

}
