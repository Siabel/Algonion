import {
  Table,
  TableHeader,
  TableColumn,
  TableBody,
  TableRow,
  TableCell,
  getKeyValue,
} from "@nextui-org/react";

import classes from "./SearchResults.module.scss";
import { useEffect, useState } from "react";
import { axiosAuthApi } from "../../utils/instance";

interface User {
  tier: string;
  nickname: string;
  userScore: number;
}

const columns = [
  {
    key: "nickname",
    label: "닉네임",
  },
  {
    key: "tier",
    label: "티어",
  },
  {
    key: "userScore",
    label: "점수",
  },
];

function SearchResults() {
  const params = new URLSearchParams(location.search);
  const query = params.get("q");
  const [rows, setRows] = useState<User[]>([]);

  useEffect(() => {
    axiosAuthApi()
      .get(`/v1/friendship/search?nickname=${query}`)
      .then(({ data }) => {
        setRows(data);
        console.log(data);
      });
  }, [query]);
  return (
    <div>
      <h2 className={classes.result}>"{query}"의 검색 결과입니다.</h2>
      <Table aria-label="Example table with dynamic content">
        <TableHeader columns={columns}>
          {(column) => (
            <TableColumn key={column.key}>{column.label}</TableColumn>
          )}
        </TableHeader>
        <TableBody items={rows} emptyContent={"일치하는 사용자가 없습니다."}>
          {(item) => (
            <TableRow key={item.nickname}>
              {(columnKey) => (
                <TableCell className={classes.cursor} onClick={() => window.location.href = `/profile/${item.nickname}`}>{getKeyValue(item, columnKey)}</TableCell>
              )}
            </TableRow>
          )}
        </TableBody>
      </Table>
    </div>
  );
}

export default SearchResults;
