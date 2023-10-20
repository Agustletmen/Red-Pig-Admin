interface FormItemProps {
  //主键ID
    id: number;
  //作者
    author: string;
  //内容
    content: string;
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
