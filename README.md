# 🖨️ Project Rental_Web

<br><br>


## ✔️ 프로젝트 소개
- 최근 렌탈 업체 게시판 CRUD
- FullCalendar를 활용한 AS일정 예약 및 취소
- 스프링 시큐리티를 이용한 로그인/로그아웃 기능 및 카카오 로그인 기능
- 카카오 상담하기 기능 및 롤링 배너
<br><br>

## ✔️ 프로젝트 계기
첫 여행 웹사이트 프로젝트는 egovFramework와 iBatis를 사용하여 개발되었습니다. <br>
이를 통해 새로운 기술과 라이브러리 및 AWS 배포 등 다양한 스킬을 사용하여 프로젝트를 진행해보고자 하는 마음에 <br>
Spring Boot와 JPA, MariaDB로 이루어진 프로젝트를 하게 되었습니다.
<br><br>

## ✔️ 팀 구성
강주희 외 1명
<br><br>

## ✔️ 개발 기간
2023.11.01 - 2023.11.15
<br><br>

## ✔️ WBS
*이미지 클릭시 원본 이미지로 표시됩니다.<br>
<img src="https://github.com/zzheek/Rental_Web/assets/133830185/d954b02f-c04c-4e44-b7fc-e6be1171719e" width="300">
<br><br>

## ✔️ 개발 환경
* Java 17.0
* Front : Bootstrap / Thymeleaf / Javascript
* Server : Apache Tomcat 9.0 / AWS EC2
* Framework : SpringBoot 3.1.0 / Spring Security 6.0
* Database : MariaDB 10.11.5
* ORM : Spring Data JPA
* IDE : IntelliJ
* API : KAKAO MAP API
* VCS : GIT
<br><br>

## ✔️ 주요 기능
*기능 클릭시 해당 코드로 이동합니다.

* ### <a href="https://github.com/zzheek/Rental_Web/blob/develop/src/main/resources/templates/index.html">메인 페이지</a>
  * 최근 렌탈 업체를 DB에서 받아와 롤링배너로 제공하도록 marquee태그를 이용했습니다.
  * 카카오 지도 API를 이용하여 위치를 표시하였고, 카카오 채널을 이용한 실시간 상담이 가능합니다.
 
    
![main1](https://github.com/zzheek/Rental_Web/assets/133830185/01f5e2d9-3c17-4c45-bd20-5f0c0879116a)
<br><br><br>


* ### <a href="https://github.com/zzheek/Rental_Web/blob/develop/src/main/java/com/rental_web/service/RenboardServiceImpl.java">CRUD 및 이미지 업로드</a>
  * 화면은 부트스트랩을 활용하여 개발하였으며, 비즈니스로직 대부분은 서비스 계층(serviceImpl)에서 처리하였습니다.
  * 데이터베이스와의 상호 작용은 JPA를 활용하여 구현하여, 데이터의 저장, 조회, 수정, 삭제(CRUD)를 처리합니다.
  * 이미지 업로드는 중복명을 방지하고자 UUID를 이용하여 이미지를 랜덤 이름+원래 이름으로 저장합니다.
 
    
![rental1](https://github.com/zzheek/Rental_Web/assets/133830185/89f93080-6b7d-4b30-9549-20255b66f184)
<br><br><br>


* ### <a href="https://github.com/zzheek/Rental_Web/blob/develop/src/main/java/com/rental_web/service/MemberServiceImpl.java">로그인 및 회원가입</a>
  * Thymeleaf와 Bootstrap을 활용하여 동적 데이터처리 및 스타일링을 포함한 회원가입 페이지를 구현하였습니다.
  * DTO에서 어노테이션을 이용한 유효성 검사를 실행합니다.
  * Spring Security의 UserDetails 및 OAuth2User를 이용하여 사용자 로그인과 카카오 로그인을 구현했습니다.
 
    
![login1](https://github.com/zzheek/Rental_Web/assets/133830185/bb74d303-60d9-4216-b2c2-9f5f14bbf886)
<br><br><br>


* ### <a href="https://github.com/zzheek/Rental_Web/blob/develop/src/main/resources/templates/ascal/main.html">풀캘린더를 이용한 AS일정</a>
  * 자바스크립트로 모달창을 이용한 예약 등록과 취소가 가능하도록 하였습니다. 
  * 오늘의 AS일정을 메인화면에서 확인할 수 있도록 데이터베이스와 연결하여 실시간 예약현황 출력합니다.
 
    
![ascal1](https://github.com/zzheek/Rental_Web/assets/133830185/30153b5a-ccac-458e-b6eb-056b5c7a1072)
<br><br><br>



## ✔️ 프로젝트 후기
처음 팀 프로젝트로 시작한 여행 커뮤니티는 학원에서 eGovframework와 iBatis, Oracle을 활용하여 개발했습니다. <br>
수료 후 SpringBoot와 JPA, MySQL이 보다 대중적이라고 판단하여 이를 학습하기로 결정했습니다. <br>
독학을 통해 책과 유튜브를 활용하고, 결과물은 AWS EC2를 통해 배포했습니다.<br><br>
eGovframework에서는 직접 쿼리문을 작성했던 것과는 달리, JPA에서는 repository 파일을 활용하여 데이터베이스와 상호작용했습니다. <br>
이때 MariaDB를 사용했는데, 오라클과 유사한 점이 많아 적응하기 어렵지 않았습니다. <br>
그러나 QueryDSL의 Gradle 설정에서 실수하여 고생한 적이 있고, 이를 통해 QueryDSL에 대한 공부의 필요성을 깨달았습니다.<br><br>
또한, 스프링 시큐리티를 이용한 로그인 구현은 처음 접하는 기능이어서 어려움을 겪었습니다. <br>
하지만 여러 책을 통해 문제를 해결하고 스프링 시큐리티에 대한 이해를 높일 수 있는 좋은 경험이었습니다.<br><br>
첫 프로젝트를 AWS EC2로 배포하고자 하였을 때는 Oracle이 유료인 RDS 설정에서 막혀 포기했었고, <br>
이번 프로젝트는 문제 없이 배포하는데 성공하였습니다.<br><br>
새로운 기술과 라이브러리 등을 습득 및 AWS 배포까지 학습한 좋은 경험이었습니다.<br>





