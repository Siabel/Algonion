import { getNickname } from './util';
import { getProblemData, removeObjectFromLocalStorage, updateProblemData } from './storage';
/**
 * 문제 내 데이터를 가져옵니다.
 * @param {string} problemNum 문제 번호
 * @returns {object} 문제 내 데이터
 */
let loader;

const currentUrl = window.location.href;


export function getSolvedResult() {
  return document.querySelector('div.popup_layer.show > div > p.txt')?.innerText.trim().toLowerCase() || '';
}

export async function parseCode() {
  const problemNum = document.querySelector('div.problem_box > h3').innerText.replace(/\..*$/, '').trim();
  const contestProbId = [...document.querySelectorAll('#contestProbId')].slice(-1)[0].value;
  updateTextSourceEvent();
  const code = document.querySelector('#textSource').value;
  await updateProblemData(problemNum, { code, contestProbId });
  
  return { problemNum, contestProbId };
}

/*
  cEditor 소스코드의 정보를 textSource에 저장하도록 하는 함수 입니다. 
*/
function updateTextSourceEvent() {
  document.documentElement.setAttribute('onreset', 'cEditor.save();');
  document.documentElement.dispatchEvent(new CustomEvent('reset'));
  document.documentElement.removeAttribute('onreset');
}

export async function parseData() {
  // const nickname = document.querySelector('#searchinput').value;

  // if (getNickname() !== nickname) return;

  // 문제 제목
  const problemTitle = document
    .querySelector('div.problem_box > p.problem_title')
    .innerText.replace(/ D[0-9]$/, '')
    .replace(/^[^.]*/, '')
    .substr(1)
    .trim();
  // 문제 난이도, 번호, 인덱스, 링크
  const problemLevel = document.querySelector('div.problem_box > p.problem_title > span.badge')?.textContent || 'Unrated';
  const problemNum = document.querySelector('body > div.container > div.container.sub > div > div.problem_box > p').innerText.split('.')[0].trim();
  const contestProbId = [...document.querySelectorAll('#contestProbId')].slice(-1)[0].value;
  const url = `${window.location.origin}/main/code/problem/problemDetail.do?contestProbId=${contestProbId}`;

  // 문제 언어, 메모리, 시간소요, 코드 길이
  const language = document.querySelector('#problemForm div.info > ul > li:nth-child(1) > span:nth-child(1)').textContent.trim();
  const memory = document.querySelector('#problemForm div.info > ul > li:nth-child(2) > span:nth-child(1)').textContent.trim().toUpperCase();
  const runtime = document.querySelector('#problemForm div.info > ul > li:nth-child(3) > span:nth-child(1)').textContent.trim();
  const codeLength = document.querySelector('#problemForm div.info > ul > li:nth-child(4) > span:nth-child(1)').textContent.trim();

  // 정답률
  const correctRate = document.querySelector("body > div.container > div.container.sub > div > div.problem_infobox2 > div.info > ul > li:nth-child(4) > span:nth-child(1)").textContent;

  // 제출날짜
  const submissionTime = document.querySelector('.smt_txt > dd').textContent.match(/\d{4}-\d{2}-\d{2} \d{2}:\d{2}/g)[0];
  const data = await getProblemData(problemNum);
  
    // 기존 문제 데이터를 로컬스토리지에 저장하고 코드 보기 페이지로 이동
  // await updateProblemData(problemNum, { level, contestProbId, link, language, memory, runtime, length});
    // console.error('소스코드 데이터가 없습니다.');
  // chrome.storage.local.clear()

  // console.log(data)
  const submissionCode = data.code;
  // console.log('파싱 완료', code);

  return { url, language, problemNum, problemLevel, problemTitle, runtime, submissionCode, memory, codeLength, correctRate ,submissionTime };
} 
