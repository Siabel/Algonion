
import { useEffect, useLayoutEffect, useState } from 'react';
import './Popup.scss';
import UserPage from './pages/UserPage'
import LoginPage from './pages/LoginPage';
import axios from 'axios';

export const Popup = () => {
  const [isStorageLoading, setIsStorageLoading] = useState(true);
  const [accessToken, setAccessToken] = useState(null);
  const [response, setResponse] = useState();
  useEffect(() => {
    async function getToken() {
      const res = await chrome.storage.sync.get("access_token");
      const accessToken = res.access_token;
      setAccessToken(accessToken);
      setIsStorageLoading(false);
    }
    getToken();
  }, [])
  useEffect(() => {
    async function getUserData() {
      if (!accessToken) return;
      const { data } = await axios.get('https://algonion.store/api/v1/profile/ext', { headers: { "Authorization": `Bearer ${accessToken}` } });
      setResponse(data);
      console.log(data);
    }
    getUserData();
  }, [accessToken])

  if (accessToken === undefined) {
    return <LoginPage />
  }
  if (response) {
    return <UserPage data={response} />
  }
  return <></>

};

export default Popup;
