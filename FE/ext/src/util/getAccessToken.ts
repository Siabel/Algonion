async function getAccessToken() {
    const res = await chrome.storage.sync.get(["access_token"]);
    const accessToken = res.access_token;
    return accessToken;
}
export default getAccessToken;