import {bj_level} from './convertLevel.ts'
import { axiosAuthApi } from "../utils/instance.ts";

export const getRecommendation = async () => {
   // const results = await axios.get(`${api}/v1/profile/recommand`);
   const results = await axiosAuthApi().get(`/v1/profile/recommand`);
  
   for (let i = 0; i < 2; i++) {
      if (results.data[i].siteName.toLowerCase() === 'baekjoon') {
         results.data[i].problemLevel = bj_level[results.data[i].problemLevel as keyof typeof bj_level];
      } 
      else if (results.data[i].siteName.toLowerCase() === 'programmers') {

         results.data[i].problemLevel = results.data[i].problemLevel + '단계'
      }
   }
   return results.data
};