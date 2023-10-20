interface FormItemProps {
  //主键id
  id: number;
  //名称
  name: string;
  //部署ID
  deploymentId: string;
  //创建者
  createBy: string;
  //更新者
  updateBy: string;
}

interface FormProps {
  formInline: FormItemProps;
}

export type { FormItemProps, FormProps };
