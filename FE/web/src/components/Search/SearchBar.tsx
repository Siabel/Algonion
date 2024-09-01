import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import classes from './SearchBar.module.scss';

export default function SearchBar() {
  const [keyword, setKeyword] = useState("");
  const navigate = useNavigate(); 

  const onChange = (event: { target: { value: string } }) => {
    // 검색어 입력 값 업데이트
    setKeyword(event.target.value);
  };
  
  const goSearch = () => {
    navigate(`/search?q=${keyword}`);
    window.location.reload();
  }

  const handleKeyPress = (event: { key: string; }) => {
    // Check if the pressed key is 'Enter'
    if (event.key === 'Enter') {
      goSearch();
    }
  };

  return (
    <div className={classes.search}>
      <input type="text" placeholder="유저의 닉네임을 검색해주세요!"
      onChange={onChange}
      onKeyDown={handleKeyPress} />
      <button type="button" onClick={goSearch}>
      <img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png" />
      </button>
    </div>
  );
}

