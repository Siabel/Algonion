import { useEffect } from "react";
import { deleteCookie, getCookie } from "../../utils/cookieUtil";
import { CircularProgress } from "@nextui-org/react";
import { useNavigate } from "react-router-dom";

function LoginSuccessPage() {
  const navigate = useNavigate();
  
  useEffect(() => {
    const fetchData = async () => {
      // access token 가져오기
      const accessToken = getCookie("access_token");
      if (accessToken) {
        localStorage.setItem('access_token', accessToken)
        deleteCookie("access_token");
      }
      
      // 작업이 완료되면 메인 페이지로 이동
      setTimeout(() => {
        navigate("/");
      }, 2000);
    };

    fetchData();
  }, [navigate]);

  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <CircularProgress
        size="lg"
        color="secondary"
        label="로그인 성공! 잠시만 기다려주세요"
      />
    </div>
  );
}

export default LoginSuccessPage;
