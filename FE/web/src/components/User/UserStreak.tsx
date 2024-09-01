import { useEffect, useState } from "react";
import dayjs from "dayjs";
import weekOfYear from "dayjs/plugin/weekOfYear";
import classes from "./UserStreak.module.scss";
import Tippy from "@tippyjs/react";
import "tippy.js/dist/tippy.css";
import { getStreak } from "../../api/mainInfoAPI";
import {CardHeader, CardBody, CardFooter, Card, Chip} from "@nextui-org/react";

function UserStreak() {
  // nickname 과 username(현재 로그인 되어 있는 유저) 불러 오기
  const nickname = decodeURIComponent(window.location.href.split('/')[4]);
  
  // getAxios
  interface StreakData {
    [key: string]: any;
  }

  const [data, setData] = useState<StreakData>({});
  const [continuity, setContinuity] = useState(0);

  useEffect(() => {
    async function getAxios(){
      let res =await getStreak(nickname, fromFormated, toFormated);
      setData(res);
    }
    
    const to = dayjs()
    const oneYearAgo = to.subtract(1, "year");
    const weekOfMonth = to.week() - to.startOf("month").week();
    const sameWeek = oneYearAgo.startOf("month").add(weekOfMonth, "week");
    const from = sameWeek.subtract(sameWeek.day(), "day");
    
    const toFormated = to.format('YYYY-MM-DD')
    const fromFormated = from.format('YYYY-MM-DD')
    
    getAxios()

  }, []);

  useEffect(() => {
    const to = dayjs()
    const oneYearAgo = to.subtract(1, "year");
    const weekOfMonth = to.week() - to.startOf("month").week();
    const sameWeek = oneYearAgo.startOf("month").add(weekOfMonth, "week");
    const from = sameWeek.subtract(sameWeek.day(), "day");

    const diff = to.diff(from, "day");
    let date = to;
    
    let continuityTemp = 0
    for (let i = 0; i <= diff; i++) {
      const key = date.format("YYYY-MM-DD");
      // console.log(key)

      if (data.hasOwnProperty(key)) {
        continuityTemp = continuityTemp + 1
      } else {
        setContinuity(continuityTemp);
        break;
      }
      date = date.subtract(1, "day");
      console.log(key, continuityTemp)
      
    }
    setContinuity(continuityTemp)
    // console.log(res['2024-02-13'])
  }, [data]);
  
  // dayjs
  dayjs.extend(weekOfYear);

  const table = (
    from?: dayjs.Dayjs | undefined,
    to: dayjs.Dayjs = dayjs()
  ): JSX.Element[] => {
    if (!from) {
      // 주차가 같은 1년 전을 from으로 설정한다.
      // ex) to가 2024-02-11일 때 2월 3주차이므로 from은 2023년 2월 3주차 첫번째 일요일인 2023-02-12이다.
      const oneYearAgo = to.subtract(1, "year"); //1년 전 날짜를 구한다
      const weekOfMonth = to.week() - to.startOf("month").week(); //to가 해당 월의 몇 주차인지 구한다
      const sameWeek = oneYearAgo.startOf("month").add(weekOfMonth, "week"); // 1년 전의 같은 월 같은 주차를 구한다.
      from = sameWeek.subtract(sameWeek.day(), "day"); // 첫 번째 일요일을 구한다
    }
    const dayOfWeekList = ["일", "월", "화", "수", "목", "금", "토"];
    const td: JSX.Element[][] = Array.from({ length: 7 }, (_, i) => [
      <td>{dayOfWeekList[i]}</td>,
    ]);
    const diff = to.diff(from, "day");
    let date = from;

    for (let i = 0; i <= diff; i++) {
      const key = date.format("YYYY-MM-DD");
      let streakOnOrOff = classes.streakOff;
      if (data[key] !== undefined) {
        streakOnOrOff = classes.streakOn;
      }

      td[date.day()].push(
        <Tippy content={date.format("YYYY-MM-DD")}>
          <td
            key={date.format("YYYY-MM-DD")}
            className={streakOnOrOff}
          ></td>
        </Tippy>
      );
      date = date.add(1, "day");
    }
    const tr = td.map((v, i) => <tr key={dayOfWeekList[i]}>{v}</tr>);

    return tr;
  };


  return (
  <Card className={`'max' ${classes['solved100-container']} p-1 mt-10`}>
  <CardHeader className="justify-between">
        <Chip
          size="lg"
          variant="shadow"
          radius="md"
          classNames={{
            base: `bg-gradient-to-br from-indigo-500 to-pink-500 border-small border-bronze/50 shadow-silver-500/50 ${classes.chip}`,
            content: `drop-shadow shadow-black text-white`,
          }}>
          <p className="text-xlarge font-bold tracking-tight text-default-300">Algo Streak</p>
          <p className="text-large font-bold ">현재 {continuity}일!</p>
        </Chip>
  </CardHeader>
  <CardBody className="px-3 py-0 text-small text-default-400">
    <p className="ms-2">
      1년 간의 스트릭이 표시됩니다.
    </p>
    <span className="pt-2">
      <table>
    <tbody>{table()}</tbody>
    </table>
    </span>
  </CardBody>
  <CardFooter className="gap-3">
  </CardFooter>
</Card>
  );
}

export default UserStreak;
