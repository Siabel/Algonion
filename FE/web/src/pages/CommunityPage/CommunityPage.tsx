import RoomList from '../../components/Community/RoomList.tsx'

import classes from "./CommunityPage.module.scss"


function CommunityPage() {
	return (
    <div className={classes.page}>
      <div className={classes.CommunityPage}>
        <RoomList/>
      </div>
    </div>
  )
}
  
export default CommunityPage