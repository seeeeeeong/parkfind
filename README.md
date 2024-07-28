<div align="center">
  <br>
  <h2> 서울시 주차 정보 서비스 </h2>
</div>
<br>

http://www.parksfind.com/

## 프로젝트 소개

본 프로젝트는 서울지역 주차장정보표준데이터를 이용하여 입력된 주소 근처의 주차장 정보를 제공하는
서비스입니다. 

## 기술 스택

- Java 17
- Gradle
- Spring Boot 3.3.0
- Spring Data JPA
- MariaDB
- Redis
- Spock
- AWS EC2
- Github
- Testcontainers
- Docker
- Jenkins
- Nginx

### 서버 아키텍처

![서버 아키텍처](https://github.com/user-attachments/assets/1814d914-fb8f-4ba7-b219-aa3eda3f1b17)


##### Jenkins, Nginx, SpringBoot(Blue), SpringBoot(Green) 이용

Blue/Green 배포 흐름

1. Git push시, Webhook을 통해 Jenkins에 Request, Jenkins는 최신 버전의 Jar 파일 빌드
2. 빌드한 Jar파일을 배포 서버로 전송, 배포 서버에서 실행
3. Blue가 가동중이라면 신버전을 Green에 배포 및 실행
4. Green 애플리케이션이 구동되었는지 Health Check, Green 애플리케이션이 가동됨을 확인
5. Nginx의 리버스 프록시 방향을 Blue에서 Green으로 변경
6. Blue 인스턴스의 애플리케이션 프로세스 종료


### Process

![서비스 세부 기능](https://github.com/seeeeeeong/parkfind/assets/136677284/6b26a812-ad54-475f-8095-047aad1fc596)


### 프로젝트 중점사항

- Spock를 이용한 테스트 코드 작성
- TestContainer를 이용하여 테스트 환경 구축
- Redis를 이용한 주차장 정보 조회 기능 구현
- Spring retry를 이용한 재처리 구현
- base62를 이용한 shorten url 개발
- Jenkins를 사용하여 CI/CD 환경 구축
- Nginx를 이용한 무중단 배포 구현

### 프로젝트 위키

[WIKI](/parkfind_wiki.md)




