import Description from "../../components/Main/Description"
import classes from "./MainPage.module.scss"
import Main from "../../components/Main/Main"
import MainDescription from "../../components/Main/MainDescription"
import { getNickname } from "../../api/nicknameAPI";
import { useEffect } from "react";


const items = [
    {
        "title": '코드·통계',
        "description": ['내 코드 관리', '난이도부터 분류까지'],

    },
    {
        "title": '코드·기록',
        "description": ['내 코드 기록', '성장의 기쁨까지'],

    },                                                                                                                                                                                         
    {
        "title": '커뮤니티',
        "description": ['모각코·모함코', '실시간 피드백까지'],
    },
]

export default function MainPage() {
  
useEffect(() => {
  const fetchData = async () => {
    // nickname 가져오기
    const results = await getNickname();
    const nickname = results;
    localStorage.setItem("nickname", nickname);
  };

  fetchData();
}, [])


	return (
    <div className={classes.page}>
      <div className={classes.mainPage}>
        
        <div className={classes.main}>
          <Main/>
        </div>
        <div  className={classes.mainDescription}>
          <MainDescription/>
        </div>

        <div className={classes.descriptions}>
          <div>
            <Description items={items[0]} />
          </div>

          <div className={classes.description_right}>
            <Description items={items[1]} />
          </div>
          
          <div>
            <Description items={items[2]} />
            {/* <img
                src={'../../assets/tier/level_Bronze.png'}
                className={classes.description_image}
              /> */}
          </div>
        </div>

      </div>
    </div>
  )
}
