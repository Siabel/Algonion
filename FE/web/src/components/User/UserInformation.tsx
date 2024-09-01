import UserSolvedProblem from './UserSolvedProblem.tsx'
import UserStreak from './UserStreak.tsx'
import classes from "./UserInformation.module.scss"


function UserInfo() {
  return (
    <>
        <div className={classes.userSolvedProblem}>
          <UserSolvedProblem/>
        </div>
        <div className={classes.userStreak}>
          <UserStreak/>
        </div>
    </>
  )
}
  
export default UserInfo