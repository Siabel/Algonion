import React, { lazy } from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import "./index.css";
import RootLayout from "./routes/RootLayout.tsx";

const UserPage = lazy(() => import("./pages/UserPage/UserPage.tsx"));
const CodeLogPage = lazy(() => import("./pages/CodeLogPage/CodeLogPage.tsx"));
const CodeLogDetailPage = lazy(
  () => import("./pages/CodeLogDetailPage/CodeLogDetailPage.tsx")
);
const SearchPage = lazy(() => import("./pages/SearchPage/SearchPage.tsx"));
const CommunityPage = lazy(
  () => import("./pages/CommunityPage/CommunityPage.tsx")
);
// eslint-disable-next-line react-refresh/only-export-components
const RoomDetailPage = lazy(
  () => import("./pages/RoomDetailPage/RoomDetailPage.tsx")
);
// eslint-disable-next-line react-refresh/only-export-components
const LoginSuccessPage = lazy(
  () => import("./pages/LoginSuccessPage/LoginSuccessPage.tsx")
);
// eslint-disable-next-line react-refresh/only-export-components
const UserNicknameSetting = lazy(
  () => import("./pages/UserPage/UserNicknameSetting.tsx")
);
const NotFound = lazy(() => import("./pages/NotFound.tsx"));
const CollaborativeEditorPage = lazy(
  () => import("./pages/CollaborativeEditorPage/CollaborativeEditorPage.tsx")
);

//
const router = createBrowserRouter([
  {
    path: "/",
    element: <RootLayout />,
    children: [
      { path: "/", element: <App /> },
      {
        path: "/profile/:username",
        element: <UserPage />,
      },
      {
        path: "/code-log/:username",
        element: <CodeLogPage />,
      },
      {
        path: "/code-log/:username/:codeId",
        element: <CodeLogDetailPage />,
      },
      {
        path: "/search",
        element: <SearchPage />,
      },
      {
        path: "/community",
        element: <CommunityPage />,
      },
      {
        path: "/room/:roomId",
        element: <RoomDetailPage />,
      },
      {
        path: "/login/success",
        element: <LoginSuccessPage />,
      },
      {
        path: "/user/nickname",
        element: <UserNicknameSetting />,
      },
      {
        path: "/collaborative/:roomName",
        element: <CollaborativeEditorPage />,
      },
      {
        path: "*",
        element: <NotFound />,
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
