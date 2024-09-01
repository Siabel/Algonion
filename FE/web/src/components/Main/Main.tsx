import { motion } from 'framer-motion';
import classes from "./Main.module.scss";
import getAsset from '../../utils/getAsset';
import { useEffect, useState } from 'react';
const getLoginUrl = (platform: string): string => `${import.meta.env.VITE_BACK_END}/oauth2/authorization/${platform}`;

const sloganVariants = {
  hidden: { y: 50, opacity: 0 },
  visible: {
    y: 0,
    opacity: 1,
    transition: {
      duration: 1,
    }
  }
};

const loginVariants = {
  hidden: { y: 50, opacity: 0 },
  visible: {
    y: 0,
    opacity: 1,
    transition: {
      duration: 1.3,
    }
  }
};


export default function Main() {
  const [isLogin, setIsLogin] = useState(localStorage.getItem('access_token'));
  useEffect(() => {
    setIsLogin(localStorage.getItem('access_token'))
    console.log(isLogin)
  }, [isLogin])

  return (
    <div className={classes.mainContainer}>
      <motion.div
        className={classes.slogunContainer1}
        variants={sloganVariants}
        initial="hidden"
        animate="visible">
        Algonion
      </motion.div>
      <motion.div
        className={classes.slogunContainer2}
        variants={sloganVariants}
        initial="hidden"
        animate="visible">
        모든 코드, 모두 모아
      </motion.div>
      <motion.div
        variants={loginVariants}
        initial="hidden"
        animate="visible">
      {!isLogin && <div className={classes.buttonContainer}>
        <a href={getLoginUrl("google")} className={classes.googleButton}>
          <img src={getAsset('social_login/googleSymbol.png')} alt="google-login" />
          <p>Google로 시작하기</p>
        </a>
        <a href={getLoginUrl("kakao")} className={classes.kakaoButton}>
          <img src={getAsset('social_login/kakaoSymbol.png')} alt="kakao-login" />
          <p>Kakao로 시작하기</p>
        </a>
      </div>
      }
      </motion.div>

    </div>
  )
}