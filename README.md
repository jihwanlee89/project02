# project02
**카카오페이 서버사전과제(2)**

**INFORMATION**

작성자 : 이지환

목차 :

1.  개발환경

2.  카드 결제 API

3.  거래 조회 API

4.  취소 및 부분 취소 API

5.  응답 코드

6.  문제 해결 전략

7.  테이블 명세서

**1. 개발환경 및 프로젝트 정보**

JAVA 1.8

Spring Boot 2.3

H2 DataBase

mybatis

SpringToolSuite4 IDE를 사용하였으며 mavenProject로 구성하였습니다.
서버는 springboot 내장 웹 서버를 사용 하였습니다.

git clone 후 Existing Maven Projects로 import 하여 주세요.

H2 DB 접속 정보

webConsole : <http://localhost:8080/h2-console>

spring.datasource.url=jdbc:h2:file:./data/project02\_db

> spring.datasource.driverClassName=org.h2.Driver
>
> spring.datasource.username=jihwan
>
> spring.datasource.password=jihwan
>
> spring.h2.console.enabled=true

**2. 카드 결제 API**

카드 결제를 위해 주문 정보 및 결제에 사용 될 카드정보를 전달 하면 결제
승인 고유 uniqueId (결제에 대한 고유 ID, 거래 조회, 취소 및 부분 취소에
사용된다.)를 응답합니다.

부가가치세는 결제금액보다 클 수 없습니다.

부가가치세를 전달 하지 않을경우 결제금액/11, 소수점이하 반올림으로
계산됩니다.

주의 -- 동일 한 카드가 결제 처리 중 일 때 해당 카드로 결제를 진행 할 수
없습니다.

**-\>Request**

*URL*

*POST <http://localhost:8080/payment/v1/pay>*

*Content-type : application/json*

*Charset : UTF-8*

**Parameter**

  |Name         |Type      |Description                                                                                        |Required|
  |------------ |--------- |-------------------------------------------------------------------------------------------------- |----------|
  |amount       |Integer   |결제 금액 100원 이상 10억 이하만 가능                                                              |O|
  |tax          |Integer   |부가가치세 결제 금액보다 클수없으며 전달 하지 않을 경우 자동으로 부가가치세를 계산하여 셋팅한다.   |X|
  |cardNumber   |String    |카드번호는 최소 10자리 최대 20자리 입니다.                                                         |O|
  |cardExpire   |String    |카드 유효기간 4자리입니다.                                                                         |O|
  |cardCvc      |String    |카드 CVC 3자리 입니다.                                                                             |O|
  |cardQuota    |Integer   |할부기간은 0\~12 개월만 가능합니다.                                                                |O|

**-\>Response**

  |Key             |Type     |Description|
  |--------------- |-------- |--------------------------------------|
  |resultCode      |String   |응답 코드|
  |resultMessage   |String   |응답 메시지|
  |amount          |int      |결제 승인 금액|
  |uniqueId        |String   |결제 고유ID (20자리)|
  |status          |String   |결제 상태|
  |applDate        |String   |승인 TIMESTATP (yyyy-MM-dd HH:mm:ss)|

**3. 거래 조회 API 명세서**

결제의 상태 및 정보를 조회 한다.

**-\>Request**

*URL*

*GET <http://localhost:8080/payment/v1/pay>*

*Charset : UTF-8*

**Parameter**

  |Name       |Type     |Description                           |Required|
  |---------- |-------- |------------------------------------- |----------|
  |uniqueId   |String   |결제시 전달 받은 uniqueId (20 자리)   |O|

**-\>Response**

  |Key            |Type     |Description|
  |-------------- |-------- |---------------------------------------------|
  |uniqueId       |String   |결제 고유ID (20자리)|
  |status         |String   |결제 상태|
  |amount         |int      |결제 금액|
  |tax            |int      |부가가치세|
  |cancelAmount   |int      |취소된 금액|
  |cancelTax      |int     |취소된 부가가치세|
  |cardNumber     |String   |암호화된 카드 번호|
  |cardExpire     |String   |암호화된 카드 유효기간|
  |cardCvc        |String   |암호화된 카드 CVC|
  |cardQuota      |int      |할부개월|
  |applDate       |String   |승인 TIMESTATP (yyyy-MM-dd HH:mm:ss)|
  |cancelDate     |String   |마지막 취소 TIMESTATP (yyyy-MM-dd HH:mm:ss)|

