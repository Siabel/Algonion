// import {GoogleLogin} from "@react-oauth/google";
// import {GoogleOAuthProvider} from "@react-oauth/google";
import classes from "./Google.module.scss";
const url = import.meta.env.VITE_BACK_END + "/oauth2/authorization/google";

const GoogleLoginButton = () => {
  return (
    <div className={classes.gSignInWrapper}>
      {/* <span className={classes.label}>Sign in with:</span> */}
      <a
        href={url}
        className={`${classes["google-button"]} ${classes.customGPlusSignIn}`}
      >
        <span className={classes.icon}></span>
        <span className={classes.buttonText}>구글 로그인</span>
      </a>
    </div>
  );
};

export default GoogleLoginButton;

// import {GoogleLogin} from "@react-oauth/google";
// import {GoogleOAuthProvider} from "@react-oauth/google";

// const GoogleLoginButton = () => {
//     const clientId = 'clientID'
//     return (
//         <>
//             <GoogleOAuthProvider clientId={clientId}>
//                 <GoogleLogin
//                     onSuccess={(res) => {
//                         console.log(res);
//                     }}
//                     onFailure={(err) => {
//                         console.log(err);
//                     }}
//                 />
//             </GoogleOAuthProvider>
//         </>
//     );
// };

// export default GoogleLoginButton
