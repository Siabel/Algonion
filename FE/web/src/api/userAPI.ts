import { axiosAuthApi } from "../utils/instance";

export const isRealUser = async (nickname: string) => {
    // const results = await axios.post(`${api}/v1/user/check-user?nickname={nickname}`);
    const results = await axiosAuthApi().get(`/v1/user/check-user?nickname=${nickname}`);
   
    return results.data
 };