**4. 취소 및 부분취소 API**

결제 승인 된 원 금액을 amount로 전달하면 전체 취소가 되며 부가가치세를
전체 취소합니다. 결제제 승인 된 금액 이하를 전달하면 부분 취소되며
부가가치세 전달 유무에 따라 자동 계산하여 취소합니다.

부가가치세는 결제금액보다 클 수 없습니다.

부가가치세를 전달 하지 않을경우 결제금액/11, 소수점이하 반올림으로
계산됩니다.

주의 -- 동일한 uniqueId 가 취소 중일 때 해당 uniqueId 취소를 진행 할 수
없습니다.

**-\>Request**

*URL*

*POST <http://localhost:8080/payment/v1/pay/cancel>*

*Content-type : application/json*

*Charset : UTF-8*

**Parameter**


| Name     | Type    | Description                       | Required |
|----------|---------|-----------------------------------|----------|
| amount   | Integer | 결제 금액 보다 클수 없고 10 억 이하   | O        |
| tax      | Integer | a\. 전체 취소 -- 부가가치세 결제  금액보다 클수없으며 전달 하지 않을 경우 전체 부가가치세를 취소한다. b\. 부분 취소 - 부가가치세 결제 금액보다 클수없으며 부분 취소 된 후 남아있는 부가가치세보다 클수없다. 전달 하지 않을 경우 자동으로 부가가치세를 계산하여 셋팅한다. | X        |
| uniqueId | String  | 결제 고유ID (20자리)              | O        |

**-\>Response**

  |Key              |Type     |Description|
  |---------------- |-------- |--------------------------------------|
  |resultCode       |String   |응답 코드|
  |resultMessage    |String   |응답 메시지|
  |amount           |int      |결제 취소된 금액|
  |tax              |int      |결제 취소된 부가가치세|
  |uniqueId         |String   |원거래 고유ID|
  |cancelUniqueId   |String   |취소 거래 고유 ID|
  |status           |String   |결제 상태|
  |cancelDate       |String   |취소 TIMESTATP (yyyy-MM-dd HH:mm:ss)|

**5. 응답코드**

**HttpStatusCode**

  |HttpStatus   |Description|
  |------------ |--------------------------------------------------------------------------------------------------------------------------|
  |200          |정상응답 및 비즈니스 에러 시 응답한다. 에러코드를 응답 할 수 있으니 반드시 resultCode를 확인해야 합니다.(결제 조회 제외)|
  |204          |결제 조회시 거래가 없을 경우 응답한다. responseBody가 응답 되지 않습니다.|
  |400          |요청 파라미터 오류일 경우 응답 합니다. resultCode 및 resultMessage를 확인 하시어 올바른 요청을 하여야 합니다.|
  |415          |올바른 MediaType이 아닐경우 응답합니다. ContentType 을application/json으로 설정 하여야 합니다.|
  |500          |API 서버 내부에 시스템에 오류가 있을 경우 응답합니다.|

**responseCode** -- 동일 한 resultCode 여도 resultMessage 세부사항에
따라 다를 수 있습니다.

  |resultCode   |Description|
  |------------ |--------------------------------------------------------------------------|
  |R000         |SUCCESS|
  |ER001        |요청 파라미터가 올바르지 않습니다.|
  |ER002        |내부 시스템에 에러가 있습니다. 담당자에게 확인 부탁드립니다.|
  |ER003        |카드사 결제 실패.|
  |ER004        |남아 있는 승인 금액 보다 취소 요청 금액이 큽니다.|
  |ER005        |취소부가세가 원 거래부가세 보다 큽니다.|
  |ER006        |해당 원 거래가 존재 하지 않습니다.|
  |ER007        |남이 있는 부가기치세를 정확히 요청 하여주세요.|
  |ER008        |해당 카드로 진행중인 거래가 있습니다. 완료 후 거래 진행 하여 주세요.|
  |ER009        |해당 고유번호로 진행중인 취소가 있습니다. 완료 후 취소 진행 하여 주세요.|
  |ER010        |올바른 DATA가 아닙니다.

