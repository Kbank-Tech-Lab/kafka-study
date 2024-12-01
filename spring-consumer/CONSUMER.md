# Consumer

## Consumer 흐름도
<img width="1020" alt="image" src="https://github.com/user-attachments/assets/535f5ce3-2db2-4a59-9e11-71b53127c023">

1. Kafka로부터 지연이체 건들을 수신한다.
2. DB로부터 각 지연이체 건의 상태를 조회한다.
3. 상태가 `pending`일 경우 
   1. 지연이체 API를 호출한다.
   2. DB에 지연이체 건의 상태를 `in progress`로 변경한다.