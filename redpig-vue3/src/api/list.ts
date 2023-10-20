import { http } from "@/utils/http";

type Result = {
  success: boolean;
  data?: {
    /** 列表数据 */
    list: Array<any>;
  };
};

/** 卡片列表 */
export const getCardList = (data?: object) => {
  return http.get<Result>("/getCardList", { data });
};

/** 版本日志 */
export const getReleases = () => {
  return http.get<Result>("/releases");
};
