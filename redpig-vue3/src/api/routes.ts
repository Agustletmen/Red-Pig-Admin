import { http } from "@/utils/http";

type Results = {
  code: Number;
  data: Array<any>;
};

export const getRoutes = () => {
  return http.get<Results>("/route/getRoutes");
};
