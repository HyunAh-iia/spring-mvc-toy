# Spring MVC
Spring MVC 이해를 위해 간단한 게시판 예제를 작성한다.

## 사용 기술
- Spring Boot 2.6.4
- WebMVC
- Java 17
- H2

## 요구사항
- MVC 패턴으로 게시판(Posts) REST API 작성
  - [x] CRUD 기능 구현
  - [ ] 파일 업로드 기능 구현
- MVC 패턴으로 댓글(Comments) REST API 작성
  - [ ] CRUD 기능 구현 (단, depth는 1단계로 제한)
- 게시판, 댓글 UI 구현
  - [ ] `@Contoller` 추가하여 view ref 제공
  - [ ] Tymeleaf를 활용한 UI 구현
- 일관된 응답 포맷 제공
  - [ ] 성공 시의 응답 포멧 정의 
  - [ ] 일반적인 예외 처리(AOP)
  - [ ] 인증 관련 응답/예외 처리(Filter)
  - [ ] 메시지 국제화(i18n) 적용
- 로그인 기능 구현
  - [ ] 사용자 도메인 구현
  - [ ] Spring session 모듈 사용하여 인메모리에 저장
  - [ ] 인메모리에 관리중인 세션을 Redis로 변경
  - [ ] Spring Security 폼 로그인
  - [ ] Spring Security oauth
  - [ ] 세션 걷어내고 jwt 사용하여 인증
- 권한
  - [ ] 본인이 작성한 게시글만 수정/삭제 가능
  - [ ] 본인이 작성한 댓글만 수정/삭제 가능
- 테스트코드 작성
  - [ ] 게시판 테스트코드 작성
  - [ ] 댓글 테스트코드 작성
- 멀티모듈 추가
  - [ ] 육각형 패턴으로 oauth session (TDD)
  - [ ] 육각형 패턴으로 jwt token (TDD)