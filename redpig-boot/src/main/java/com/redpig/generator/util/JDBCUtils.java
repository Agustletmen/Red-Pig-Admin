package com.redpig.generator.util;

import cn.hutool.core.util.StrUtil;
import com.redpig.generator.vo.ClassVO;
import com.redpig.generator.vo.FieldVO;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * JDBC 工具类
 */
public class JDBCUtils {
    public static Connection conn = null;

    public static String dbName = null;

    @Deprecated
    private static List<FieldVO> getFields(String tableName) throws SQLException {

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from " + tableName);

        int columnCount = rs.getMetaData().getColumnCount();
        List<FieldVO> fields = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = rs.getMetaData().getColumnName(i);
            String columnTypeName = rs.getMetaData().getColumnTypeName(i);

            String fieldName = StringUtils.getName(columnName);
            String fieldType = TypeUtils.getJavaTypeByMysqlType(columnTypeName);

            FieldVO fieldVO = new FieldVO();
            fieldVO.setColumnName(columnName);
            fieldVO.setFieldName(fieldName);
            fieldVO.setFieldType(fieldType);

            fields.add(fieldVO);
        }
        return fields;
    }

    public static List<FieldVO> getTableFields(String tableName) throws Exception {

        DatabaseMetaData dbmd=conn.getMetaData();

        ResultSet resultSet = dbmd.getTables(dbName, tableName, tableName, new String[] { "TABLE" });

        List<FieldVO> fieldVOS = new ArrayList<>();
        while (resultSet.next()) {
            String TABLE_CAT = resultSet.getString("TABLE_CAT");//指定的表所在的数据库的名称
            String TABLE_NAME = resultSet.getString("TABLE_NAME");

            if(dbName.equals(TABLE_CAT) && tableName.equals(TABLE_NAME)){
                ResultSet rs = dbmd.getColumns(dbName, "%", tableName, "%");
                String REMARKS = resultSet.getString("REMARKS");
                if(StrUtil.isBlank(REMARKS)){
                    continue;
                }
                System.out.println("REMARKS = " + REMARKS);
                while(rs.next()){
                    String columnName = rs.getString("COLUMN_NAME");
                    String columnTypeName = rs.getString("TYPE_NAME");

                    String fieldName = StringUtils.getName(columnName);
                    String fieldType = TypeUtils.getJavaTypeByMysqlType(columnTypeName);

                    FieldVO fieldVO = new FieldVO();
                    fieldVO.setFieldType(fieldType);
                    fieldVO.setFieldName(fieldName);
                    fieldVO.setColumnName(columnName);
                    fieldVO.setComments(rs.getString("REMARKS").replaceAll("\"",""));
                    fieldVO.setColumnType(TypeUtils.getMySqlType(columnTypeName));
                    fieldVO.setJsType(TypeUtils.getJavaScriptTypeByMysqlType(columnTypeName));
                    fieldVO.setGetMethodName("get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1));
                    fieldVO.setSetMethodName("set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1));
                    fieldVOS.add(fieldVO);
                }
            }
        }
        return fieldVOS;
    }

    public static void main(String[] args) throws Exception {
        generator_perm_sql();
    }

    //生成权限SQL
    public static void generator_perm_sql() throws Exception {
        System.out.println(getAllTableNames());
        List<String> allTableNames = getAllTableNames();
        for (String tableName : allTableNames) {
            ClassVO classVO = getTableInfo(tableName);

            String pageName = classVO.getComment()+"分页查询";
            String pageTag = classVO.getEntityName()+":page";

            String saveOrUpdateName = "保存或更新"+classVO.getComment();
            String saveOrUpdateTag = classVO.getEntityName()+":saveOrUpdate";

            String delByIdName = "根据ID删除"+classVO.getComment();
            String delByIdTag = classVO.getEntityName()+":delById";

            String getByIdName = "根据ID查询"+classVO.getComment();
            String getByIdTag = classVO.getEntityName()+":getById";

            String remoteByIdsName = "批量删除"+classVO.getComment();
            String remoteByIdsTag = classVO.getEntityName()+":remoteByIds";

            String pagePerm = "INSERT INTO `redpig_sys_perm` (`deleteStatus`,`name`, `tag`, `create_by`, `update_by`, `remark`, `menu_id`) VALUES ('1', '"+pageName+"', '"+pageTag+"', '张三', '李四', '"+classVO.getComment()+"', '1');";
            String saveOrUpdatePerm = "INSERT INTO `redpig_sys_perm` (`deleteStatus`,`name`, `tag`, `create_by`, `update_by`, `remark`, `menu_id`) VALUES ('1', '"+saveOrUpdateName+"', '"+saveOrUpdateTag+"', '张三', '李四', '"+classVO.getComment()+"', '1');";
            String delByIdPerm = "INSERT INTO `redpig_sys_perm` (`deleteStatus`,`name`, `tag`, `create_by`, `update_by`, `remark`, `menu_id`) VALUES ('1', '"+delByIdName+"', '"+delByIdTag+"', '张三', '李四', '"+classVO.getComment()+"', '1');";
            String getByIdPerm = "INSERT INTO `redpig_sys_perm` (`deleteStatus`,`name`, `tag`, `create_by`, `update_by`, `remark`, `menu_id`) VALUES ('1', '"+getByIdName+"', '"+getByIdTag+"', '张三', '李四', '"+classVO.getComment()+"', '1');";
            String remoteByIdsPerm = "INSERT INTO `redpig_sys_perm` (`deleteStatus`,`name`, `tag`, `create_by`, `update_by`, `remark`, `menu_id`) VALUES ('1', '"+remoteByIdsName+"', '"+remoteByIdsTag+"', '张三', '李四', '"+classVO.getComment()+"', '1');";

            System.out.println(pagePerm);
            System.out.println(saveOrUpdatePerm);
            System.out.println(delByIdPerm);
            System.out.println(getByIdPerm);
            System.out.println(remoteByIdsPerm);

        }
    }

    public static List<String> getAllTableNames() throws Exception{
        List<String> list = new ArrayList<String>();


        String sql = "select table_name from information_schema.tables where table_schema='"+dbName+"'";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while(result.next()){
            list.add(result.getString("TABLE_NAME"));
        }
        return list;
    }

    public static void setClassVO(ClassVO classVO) throws Exception {

        DatabaseMetaData dbmd=conn.getMetaData();

        ResultSet resultSet = dbmd.getTables(dbName, "%", "%", new String[] { "TABLE" });

        while (resultSet.next()) {
            String tn=resultSet.getString("TABLE_NAME");
            if(tn.equals(classVO.getTableName())){
                ResultSet rs = dbmd.getColumns(dbName, "%", classVO.getTableName(), "%");
                classVO.setComment(resultSet.getString("REMARKS"));
            }
        }
    }

    public static ClassVO getTableInfo(String tableName) throws SQLException {

        DatabaseMetaData dbmd=conn.getMetaData();

        ResultSet resultSet = dbmd.getTables(dbName, "%", "%", new String[] { "TABLE" });
        ClassVO classVO = new ClassVO();
        while (resultSet.next()) {
            String tn=resultSet.getString("TABLE_NAME");
            if(tn.equals(tableName)) {
                ResultSet rs = dbmd.getColumns(dbName, "%", classVO.getTableName(), "%");
                classVO.setComment(resultSet.getString("REMARKS"));

                classVO.setTableName(tableName);
                String className = "";
                if(tableName.contains("_")){
                    String entityName = tableName.split("_")[tableName.split("_").length - 1];
                    className = entityName.substring(0,1).toUpperCase()+entityName.substring(1);
                }else{
                    className = tableName;
                }
                classVO.setClassName(className);
                classVO.setEntityName(classVO.getClassName().substring(0,1).toLowerCase()+classVO.getClassName().substring(1));

            }
        }

        return classVO;
    }

    /**
     * 释放资源
     * @param rs
     * @param st
     * @param conn
     */
    public static void close(ResultSet rs, Statement st,Connection conn){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(st != null){
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public static String getCreateSql(Connection src,String tableName) throws SQLException {
        String sql = String.format("SHOW CREATE TABLE %s", tableName);//查询sql
        PreparedStatement ps = null;
        String ddl = null;
        try {
            ps = src.prepareStatement(sql);
            //ps.setString(1, tableName);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                //System.out.println(resultSet.getString(1));//第一个参数获取的是tableName
                //System.out.println(resultSet.getString(2));//第二个参数获取的是表的ddl语句
                ddl = resultSet.getString(2);
            }
        } catch (SQLException e) {
            System.out.println(sql);
            System.out.println(tableName);
            e.printStackTrace();
        }finally {
            //src.close();
        }

        return ddl;
    }

    public static void executeUpdate(Connection dest,String sql) throws Exception {
        PreparedStatement ps = null;
        try {
            //创建执行SQL的prepareStatement对象
            ps = dest.prepareStatement(sql);
            //用于增删改操作
            int result = ps.executeUpdate();

        } catch (Exception e) {
            BufferedWriter out = new BufferedWriter(new FileWriter("c:\\war\\m.sql",true));
            out.write(sql+"\n");
            out.close();
            e.printStackTrace();
        }finally{
            ps.close();
            dest.close();
        }
    }

    /**
     * 获取表的批量插入语句insert into tableName(f1,f2,f3) values (v1,v2,v3),(v1,v2,v3)....
     * @return
     */
    public static String getBatchInsert(String tableName){



        return "";
    }

}

