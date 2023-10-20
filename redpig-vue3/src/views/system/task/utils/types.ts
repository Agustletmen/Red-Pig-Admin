interface FormItemProps {
  //主键id
    id: number;
  //任务ID
    taskId: string;
  //任务名称
    taskName: string;
  //执行ID
    taskExecutionId: string;
  //任务描述
    taskDescription: string;
  //任务处理人
    taskAssignee: string;
  //流程实例ID
    taskProcessInstanceId: number;
  //创建者
    createBy: string;
  //更新者
    updateBy: string;
}

interface FormProps {
  formInline: FormItemProps;
}

export type { FormItemProps, FormProps };
