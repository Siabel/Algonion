import { findUsername, findResultUsername } from "./user.js";
import { mutationObserver } from "./observer.js"

const url = window.location.href;

console.log("[E1I5] 백준 문제 페이지 입니다");

const username = findUsername();
const resultUsername = findResultUsername(url);

// if (username === resultUsername) {
    const lastResultCategory = document.querySelector('.result-text');

    const status = document.querySelector('.result-text').dataset.color;
    console.log(status);

    mutationObserver(lastResultCategory);

// }