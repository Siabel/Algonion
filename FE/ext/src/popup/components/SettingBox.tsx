import { Variants, motion } from 'framer-motion';
import classes from './SettingBox.module.scss';
import getAsset from '../utils/getAsset';
function SettingBox(props: { item: Variants | undefined }) {
  return (
    <motion.div variants={props.item} className={classes.box}>
      <img src={getAsset('icons/setting-2.svg')} width="32"></img>
    </motion.div>
  );
}
export default SettingBox;
