interface FormItemProps {
  //主键ID
  id: number;
  //角色名称
  name: string;
  //角色标签
  tag: string;
  //创建者
  createBy: string;
  //更新者
  updateBy: string;
  //角色描述
  remark: string;
}

interface FormProps {
  formInline: FormItemProps;
}

export type { FormItemProps, FormProps };
