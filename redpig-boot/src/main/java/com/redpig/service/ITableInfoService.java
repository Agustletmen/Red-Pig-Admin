package com.redpig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redpig.entity.TableInfo;
import com.redpig.generator.vo.FieldVO;

import java.util.List;

/**
 * 表信息 服务类
 *
 * @author zqd
 *
 * @date 2023-07-13 08:59:10
 */
public interface ITableInfoService extends IService<TableInfo> {
    void createTableById(Long tableInfoId);

    List<FieldVO> getFieldByTableInfoId(TableInfo tbi);

    void generatorByTableInfoId(Long id);

    void generatorMenu(Long tableInfoId);

    List<String> selectAllTableNames();

    public void createTableProperties();
}
