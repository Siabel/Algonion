import { axiosAuthApi } from "../utils/instance";

export const getNickname = async () => {
     const results = await axiosAuthApi().get(`/v1/user/login-nickname`);
     console.log(results, 'results')
     return results.data.nickname;
 };
 
 // 닉네임 중복검사
//  true : 중복 없음, false: 중복이라 안됨
// export const isNicknameAvailable = async (newNickname: string) => {
//    const results = await axiosAuthApi().get(`/v1/user/nickname`, {  params: { nickname: newNickname } });
   
//    return results.data;
// };

// export const updateNickname = async (newNickname: string) => {
//    if(!isNicknameAvailable(newNickname)) {
//       throw new Error('중복된 닉네임입니다.')
//    }
//    else {
//       await axiosAuthApi().put(`/v1/user/nickname`, { "nickname": newNickname });
//       localStorage.setItem("nickname", newNickname)
//    }
// };

// function UserDashboard() {
//    const [nickname, setNickname] = useState("");
   
//    useEffect(() => {
//      const fetchData = async () => {
//        const results = await getNickname();
//        setNickname(results.data.nickname);
//      };
   
//      fetchData();
//    }, []);