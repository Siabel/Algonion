// src/Tiptap.jsx
import {Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, Button, useDisclosure} from "@nextui-org/react";
import { useEditor, EditorContent, FloatingMenu, BubbleMenu } from '@tiptap/react';
import StarterKit from '@tiptap/starter-kit';
import dayjs from "dayjs";

import classes from "./CodeLogMemo.module.scss";

import { useEffect, useState } from "react";
import { putCodeLogMemo, shareCodeLog } from "../../api/codeLogAPI.ts";

// define your extension array
const extensions = [
  StarterKit,
]

interface MemoProps {
  problemId: string,
  memo: string,
}


const Tiptap = (props: MemoProps) => {
  const [memo, setMemo] = useState(props.memo);

  if (!memo) {
    // setMemo('<h1>📝메모</h1>')
    setMemo('<h1>📝메모</h1><p></p><h2>- 문제 요약</h2><p></p><p></p><h2>- 사용한 알고리즘</h2><ul><li></li><li></li></ul><h2>- 풀이</h2><p></p><p></p>')
    }
  
  useEffect(() => {
    // console.log('props', props.memo)
    setMemo(props.memo)
  }, [props.memo])
  
  // 저장 1
  const editor = useEditor({
    extensions,
    content: memo,
    onUpdate({ editor }) {
      // 내용이 업데이트 되었을 때
      const html = editor.getHTML();
      setMemo(html)
      // console.log(html);
    },
  })

  useEffect(() => {
    if (editor) {
      editor.commands.setContent(memo)
    }
  }, [memo])
    
  // 저장 2
  const [now, setNow] = useState('');
  const [isSaved, setIsSaved] = useState(false);
  
  async function putAxios(){
    setIsSaved(true);

    const time = dayjs().format("YY.MM.DD HH:mm:ss");
    setNow(time);

    const json = {"memo": memo};
    await putCodeLogMemo(props.problemId, json);
    // setMemo(newMemo)
    console.log('memo', memo)
    editor!.commands.setContent(memo)
  }

  // modal - 노션 내보내기
  const {isOpen, onOpen, onClose} = useDisclosure();

  const handleOpen = () => {
    onOpen();
  }

  const [apiKey, setAPIKey] = useState('');
  const [dbid, setDBId] = useState('');

  async function shareAxios(){
    // const problemId = window.location.href.split('/')[5];
    console.log(props.problemId)
    const json = {"apiKey": apiKey, "dbId": dbid, "solvedProblemId": Number(props.problemId)};
    await shareCodeLog(json);
  }
  const share = () => {
      shareAxios()
      onClose()
  }

  return (
    <div>
      <div className={classes.editor}>
        <EditorContent editor={editor} />
        <FloatingMenu editor={editor ? editor : undefined}> </FloatingMenu>
        <BubbleMenu editor={editor ? editor : undefined}> </BubbleMenu>
      </div>
      <div className={classes.buttons}>
        {isSaved && <p>{now} 저장됨</p>}
      <Button color="secondary" variant="bordered" onPress={() => putAxios()}>
        SAVE
      </Button>
      <Button color="secondary" variant="solid" onPress={() => handleOpen()}>
        NOTION
      </Button>
      </div>
      
      <Modal backdrop="opaque" isOpen={isOpen} onClose={onClose}>
        <ModalContent>
          {(onClose) => (
            <>
              <ModalHeader className="flex flex-col gap-1">Notion 으로 내보내기</ModalHeader>
              <ModalBody>
              <div className={classes.formbox}>
                <form>
                  <input
                    type="text"
                    placeholder="Notion API Key"
                    onChange={(e) => setAPIKey(e.target.value)}
                  />
                  <input
                    type="text"
                    placeholder="Notion DB ID"
                    onChange={(e) => setDBId(e.target.value)}
                  />
                </form>
              </div>
              </ModalBody>
              <ModalFooter>
                <Button color="secondary" variant="light" onPress={onClose}>
                  Close
                </Button>
                <Button color="secondary" onPress={share}>
                  Share
                </Button>
              </ModalFooter>
            </>
          )}
        </ModalContent>
      </Modal>

    </div>
  )
}

export default Tiptap