import { useState } from 'react';
import UserInformation from './UserInformation.tsx'
import UserDashboard from './UserDashboard.tsx'
import UserFriendList from './UserFriendList.tsx'
import classes from "./UserBar.module.scss"

function UserBar() {
    const [selectedTab, setSelectedTab] = useState('userinfo');

    const TabClick = (tab: 'userinfo' | 'userDashboard' | 'userRecommendation' | 'friendList') => {
      setSelectedTab(tab);
    };

    return (
      <>
        <div className={classes.userBar}>
          <button
            className={selectedTab === 'userinfo' ? classes.activeTab : ''}
            onClick={() => TabClick('userinfo')}
            >
            메인정보
          </button>
          <button
            className={selectedTab === 'userDashboard' ? classes.activeTab : ''}
            onClick={() => TabClick('userDashboard')}
            >
            대시보드
          </button>
          <button
            className={selectedTab === 'friendList' ? classes.activeTab : ''}
            onClick={() => TabClick('friendList')}
            >
            친구정보
          </button>
            </div>
          <hr />
          <div className={classes.tab}>
          {selectedTab === 'userinfo' && 
            <UserInformation/>
          }
          {selectedTab === 'userDashboard' && 
          <UserDashboard/>
          }
          {selectedTab === 'friendList' && 
          <UserFriendList/>
          }
        </div>
      </>
    )
}
  
export default UserBar