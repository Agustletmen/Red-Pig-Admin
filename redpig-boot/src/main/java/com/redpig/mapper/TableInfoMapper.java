package com.redpig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redpig.entity.TableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  表信息 Mapper 接口
 *
 * @author zqd
 *
 * @date 2023-07-13 08:59:10
 */
@Mapper
public interface TableInfoMapper extends BaseMapper<TableInfo> {
    TableInfo selectTableInfoWithTableProperties(@Param("id")Long id);

    void createTable(TableInfo tableInfo);

    boolean existTable(@Param("databaseName")String databaseName,@Param("tableName")String tableName);

    List<String> selectAllTableNames(@Param("databaseName")String databaseName);

}
