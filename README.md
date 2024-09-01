# ALGONION - 온라인 저지 사이트 통합 서비스

> Algonion 링크  👉 https://algonion.store/

## 목차
1. [개요](#개요)
2. [주요 기능](#주요-기능)
3. [개발 환경](#개발-환경)
4. [프로젝트 파일 구조](#프로젝트-파일-구조)
5. [서비스 화면](#서비스-화면)
6. [설계 문서](#설계-문서)
7. [팀원 소개](#팀원-소개)

## 개요

> **모든 코드, 모두 모아**

Algonion은 Algorithm과 Union의 합성어 입니다.</br>
Algonion은 여러 온라인 저지 사이트에서 자신이 해결한 문제를 통합, 관리할 수 있는 서비스입니다.

온라인 저지 사이트는 다양한 난이도의 문제를 제공하지만, 각 온라인 저지 사이트 간의 통합이 되어있지 않다는 아쉬운 점이 있습니다.</br> 
다양한 온라인 저지 사이트의 문제 난이도를 분석하여 문제의 난이도를 한 가지 기준으로 설정하여, </br>
프로그래밍 실력 향상을 위한 발판을 마련해 줄 수 있을 것입니다.

## 주요 기능
- ### 해결한 문제 저장
    - Algonion에서 제공하는 크롬 확장 프로그램을 설치하세요.
    - 여러 온라인 저지 사이트에서 문제를 풀면 자동으로 Algonion 사이트에 저장됩니다.
    - 여러 온라인 저지 사이트에서 해결한 문제를 한 눈에 볼 수 있습니다.

</br>

- ### 기록 관리
    - 해결한 문제를 볼 수 있고, 메모를 남기거나 Notion에 저장할 수 있습니다.
    - 해결한 문제를 통합하여 그래프 등을 활용해 시각화하여 볼 수 있습니다.
    - 스트릭을 활용하여 푼 날짜를 확인할 수 있습니다.

## 개발 환경
Management Tool 
</br>

<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">
<img src="https://img.shields.io/badge/Mattermost-0058CC?style=for-the-badge&logo=Mattermost&logoColor=white">
<img src="https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=Jira&logoColor=white">

Infra 
</br>

<img src="https://img.shields.io/badge/AmazonAWS-232F3E?style=for-the-badge&logo=AmazonAWS&logoColor=white">
<img src="https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white">
<img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"> 
<img src="https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=Ubuntu&logoColor=white"/>

Front 
</br>

<img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white">
<img src="https://img.shields.io/badge/Typescript-3178C6?style=for-the-badge&logo=Typescript&logoColor=white"/>
<img src="https://img.shields.io/badge/scss-CC6699?style=for-the-badge&logo=sass&logoColor=white"/>
<img src="https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=Vite&logoColor=white"/>

Backend 
</br>

<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white">

## 프로젝트 파일 구조
### Front-end
```
frontend
    ├─public
    └─src
        ├─api
        ├─assets
        │  ├─img
        │  ├─logo
        │  ├─social_login
        │  │  └─kakao
        │  └─tier
        ├─components
        │  ├─Board
        │  ├─CodeLog
        │  │  └─CodeLogList
        │  ├─Community
        │  ├─Main
        │  ├─Search
        │  ├─svg
        │  └─User
        ├─containers
        │  ├─Footer
        │  └─Header
        ├─pages
        │  ├─CodeLogDetailPage
        │  ├─CodeLogPage
        │  ├─CollaborativeEditorPage
        │  ├─CommunityPage
        │  ├─LoginSuccessPage
        │  ├─MainPage
        │  ├─RoomDetailPage
        │  ├─SearchPage
        │  └─UserPage
        ├─routes
        └─utils
```

### Back-end
```
backend
    ├─domain
    │  ├─problem
    │  │  ├─controller
    │  │  ├─exception
    │  │  ├─model
    │  │  │  └─entity
    │  │  ├─repository
    │  │  ├─request
    │  │  ├─response
    │  │  └─service
    │  └─user
    │      ├─controller
    │      ├─dto
    │      │  ├─request
    │      │  └─response
    │      ├─entity
    │      ├─exception
    │      ├─repository
    │      └─service
    └─global
        ├─annotation
        ├─config
        ├─error
        ├─jwt
        ├─oauth
        └─util
```

## 서비스 화면
### 메인 화면
- Algonion의 메인 화면입니다
- 로그인을 할 수 있고, 상단 바를 통해 메뉴를 이용할 수 있습니다
</br>

<img src="https://github.com/ksh4030/public-wifi/assets/124498333/67f834b8-d78f-4fbb-9ca9-c4bc8fd9e368" width="500">

### 마이페이지
- 해결한 문제의 점수에 따라 개인 레벨을 보여줍니다
- 해결한 문제들을 보여줍니다
- 해결한 날짜를 기준으로 스트릭을 채울 수 있습니다 
</br>

<img src="https://github.com/ksh4030/public-wifi/assets/124498333/9dbadeed-6ba7-4ad0-ad00-b6a215174e67" width="500">

### 해결한 문제 리스트
- 해결한 문제들을 리스트로 볼 수 있습니다 
</br>

<img src="https://github.com/ksh4030/public-wifi/assets/124498333/80ea60c1-d457-4d95-8183-8ac485c5f519" width="500">


### 메모
- 해결한 문제를 선택해서 메모를 남길 수 있습니다
</br>

<img src="https://github.com/ksh4030/public-wifi/assets/124498333/387c3465-1f4d-4972-98ac-dfa65daee2f4" width="500">

### 노션에 저장
- 해결한 문제를 선택해서 노션에 저장할 수 있습니다
</br>

<img src="https://github.com/ksh4030/public-wifi/assets/124498333/f9c5406c-1d88-4668-9ff4-5a413042c4c4" width="500">

### 유저 조회
- 다른 유저를 조회할 수 있습니다
- 조회한 유저의 해결한 문제나 스트릭 등을 확인할 수 있습니다
</br>

<img src="https://github.com/ksh4030/public-wifi/assets/124498333/64a597cb-045b-4f9a-80b7-735d05815b37" width="500">

### webRTC
- webRTC를 통해 다른 유저와 마크다운 형태로 코드를 공유하거나 채팅을 이용할 수 있습니다
</br>

<img src="https://github.com/ksh4030/public-wifi/assets/124498333/5af08882-04c0-4c1a-ad11-39ffacacb2c4" width="500">

### 해결한 문제 가져오기
- 해결한 문제를 크롬 확장프로그램을 통해 자동으로 저장합니다
- 설문의 결과로 가장 많은 사용자가 있는 백준, 프로그래머스, SWEA에서 문제를 가져옵니다.
</br>

<img src="https://github.com/ksh4030/public-wifi/assets/124498333/8d110e5e-72f9-438c-8336-0e8683c9aa55" width="500">

</br>

<img src="https://github.com/ksh4030/public-wifi/assets/124498333/37ea753b-7ac9-4112-878a-6e218c3b38b0" width="500">

</br>

<img src="https://github.com/Siabel/Algorithm_solved/assets/128214383/aa102f6d-239c-46f5-bb9d-c9b8e55e14bc" width="500">

## 설계 문서
- [기능/요구사항 명세서](https://algonion.notion.site/77994c2f2c9347f3a6a52fde80166b80?v=7d72d856fd6d40aba986395d3c7eb4da)
- [API](https://algonion.notion.site/API-6c8ab9b1e67e47f19295e7840431081a?pvs=74)
- [컨벤션](https://algonion.notion.site/40b4b7d9c3004b2cb76c03247e3dfde7)
- [기획서](https://algonion.notion.site/242a078ba4444c5b8f97a176a2660b51)
- [ERD](https://algonion.notion.site/ERD-9af2aabb48144ad58d93ca0c13b31741?pvs=25)
- [Components](https://algonion.notion.site/Components-bfbf439605ac478983e5618f6eec47cc?pvs=4)

## 팀원 소개
- 엄예진 (팀장)
    - API 설계 및 구현
    - 협업툴 및 문서 관리
    - 모델링 및 DB 설계
- 김예지 (프론트엔드 리더)
    - 인프라 구축
    - UI/UX 설계 및 구현
    - webRTC 구현
- 김수환 (백엔드 리더)
    - 인프라 구축
    - API 설계 및 관리
    - 외부 API 관리
- 정원종 (프론트엔드)
    - UI/UX 설계 및 구현
    - 와이어프레임 및 API 설계
    - 데이터 스크래핑
- 전소현 (프론트엔드)
    - UI/UX 설계 및 구현
    - 와이어프레임 및 API 설계
    - 데이터 스크래핑
- 신유진 (백엔드)
    - 모델링 및 DB 설계
    - API 설계 및 구현
