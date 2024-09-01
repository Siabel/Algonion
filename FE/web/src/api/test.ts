import axios from "axios";
const api = import.meta.env.VITE_BACK_END;

export const getTest = async () => {
   const result = await axios.get(`${api}/`);
   return result.data;
};