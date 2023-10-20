interface FormItemProps {
  //主键ID
    id: number;
  //操作模块
    operModul: string;
  //操作类型
    operType: string;
  //操作描述
    operDesc: string;
  //请求方法
    operMethod: string;
  //请求参数
    operRequParam: string;
  //返回参数
    operRespParam: string;
  //用户id
    operUserId: string;
  //用户名称
    operUserName: string;
  //操作IP
    operIp: string;
  //操作路径
    operUri: string;
  //创建者
    createBy: string;
  //更新者
    updateBy: string;
}

interface FormProps {
  formInline: FormItemProps;
}

export type { FormItemProps, FormProps };
