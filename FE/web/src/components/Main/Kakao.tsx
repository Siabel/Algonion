// declare global {
//   interface Window {
//     Kakao: any;
//   }
// }

import Kakaoimg from "../../assets/logo/kakaoSymbol.png";
import classes from "./Kakao.module.scss"; //
const url = import.meta.env.VITE_BACK_END + "/oauth2/authorization/kakao";
const KakaoLoginButton = () => {
  // const REST_API_KEY = import.meta.env.VITE_KAKAO_REST_API_KEY;
  // const REDIRECT_URI = import.meta.env.VITE_KAKAO_URL;
  // const link = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;

  // const loginHandler = () => {
  //   window.location.href = link;
  // };
  //   <a href='#' >
  //   <img src="https://i.ibb.co/H20CtsM/Kakao-login-large-narrow.png" width='240px'
  //   alt="카카오 로그인" />
  // </a>
  return (
    <a
      href={url}
      className={classes["kakao-button"]}
      // onClick={loginHandler}
    >
      <img
        src={Kakaoimg}
        alt="Kakao Logo"
        className="h-4 w-auto inline-block"
      />
      <span className="mx-auto">카카오 로그인</span>
    </a>
  );
};

export default KakaoLoginButton;
