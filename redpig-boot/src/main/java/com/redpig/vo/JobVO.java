package com.redpig.vo;

import lombok.Data;

@Data
public class JobVO {

    private String jobClassName;

    private String jobGroupName;

    private String cronExpression;
}
