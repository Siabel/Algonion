import UserBasis from "../../components/User/UserBasis.tsx";
import UserBar from "../../components/User/UserBar.tsx";

import classes from "./UserPage.module.scss";
import { isRealUser } from "../../api/userAPI.ts";
import { useEffect } from "react";

function UserPage() {
  // nickname 받아오기 + 존재하지 않는 사용자 404 처리
  const nickname = decodeURIComponent(window.location.href.split("/")[4]);

  useEffect(() => {
    async function getAxios() {
      const isValid = await isRealUser(nickname);
      // console.log(nickname, isValid)
      if (!isValid) {
        // 페이지 이동 함수
        window.location.href = "/";
      }
    }
    getAxios();
  }, [nickname]);

  return (
    <div className={classes.page}>
      <UserBasis />
      <div className={classes.userBarContainer}>
        <UserBar />
      </div>
    </div>
  );
}

export default UserPage;