거래 status : 거래 조회 및 거래 요청 응답시 상태를 반드시 확인하세요.

  |status         |Description|
  |-------------- |-------------------------------------------|
  |READY          |결제 승인 , 취소 , 부분취소 준비상태|
  |PAY            |결제 승인 상태|
  |CANCEL         |전체 취소 상태|
  |PRTCCANCEL     |부분 취소 상태|
  |CANCEL\_FAIL   |취소 및 부분 취소 실패 시 응답하는 status|

**6. 문제 해결 전략**

**a. LOG :** Filter 를 통하여 request , response log를 logging 하였으며
요청 에 대한 트랜잭션을 구분 하여 logging 하기 위해 임의 transactionId를
UUID 로 셋팅 하여 custom loggin 하였습니다.

**b. ERRORHANDLE :** \@RestControllerAdvice 통하여 에러 핸들 하였으며
customException 을 만들었으며 에러 catch 시 customExcpetion을 생성하여
controller 까지 throws 하였습니다. 에러 응답 코드 및 에러 메시지는 enum
으로 관리하였습니다.

**c. 카드 결제 API :** 파라미터 바인딩 validation을 javax validation을
사용하였으며 javax validation으로 불가능한 validation은 {
ex)tax(부가가치세) 계산} 파라미터 초기화 메소드 통해 validation 실패시
BAD\_REQUEST 에러 핸들 하였습니다.

거래 요청시 과제로 요구 되는 카드번호 , 카드유효기간, 카드CVC 는
KISA\_SEED\_CBC 모듈을 통해 암호화 하였습니다.

uniqueId 는 UUID 생성 하여 20자리로 잘라서 사용하였으며 카드사 저장 카드
거래를 관리하는 TABLE (CARD\_PAYMENT\_TRANS) 에 status를 READY 상태로
insert 하였으며 카드사 DB 저장 성공시 상태를 PAY로 업데이트 하였습니다.

동일한 카드 중복 결제를 막기 위해 AOP 를 활용하여 카드결제 서비스 메소드
전 primay key 설정되어 있는 TABLE(PAYMENT\_TRANSACTION\_LOCK) 에
카드번호 카드 유효기간 카드 CVC를 합쳐서 SEED\_CBC 로 암호화하여 KEY
값으로 저장하였고 서비스 메소드 실행 후 해당 table에 입력되어 있는 key
를 delete 하여 한 카드로 동시 결제를 방어하였습니다.

**d. 거래 조회 API** : uniqueId로 TABLE (CARD\_PAYMENT\_TRANS) 을
조회하여 정보를 조회하며 암호화된 값들 복호화 하여 응답합니다.
카드번호는 과제 조건으로 마스킹 처리 하였습니다.

거래가 없을경우 customException 으로 throw 하였으며 httpStatusCode 204
로 응답 하게 하였습니다.

**e. 취소 및 부분 취소 API** : 해당 url을 통해 결제 금액에 따라 취소 및
부분 취소를 판단하여 처리 하였으며 취소 요청 금액과 결제 금액이
일치할경우 전체 취소 처리 하였습니다. 부분 취소시 취소 가능 금액 및 취소
가능 부가가치세를 구하여 가능 금액 초과시 에러 처리하였습니다.

취소 거래이력을 쌓기 위해 CARD\_CANCEL\_TRANS에 취소 이력을 쌓았습니다.

취소와 부분취소는 status를 통해 구분 하였습니다.

