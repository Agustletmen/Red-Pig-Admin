package com.redpig.vo;

import com.redpig.entity.TableProperty;
import lombok.Data;

import java.util.List;

@Data
public class TablePropertyVO {
    private Long id;

    private List<TableProperty> tableProperties;
}
