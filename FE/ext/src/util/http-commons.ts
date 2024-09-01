//http-commons.js
import axios from "axios";
import getAccessToken from "./getAccessToken";

const { VITE_BACKEND } = import.meta.env;

function axiosInstance() {
    const instance = axios.create({
        baseURL: VITE_BACKEND,
        headers: {
            "Content-Type": "application/json;charset=utf-8",
        },
    });
    return instance;
}

// axiosInstance().interceptors.request.use(
//     async (config) => {
//         const accessToken = await getAccessToken();
//         console.log(accessToken);
//         config.headers['Authorization'] = `Bearer ${accessToken}`;

//         return config;
//     },
//     (error) => {
//         console.log(error);
//         return Promise.reject(error);
//     }
// );

export { axiosInstance };