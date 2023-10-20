package com.redpig.entity;

import lombok.Data;

import java.math.BigInteger;

@Data
public class JobAndTrigger {
	private String jobName;
	private String jobGroup;
	private String jobClassName;
	private String triggerName;
	private String triggerGroup;
	private BigInteger repeatInterval;
	private BigInteger timesTriggered;
	private String cronExpression;
	private String timeZoneId;
	private boolean status;
}
