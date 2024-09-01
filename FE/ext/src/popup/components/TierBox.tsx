import { Variants, motion } from 'framer-motion';
import getAsset from '../utils/getAsset';
import classes from './TierBox.module.scss';

const tierDic: { [key: number]: string } = {
  0: 'unranked',
  1: 'bronze',
  2: 'silver',
  3: 'gold',
  4: 'platinum',
  5: 'diamond',
  6: 'master',
};
export function TierBox(props: { item: Variants | undefined; nickname?: string; tier: number }) {
  return (
    <motion.div className={classes.box} variants={props.item}>
      {/* <img src={getAsset('tier/star_master.svg')} alt="tier-master" /> */}
      <motion.img
        src={getAsset(`tier/star-${tierDic[props.tier]}.svg`)}
        initial={{ scale: 0, rotate: -180 }}
        animate={{ rotate: 0, scale: 1 }}
        whileHover={{ scale: 1.2, rotate: 90 }}
        transition={{
          type: 'spring',
          stiffness: 260,
          damping: 20,
        }}
      />
      <div className={classes.text}>
        <p className={classes.title}>티어</p>
        <div className={classes.content}>
          <p className={classes.username}>{props?.nickname}</p>
          <p className={classes.tier}>{tierDic[props.tier]}</p>
        </div>
      </div>
    </motion.div>
  );
}
