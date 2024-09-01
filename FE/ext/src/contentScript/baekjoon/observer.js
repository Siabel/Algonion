
import { baekjoonInfo } from "./storage.js";
import { parsingResultTableList, parsingCorrectResultTable } from "./submission.js";
import { saveData, uploadData} from "./storage.js";

export let resultTableList = []

export function mutationObserver(lastResultCategory) {
  	let status = document.querySelector('.result-text').dataset.color;

    const observer = new MutationObserver((mutations) => {	
		status = document.querySelector('.result-text').dataset.color;
	
		resultTableList = parsingResultTableList(document);
	
		if (status !== "wait" && status !== "compile" && status !== "judging") {
			resultTableList = parsingResultTableList(document);
			const correctResultTable = parsingCorrectResultTable(resultTableList);

			saveData(correctResultTable);
			
			console.log("Judging Complete!!");

			observer.disconnect();
		}
    });

    const mutationObserverOption = {
      attributes: true,
      characterData: true,
    };

	if (status === "wait" || status === "compile" || status === "judging") {
	    observer.observe(lastResultCategory, mutationObserverOption);
	}
	else {
	    const resultTableList = parsingResultTableList(document);
	    const correctResultTable = parsingCorrectResultTable(resultTableList);

	    saveData(correctResultTable);

	    console.log("already judged!!");
	}

	return
  }