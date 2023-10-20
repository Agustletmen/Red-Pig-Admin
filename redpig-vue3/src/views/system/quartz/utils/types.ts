interface FormItemProps {
  jobName: string;
  jobGroup: string;
  jobClassName: string;
  triggerName: string;
  triggerGroup: string;
  repeatInterval: number;
  timesTriggered: number;
  cronExpression: string;
  timeZoneId: string;
  status: boolean;
}

interface FormProps {
  formInline: FormItemProps;
}

export type { FormItemProps, FormProps };
