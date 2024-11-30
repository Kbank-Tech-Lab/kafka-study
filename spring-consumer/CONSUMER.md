# Consumer

## Consumer 흐름도  

---
<img width="1441" alt="image" src="https://github.com/user-attachments/assets/28aad71c-2d0d-49f0-b585-0940f714d5c9">

1. Kafka로부터 지연이체 건들을 수신한다.
2. DB로부터 각 지연이체 건의 상태를 조회한다.
3. 상태가 `pending`일 경우 지연이체 API를 호출한다.