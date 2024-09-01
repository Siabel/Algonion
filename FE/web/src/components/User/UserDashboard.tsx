import BoardRadarGraph from "../Board/BoardRadarGraph";
import BoardLinearGraph from "../Board/BoardLinearGraph";
import BoardRoundGraph from "../Board/BoardRoundGraph";
import classes from "./UserDashboard.module.scss"

import {getCategoryGraph, getLevelGraph, getProblemStackGraph, getPointStackGraph} from '../../api/dashboardAPI.ts'

import { useEffect, useState } from "react";

interface linearData {
  categories: string[]
  data: number[]
}

function UserDashboard() {
  const nickname = decodeURIComponent(window.location.href.split('/')[4]);
  // console.log(nickname);  
  
  const [categoryData, setCategoryData] = useState<number[]>();
  const [levelData, setLevelData] = useState<number[]>();
  const [problemData, setProblemData] = useState<linearData>();
  const [pointData, setPointData] = useState<linearData>();
  
  useEffect(() => {
    async function getAxios(){
      // - [방사형 그래프] 문제 유형
      let res1 =await getCategoryGraph(nickname);
      setCategoryData(res1);
      // - [원형 그래프] 문제 난이도 (우리 티어) 
      let res2 =await getLevelGraph(nickname);
      setLevelData(res2);
      // - [선형 그래프] 기간별 문제 푼 수 누적 추이
      let res3 =await getProblemStackGraph(nickname);
      setProblemData(res3);
      // - [선형 그래프] 기간별 포인트 상승량 누적 추이
      let res4 =await getPointStackGraph(nickname);
      setPointData(res4); 
    }
    getAxios()
  },[]);


  return (
    <div className={classes.component}>

      <div className={classes.graphTable}>
      <p>기록의 여정, 육각형 개발자</p>

        <div className={classes.graph}>
          {/* <BoardRadarGraph/> */}
          <BoardRadarGraph data={categoryData}/>
        </div>
      </div>

      <div className={classes.graphTable}>
        <p>기록의 여정, 문제 난이도</p>
        <div className={classes.graph}>
        {/* <BoardRoundGraph/> */}
          <BoardRoundGraph series={levelData}/>
        </div>
      </div>

      <div className={classes.graphTable}>
        <p>365일의 걸음, 푼 문제</p>
        <div className={classes.graph}>
        {/* <BoardLinearGraph/> */}
          <BoardLinearGraph categories={problemData?.categories || []}
                            data={problemData?.data || []}/>
        </div>
      </div>

      <div className={classes.graphTable}>
        <p>365일의 걸음, 누적 점수</p>
        <div className={classes.graph}>
        {/* <BoardLinearGraph/> */}
        <BoardLinearGraph categories={pointData?.categories || []}
                            data={pointData?.data || []}/>
        </div>
      </div>

    </div>
  )
}
  
export default UserDashboard