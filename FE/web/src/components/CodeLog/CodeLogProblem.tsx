import {Card, CardBody} from "@nextui-org/react";

import classes from './CodeLogProblem.module.scss'

interface CodeLogProblemProps {
  problemId: string;
  siteName: string;
  problemNum: string;
  problemTitle: string;
  url: string;
}

export default function CodeLogProblem(props: CodeLogProblemProps) {
  function goToProblem(url: string) {
    window.open(url, "_blank");
  }

  return (
    <div>
      <Card>
      <CardBody className={classes.card} onClick={() => goToProblem(props.url)}>
        <div className={classes.number}>
          <p>{props.siteName}</p>
          <p className={classes.number2}>{props.problemNum}</p>
        </div>
        <div className={classes.name}>{props.problemTitle}</div>
      </CardBody>
      </Card>
    </div>
  );
}
