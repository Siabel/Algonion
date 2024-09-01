
const isLoggedIn = () => {
  const token = localStorage.getItem("access_token");

  // 토큰이 존재하지 않거나 유효하지 않으면 false 반환
  if (!token) {
    return false;
  }

  // 토큰이 존재하고 유효하면 true 반환
  return true;
};

// const hasNickname = (): boolean => {
//   // 서버 API 호출하여 사용자 정보 가져오기
//   // ...

//   // 사용자 정보에서 닉네임 추출
//   const nickname = // ...

//   // 닉네임이 존재하면 true, 아니면 false 반환
//   return !!nickname;
// };