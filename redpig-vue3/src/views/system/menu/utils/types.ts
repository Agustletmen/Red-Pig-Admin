interface FormItemProps {
  //主键ID
  id: number;
  //菜单名称
  name: string;
  //菜单地址
  path: string;
  //组件名:如果不填,默认就是菜单路径,从前端src目录开始计算,例如:src/views/ads/index.vue
  component: string;
  //标题
  title: string;
  //类型：0、目录 1、菜单 2、接口
  type: number;
  //图标
  icon: string;
  //链接
  showLink: boolean;
  //排序:值越小越靠前
  rank: number;
  //父菜单
  parentId: number;
}

interface FormProps {
  formInline: FormItemProps;
}

interface MenuRolePerm {
  //菜单ID
  id: number;
  //角色ID数组
  roles: Array<any>;
  //权限ID数组
  perms: Array<any>;
  name: string;
  title: string;
}

interface FormMenuRolePermProps {
  formInline: MenuRolePerm;
}

export type { FormItemProps, FormProps, MenuRolePerm, FormMenuRolePermProps };
