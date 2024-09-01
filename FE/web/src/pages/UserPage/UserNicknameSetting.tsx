import { useCallback, useState } from "react";
import classes from "./UserNicknameSetting.module.scss";
import { axiosApi, axiosApi as axiosAuthApi } from "../../utils/instance";

const validateLength = (nickname: string): boolean => {
  return nickname.length >= 2 && nickname.length <= 20;
};

const validateNickname = (nickname: string): boolean => {
  const regex = /^[가-힣|a-z|A-Z|0-9]+$/;
  return regex.test(nickname.toLowerCase());
};

const validateDuplicate = async (nickname: string): Promise<boolean> => {
  const { data } = await axiosAuthApi().get("/v1/user/nickname", {
    params: {
      nickname,
    },
  });
  const { result } = data;
  return result;
};

function UserPage() {
  const [nickname, setNickname] = useState("");
  const [nicknameMsg, setNicknameMsg] = useState("");

  const onChangeNickname = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      const currNickname = e.target.value;
      setNickname(currNickname);

      const isValidLength = validateLength(currNickname);
      const isValidNickname = validateNickname(currNickname);

      if (!isValidLength) {
        setNicknameMsg("한글, 영문, 숫자 포함 20글자 이내로 입력해주세요");
      } else if (!isValidNickname) {
        setNicknameMsg("적절하지 않은 형식의 닉네임 입니다.");
      } else {
        setNicknameMsg("");
      }
    },
    []
  );

  const onSubmit: React.FormEventHandler<HTMLFormElement> = async (e) => {
    e.preventDefault();

    const isValidLength = validateLength(nickname);
    const isValidNickname = validateNickname(nickname);

    if (!isValidLength || !isValidNickname) {
      if (!isValidLength) {
        window.alert("닉네임의 길이를 확인해주세요");
      } else if (!isValidNickname) {
        window.alert("적절하지 않은 형식의 닉네임 입니다.");
      }
      return;
    }

    const isDuplicate = await validateDuplicate(nickname);
    if (!isDuplicate) {
      alert("이미 존재하는 닉네임입니다.");
      return;
    }

    try {
      await axiosAuthApi().put("/v1/user/nickname", { nickname });
      localStorage.setItem("nickname", nickname);
      alert("닉네임이 변경되었습니다!");
    } catch (err) {
      console.log(err, 'error');
    }
  };

  return (
    <div className={classes.page}>
      <div className={classes.textbox}>
        <p>
          <b>알고니온</b>에서 사용할
        </p>
        <p>닉네임을 변경합니다.</p>
      </div>


      <form onSubmit={onSubmit}>
        <div className={classes.formbox}>
          <input
            type="text"
            placeholder="변경할 닉네임을 입력해주세요"
            value={nickname}
            onChange={onChangeNickname}
          />
          {nicknameMsg && <p className={classes.message}>{nicknameMsg}</p>}

          <div className={classes.buttonbox}>
            <button type="submit">확인</button>
          </div>
        </div>
      </form>
    </div>
  );
}

export default UserPage;
