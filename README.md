# Real Moment
Restful API 서버 프로젝트 / 화장품 판매 쇼핑몰


## ⏰ 개발 기간
- 2024.01.01 ~ 


## 🤝 참여 인원
- FE 1명, 본인 포함 BE 2명


## 🔍 기술 스택
- Spring Boot
- Spring Security
- Spring Data JPA
- Querydsl
- PostgreSQL
- JWT
- AWS S3


## 🏃 프로젝트 소개
웹 개발에 대해 공부한 뒤로 처음 경험해보는 프로젝트였기에 프로젝트 주제에 대해 고민이 많았습니다.

팀원들과 고민 끝에 웹 개발과 관련한 다양한 기술 스택이 접목되어 많은 경험을 할 수 있는 화장품 쇼핑몰로 주제를 정하게 되었습니다.

ERD와 FlowChart를 설계하고 작성된 API 명세에 따라 서로 다른 독립적인 서버를 개발하기로 결정했습니다.


## 🤖 Architecture

아직 프로젝트를 배포하지 못했기 때문에, 현재 구현된 기술들을 바탕으로 아키텍처를 제작했습니다.

![아키텍처 drawio](https://github.com/OSSING/Real_Moment/assets/98817068/1cf71af3-b925-430f-a73f-28b4fbba3aac)

## 📊 ERD

- 상품의 가격이 수정될 경우 상품에 대한 주문의 결제 정보가 변경되는 상황을 대비하여 주문 상세에 상품의 금액 정보를 따로 저장했습니다.
- 상품을 카테고리를 세분화 하기 위해 카테고리는 자기 참조를 통해 상위 카테고리와 하위 카테고리를 갖습니다.
- 회원과 관리자, 상품은 삭제 시 연관된 데이터들도 같이 삭제되어야 하기 때문에 삭제 여부 Column을 통해 삭제 처리했습니다.
- 결제 상태와 성별을 Enum 타입으로 관리하여 가독성과 유지보수성을 향상시켰습니다.
- 배송지, 공지사항, 상품의 이미지에 대표 및 기본을 지정하는 칼럼을 추가하여 사용자 경험을 향상시켰습니다.

![쇼핑몰 erd](https://github.com/OSSING/Real_Moment/assets/98817068/71114233-90fd-46ae-a461-843093c247da)

## API 명세서
[프로젝트 API 명세서 링크](https://documenter.getpostman.com/view/26692471/2sA3BrZWNJ)

## 프로젝트 핵심 기능
### JWT를 활용한 인증 및 인가

- Spring Secutiry의 Filter Chain을 이용하여 JWT를 구현했습니다.
- 로그인 시 사용자를 검증하고 AccessToken과 RefreshToken을 발급하여 응답합니다.
- AccessToken의 만료 기간은 30분이며, RefreshToken의 만료 기간은 1주일로 설정했습니다.
- 발급한 RefreshToken을 Redis에 저장하여, AccessToken 재발급 시 검증 과정을 거치도록 했습니다.
- 회원 탈퇴 및 로그아웃 시 RefreshToken을 Redis의 BlackList에 등록하여 재사용 할 수 없도록 했습니다.

#### JWT를 사용한 이유

- HTTP의 비상태성(Stateless)으로 Session에 비해 높은 확장성과 인증 및 인가 속도를 가질 수 있습니다.
- Token은 클라이언트 측에 저장되기 때문에 서버 측에 저장되는 세션과 비교하여 서버의 부하를 줄일 수 있습니다.

#### Redis를 Token 저장소로 사용한 이유

- 메모리 내에서 데이터를 저장하고 조회하기 때문에 인증 및 권한 부여 작업에 대 매우 빠른 응답 시간을 갖습니다.
- JWT의 stateless의 특성을 최대한 살리면서 보안을 강화할 수 있습니다.

### 검색 조건를 활용한 검색 기능
### 회원 / 관리자 전용 페이지 분리
### 관리자의 등급에 따른 접근 가능한 API 분리
### AWS S3를 통한 이미지 관리
### PortOne를 통한 결제 및 환불 처리





[ReadMe 작성법 참고 블로그](https://backendcode.tistory.com/165)
