import { Key, useEffect, useState } from "react";
import {Card, CardHeader, CardBody, Button} from "@nextui-org/react";

import { getRecommendation } from "../../api/recommendationAPI";
import classes from "./CodeLogRecommendationItem.module.scss"

type item = { siteName: string; problemNum: string; problemTitle: string ; problemLevel: string; url: string; }[]

export default function CodeLogRecommendationItem() {
  const [data, setData] = useState<item>();
  useEffect(() => {
    async function getAxios(){
      let res = await getRecommendation();
      setData(res);
      console.log(res);
    }
    getAxios()
  }, []);

  // function goToSolve(url: string) {
  //   window.location.href=`${url}`;
  // };

  if (!data) {
    return (
      <div> 로딩 중 입니다.</div>
      );
  }

  return (
    <div className={classes.items}>
      {data.map((item: { siteName: string; problemNum: string; problemTitle: string; problemLevel: string; url: string;}, index: Key) => (
        <Card key={index} className={classes.item}>
          <CardHeader className="justify-between">
            <div className="flex gap-5 ms-2 ">
              <div className="flex flex-col gap-1 items-start justify-center">
                <h4 className="text-xs font-semibold leading-none text-default-600">{item.siteName} - {item.problemNum}</h4>
                <h5 className="text-m tracking-tight  text-default-400">{item.problemTitle}</h5>
              </div>
            </div>
            <div className="flex gap-5 me-2">
              <div className="flex flex-col gap-1 items-start justify-center">
                <h6 className="text-xs text-purple-400 font-semibold leading-none text-default-600">{item.problemLevel}</h6>
              </div>
            </div>
          </CardHeader>
          <CardBody className="px-3 py-0 text-small text-default-400">
          <a
            className="mt-2 mb-2"
            href={item.url}
            target="_blank"
            rel="noopener noreferrer"
          >
            <Button
              size="lg"
              variant="bordered"
              fullWidth={true}
            >
              문제 풀러 가기!
            </Button>
          </a>
          </CardBody>
        </Card>
      ))}
    </div>
  );
}