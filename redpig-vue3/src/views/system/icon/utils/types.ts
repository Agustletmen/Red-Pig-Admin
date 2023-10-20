interface FormItemProps {
  //主键ID
  id: number;
  //图标
  iconTag: string;
  //创建者
  createBy: string;
  //更新者
  updateBy: string;
  //备注
  remark: string;
}

interface FormProps {
  formInline: FormItemProps;
}

export type { FormItemProps, FormProps };