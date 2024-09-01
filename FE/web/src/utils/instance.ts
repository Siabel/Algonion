import axios, { AxiosInstance } from "axios";
import { setCookie } from "./cookieUtil";
const api = import.meta.env.VITE_BACK_END;

const accessToken = localStorage.getItem("access_token");

const axiosAuthApi = (): AxiosInstance => {
  const instance = axios.create({
    baseURL: api,
    headers: { Authorization: "Bearer " + accessToken },
    timeout: 1000,
  });
  return instance;
};

const axiosApi = (): AxiosInstance => {
  const instance = axios.create({
    baseURL: api,
    timeout: 1000,
  });
  return instance;
};

axios.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const {
      config,
      response: { status },
    } = error;
    if (status === 401) {
      const originalRequest = config;
      // token refresh 요청
      const { data } = await axios.post(
        `${api}/v1/user/token`,
        {},
        { headers: { Authorization: `Bearer ${accessToken}` } }
      );
      // 새로운 토큰 저장
      localStorage.setItem("access_token", data.access_token);
      setCookie("refresh_token", data.refresh_token, 0);
      originalRequest.headers.authorization = `Bearer ${data.access_token}`;
      // 새로운 토큰으로 재요청
      return axios(originalRequest);
    }
    return Promise.reject(error);
  }
);

export { axiosAuthApi, axiosApi };
