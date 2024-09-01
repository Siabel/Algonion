// import "./styles.scss";
import "./CollaborativeEditorPage.scss";
import CodeBlockLowlight from "@tiptap/extension-code-block-lowlight";
import Collaboration from "@tiptap/extension-collaboration";
import CollaborationCursor from "@tiptap/extension-collaboration-cursor";
import Document from "@tiptap/extension-document";
import Paragraph from "@tiptap/extension-paragraph";
import Placeholder from "@tiptap/extension-placeholder";
import Text from "@tiptap/extension-text";
import { EditorContent, useEditor } from "@tiptap/react";
import StarterKit from "@tiptap/starter-kit";
import { WebrtcProvider } from "y-webrtc";
import * as Y from "yjs";
import { lowlight } from "lowlight";
const ydoc = new Y.Doc();
const provider = new WebrtcProvider(window.location.pathname, ydoc, {
  signaling: ["wss://algonion.store/signal"],
});

function getRandomHexColor() {
  // 0부터 16777215 사이의 랜덤한 숫자를 생성합니다 (16777215는 #ffffff를 10진수로 표현한 값입니다).
  let randomColor = Math.floor(Math.random() * 16777215).toString(16);
  // 만약 생성된 숫자의 길이가 6자리보다 작다면 앞에 0을 추가하여 6자리로 만듭니다.
  while (randomColor.length < 6) {
    randomColor = "0" + randomColor;
  }
  // '#'를 추가하여 Hex 색상 코드로 변환하여 반환합니다.
  return "#" + randomColor;
}

function CollaborativeEditorPage() {
  const editor = useEditor({
    extensions: [
      Document,
      Paragraph,
      Text,
      StarterKit.configure({
        // The Collaboration extension comes with its own history handling
        history: false,
      }),
      Collaboration.configure({
        document: ydoc,
      }),
      CodeBlockLowlight.configure({
        lowlight,
      }),
      CollaborationCursor.configure({
        provider,
        user: {
          name: `${localStorage.getItem("nickname")}`,
          color: getRandomHexColor(),
        },
      }),
      Placeholder.configure({
        placeholder:
          "무언가를 작성해 보세요... 이 창을 보고 있는 다른 모든 사람들과 공유됩니다.",
      }),
    ],
  });

  return (
    <>
      <p className="pl-7">방 제목</p>
      <h1 className="font-bold text-3xl pl-7">
        {decodeURI(window.location.pathname.split("/")[2])}
      </h1>
      <EditorContent className="editor" editor={editor} />
    </>
  );
}

export default CollaborativeEditorPage;
