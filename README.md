# Todo App 🗒️
- 할 일을 기록하고 완료 여부를 체크할 수 있는 개인용 앱입니다.
- 제목과 내용, 우선순위를 정해 기입할 수 있습니다.
- 1순위는 <span style = "background-color: red">*빨간색*</span>, 2순위는 <span style = "background-color: orange">*주황색*</span>, 3순위는 <span style = "background-color: green">*초록색*</span>으로 구분되며 우선순위가 없는 항목은 *회색*으로 구분됩니다.
- 완료된 할 일은 체크하여 완료로 표시할 수 있으며 미완료 항목만 목록에 보이게 할 수 있습니다.
- 추가적인 기능으로 할 일을 매일 자동으로 등록하는 기능이 있습니다.
- Spring Boot 프로젝트입니다.

## 소스 빌드 및 실행 방법 💻
- todo-api-springboot 코드와 Todo-FE의 코드를 함께 내려받습니다  
(FE : https://github.com/iylee15/Todo-FE)
- 받은 코드 내부의 *TodoDB.sql* 파일을 MySQL workbench의 Data Import/Restore 메뉴를 통해 실행하여 앱의 초기 데이터를 저장합니다.  
![Image](https://github.com/user-attachments/assets/f9ea3200-4e8d-457c-8de0-1a52ea53ab0c)
- 프로젝트 내 resources 폴더에 application.properties를 생성하고 datasource 값을 설정합니다.
- 포트값도 지정해 줍니다.

        #port
        server.port=9000
  
        spring.datasource.url=jdbc:mysql://localhost:3306/[스키마명(예시:todo)]
        spring.datasource.username=[아이디]
        spring.datasource.password=[비밀번호]
        spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  
- intelliJ를 통해 Gradle 빌드 실행
    - View > Tool Windows > Gradle > Tasks > build > build 클릭을 통해 빌드
    - TodoApplication을 실행  
![intelliJ](https://github.com/user-attachments/assets/dbff7a6e-f9ae-4747-9ec2-f070f56219e1)

- 이어서 Todo-FE 코드를 실행합니다.
- API 명세는 앱 실행 후 http://localhost:9000/swagger-ui/index.html 로 접속하여 확인할 수 있습니다.


## 주력으로 사용한 라이브러리 설명 및 사용 이유 📚
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

#### Springdoc
  - Spring Boot에서 API 문서를 자동으로 생성해주는 라이브러리
  - REST API를 쉽게 테스트하고 문서화할 수 있도록 도와주는 도구
  - API 문서의 간편한 작성과 코드 수정사항의 빠른 문서화를 위해 사용
