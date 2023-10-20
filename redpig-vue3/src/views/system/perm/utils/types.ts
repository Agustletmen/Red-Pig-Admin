interface FormItemProps {
  //主键ID
  id: number;
  //权限名称
  name: string;
  //权限标签
  tag: string;
  //创建者
  createBy: string;
  //更新者
  updateBy: string;
  //备注
  remark: string;
  //所属菜单
  menuId: number;
}

interface FormProps {
  formInline: FormItemProps;
}

export type { FormItemProps, FormProps };
