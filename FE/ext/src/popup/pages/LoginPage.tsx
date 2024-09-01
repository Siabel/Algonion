import getAsset from "../utils/getAsset";
import classes from "./LoginPage.module.scss";
function LoginPage() {

    return (
        <div className={classes.wrapper}>
            <img src={getAsset("logo/logo_dark.svg")} />
            <a href="http://algonion.store" target="_blank">로그인</a>
        </div>
    )
}
export default LoginPage;