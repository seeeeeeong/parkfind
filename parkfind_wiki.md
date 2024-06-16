# 프로젝트 wiki

### Spring retry를 통한 재처리

- 서버와 서버간 api 호출 실패에 대한 재시도
- 1번의 호출 실패로 비지니스 로직의 실패 처리 또는 fallback 처리는 재처리보다 큰 리소스 낭비가 될 수 있다.

**@Retryable**

![image](https://github.com/seeeeeeong/doodle/assets/136677284/8adde66a-dffa-4acf-9574-d2d00d6fe334)

- RuntimeException시 Retry
- maxAttempts = 2 : 재시도 횟수
- @Backoff(delay = 2000) : 시도 실패시 다음 시도까지의 딜레이

**@Recover**

- @Retryable에서 정한 횟수만큼 시도 후에도 정상 동작하지 않을 경우

![image](https://github.com/seeeeeeong/doodle/assets/136677284/5d9c47da-df56-43e9-bf86-2e2d7a62d845)

<hr>

![image](https://github.com/seeeeeeong/doodle/assets/136677284/1c033196-e367-47bf-a289-aa584f86378a)

### CI

- Continuous Integration
- 빌드/테스트 자동화 과정
- Commit시 빌드와 테스트가 자동으로 이루어져 코드 충돌의 문제를 방지한다.

### CD

- Continuous Delivery(지속적인 제공) : CI에서 Build 및 Test 후 검증, 이후 수동으로 Prod 환경 배포
- Continuous Deployment(지속적인 배포) : CI에서 Build 및 Test 후, 자동화를 통해 Prod 환경 배포

<hr>

### Jenkins Pipeline

- Git Push시 Webhook을 통해 Jenkins에 Request, Jenkins는 최신 버전의 Jar 파일 빌드
![image](https://github.com/seeeeeeong/doodle/assets/136677284/75b94a7c-0f07-4775-acae-65d30bfc1d91)

- Docker 이미지 빌드 및 Docker hub 푸시

![image](https://github.com/seeeeeeong/doodle/assets/136677284/eaba8819-3ad3-49fd-b673-be16bd28b42c)

- 배포 서버에서 deploy.sh 스크립트 실행
 
![image](https://github.com/seeeeeeong/doodle/assets/136677284/078e4512-7d39-44fd-98e4-a95e7749f72f)


** Docker에 설치된 Jenkins 내부에서 Docker를 실행 ?

1. Docker 위에 Docker Container를 생성한다 (Docker In Docker)
2. Local Machine 위에서 실행 중인 Docker Daemon을 이용한다 (Docker out of Docker) - 권장
- '/var/run/docker.sock:/var/run/docker.sock' 볼륨에 위와 같은 옵션을 주어서 호스트와 컨테이너가 같은 장소를 공유할 수 있도록 한다.

<hr>

### Deploy.sh

- docker ps | grep parkfind-blue를 통해 현재 가동중인 서버 확인
![image](https://github.com/seeeeeeong/doodle/assets/136677284/75115234-1718-4720-ae32-6f6e0d344a9b)

- Blue 서버가 가동중이지 않다면 Green 서버 가동, Green 컨테이너를 내린다.
![image](https://github.com/seeeeeeong/doodle/assets/136677284/89e71056-2777-4753-97ce-29a3bc3dc772)


- Blue 컨테이너 실행 후 Health Check
![image](https://github.com/seeeeeeong/doodle/assets/136677284/5728e0a1-0572-428e-9be9-fceb17416f19)


### Nginx의 리버스 프록시 방향을 Green에서 Blue로 변경 (8081 -> 8080)

** Nginx의 리버스 프록시

- 리버스 프록시 : 클라이언트와 내부 서버를 연결하는 프록시
- 클라이언트가 요청한 내용을 어떤 서버로 보내야할지 결정하는 역할 (로드벨런서)
- 로드벨런서는 부하를 줄이기 위해 사용할 수 있지만, 1개의 서비스를 2개의 WAS로 실행시켜 무중단 배포를 위해 사용할 수도 있다.
