// 虽然字段很少 但是抽离出来 后续有扩展字段需求就很方便了

interface FormItemProps {
  id: number;
  username: string;
  password: string;
  nickname: string;
  enabled: boolean;
  sex: number;
  mobile: string;
  email: string;
  dept: any;
  deptId: number;
}

interface FormProps {
  formInline: FormItemProps;
}

interface UserRolePerm {
  //用户ID
  id: number;
  //角色ID数组
  roles: Array<any>;
  //权限ID数组
  perms: Array<any>;
  username: string;
}

interface FormUserRolePermProps {
  formInline: UserRolePerm;
}

export type { FormItemProps, FormProps, UserRolePerm, FormUserRolePermProps };
