
import {Table, TableHeader, TableColumn, TableBody, TableRow, TableCell, getKeyValue} from "@nextui-org/react";
import { useEffect, useState } from "react";
import { getFriendList } from "../../api/friendListAPI";
import classes from './UserFriendList.module.scss'

type item = { count: number; nickname: string; tier: string ; userScore: number;}[]

const columns = [
  {
    key: "nickname",
    label: "닉 네 임",
  },
  {
    key: "tier",
    label: "티  어",
  },
  {
    key: "userScore",
    label: "포 인 트",
  },
  {
    key: "count",
    label: "푼 문제 수",
  },
];

function UserFriendList() {
  const [friendList, setFriendList] = useState<item>();
  useEffect(() => {
    async function getAxios(){
      let res = await getFriendList();
      setFriendList(res);
      // console.log(res)
    }
    getAxios()
  },[]);
  
  
  if (!friendList || friendList[0] == undefined) {
    return (
      <div>친구를 추가해주세요!</div>
      );
  }

  return (
    <div>
      <Table aria-label="Example table with dynamic content">
      <TableHeader columns={columns}>
        {(column) => <TableColumn key={column.key}>{column.label}</TableColumn>}
      </TableHeader>
      <TableBody items={friendList}>
        {(item) => (
          <TableRow key={item.nickname} className={classes.cursor} onClick={() => window.location.href = `/profile/${item.nickname}`}>
            {(columnKey) => <TableCell>{getKeyValue(item, columnKey)}</TableCell>}
          </TableRow>
        )}
      </TableBody>
    </Table>
    </div>
  )
}
  
export default UserFriendList

