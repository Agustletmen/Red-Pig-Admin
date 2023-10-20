interface FormItemProps {
  //主键id
  id: number;
  //部门名称
  deptName: string;
  //显示顺序
  orderNum: number;
  //负责人
  manager: string;
  //联系电话
  phone: string;
  //邮箱
  email: string;
  //部门状态（0正常 1停用）
  status: boolean;
  //创建者
  createBy: string;
  //更新者
  updateBy: string;
  //父部门ID
  parentId: number;
}

interface FormProps {
  formInline: FormItemProps;
}

export type { FormItemProps, FormProps };
