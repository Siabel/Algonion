import ParticipantsList from '../../components/Community/ParticipantsList.tsx';

function RoomDetailPage() {
	return (
    <div>
      <ParticipantsList/>

    </div>
  )
}
  
export default RoomDetailPage

// import { useState } from "react";
// import ParticipantsList from "../../components/Community/ParticipantsList";

// export default function RoomDetailPage() {
//   const [room, setRoom] = useState<Room>({
//     id: 1,
//     title: "채팅방 제목",
//     tag: "채팅방 태그",
//     participants: [
//       {
//         id: 1,
//         name: "사용자 1",
//         profileImage: "https://example.com/user1.png",
//       },
//       {
//         id: 2,
//         name: "사용자 2",
//         profileImage: "https://example.com/user2.png",
//       },
//     ],
//     description: "채팅방 설명",
//   });

//   const [messages, setMessages] = useState<Message[]>([]);

//   const handleSendMessage = (message: string) => {
//     // 서버로 메시지를 전송하는 코드를 추가해야 합니다.
//     setMessages((prev) => [...prev, {
//       id: Date.now(),
//       sender: {
//         id: 1,
//         name: "사용자 1",
//         profileImage: "https://example.com/user1.png",
//       },
//       content: message,
//       timestamp: Date.now(),
//     }]);
//   };

//   return (
//     <div>
//       <RoomInfo room={room} />
//       <ChatBox messages={messages} onSendMessage={handleSendMessage} />
//       <ParticipantsList participants={room.participants} />
//     </div>
//   );
// };