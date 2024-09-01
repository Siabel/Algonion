import { useEffect, useState } from "react";

import CodeLogProblem from "../../components/CodeLog/CodeLogProblem.tsx";
import CodeLog from "../../components/CodeLog/CodeLog.tsx";
import CodeLogMemo from "../../components/CodeLog/CodeLogMemo.tsx";

import { getCodeLogDetail } from "../../api/codeLogAPI.ts";
import classes from "./CodeLogDetailPage.module.scss";
import { isRealUser } from "../../api/userAPI.ts";

interface Data {
  siteName: string;
  submissionId: string;
  problemNum: string;
  problemTitle: string;
  problemLevel: string;
  memory: string;
  runtime: string;
  language: string;
  submissionCode: string;
  codeLength: string;
  submissionTime: string;
  url: string;
  memo: string;
  visible: boolean;
}

function CodeLogDetailPage() {
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

  const problemId = window.location.href.split("/")[5];
  // console.log(problemId)

  const [data, setData] = useState<Data>();

  useEffect(() => {
    async function getAxios() {
      let res = await getCodeLogDetail(problemId);
      // console.log(res)
      setData(res);
    }
    getAxios();
  }, []);

  return (
    <div className={classes.page}>
      <div className={classes.codeLogDetailPage}>
        <CodeLogProblem
          problemId={problemId}
          siteName={data?.siteName || ""}
          problemNum={data?.problemNum || ""}
          problemTitle={data?.problemTitle || ""}
          url={data?.url || ""}
        />
        <CodeLog code={data?.submissionCode || ""} />
        <CodeLogMemo problemId={problemId} memo={data?.memo || ""} />
      </div>
    </div>
  );
}

export default CodeLogDetailPage;
