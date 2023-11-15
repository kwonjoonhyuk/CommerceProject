# 커머스 프로젝트

일반적으로 흔히 볼 수 있는 쿠팡, 11번가와 같이 커머스 애플리케이션처럼 물건을 사고 팔고 결제를 할 수 있는 애플리케이션

# 프로젝트 기능 및 설계

## 필수 추가 기능

### 1. 회원관리

#### 1-1. 회원가입 기능(판매자 및 고객 공통)

- [아이디,닉네임] (중복불가)
- 이름,비밀번호,핸드폰 번호
- 이메일(google SMTP로 인증절차)

#### 1-2. 아이디/비밀번호 찾기

- 아이디 찾기 절차
    - 가입했던 핸드폰번호와 이름 작성
    - 인증완료 후 아이디(이메일) 확인 가능
    - 인증번호는 Redis에 저장후 6시간후 만료

- 비밀번호 찾기 절차
    - 가입했던 이름과 아이디(이메일) 작성
    - 작성후 이메일로 인증코드 발송(가입됬던 이메일과 이름이 일치하지 않으면 에러)
    - 인증완료 후 비밀번호 재설정 가능
    - 인증번호는 Redis에 저장후 6시간후 만료

#### 1-3. 로그인, 로그아웃 기능

- security와 jwt 토큰과 Redis를 이용한 로그인 로그아웃 구현

#### 1-4. 회원정보 조회 기능(구매자, 판매자)

- 아이디 확인
- 닉네임 확인 및 변경(중복불가)
- 비밀번호 변경

### 2. 공통적인 기능

#### 2-1. 상품 검색
- 카테고리
    -  등록된 최신순 검색
- 가격순
    - 낮은 가격순, 높은 가격순으로 검색 가능
- 최신순
    - 최신순으로 등록된 상품 검색
- 별점순
    - 리뷰에 달린 별점 평점순으로 오름차순 내림차순 검색
- 상품이름
    - 등록된 상품명으로 검색

#### 2-2. 개인정보 수정

- 고객, 판매자 모두 개인정보 수정 가능
    - 핸드폰 번호 수정
    - 닉네임 수정
    - 비밀번호 수정(비밀번호 입력 받고 확인시 변경 가능)
    - 이름, 이메일은 수정 불가

### 3. 판매자

#### 3-1. 상품 CRUD

- 가격(필수 등록)
    - 상품에 대한 가격을 등록 및 수정
- 상품명(필수 등록)
    - 상품명 등록 및 수정
- 상세 설명(필수 등록)
    - 상품에 대한 상세한 설명 등록 및 수정
- 별점
    - 별점은 자동으로 0으로 되고 리뷰 등록시에 리뷰에 달린 점수들의 평균값으로 업데이트
- 상세 상품 옵션 (무조건 한개 이상은 등록 하도록 구현)
    - 상품 사이즈 (상품마다 다름) - 사이즈별로 따로 따로 등록(필수 등록)
    - 상품 개수 - 0개 이하로는 등록하지 못한다. 최소 한개 이상, 다팔린 경우에는 검색시에 나오지 않도록 구현할 예정

#### 3-2. 판매중인 목록 확인

- 상품 목록
    - 내가 팔고있는 상품 목록 확인,수정,삭제

#### 3-3. 상품 리뷰에 대한 답글

- 등록한 상품 리뷰에 대한 답글
    - 판매자가 등록한 상품에 대한 리뷰에 대한 답글을 CRUD

### 4. 구매자

#### 4-1. 상품 구매

- 원하는 상품 구매
    - 원하는 상품 구매 가능
    - 잔액은 0이하로 떨어지지 않고 잔액이 모자라면 구매 불가
- 결제 내역
    - 내가 결제한 내역(취소,결제 내역) 확인
- 주문 목록 확인
    - 상품 주문 목록 확인

#### 4-2. 상품 장바구니(완료)

- 장바구니 CRUD
    - 장바구니에 원하는 상품 추가 수정 삭제 목록 확인

#### 4-3. 상품 구매 및 취소

- 구매 취소
    - 상품 구매한 것들 취소 가능

#### 4-4. 배송지 CRUD(완료)

- 상품을 받을 배송지를 CRUD 가능

#### 4-5. 상품 리뷰 댓글

- 상품에 대한 리뷰 CRUD
    - 리뷰내용
    - 별점(별점은 0~5사이에 숫자로 줄수있다.)

## 추후에 추가할 만한 기능(필수기능 다 완성후에 추후에 추가)

- 리뷰,답글시에 알림 판매자와 구매자에게 알림
- 관리자도 추후에 추가 예정(따로 회원가입은 만들지 않음)
- 구현 진행하면서 추가할 기술 AOP 등등 추후에 추가
- 쿠폰 기능 추가

# Troble Shooting

[이번주 문제점 및 어려웠던 점](doc%2FTROUBLE_SHOOTING.md)

# Tech Stack

### 언어 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JAVA(JDK 11)

### 서버 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Apache Tomcat 9.0

### 프레임워크 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Spring Framwork 2.7.16

### DB &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MySQL

### IDE &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Intellij 2023.2
