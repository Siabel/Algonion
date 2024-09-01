console.log("컨텐츠스크립트")
const accessToken = localStorage.getItem("access_token");
// chrome.runtime.sendMessage({ type: 'ALGONION_ACCESS_TOKEN', accessToken })
if (accessToken != null) {
    chrome.storage.sync.set({ "access_token": accessToken }).then(() => {
        console.log("Value is set");
    });
}
