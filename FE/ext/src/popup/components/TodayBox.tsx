import { Variants, motion } from 'framer-motion';
import classes from './TodayBox.module.scss';
export function TodayBox(props: { item: Variants | undefined, problemCnt: number }) {
  return (
    <motion.div className={classes.box} variants={props.item}>
      <p className={classes.title}>오늘</p>
      <div className={classes.solved}>
        <strong className={classes.count1}>{props.problemCnt}</strong>
        <span className={classes.count2}>문제</span>
      </div>
    </motion.div>
  );
}
export default TodayBox;
