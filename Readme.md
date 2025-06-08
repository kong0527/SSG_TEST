# SSG.COM 주문서비스 개발팀 사전과제

### 📶 DB 설계

DB: H2 In-memory 사용



1. 상품 테이블

| 컬럼 명           | 한글 명   | 컬럼 타입    |
| ----------------- | --------- | ------------ |
| **GOODS_ID** (PK) | 상품 ID   | BIGINT       |
| GOODS_NM          | 상품 명   | VARCHAR(255) |
| SALE_PRICE        | 판매 가격 | BIGINT       |
| DISCOUNT_PRICE    | 할인 금액 | BIGINT       |
| STOCK_QTY         | 재고      | INTEGER      |

상품 테이블은 과제에 제시된 내용과 동일하게 설계 



2. 주문 테이블

| 컬럼 명            | 한글 명        | 컬럼 타입    |
| ------------------ | -------------- | ------------ |
| **ORDER_NUM** (PK) | 주문 번호      | BIGINT       |
| **ORDER_SEQ** (PK) | 주문 일련 번호 | INTEGER      |
| ORDER_QTY          | 주문 수량      | INTEGER      |
| GOODS_ID           | 상품 ID        | BIGINT       |
| GOODS_NM           | 상품 명        | VARCHAR(255) |
| ORDER_STATUS       | 주문 상태      | VARCHAR(5)   |
| SALE_PRICE         | 상품 판매 가격 | BIGINT       |
| DISCOUNT_PRICE     | 상품 할인 가격 | BIGINT       |
| ORDER_DTTM         | 주문 일시      | DATETIME     |



3. 결제 테이블

| 컬럼 명            | 한글 명        | 컬럼 타입 |
| ------------------ | -------------- | --------- |
| **ORDER_NUM** (PK) | 주문 번호      | BIGINT    |
| **PAY_SEQ** (PK)   | 결제 일련 번호 | INTEGER   |
| PAY_AMOUNT         | 결제 금액      | BIGINT    |



### ▶️ 프로젝트 실행

API 문서화는 swagger를 사용하였음

프로젝트 실행 후, http://localhost:8080/swagger-ui/index.html 접속

**주문 생성 API Req 예시**

```json
[
  {
    "goodsId": 1000000001,
    "orderQty": 1
  },
  {
    "goodsId": 1000000002,
    "orderQty": 2
  }    
]
```

**주문 취소 API Req 예시**

```json
{
  "orderNum": 20250608000001,
  "goodsId": 1000000002
}
```

**주문 목록 조회 API Req 예시**

```
orderNum: 20250608000001
```



### *️⃣ 설계시 고려한 부분

**✅ 응답 값 공통화**

CommonResponse class를 정의하여 백엔드의 응답 값을 resultCode, resultMessage, resultData의 형식으로 공통화 함

resultCode와 resultMessage는 Enum에 등록하여 사용



✅ **주문 생성시 재고 동시성 문제** 

주문 생성시 한 상품에 대하여 동시에 요청이 들어오면 재고가 부족할 때에도 주문이 생성될 위험이 있음

이를 방지하기 위해 FOR UPDATE 문 사용하여 해당 행에 Lock이 걸리도록 설정

```sql
SELECT
    STOCK_QTY
    , SALE_PRICE
    , DISCOUNT_PRICE
    , GOODS_NM
    , GOODS_ID
FROM
    GOODS_DTL
WHERE
    GOODS_ID = #{goodsId}
FOR UPDATE
```

사용자 A가 상품 1번에 대해 주문을 시작 할 때, Lock을 걸고 B가 같은 상품에 대해 주문을 시도하면 A가 락을 걸고 있기 때문에 대기 상태가 됨. 이후 사용자 A가 트랜잭션을 커밋하면 락이 해제되고, B가 주문 생성 가능

주문 생성 API 내에는 @Transactional 을 사용하여 락이 유지 되도록 설정



✅ **주문 상품 조회 API 가격**

주문 상품 조회 API의 totalPrice는 취소되지 않은 상품들에 대한 가격만 반영



✅ **주문 테이블 설계**

주문 테이블은 (ORDER_NUM, ORDER_SEQ) 조합으로 PK 선정

주문 부분 취소 계산에 용이하도록 상품 1개당 row가 한 개씩 생성되며, 각 row당 ORDER_QTY는 무조건 1

ORDER_STATUS를 통해 해당 상품의 주문 상태를 확인 (주문 완료, 취소, 등...)

주문 번호는 yyyyMMdd + 주문 시퀀스(ORDER_NUM_SEQ) 조합으로 채번되도록 설계

ORDER_NUM_SEQ: 1부터 시작, 1씩 증가



✅ **테스트 코드**

테스트는 JUnit5를 사용하여 진행 (ServiceImpl class 대상)

각 API에 대하여 발생할 수 있는 모든 응답 경우의 수에 대해 작성 진행

