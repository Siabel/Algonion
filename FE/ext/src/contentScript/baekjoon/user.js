import { isNull, isEmpty } from "./util.js";

// 로그인된 유저의 이름을 가져오는 함수
export function findUsername() {
    const el = document.querySelector('a.username');
    if (isNull(el)) return null;

    const username = el?.innerText?.trim();
    if (isEmpty(username)) return null;
    return username;
  }

// 문제 제출 페이지에서의 유저의 이름을 가져오는 함수
export function findResultUsername(baekjoonUrl) {
    if (baekjoonUrl.startsWith('https://www.acmicpc.net/status?'))
        return baekjoonUrl.split("user_id=")[1].split("&")[0];
    return null;
}