한 개의 결제 건에 대해 전체 취소 및 부분취소를 동시에 할 수 없게 하기
위해 TABLE(PAYMENT\_TRANSACTION\_LOCK) 에 AOP를 활용하여 uniqueId를
insert 및 delete 하였습니다.

**테스트 케이스**

*결제 : 하나의 카드번호로 동시에 결제를 할 수 없습니다.*

*전체취소 : 결제 한 건에 대한 전체취소를 동시에 할 수 없습니다.*

*부분취소 : 결제 한 건에 대한 부분취소를 동시에 할 수 없습니다.*

**위 테스트에 대해서 springboot 로 webappliation을 구동 시킨 후 Project02MultiThreadCancel.java, Project02MultiThreadPay1.java 로 runnable interface를 상속받아 HttpURLConnection 으로 직접 구동중인 로컬 호스트 테스트 하여 검증 하였습니다.**

**7. 테이블 명세서**

*TABLE : PAYMENT\_TRANSACTION\_LOCK (거래 트랜잭션 제어 테이블)*

*COLUMN :*

*NO BIGINT AUTO\_INCREMENT, (시퀀스)*

*TRANSACTION\_KEY VARCHAR2(300) NOT NULL PRIMARY KEY, (제어될 결제
고유ID혹은 , 카드정보)*

*TRANSACTION\_ID VARCHAR2(100) NOT NULL, 로깅되는 트랜잭션 ID*

*REGIST\_DATE TIMESTAMP 현재시간*

*TABLE : CARD\_PAYMENT\_TRANS (거래 관리 테이블)*

*COLUMN :*

*NO BIGINT AUTO\_INCREMENT, (시퀀스)*

*UNIQUE\_ID VARCHAR2(20) PRIMARY KEY, (거래고유ID)*

*STATUS VARCHAR2(10), (거래 상태)*

*AMOUNT INT NOT NULL, (결제 금액)*

*TAX INT NOT NULL, (결제 부가가치세)*

*ENC\_CARD\_NUMBER VARCHAR2(100) NOT NULL, (암호화된 카드번호)*

*ENC\_CARD\_EXPIRE VARCHAR2(100) , (암호화된 카드 유효기간)*

*ENC\_CARD\_CVC VARCHAR2(100) , (암호화된 카드 CVC)*

*CARD\_QUOTA INT, (할부개월)*

*TRANSACTION\_ID VARCHAR2(100) NOT NULL, (로깅 하는 트랜잭션 ID)*

*REGIST\_DATE TIMESTAMP, (등록시간)*

*UPDATE\_DATE TIMESTAMP, (업데이트 시간)*

*APPL\_DATE TIMESTAMP, (결제 승인 시간)*

*CANCEL\_DATE TIMESTAMP 결제 취소 시간)*

*TABLE : CARD\_CANCEL\_TRANS (거래 취소 테이블)*

*COLUMN :*

*NO BIGINT AUTO\_INCREMENT, (시퀀스)*

*CANCEL\_UNIQUE\_ID VARCHAR2(20) PRIMARY KEY, (취소 고유 ID)*

*STATUS VARCHAR2(10), (거래 상태)*

*AMOUNT INT NOT NULL, (취소 및 취소요청 금액)*

*TAX INT NOT NULL, (취소 및 취소 요청 부가가치세)*

*UNIQUE\_ID VARCHAR2(20) NOT NULL, (원 거래 고유 ID)*

*TRANSACTION\_ID VARCHAR2(100) NOT NULL, (로깅 하는 트랜잭션 ID)*

*REGIST\_DATE TIMESTAMP, (등록시간)*

*UPDATE\_DATE TIMESTAMP, (업데이트 시간)*

*CANCEL\_DATE TIMESTAMP (취소 시간)*

*TABLE : CARD\_COMPANY\_TRANS(카드사 저장 테이블)*

*COLUMN :*

*NO BIGINT AUTO\_INCREMENT, (시퀀스)*

*REQUEST\_DATA VARCHAR2(4000) (카드사로 요청된 DATA*
