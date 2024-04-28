# Real Moment
Restful API 서버 프로젝트 / 화장품 판매 쇼핑몰


## ⏰ 개발 기간
- 2024.01.01 ~ 


## 🤝 참여 인원
- FE 1명, 본인인 포함 BE 2명


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
- Token은 클라이언트 측에 저장되기 때문에 서버 측에 저장되는 Session과 비교하여 서버의 부하를 줄일 수 있습니다.

#### Redis를 Token 저장소로 사용한 이유

- 메모리 내에서 데이터를 저장하고 조회하기 때문에 인증 및 권한 부여 작업에서 매우 빠른 응답 시간을 갖습니다.
- 클라이언트가 요청한 Token에 대해 서버는 해당 Token의 유효성 검증만 진행하면 되기 때문에 서버는 보안을 강화함과 동시에 JWT의 stateless의 특성을 유지할 수 있습니다.

- - -

### 검색 조건를 활용한 검색 기능

- 회원의 상품 조회와 주문 내역 조회, 관리자의 회원 조회와 같은 조회 API에서 조건부 필터를 통해 페이징된 다양한 결과를 얻을 수 있습니다.
- 관리자 페이지의 상품 목록 조회를 예로 들자면 카테고리, 상품 이름, 품절 여부, 삭제 여부를 조건 파라미터로 서버에 요청할 수 있습니다.
- Querydsl의 BooleanExpression을 통해 조건 메서드를 생성하여 각각의 조건을 독립적으로 처리하여 확장성과 재사용성을 확보했습니다.

- - -

### 회원 / 관리자 전용 페이지 분리

- 회원 테이블과 관리자 테이블을 별개로 설계하여 권한을 구분하고 관리자와 회원 간의 데이터 일관성을 유지합니다.
- 로그인하고 발급받은 Token에 저장된 권한을 바탕으로 API에 대한 인가처리를 진행합니다.
- 회원은 리뷰, 문의, 마이페이지, 검색, 결제 서비스를 구현하였으며, 회원가입 / 로그인 및 조회는 인가과정을 생략하도록 구현하였습니다.
- 관리자는 상품 관리, 주문 관리, 회원 등급 관리, 관리자 관리 서비스를 구현하였습니다.

- - -

### 관리자 권한 등급에 따른 접근 가능한 API 분리

- 관리자의 등급을 기본 관리자, 상품 관리자, 회원 관리자, 총 관리자로 역할을 세분화하여 관리자 페이지에서 접근 가능한 API를 구분했습니다.
- 상품 조회, 회원 조회, 공지사항 조회 등 조회에 관한 정보는 모든 관리자 권한이 접근 가능하게 설정했습니다.

- - -

### 상품 이미지 다양성을 설계

- 상품의 이미지를 등록할 때 메인 이미지와 서브 이미지를 등록할 수 있습니다.
- 이미지는 클라이언트에게 보여질 순서를 지정할 수 있으며, 순서가 0인 메인 이미지는 대표 이미지로써 사용자에게 보여집니다.
- 관리자 상품 수정 API를 통해 이미지의 순서 변경, 이미지 변경, 삭제, 추가가 가능합니다.
- 저장된 이미지는 AWS S3에 등록되며, 저장된 이미지의 URL을 DB에 저장합니다.

#### AWS S3를 사용한 이유

- AWS S3는 저장 공간에 대한 제한이 없기 때문에 비즈니스에 성장에 맞게 유연하게 대응할 수 있으며, 높은 가용성으로 이미지의 안정성을 보장합니다.

- - -

### PortOne를 통한 결제 및 환불 처리 구현

- PortOne을 통해 상품 결제 시 PG사와 연동하여 결제를 진행합니다.
- DB에 저장된 주문 고유 번호를 통해 결제 취소 또는 환불을 진행할 수 있습니다.

#### PortOne을 사용한 이유

- 다양한 플랫폼과의 간편한 연동이 가능하기 때문에 쇼핑몰과 같은 서비스에서 빠르고 효율적인 결제 시스템을 구현할 수 있습니다.
- 신용카드, 휴대전화, 가상계좌 등 다양한 결제 방법을 지원하기 때문에 확장성이 좋습니다.
- 실시간 결제 상태를 모니터링 할 수 있는 기능을 제공하기 때문에 관리에 용이합니다.

## 트러블 슈팅
- 인가가 필요한 API에 대해 Token을 검증할 때 회원인지 관리자인지 구분해야 하는데 어떻게 해야 하지?


[ReadMe 작성법 참고 블로그](https://backendcode.tistory.com/165)
