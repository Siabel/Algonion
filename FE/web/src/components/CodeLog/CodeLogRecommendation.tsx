import {Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, Button, useDisclosure, Card, CardHeader, CardBody} from "@nextui-org/react";
import { Key, useEffect, useState } from "react";

import { getRecommendation } from "../../api/recommendationAPI";
// import CodeLogRecommendationItem from "./CodeLogRecommendationItem.tsx"
import classes from "./CodeLogRecommendation.module.scss"

type item = { siteName: string; problemNum: string; problemTitle: string ; problemLevel: string; url: string; }[]


export default function CodeLogRecommendation() {
  const {isOpen, onOpen, onClose} = useDisclosure();

  const [data, setData] = useState<item>();
  
  async function getAxios(){
    let res =await getRecommendation();
    setData(res);
    // console.log(res);
  }

  useEffect(() => {
    getAxios()
  }, []);

  function onRefresh() {
    getAxios()
  };
  
  if (!data) {
    return (
      <div> 로딩 중 입니다.</div>
      );
  }

  return (
    <>
      <div className="flex flex-wrap gap-3">
          <Button  
            // key={'p'}
            variant="flat" 
            color="secondary" 
            onPress={() => onOpen()}
            className="capitalize"
          >
            문제 추천
          </Button>
      </div>
      <Modal backdrop={'opaque'} isOpen={isOpen} onClose={onClose}>
        <ModalContent>
          {(onClose) => (
            <>
              <ModalHeader className="flex flex-col gap-1">이 문제는 어때요?</ModalHeader>
              <ModalBody>

              <div className={classes.items}>
                {data.map((item: { siteName: string; problemNum: string; problemTitle: string; problemLevel: string; url: string;}, index: Key) => (
                    <Card key={index} className={classes.item}>
                    <CardHeader className="justify-between">
                        <div className="flex gap-5 ms-2 ">
                        <div className="flex flex-col gap-1 items-start justify-center">
                            <h4 className="text-xs font-semibold leading-none text-default-600">{item.siteName} - {item.problemNum}</h4>
                            <h5 className="text-m tracking-tight  text-default-400">{item.problemTitle}</h5>
                        </div>
                        </div>
                        <div className="flex gap-5 me-2">
                        <div className="flex flex-col gap-1 items-start justify-center">
                            <h6 className="text-xs text-purple-400 font-semibold leading-none text-default-600">{item.problemLevel}</h6>
                        </div>
                        </div>
                    </CardHeader>
                    <CardBody className="px-3 py-0 text-small text-default-400">
                    <a
                        className="mt-2 mb-2"
                        href={item.url}
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        <Button
                        size="lg"
                        variant="bordered"
                        fullWidth={true}
                        >
                        문제 풀러 가기!
                        </Button>
                    </a>
                    </CardBody>
                    </Card>
                ))}
                </div>

              </ModalBody>
              <ModalFooter>
                <Button color="secondary" variant="light" onPress={onClose}>
                  Close
                </Button>
                <Button color="secondary" onPress={onRefresh}>
                  Refresh
                </Button>
              </ModalFooter>
            </>
          )}
        </ModalContent>
      </Modal>
    </>
  );
}
