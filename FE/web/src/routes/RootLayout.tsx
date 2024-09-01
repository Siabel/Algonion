import { Outlet } from "react-router-dom";
import Header from "../containers/Header/Header";
import Footer from "../containers/Footer/Footer";
import ExtAlert from "../containers/Header/ExtAlert";
function RootLayout() {
  return (
    <>
      <ExtAlert />
      <Header />
      <Outlet />
      <Footer />
    </>
  );
}
export default RootLayout;
