import { default as axios } from 'axios';
const api = import.meta.env.VITE_BACKEND;
console.log('[ALGONION] 프로그래머스 문제 페이지 입니다');
const btnSubmit = document.querySelector('#submit-code');
const modal = document.querySelector('.modal') as HTMLDivElement;
const modalMutationOption = {
  childList: true,
};

const parseData = () => {
  const problemCategory = [(document.querySelector('.breadcrumb > li:nth-child(2) > a') as HTMLAnchorElement).innerText];
  const problemNum = (document.querySelector('div.main > div.lesson-content') as HTMLDivElement).getAttribute(
    'data-lesson-id',
  );
  const problemLevel = (document.querySelector('body > div.main > div.lesson-content') as HTMLDivElement).getAttribute(
    'data-challenge-level',
  );
  const problemTitle = (document.querySelector('body > div.main > div.lesson-content') as HTMLDivElement).getAttribute(
    'data-lesson-title',
  );
  const language = (document.querySelector('div.editor > ul > li.nav-item > a') as HTMLAnchorElement).getAttribute(
    'data-language',
  );
  const submissionCode = (document.querySelector('textarea#code') as HTMLTextAreaElement).value;

  const passedTestCase = document.querySelectorAll('td.result.passed').length;
  const failedTestCase = document.querySelectorAll('td.result.failed').length;
  const url = window.location.href;

  return {
    problemNum,
    problemLevel,
    problemTitle,
    language,
    submissionCode,
    url,
    problemCategory,
  };
};

const modalMutationObserver = new MutationObserver((mutations) => {
  if (!mutations.length) return;
  const modalTitle = (document.querySelector('.modal-title') as HTMLHeadingElement).innerHTML;
  const isSuccess = modalTitle.includes('정답');
  const data = parseData();
  if (!isSuccess) return;
  console.log('보낼준비');
  console.log(JSON.stringify(data));
  chrome.storage.sync.get(['access_token'], (res) => {
    axios
      .post(`${api}/v1/solved-problems/programmers`, data, { headers: { Authorization: `Bearer ${res.access_token}` } })
      .then((res) => {
        console.log('[ALGO] 업로드 성공');
      })
      .catch((e) => console.log(e));
  });

  modalMutationObserver.disconnect();
});

const isProgrammersLoggedIn = btnSubmit !== null;
if (isProgrammersLoggedIn) {
  btnSubmit.addEventListener('click', () => {
    modalMutationObserver.observe(modal, modalMutationOption);
  });
}
