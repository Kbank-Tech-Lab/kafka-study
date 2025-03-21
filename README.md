# kafka-study


### 스터디 주차별 학습 <br>
1주차 카프카 이론 공부 <br>
2주차 개발환경 세팅 / 명세서 작성 <br>
3주차 기능개발에 주력한 서비스 개발 / 테스트(기능+성능) <br>
4주차 고도화 <br>


### 멤버

[🦒 강희원](https://github.com/investing-life)  
[🐥 박정은](https://github.com/jungeunevepark)  
[🐮 조상현](https://github.com/Cho-SangHyun)  
[🐬 최훈진](https://github.com/hunjin-choi) <br>


### ERD
**https://www.erdcloud.com/d/F4pkcGhThyksWxswp** <br>
![image](https://github.com/user-attachments/assets/a410311d-6840-4bff-bb01-f38686ed706d)<br>



### CORE-BANKING ERROR
| **코드 그룹**           | **에러 코드**             | **메시지**                                  |
|-------------------------|---------------------------|---------------------------------------------|
| **시스템 관련 에러**      | SYS0001                  | A system error occurred                     |
|                         | SYS0002                  | Invalid parameter                           |
| **계좌 관련 에러**        | COR0001                  | Account not found                           |
| **고객 관련 에러**        | COR1001                  | Customer not found                          |
| **송금 관련 에러**        | COR2001                  | Insufficient account balance                |
|                         | COR2002                  | Transfer to same account                    |
|                         | COR2003                  | Transfer to other bank                      |
| **은행 관련 에러**        | COR3001                  | Bank not found                              |
|                         | COR3002                  | Bank under maintenance                      |
|                         | COR3003                  | Other bank transfer timed out               |
|                         | COR3004                  | Bank transfer was interrupted               |
| **지연이체 관련 에러**    | COR4001                  | Delayed transfer not found                  |
