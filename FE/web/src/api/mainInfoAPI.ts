import { axiosAuthApi } from "../utils/instance";


export const getSolved100 = async (nickname: string) => {
   const results = await axiosAuthApi().get(`/v1/dashboard/top100?nickname=${nickname}`);

//    console.log(results.data, 'getSolved100')
   return results.data
};

export const getStreak = async (nickname: string, from: string, to: string) => {
    // const results = await axios.get(`${api}/v1/profile/streak?nickname={nickname}&from={from}&to={to}`);
   const results = await axiosAuthApi().get(`/v1/profile/streak?nickname=${nickname}&from=${from}&to=${to}`);

   //  console.log(results.data)
    return results.data
};

// export const postNewFriend = async (nickname: string) => {
//    const results = await axios.post(`${api}/v1/profile/streak?username=${username}&from={from}&to={to}=${nickname}`);
// const results = await axiosAuthApi().post(`/v1/profile/streak?username=${username}&from={from}&to={to}=${nickname}`);
  
//    return results.data
// };