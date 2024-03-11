# Store Reservation Project
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white">  <img src="https://img.shields.io/badge/java-%23ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"> 
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=springboot&logoColor=white">

<br/><br/>

## 📜프로젝트 소개
- 매장 테이블 예약 서비스 프로젝트입니다.
<br/><br/>
## ⏱개발 기간
* 2024.03.02 ~ 2024.03.11
<br/><br/>

## ⭐주요 기능
# Member

✅ POST - /member/{signup}
- 매장 예약 서비스 회원가입 API (유저기능과 매장 점주 기능 두 가지 권한)

✅ POST - /member/{signin}
- 매장 예약 서비스 로그인 API (유저기능과 매장 점주 기능 두 가지 권한)
  
# Store

✅ GET - /store/list
- 요청된 정렬 기준에 맞게 매장 목록 조회 API

✅ POST - /store/register
- 매장 등록 API

✅ POST - /store/{userId}
- 매장 상세 정보 수 API

✅ DELETE - /store/{storeName}
- 매장삭제 API

# Reservation

✅ GET - /reservation/list
- 매장 점주가 요청한 날짜의 예약 정보 조회 API

✅ POST - /reservation/{storeId}
- 매장 예약 API

✅ POST - /reservation/approve/{reservationId}
- 매장 점주가 예약 승인/거절하는 API

✅ POST - /reservation/confirm/{reservationId}
- 예약자 방문확인 체크 API

# Review

✅ POST - /review/write/{reservationId}
- 리뷰 작성 API

✅ POST - /review/{reservationId}
- 리뷰 수정 API

✅ POST - /review/{reviewId}
- 리뷰 삭제 API









