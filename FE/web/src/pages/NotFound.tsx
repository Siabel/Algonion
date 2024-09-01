import { Button, Link } from "@nextui-org/react";
import classes from './NotFound.module.scss'

function NotFound() {
  // 꾸미고 싶으면 예쁘게 꾸며주세요 ><
  return (
    <div className={classes.page}>
      <div className={classes.textbox}>
      <h1>404</h1>
      <h2>페이지를 찾을 수 없습니다.</h2>
      <p>페이지가 존재하지 않거나 사용할 수 없는 페이지입니다.</p>
      <p>입력하신 주소가 정확한지 다시 한 번 확인해주세요.</p>
      </div>
      <div className={classes.formbox}>
      <Button className={classes.button} onClick={() => history.go(-1)} as={Link}>
        이전 페이지
      </Button>
      <Button className={classes.button} href="/" color="secondary" as={Link}>
        홈으로 가기
      </Button>
      </div>
    </div>
  );
}

export default NotFound;
