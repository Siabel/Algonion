// https://www.framer.com/motion/scroll-animations/
import { motion } from "framer-motion";
import classes from "./Description.module.scss";

// type items = { title: string, description: string[] };
type items = { items: { 
  title: string; 
  description: string[];
} };

function Description(props: items) {
  return (
    <div className={classes.component}>
      <motion.div
        initial={{ opacity: 0, y: 50 }}
        whileInView={{ opacity: 1, y: 0 }}
        viewport={{ once: false }}
        transition={{
          ease: "easeInOut",
          duration: 0.5,
          y: { duration: 0.5 },
        }}
      >
        <p className={classes.title}> {props.items.title}</p>
      </motion.div>

      <motion.div
        className={classes.description}
        initial={{ opacity: 0, y: 50 }}
        whileInView={{ opacity: 1, y: 0 }}
        viewport={{ once: false }}
        transition={{
          ease: "easeInOut",
          duration: 0.5,
          y: { duration: 0.5 },
        }}
      >
        <p>{props.items.description[0]}</p>
        <p>{props.items.description[1]}</p>
        
      </motion.div>
    </div>
  );
}

export default Description;
