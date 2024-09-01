import { axiosAuthApi } from "../utils/instance";

export const getLevelGraph = async (nickname: string) => {
   // const results = await axios.get(`${api}/v1/dashboard/level-graph?nickname=${nickname}`);
   const results = await axiosAuthApi().get(`/v1/dashboard/level-graph?nickname=${nickname}`);
   // console.log("원형", results.data)
   return results.data
};

export const getCategoryGraph = async (nickname: string) => {
   // const results = await axios.get(`${api}/v1/dashboard/category-graph?nickname=${nickname}`);
   const results = await axiosAuthApi().get(`/v1/dashboard/category-graph?nickname=${nickname}`);
   // console.log("방사형",results.data)
   
   return results.data
};

export const getProblemStackGraph = async (nickname: string) => {
   // const results = await axios.get(`${api}/v1/dashboard/problem-stack?nickname=${nickname}`);
   const results = await axiosAuthApi().get(`/v1/dashboard/problem-stack?nickname=${nickname}`);
   // console.log("선형",results.data)

   return results.data
};

export const getPointStackGraph = async (nickname: string) => {
   // const results = await axios.get(`${api}/v1/dashboard/point-stack?nickname=${nickname}`);
   const results = await axiosAuthApi().get(`/v1/dashboard/point-stack?nickname=${nickname}`);
   
   return results.data
};