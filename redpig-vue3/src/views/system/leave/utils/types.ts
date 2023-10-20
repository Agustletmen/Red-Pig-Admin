interface FormItemProps {
  //主键id
  id: number;
  //请假申请人ID
  userId: number;
  //请假理由
  leaveReason: string;
  //请假起始时间
  startTime: string;
  //请假结束时间
  endTime: string;
  //流程实例ID
  processInstanceId: string;
  //流程定义ID
  processDefinitionId: string;
  //请假是否同意
  agree: boolean;
  //创建者
  createBy: string;
  //更新者
  updateBy: string;
}

interface FormProps {
  formInline: FormItemProps;
}

export type { FormItemProps, FormProps };
