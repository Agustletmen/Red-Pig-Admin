interface FormItemProps {
  //主键ID
    id: number;
  //商品名称
    name: string;
  //创建者
    createBy: string;
  //更新者
    updateBy: string;
  //商品描述
    remark: string;
}

interface FormProps {
  formInline: FormItemProps;
}

export type { FormItemProps, FormProps };
