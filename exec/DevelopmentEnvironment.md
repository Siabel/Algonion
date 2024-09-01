
# 목차
1. 개발 환경
2. Stacks
3. Build & Distribute
4. Deployment Command
5. MySQL WorkBench Connection
6. Nginx default
7. EC2 Setting
8. Files ignored
9. etc) Settings or Tips

## 개발 환경
- JAVA : 17
- Springboot : 3.2.1
- JPA : 3.2.2
- REACT: 18
- TypeScript : 5
- SCSS : ^1.70
- MySQL : 8.0.36
- IntelliJ : 2023.1
- VS Code : 


## Stacks
- Issue Management
    - Jira
- SCM(Software Configuration Management)
    - GitLab
- Communities
    - Mattermost
    - Nnotion
- Development Environment
    - Gradle
    - Vite
- Detail
    - mysql
    - OAuth
        - kakao
        - google
    - apexcharts
    - notion api

## Build & Distribute
- React
    ```Shell
    npm install
    npm run build
    ```
- Spring Boot
    ```Shell
    Gradle 실행
    bootjar 실행
    ```
- nodejs
  ```shell
    node server.js
  ```
- Docker
    ```Shell
    docker build -t [dockerHub ID]/[image 이름]:[tag 이름] .
    docker run -d -p [dockerHub ID]/[image 이름]:[tag 이름]
    ```
- Nginx
    ```
        server {
        listen 80 default_server;
        listen [::]:80 default_server;

        root /var/www/html;

        # Add index.php to the list if you are using PHP
        index index.html index.htm index.nginx-debian.html;

        server_name _;

        location / {
            try_files $uri $uri/ =404;
        }
            
        }

        server {
            server_name algonion.store; # managed by Certbot


            location / {
                proxy_pass http://localhost:5173;
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
            }

            listen [::]:443 ssl ipv6only=on; # managed by Certbot
            listen 443 ssl; # managed by Certbot
            ssl_certificate /etc/letsencrypt/live/algonion.store/fullchain.pem; # managed by Certbot
            ssl_certificate_key /etc/letsencrypt/live/algonion.store/privkey.pem; # managed by Certbot
            include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
            ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot

        }
        server {
            if ($host = algonion.store) {
                return 301 https://$host$request_uri;
            } # managed by Certbot


                listen 80 ;
                listen [::]:80 ;
            server_name algonion.store;
            return 404; # managed by Certbot
        }
    ```

## Deployment Command
- React Storybook
- Front & Back End Server
- Nginx Web Server

## MySQL WorkBench Connection
- (공통) Spring Boot에서 연결
- Standard TCP/IP 연결
- Standard TCP/IP over SSH Connection

## Nginx default

## EC2 Setting
- Docker
- Nginx

## Files ignored
- git ignore
    - React : 
    - Spring : application.yml (\src\main\resources에 위치)
        ```yml
        spring:
        datasource:
            driver-class-name: com.mysql.cj.jdbc.Driver
            url: <MySQL DB 주소>
            username: <mysql 유저 이름>
            password: <유저 비밀번호>
        jpa:
            properties:
            hibernate:
                format_sql: 'true'
            show-sql: 'true'
            hibernate:
            ddl-auto: update
        security:
            oauth2:
            client:
                registration:
                google:
                    client-id: <Google Cloud console 클라이언트ID>
                    client-secret: <Google Cloud console 클라이언트 보안 비밀번호>
                    redirect-uri: <Google Cloud console redirect uri>
                    scope:
                    - email
                    - profile
                kakao:
                    client-id: <Kakao Developers REST API 키>
                    client-secret: <Kakao Developers Client Secret 코드>
                    redirect-uri: <Kakao Developers에 설정한 Reddirect url>
                    authorization-grant-type: authorization_code
                    client-authentication-method: client_secret_post
                    client-name: Kakao
                    scope:
                    - profile_nickname
                provider:
                    kakao:
                        authorization-uri: https://kauth.kakao.com/oauth/authorize
                        token-uri: https://kauth.kakao.com/oauth/token
                        user-info-uri: https://kapi.kakao.com/v2/user/me
                        user-name-attribute: id
        jwt:
        issuer: 
        secret-key: <JWT Secret Key>
        access-token-expiration-time: 1800
        refresh-token-expiration-time: 604800
        ```

## Settings and Tips

1. 카카오 API 등록
    - 카카오 디벨로퍼에서 애플리케이션 등록
    - Web 플랫폼 등록
    - 카카오 로그인 활성화
    - 비즈니스 설정

2. Google Cloud Console
    - 새 프로젝트 생성
    - 동의 화면 구성
    - 사용자 인증 정보 만들기
