# Todo API
- 할 일을 기록하고 완료 여부를 체크할 수 있는 개인용 앱입니다.
- 제목과 내용, 우선순위를 정해 기입할 수 있습니다.
- 1순위는 빨간색, 2순위는 주황색, 3순위는 초록색으로 구분되며 우선순위가 없는 항목은 회색으로 구분됩니다.
- 완료된 할 일은 체크하여 완료로 표시할 수 있으며 미완료 항목만 목록에 보이게 할 수 있습니다.

## 소스 빌드 및 실행 방법
- 이 저장소의 코드와 Todo-FE의 코드를 함께 내려받습니다
(FE : https://github.com/iylee15/Todo-FE)
- MySQL workbench를 이용해 연결할 DB 스키마를 생성하고 application.properties의 datasource 값을 설정합니다.
- 포트값도 지정해 줍니다.

        #port
        server.port=9000
  
        spring.datasource.url=jdbc:mysql://localhost:3306/[스키마명]
        spring.datasource.username=[아이디]
        spring.datasource.password=[비밀번호]
        spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  
- intellJ를 이용해 todo-api-springboot의 TodoApplication를 실행합니다.
- 이어서 Todo-FE 코드의 빌드 및 실행을 진행합니다.


## 주력으로 사용한 라이브러리 설명 및 사용 이유
#### Lombok
  - Java 라이브러리
  - 여러가지 어노테이션을 제공
  - 반복되는 getter, setter, 생성 등의 메서드 작성 코드를 줄이고 생산성과 유지보수성을 향상시키기 위해 사용

#### jUnit
  - 전체 프로젝트의 구동 없이 단위 코드 테스트를 할 수 있게 해주는 라이브러리
  - WAS를 구동하지 않고 간단하게 필요한 코드만 테스트해보기 위해 사용

#### modelmapper
  - Java 객체 간 변환을 쉽게 해주는 라이브러리
  - Entity와 DTO간의 변환 코드를 간결하게 작성하여 불필요한 정보의 전송을 막고 생산성 및 유지보수성을 향상시키기 위해 사용

#### Validation
  - 값 입력시 잘못된 값의 입력을 자동으로 검사하여 막아주는 유효성 검사를 위한 라이브러리
  - 잘못된 SQL문의 입력을 통한 공격을 막음으로써 보안을 강화
  - 사용자의 잘못된 입력을 방지함으로써 사용자 경험 개선
  - 사전에 검사하여 잘못된 데이터의 저장을 막고 오류 메세지를 보여주는 코드를 간결하게 작성하기 위해 사용
