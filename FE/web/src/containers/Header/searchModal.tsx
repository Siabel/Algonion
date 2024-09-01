import { useEffect, useState } from "react";
import {
  Modal,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Button,
  useDisclosure,
  Link,
} from "@nextui-org/react";
import getAsset from "../../utils/getAsset";
import { MyInput } from "./MyInput";
import classes from "./searchModal.module.scss";
import { axiosAuthApi } from "../../utils/instance";

interface SearchResults {
  nickname: string;
  tier: string;
  userScore: number;
}

export default function SearchModal() {
  const { isOpen, onOpen, onOpenChange } = useDisclosure();
  const [keyword, setKeyword] = useState("");
  const [searchResults, setSearchResults] = useState<SearchResults[]>([]);
  useEffect(() => {
    // 검색창 입력값이 바뀌면 타이머 등록
    const delayDebounceTimer = setTimeout(() => {
      axiosAuthApi()
        .get(`/v1/friendship/search?nickname=${keyword}`)
        .then(({ data }) => setSearchResults(data));
    }, 500); // 0.5초 지연시간
    // 이전에 설정한 타이머를 클리어하여 디바운스 취소
    return () => clearTimeout(delayDebounceTimer);
  }, [keyword]);
  const onChange = (event: { target: { value: string } }) => {
    setKeyword(event.target.value);
  };

  return (
    <div className="flex flex-col gap-2">
      <Link onPress={onOpen} color="foreground" className={classes.searchLink}>
        <img src={getAsset("search_icon.svg")} className={classes.searchLink} />
        유저검색
      </Link>
      {/* <Button onPress={onOpen}>Open Modal</Button> */}
      <Modal
        size="2xl"
        isOpen={isOpen}
        // z-index=200
        placement={"top"}
        onOpenChange={onOpenChange}
      >
        <ModalContent>
          {(onClose) => (
            <>
              <ModalHeader className="flex flex-col gap-1">
                유저의 닉네임을 검색해주세요!
              </ModalHeader>
              <ModalBody>
                <MyInput
                  onChange={onChange}
                  value={keyword}
                  isClearable
                  placeholder="search..."
                  radius="md"
                  size="md"
                  startContent={
                    <img
                      src={getAsset("search_icon.svg")}
                      className="text-zinc-500"
                    />
                  }
                />
                <ul>
                  {searchResults.map((v, i) => (
                    <li key={i}>
                      <Link href={`/profile/${v.nickname}`} color="foreground">
                        {v.nickname}
                      </Link>
                    </li>
                  ))}
                </ul>
              </ModalBody>
              <ModalFooter>
                <Button color="secondary" variant="light" onPress={onClose}>
                  Close
                </Button>
                <Button
                  href={`/search?q=${keyword}`}
                  color="secondary"
                  as={Link}
                >
                  Search
                </Button>
              </ModalFooter>
            </>
          )}
        </ModalContent>
      </Modal>
    </div>
  );
}
