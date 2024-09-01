import { Variants, motion } from 'framer-motion';
import classes from './StreakBox.module.scss';
import { useEffect, useState } from 'react';
export function StreakBox(props: { item: Variants | undefined, streak: boolean[] }) {
  const [day, setDay] = useState(0);
  useEffect(() => {
    const trueCnt = props.streak.reduce((acc, currentValue) => currentValue ? acc + 1 : 0, 0);
    setDay(trueCnt);
  }, [props.streak]);
  return (
    <motion.div className={classes.box} variants={props.item}>
      <p className={classes.title}>연속</p>
      <p className={classes.continuous}>
        <strong>{day}일 째🔥</strong>
      </p>
      <div className={classes.wrapper}>
        <ul className={classes.container}>
          {props.streak.map((v, i) => <li key={i} className={`${classes.streak} ${v ? classes.active : ''}`}></li>)}
        </ul>
        {/* <a className={classes.more} href={import.meta.env.VITE_ALGONION}>
          더보기
        </a> */}
      </div>
    </motion.div>
  );
}
export default StreakBox;
