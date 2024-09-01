import {convertResultTableHeader, convertResultTableList, isNull, isEmpty} from "./util.js";
import { baekjoonInfo } from "./storage.js";

// 문제 제출 페이지에서 테이블을 가져오는 함수
export function parsingResultTableList(doc) {
    const table = doc.getElementById('status-table');
    if (table === null || table === undefined || table.length === 0) return null;
    
    const headers = Array.from(table.rows[0].cells, (x) => convertResultTableHeader(x.innerText.trim()));

    const list = [];
    for (let i = 1; i < table.rows.length; i++) {
      const row = table.rows[i];
  
      const cells = Array.from(row.cells, (x, index) => convertResultTableList(x, headers[index]));

      let obj = {};
      obj.elementId = row.id;
      for (let j = 0; j < headers.length; j++) {
        obj[headers[j]] = cells[j];
      }
      obj = { ...obj, ...obj.result, ...obj.problemId};
      list.push(obj);
    }
    return list;
}

// 문제 제출 페이지에서 맞은 제출 테이블을 가져오는 함수
export function parsingCorrectResultTable(table) {
    // console.log(table);
    if (isNull(table)) return null;
    
    const list = [];
    // console.log(list);
    for (let row in table) {
        if (table[row].result == "맞았습니다!!") {
            // console.log(table[row].result);
            list.push(table[row]);
        } 
    }
    // console.log(list);
    return list[0];
}


// 제출 코드를 가져오는 함수 
export async function getSubmissionCode(submissionId) {
    const res = await fetch(`https://www.acmicpc.net/source/download/${submissionId}`, {
        method: 'GET',
    });
    // console.log(res)
    const code = await res.text();
    console.log(code)
    // baekjoonInfo.submissionCode = code;
    return code
}

// 문제의 정보를 가져오는 함수
export async function getProblem(problemNum) {
    const res = await fetch(`https://www.acmicpc.net/problem/${problemNum}`, {
        method: 'GET',
    })
    
    const doc = new DOMParser().parseFromString(await res.text(), "text/html");
    try {
        const title = doc.querySelector("title").innerText.split(": ")[1];
        const categories = doc.querySelector(".spoiler-list").querySelectorAll("li");
        const category = Array.from(categories).map((category) => category.innerText.trim());
        return [title, category]
    }

    catch (err) {
        // console.log(err);
        return [null, null]
    }

}

// 문제의 정보를 가져오는 함수
export async function getProblemLevel(problemNum) {
    const res = await fetch(`https://www.acmicpc.net/problem/${problemNum}`, {
        method: 'GET',
    })
    
    const doc = new DOMParser().parseFromString(await res.text(), "text/html");
    try {
        const level = doc.querySelector(".solvedac-tier").src.split("/tier/")[1].split(".svg")[0];
        return level
    }

    catch (err) {
        // console.log(err);
        return null
    }

}
