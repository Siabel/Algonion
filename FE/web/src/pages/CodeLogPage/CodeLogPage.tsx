import CodeLogList from "../../components/CodeLog/CodeLogList.tsx";
import CodeLogRecommendation from "../../components/CodeLog/CodeLogRecommendation.tsx";

// import CodeLogSearchBar from '../../components/CodeLog/CodeLogSearchBar.tsx'
// import CodeLogSearchResults from '../../components/CodeLog/CodeLogSearchResults.tsx'

import classes from "./CodeLogPage.module.scss";
import { useEffect } from "react";
import { isRealUser } from "../../api/userAPI.ts";

export default function CodeLogPage() {
  // nickname 받아오기 + 존재하지 않는 사용자 404 처리
  const nickname = decodeURIComponent(window.location.href.split("/")[4]);

  useEffect(() => {
    async function getAxios() {
      let isValid = await isRealUser(nickname);
      console.log(nickname, isValid);
      if (!isValid) {
        // 페이지 이동 함수
        window.location.href = "/*";
      }
    }
    getAxios();
  }, []);

  return (
    <div>
      <div className={classes.page}>
        <div className={classes.search}>
          {/* <CodeLogSearchBar/>
        <CodeLogSearchResults/> */}
        </div>
        <div className={classes.title}>
          <h3>
            <strong>{nickname}</strong> 님의 코드로그
          </h3>
          <CodeLogRecommendation />
        </div>
        <div className={classes.codeLogPage}>
          <CodeLogList />
        </div>
      </div>
    </div>
  );
}
