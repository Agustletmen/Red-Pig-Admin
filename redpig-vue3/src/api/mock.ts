import { http } from "@/utils/http";

type Result = {
  success: boolean;
  data: Array<any>;
};

/** 地图数据 */
export const mapJson = (params?: object) => {
  return http.get<Result>("/getMapInfo", { params });
};
