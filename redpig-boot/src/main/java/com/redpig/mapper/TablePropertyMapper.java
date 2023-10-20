package com.redpig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redpig.entity.TableProperty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  表属性 Mapper 接口
 *
 * @author zqd
 *
 * @date 2023-07-13 13:05:02
 */
@Mapper
public interface TablePropertyMapper extends BaseMapper<TableProperty> {

    List<TableProperty> getTablePropertyByTableInfoId(@Param("tableInfoId")Long tableInfoId);

    List<TableProperty> getTablePropertiesFromInformationSchema(@Param("databaseName")String databaseName,@Param("tableName")String tableName);

}
