interface FormItemProps {
  //主键ID
  id: number;
  //表名
  tableName: string;
  //表备注
  tableComment: string;
  //类名
  className: string;
  //创建者
  createBy: string;
  //更新者
  updateBy: string;
  //备注
  remark: string;
}

interface FormItemPropertyProps {
  id: number;
  tableProperties: Array<any>;
}

interface FormProps {
  formInline: FormItemProps;
}

interface FormPropertyProps {
  formInline: FormItemPropertyProps;
}

export type {
  FormItemProps,
  FormProps,
  FormItemPropertyProps,
  FormPropertyProps
};
