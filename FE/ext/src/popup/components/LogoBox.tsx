import { Variants, motion } from 'framer-motion';
import getAsset from '../utils/getAsset';
import classes from './LogoBox.module.scss';
export function logoBox(props: { item: Variants | undefined }) {
  return (
    <motion.a className={classes.box} variants={props.item} href={import.meta.env.VITE_ALGONION} target='_blank'>
      <img src={getAsset('logo/logo_dark.svg')} alt="logo" />
    </motion.a>
  );
}
export default logoBox;
