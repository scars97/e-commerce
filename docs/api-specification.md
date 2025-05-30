* [상품 조회](#상품-조회)
* [인기 상품 조회](#인기-상품-조회)
* [주문](#주문)
* [결제](#결제)
* [쿠폰 발급](#쿠폰-발급)
* [포인트 충전](#포인트-충전)
* [포인트 조회](#포인트-조회)

## 상품 조회

### EndPoint
```http request
GET http://localhost:8080/api/items
```

### Query String Parameter
| name | description | type | require | example |
|------|------------|-----|-------|--------|
| page | 페이지        | Int | X | 0 ~ n  |
| size | 페이지 당 건수   | Int | X | 5      | 
```http request
GET http://localhost:8080/api/items?page=0&size=10
```

### Response
| name           | description | type | require | example |
|----------------|-------------|------|---------|---------|
| currentPerPage | 페이지 별 데이터   | Int  | O       | 10      |
| currentPage    | 현재 페이지      | Int  | O       | 0       |
| totalPages     | 전체 페이지      | Int  | O       | 1 ~ n   |
| totalElements  | 전체 데이터      | Long | O       | 1 ~ n   |
| content          | 상품 목록 데이터   | List | O       |         |

### content
| name      | description | type    | require | example           |
|-----------|-------------|---------|---------|-------------------|
| itemId    | 상품 ID       | Long    | O       | 1                 |
| category  | 카테고리 ID     | Long    | O       | 1                 |
| name      | 상품명         | String  | O       | 오버핏 반팔티           |
| price     | 상품 가격       | Decimal | O       | 12000             |
| thumbnail | 상품 이미지      | String  | O       | https://test.png  |
| status    | 판매 상태       | String  | O       | SELLING, SOLD_OUT |

```json
// 200 Ok
{
  "currentPerPage": 10
  "currentPage": 1
  "totalPages": 50
  "totalElements": 1000
  "content": [
    {
      "itemId": 1,
      "categoryId": 1
      "name": "오버핏 반팔티",
      "price": 12000,
      "thumbnail": "https://test.png",
      "status": "SELLING" 
    }
  ]
}
```

## 인기 상품 조회

### EndPoint
```http request
GET http://localhost:8080/api/items/popular
```

### Query String Parameter
| name   | description | type | require | example  |
|--------|-------------|------|-------|----------|
| period | 기간          | Long | X | 3, 7, 30 | 
```http request
GET http://localhost:8080/api/items/popular?period=3
```

### Response
| name            | description | type | require | example           |
|-----------------|-------------|------|---------|-------------------|
| rank            | 인기 순위       | Int  | O       | 1 ~ n             |
| cumulativeSales | 누적 판매량      | Long | O       | 1 ~ n             |
| item            | 상품 정보       | Item | O       |              |

### Item
| name            | description | type    | require | example           |
|-----------------|-------------|---------|---------|-------------------|
| itemId          | 상품 ID       | Long    | O       | 1                 |
| name            | 상품명         | String  | O       | 오버핏 반팔티           |
| price           | 상품 가격       | Decimal | O       | 12000             |
| thumbnail       | 상품 이미지      | String  | O       | https://test.png  |
| status          | 판매 상태       | String  | O       | SELLING, SOLD OUT |

```json
// 200 Ok
[
  {
    "rank": 1,
    "cumulativeSales": 25000,
    "item": {
      "itemId": 1,
      "name": "오버핏 반팔티",
      "price": 12000,
      "thumbnail": "https://test.png",
      "status": "SELLING"
    }
  }
]
```

## 주문

### EndPoint
```http request
POST http://localhost:8080/api/orders
Content-Type: application/json
```

### Request Body
| name       | description | type       | require | example   |
|------------|-------------|------------|---------|-----------|
| userId     | 사용자 ID      | Long       | O       | 1         |
| couponId   | 쿠폰 ID       | Long       | X       | 1 or null |
| orderItems | 주문 상품 목록    | List | O       |           |

### orderItems
| name     | description | type       | require | example |
|----------|-------------|------------|---------|---------|
| itemId   | 상품 ID       | Long       | O       | 1       |
| quantity | 주문 수량       | Long       | O       | 1 ~ n   |

```json
{
  "userId": 1,
  "couponId": 1,
  "orderItems": [
    {
      "itemId": 1,
      "quantity": 2
    },
    {
      "itemId": 2,
      "quantity": 1
    }
  ]
}
```

### Response
| name          | description | type    | require | example               |
|---------------|-------------|---------|---------|-----------------------|
| orderId       | 주문 ID       | Long    | O       | 1                     |
| couponId      | 쿠폰 ID       | Long    | X       | 1 or null             |
| userId        | 사용자 ID      | Long    | O       | 1                     |
| originPrice   | 주문 금액       | Decimal    | O       | 12000                 |
| discountPrice | 할인 금액       | Decimal | X       | 3000                  |
| totalPrice    | 최종 금액       | Decimal | O       | 9000                  |
| status        | 주문 상태       | String  | O       | ORDERED, PAID, CANCEL |
| orderItems    | 주문 상품 정보    | List    | O       |                       |

### orderItems
| name     | description | type    | require | example |
|----------|-------------|---------|---------|---------|
| itemId   | 상품 ID       | Long    | O       | 1       |
| quantity | 주문 수량       | Long    | O       | 1 ~ n   |

```json
// 201 Created
{
  "orderId": 1,
  "couponId": 1,
  "userId": 1,
  "originPrice": 12000,
  "discountPrice": 3000,
  "totalPrice": 9000,
  "status": "ORDERED",
  "orderItems": [
    {
      "itemId": 1,
      "quantity": 2
    },
    {
      "itemId": 2,
      "quantity": 3
    }
  ]
}
```

### Error

```json
// 400 Bad Request
{
  "errorCode": "INVALID_COUPON",
  "message": "만료되었거나 사용할 수 없는 쿠폰입니다."
}

// 404 Not Found
{
  "errorCode": "ITEM_NOT_FOUND",
  "message": "존재하지 않는 상품입니다."
}

// 409 Conflict
{
  "errorCode": "OUT_OF_STOCK",
  "message": "요청하신 상품의 재고가 부족하여 주문할 수 없습니다."
}
```

## 결제

### EndPoint
```http request
POST http://localhost:8080/api/payments
Content-Type: application/json
```

### Request Body
| name    | description | type    | require | example |
|---------|-------------|---------|---------|---------|
| userId  | 사용자 ID      | Long    | O       | 1       |
| orderId | 주문 ID       | Long    | O       | 1       |
| price   | 결제 금액       | Decimal | O       | 12000   |

```json
{
  "userId": 1,
  "orderId": 1,
  "price": 12000
}
```

### Response
| name      | description | type    | require | example      |
|-----------|-------------|---------|---------|--------------|
| paymentId | 결제 ID       | Long    | O       | 1            |
| orderId   | 주문 ID       | Long    | O       | 1            |
| userId    | 사용자 ID      | Long    | O       | 1            |
| price     | 결제 금액       | Decimal | O       | 12000        |
| status    | 결제 상태       | String  | O       | PAID, CANCEL |

```json
// 201 Created
{
  "paymentId": 1,
  "orderId": 1,
  "userId": 1,
  "price": 12000,
  "status": "PAID"
}
```

### Error
```json
// 400 Bad Request
{
  "errorCode": "INVALID_PRICE",
  "message": "결제 금액이 맞지 않습니다."
}

// 404 Not Found
{
  "errorCode": "OREDER_NOT_FOUND",
  "message": "주문 내역을 찾을 수 없습니다."
}

// 422 Unprocessable Entity
// 요청도 이해하고 문법도 올바르지만 
// 유저의 상태가 조건을 만족하지 않아 결제를 처리할 수 없음
// 유저가 조건에 맞도록 상태를 수정할 수 있다면 정상적으로 요청 처리
{
  "errorCode": "INSUFFICIENT_POINT",
  "message": "잔액이 부족합니다."
}
```

## 쿠폰 발급

### EndPoint
```http request
POST http://localhost:8080/api/coupons/first-come
Content-Type: application/json
```

### Request Body
| name     | description | type | require | example |
|----------|-------------|------|---------|---------|
| couponId | 쿠폰 ID       | Long | O       | 1       |
| userId   | 사용자 ID      | Long | O       | 1       |

```json
{
  "couponId": 1
  "userId": 1
}
```

### Response
| name          | description | type          | require | example              |
|---------------|-------------|---------------|---------|----------------------|
| couponId      | 쿠폰 ID       | Long          | O       | 1                    |
| title         | 쿠폰명         | varchar       | O       | 선착순 쿠폰               |
| type          | 할인 쿠폰 종류    | String        | O       | RATE(%), AMOUNT(won) |
| discount      | 할인 금액       | Long          | O       | 30, 3000             |
| expirationDay | 유효 일수       | Int           | O       | 30                   |
| issuedAt      | 발급 일자       | LocalDateTime | O       | 2025-04-01 16:00:00  |
| expireAt      | 만료 기간       | LocalDateTime | O       | 2025-04-30 16:00:00  |

```json
// 201 Created
{
  "couponId": 1,
  "title": "선착순 쿠폰",
  "type": "RATE",
  "discount": 30,
  "expirationDay": 30,
  "issuedAt": "2025-04-01 16:00:00",
  "expireAt": "2025-04-30 16:00:00"
}
```

### Error
```json
// 404 Not Found
{
  "errorCode": "USER_NOT_FOUND",
  "message": "사용자를 찾을 수 없습니다."
}

// 409 Conflict
{
  "errorCode": "COUPONS_ARE_EXHAUSTED",
  "message": "쿠폰이 모두 소진되었습니다."
}
```

## 포인트 충전

### EndPoint
```http request
POST http://localhost:8080/api/points
Content-Type: application/json
```

### Request Body
| name   | description | type    | require | example |
|--------|-------------|---------|---------|---------|
| userId | 사용자 ID      | Long    | O       | 1       |
| price  | 충전 금액       | Decimal | O       | 10000   |

```json
{
  "userId": 1,
  "price": 10000
}
```

### Response
| name           | description | type    | require | example |
|----------------|-------------|---------|---------|---------|
| userId         | 사용자 ID      | Long    | O       | 1       |
| remainingPoint | 포인트 잔액      | Decimal | O       | 15000   |

```json
// 201 Created
{
  "userId": 1,
  "remainingPoint": 15000
}
```

### Error
```json
// 404 Not Found
{
  "errorCode": "USER_NOT_FOUND",
  "message": "사용자를 찾을 수 없습니다."
}
```

## 포인트 조회

### EndPoint
```http request
GET http://localhost:8080/api/points/{userId}
```

### Request Parameter
| name   | description | type    | require | example |
|--------|-------------|---------|---------|---------|
| userId | 사용자 ID      | Long    | O       | 1       |
```http request
GET http://localhost:8080/api/points/1
```

### Response
| name   | description | type    | require | example |
|--------|-------------|---------|---------|---------|
| userId | 사용자 ID      | Long    | O       | 1       |
| remainingPoint  | 포인트 잔액      | Decimal | O       | 15000   |

```json
// 200 Ok
{
  "userId": 1,
  "remainingPoint": 15000
}
```

### Error

```json
// 404 Not Found
{
  "errorCode": "USER_NOT_FOUND",
  "message": "사용자를 찾을 수 없습니다."
}
```