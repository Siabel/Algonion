import getAsset from '../../utils/getAsset';
import { variableProblemLevel } from '../../utils/variable';
import classes from './UserProblem.module.scss'
import { Tooltip } from '@nextui-org/react';

export default function Problem100({ problems }: {
  problems: {
    algoScore: number;
    problemId: number;
    problemNum: string;
    problemTitle: string;
    problemLevel: string;
    siteName: string;
    url: string;
  }[];
}) {
  const gridLists: React.ReactNode[] = [];
  
  for (let i = 0; i < 10; i++) {
    const gridListItems: React.ReactNode[] = [];
    
    for (let j = 0; j < 10; j++) {
      const index = i * 10 + j;
      
      if (index < problems.length) {
        const { problemId, problemNum, problemTitle, problemLevel, siteName, url } = problems[index];
        
        const changeLevel = variableProblemLevel[siteName][problemLevel]
        const tierImg = getAsset(`tier/level_${changeLevel}.png`)

          gridListItems.push(
            <div key={problemId}>
              <Tooltip
                showArrow
                content = {
                  <div className="px-1 py-2">
                    <div className="text-tiny">{siteName}. {problemNum} </div>
                    <div >{changeLevel} </div>   
                    <div className="text-small font-bold">{problemTitle}</div>
                  </div>
              }
                classNames={{
                  base: [
                    "before:bg-neutral-400 dark:before:bg-white",
                  ],
                  content: [
                    "py-2 px-4 shadow-xl",
                    "text-black bg-gradient-to-br from-white to-neutral-200",
                  ],
                }}
              >
                <span className={classes.span}>
                  <a href={url}>
                    <img src={tierImg} alt={tierImg} className={classes.img}/>
                  </a>
                </span>
              </Tooltip>
            </div>
          );
        }
      }

      gridLists.push(
        <div className={classes["grid-list"]} key={i}>
          {gridListItems}
        </div>
      );
    }
  
    return (
      <div className={classes["grid-container"]}>
        {gridLists}
      </div>
    );
}