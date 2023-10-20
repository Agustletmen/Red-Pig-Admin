import { http } from "@/utils/http";

type ResultBase = {
  code: Number;
  data?: object;
};

type ResultPage = {
  code: Number;
  data?: {
    /** 列表数据 */
    records: Array<any>;
    /** 总条目数 */
    total?: number;
    /** 每页显示条目个数 */
    pageSize?: number;
    /** 当前页数 */
    currentPage?: number;
  };
};

type ResultArray = {
  code: Number;
  data?: Array<any>;
};

/** 分页查询:url:请求接口 data:数据 **/
export const getPageList = (url?: string, data?: object) => {
  return http.get<ResultPage>(url, data);
};

/** 注:下面几个post请求实际上可以使用同一个 但是为了便于理解所以有了不同命名 **/
/** 新增或者更新:url:请求接口 data:数据 **/
export const saveOrUpdate = (url?: string, data?: object) => {
  return http.post<ResultBase>(url, { data });
};

/** 根据ID删除:url:请求接口 data:数据 **/
export const delById = (url?: string, data?: object) => {
  return http.post<ResultBase>(url, { data });
};

/** 保存权限:url:请求接口 data:数据 **/
export const saveAuth = (url?: string, data?: object) => {
  return http.post<ResultBase>(url, { data });
};

/** 根据ID批量删除:url:请求接口 data:数据 **/
export const removeBatchByIds = (url?: string, data?: object) => {
  return http.post<ResultBase>(url, { data });
};

/** 创建表:url:请求接口 data:数据 **/
export const createTable = (url?: string, data?: object) => {
  return http.post<ResultBase>(url, { data });
};

/** 生成代码:url:请求接口 data:数据 **/
export const generatorCode = (url?: string, data?: object) => {
  return http.post<ResultBase>(url, { data }, { responseType: "blob" });
};

/** 获取文件:url:请求接口 data:数据 **/
export const getFile = (url?: string, data?: object) => {
  return http.post<ResultBase>(url, { data }, { responseType: "blob" });
};

/** 同步数据库:url:请求接口 data:数据 **/
export const asyncDatabase = (url?: string, data?: object) => {
  return http.post<ResultBase>(url, { data });
};

/** 重置密码:url:请求接口 data:数据 **/
export const ReSetPassword = (data?: object) => {
  return http.post<ResultBase>("/user/ReSetPassword", { data });
};

/** 重置密码:url:请求接口 data:数据 **/
export const upload = (url?: string, data?: object) => {
  return http.post<ResultBase>(
    url,
    { data },
    {
      headers: {
        "Content-Type": "multipart/form-data"
      }
    }
  );
};

/** 获取List集合:url:请求接口 data:数据 **/
export const getList = (url?: string, data?: object) => {
  return http.get<ResultArray>(url, data);
};

/** 根据ID查询:url:请求接口 data:数据 **/
export const getById = (url?: string, data?: object) => {
  return http.get<ResultBase>(url, data);
};

/** 请假审批情况:url:请求接口 data:数据 **/
export const isEnd = (url?: string, data?: object) => {
  return http.post<ResultBase>(url, { data });
};

/** 请假审批情况:url:请求接口 data:数据 **/
export const quartzChangeStatus = (url?: string, data?: object) => {
  return http.post<ResultBase>(url, { data });
};

