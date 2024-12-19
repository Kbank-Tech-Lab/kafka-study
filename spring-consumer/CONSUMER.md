# Consumer

## Consumer 흐름도
<img width="1367" alt="image" src="https://github.com/user-attachments/assets/8ba62386-163e-4fa1-ac33-ea14a7fa74b0">

1. Kafka로부터 지연이체 건들을 수신한다.
2. DB로부터 각 지연이체 건의 상태를 조회한다.
3. 상태가 `pending`일 경우 
   1. DB에 지연이체 건의 상태를 `in progress`로 변경한다.
   2. 지연이체 API를 호출한다.
   3. 성공/실패에 따라 DB에 상태를 업데이트한다
   