import { isNull } from "./util";

export async function saveObjectInLocalStorage(obj) {
    return new Promise((resolve, reject) => {
        // chrome.storage.local.clear() 
        try {
            chrome.storage.local.set(obj, function () {
                resolve();
            });
        } catch (ex) {
            reject(ex);
        }
    });
}


export async function getObjectFromLocalStorage(key) {
    return new Promise((resolve, reject) => {
        ''
        try {
            chrome.storage.local.get(key, function (value) {
                resolve(value);
            });
        } catch (ex) {
            reject(ex);
        }
    });
}


export async function updateProblemData(problemNum, obj) {
    return getObjectFromLocalStorage('swea').then((data) => {
        // console.log(data)
        if (!isNull(data)) data = { swea: {} };
        // if (!data.swea) data.swea = {};
        data.swea[problemNum] = { ...data[problemNum], ...obj, save_date: Date.now() };
        
        saveObjectInLocalStorage(data);
        console.log(data)
        return data;
    });
}


export async function getProblemData(problemNum) {
    // console.log('problemNum:', problemNum)
    return getObjectFromLocalStorage('swea')
    .then((data) => {        
        return data.swea[problemNum]
    });
}



export async function removeObjectFromLocalStorage(keys) {
    return new Promise((resolve, reject) => {
        try {
            chrome.storage.local.remove(keys, function () {
                resolve();
            });
        } catch (ex) {
            reject(ex);
        }
    });
}

