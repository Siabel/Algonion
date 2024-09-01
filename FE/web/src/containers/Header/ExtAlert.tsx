import CloseIcon from "../../components/svg/CloseIcon";
import classes from "./ExtAlert.module.scss";
import { Button, Link } from "@nextui-org/react";
function ExtAlert() {
  const isInstalled = localStorage.getItem("isInstalled") ? true : false;
  //css 스타일 맘대로 수정해도 됩니다
  function handleClick() {
    localStorage.setItem("isInstalled", "true");
  }
  return (
    <>
      {!isInstalled && (
        <div className={classes.alert}>
          <div className={classes.center}>
            <span className="pr-3">
              🚀 chrome 웹 스토어에서 Algonion 확장앱을 설치해주세요!
            </span>
            <Button
              radius="full"
              className="bg-gradient-to-tr from-pink-500 to-yellow-500 text-white shadow-lg"
              as={Link}
              href="https://chromewebstore.google.com/detail/algonion/cgkmeoeahekcjfofncekofbjkhbangoe"
              target="_blank"
            >
              설치하러 가기
            </Button>
          </div>

          <button className={`${classes.close} pr-3`} onClick={handleClick}>
            <CloseIcon />
          </button>
        </div>
      )}
    </>
  );
}
export default ExtAlert;
