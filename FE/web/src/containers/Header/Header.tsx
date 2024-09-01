import classes from "./Header.module.scss";
import React, { useEffect, useState } from "react";
import { Avatar } from "@nextui-org/react";
import {
  NavbarMenu,
  NavbarMenuItem,
  NavbarMenuToggle,
  NextUIProvider,
} from "@nextui-org/react";
import {
  Navbar,
  NavbarBrand,
  NavbarContent,
  NavbarItem,
  Link,
} from "@nextui-org/react";
import {
  Dropdown,
  DropdownTrigger,
  DropdownMenu,
  DropdownItem,
} from "@nextui-org/react";
import getAsset from "../../utils/getAsset";
import SearchModal from "./searchModal";
import { deleteCookie } from "../../utils/cookieUtil";
import { getNickname } from "../../api/nicknameAPI";

import pImage from "./profile_image.png";

export default function Header() {
  // nickname 받아오기
  // const [nickname, setNickname] = useState('');
  const isLogin = localStorage.getItem("access_token") ? true : false;
  const [menuItems, setMenuItems] = React.useState<string[]>([]);

  useEffect(() => {
    async function getAxios() {
      const name = await getNickname();
      // setNickname(name)
      // console.log(name)
      if (name) {
        setMenuItems([
          "나의정보",
          "코드로그",
          "커뮤니티",
          "로그아웃",
          "회원탈퇴",
        ]);
      }
    }
    getAxios();
  }, []);

  const [isMenuOpen, setIsMenuOpen] = React.useState(false);

  // 닉네임 변경 페이지로 이동
  const NicknameSet = () => {
    window.location.href = "/user/nickname";
    // // 페이지 이동 함수
    // const navigate = () => {
    // };

    // // 확인 메시지
    // const confirm = window.confirm("닉네임 변경 페이지로 이동하시겠습니까?");

    // // 확인 메시지에 따라 페이지 이동
    // if (confirm) {
    //   navigate();
    // }
  };

  // 로그아웃 처리
  const Logout = () => {
    localStorage.removeItem("access_token");
    localStorage.removeItem("nickname");
    window.location.href = "/";
  };

  // 회원탈퇴 처리
  const Withdraw = () => {
    fetch("api/v1/user/withdraw", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`,
      },
    })
      .then((res) => {
        if (res.status === 200) {
          localStorage.removeItem("access_token");
          localStorage.removeItem("nickname");
          deleteCookie("refresh_token");
          console.log("회원탈퇴 성공!");
          window.location.href = "/";
          window.location.reload();
        } else {
          console.log("회원탈퇴 실패!");
        }
      })
      .catch((err) => {
        console.log("회원탈퇴 API 호출 오류:", err);
      });
  };

  // nickname 받아오기
  const [nickname, setNickname] = useState("");

  useEffect(() => {
    async function getAxios() {
      let name = await getNickname();
      setNickname(name);
    }
    getAxios();
  }, []);
  // console.log(nickname, 'nickname')

  return (
    <NextUIProvider className={classes.header}>
      <Navbar onMenuOpenChange={setIsMenuOpen}>
        <NavbarContent>
          {isLogin && (
            <NavbarMenuToggle
              aria-label={isMenuOpen ? "Close menu" : "Open menu"}
              className="sm:hidden"
            />
          )}
          <NavbarBrand>
            <Link href="/">
              {/* <img className={classes.img} src={getAsset('logo/icon_square.svg')} alt="algo_logo_dark" /> */}
              <img
                className={classes.logo}
                src={getAsset("logo/logo_light.svg")}
                alt="Algonion 로고"
              />
            </Link>
          </NavbarBrand>
        </NavbarContent>
        {isLogin && (
          <>
            <NavbarContent className="hidden sm:flex gap-4" justify="center">
              <NavbarItem>
                <Link
                  href={`/profile/${nickname}`}
                  color="foreground"
                  className={classes.hover}
                >
                  나의정보
                </Link>
              </NavbarItem>

              <NavbarItem>
                <Link
                  href={`/code-log/${nickname}`}
                  color="foreground"
                  className={classes.hover}
                >
                  코드로그
                </Link>
              </NavbarItem>

              <NavbarItem>
                <Link
                  href={`/community`}
                  color="foreground"
                  className={classes.hover}
                >
                  커뮤니티
                </Link>
              </NavbarItem>

              <NavbarItem>
                <Link aria-current="page">
                  <SearchModal />
                </Link>
              </NavbarItem>
            </NavbarContent>

            <NavbarContent justify="end">
              <NavbarItem>
                {/* 프로필 페이지 - 티어에 따라서 프로필 테두리 색 다르게 주기 (예정) */}
                <div className="flex gap-4 items-center">
                  <Dropdown>
                    <DropdownTrigger>
                      <div className="profile_wrapper">
                        <div className="gradation_animate"></div>
                        <div className="image_wrapper">
                          <Avatar
                            isBordered
                            className="image"
                            color="secondary"
                            src={pImage}
                          />
                        </div>
                      </div>
                    </DropdownTrigger>
                    <DropdownMenu
                      variant="flat"
                      aria-label="Dropdown menu with shortcut"
                    >
                      <DropdownItem key="change-nickname" onClick={NicknameSet}>
                        닉네임 변경
                      </DropdownItem>
                      <DropdownItem key="log-out" onClick={Logout}>
                        로그아웃
                      </DropdownItem>
                      <DropdownItem
                        key="sign-out"
                        onClick={Withdraw}
                        className="text-danger"
                        color="danger"
                      >
                        회원탈퇴
                      </DropdownItem>
                    </DropdownMenu>
                  </Dropdown>
                </div>
              </NavbarItem>
            </NavbarContent>
          </>
        )}
        <NavbarMenu>
          {menuItems.map((item, index) => (
            <NavbarMenuItem key={`${item}-${index}`}>
              <Link
                color={index === menuItems.length - 1 ? "danger" : "foreground"}
                className={`"w-full" ${classes.hover}`}
                href={
                  index === 0
                    ? `/profile/${nickname}`
                    : index === 1
                    ? `/code-log/${nickname}`
                    : index === 2
                    ? `/community`
                    : `/`
                }
                size="lg"
              >
                {item}
              </Link>
            </NavbarMenuItem>
          ))}
        </NavbarMenu>
      </Navbar>
    </NextUIProvider>
  );
}
