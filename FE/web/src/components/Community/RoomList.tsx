import { Input, Button, Link } from "@nextui-org/react";
import classes from "./RoomList.module.scss";
import { ChangeEvent, useEffect, useState } from "react";

export default function RoomList() {
  const [roomName, setRoomName] = useState("");
  const [isInvalid, setIsInvalid] = useState(false);

  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setIsInvalid(false);
    setRoomName(event.target.value);
  };
  const handleClick = (e: React.MouseEvent) => {
    if (!roomName.length) {
      e.preventDefault();
      setIsInvalid(true);
      return;
    }
  };

  useEffect(() => {}, []);

  return (
    <div className={classes.container}>
      <div className={classes.buttonContainer}>
        <h1 className={classes.communityTitle}>커뮤니티</h1>
        <h2 className="text-foreground-500">
          방을 생성하여 다른 사람들의 도움을 받아보세요.
        </h2>
        <br />
        <Input
          type="text"
          label="방 제목"
          isInvalid={isInvalid}
          errorMessage={isInvalid && "방 이름을 입력해 주세요."}
          onChange={handleChange}
        />
        <br />
        <Button
          as={Link}
          color="secondary"
          className="w-full"
          onClick={handleClick}
          href={`/collaborative/${roomName}`}
        >
          참가
        </Button>
      </div>
      <div className={classes.roomContainer}></div>
    </div>
  );
}
