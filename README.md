# 🗓️ 일정 관리 앱 (with 댓글 기능)

Spring Boot를 활용한 개인 일정 및 댓글 관리 API 서버입니다.  
계층형 아키텍처(3-Layer Architecture)를 준수하며, 입구(DTO)에서의 수동 검증과 전역 예외 처리를 통해 안정적인 서비스를 지향합니다.

---

## 🔗 API 명세서 (Postman)
상세한 API 사용법과 예시 데이터는 아래 링크에서 확인하실 수 있습니다.
- [일정 관리 API 명세서](https://documenter.getpostman.com/view/8007044/2sBXc7M58q#f80c3477-42c8-4fa7-b472-ca0ec13194d9)

---

## 🚀 주요 기능

### 1. 일정 관리 (Schedule)
- **등록**: 제목, 내용, 작성자, 비밀번호를 포함한 일정 생성
- **조회**:
    - **목록 조회**: 작성자명 필터링 및 수정일 기준 내림차순 정렬
    - **상세 조회**: 일정 상세 정보 + **해당 일정의 댓글 목록** 동시 출력
- **수정/삭제**: 비밀번호 일치 여부 확인 후 처리

### 2. 댓글 관리 (Comment)
- **등록**: 특정 일정(`scheduleId`)에 댓글 추가
- **제한**: 한 일정당 최대 10개까지만 댓글 작성 가능
- **검증**: 내용(100자 이내), 작성자명, 비밀번호 필수값 체크

### 3. 예외 처리 및 검증 (Validation & Exception)
- **수동 검증**: Bean Validation 없이 유틸리티 클래스를 활용한 조건부 검증 구현
- **전역 예외 처리**: `@RestControllerAdvice`를 통해 공통 에러 응답(`BaseErrorResponse`) 반환

---

## 🛠️ 기술 스택
- **언어 및 프레임워크**: Java 17, Spring Boot 3.x
- **데이터베이스**: MySQL (H2)
- **ORM**: Spring Data JPA
- **도구**: Lombok, Gradle

---

## 📊 데이터베이스 설계 (ERD)



- **Schedule** (1) : **Comment** (N) 관계
- 댓글 엔티티는 부모인 일정의 ID(`schedule_id`)를 외래키로 가집니다.
  ![img.png](ERD.png)
---
