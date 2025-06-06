-- 상품 테이블
CREATE TABLE GOODS_DTL (
    GOODS_ID BIGINT
    , GOODS_NM VARCHAR(255)
    , SALE_PRICE BIGINT
    , DISCOUNT_PRICE BIGINT
    , STOCK_QTY INTEGER
    , PRIMARY KEY (GOODS_ID)
);

-- 주문 테이블
CREATE TABLE ORDER_DTL (
    ORDER_NUM BIGINT
    , ORDER_SEQ INTEGER
    , ORDER_QTY INTEGER
    , GOODS_ID BIGINT
    , GOODS_NM VARCHAR(255)
    , ORDER_STATUS VARCHAR(5)
    , SALE_PRICE BIGINT
    , DISCOUNT_PRICE BIGINT
    , ORDER_DTTM DATETIME
    , PRIMARY KEY (ORDER_NUM, ORDER_SEQ)
);

-- 주문 시퀀스 생성
CREATE SEQUENCE "ORDER_NUM_SEQ"
MINVALUE 1
MAXVALUE 999999999
INCREMENT BY 1
START WITH 1
NOCACHE;


-- 결제 테이블
CREATE TABLE PAY_DTL (
    ORDER_NUM BIGINT
    , PAY_SEQ INTEGER
    , PAY_AMOUNT BIGINT
    , PRIMARY KEY (ORDER_NUM, PAY_SEQ)
);
