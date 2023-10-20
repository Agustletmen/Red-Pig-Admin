interface FormItemProps {
  //主键ID
    id: number;
  //请求参数
    excRequParam: string;
  //操作方法
    operMethod: string;
  //异常名称
    excName: string;
  //异常信息
    excMessage: string;
  //操作者用户ID
    operUserId: string;
  //操作者用户名
    operUserName: string;
  //操作的地址
    operUri: string;
  //操作者IP
    operIp: string;
  //操作时间
    operCreateTime: string;
  //创建者
    createBy: string;
  //更新者
    updateBy: string;
}

interface FormProps {
  formInline: FormItemProps;
}

export type { FormItemProps, FormProps };
