<!doctype html>
<html lang="en">
<head>
    <title>Transfer Page</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/5.2.2/dist/css/bootstrap.min.css">
    <style>
        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>
<div class="container" id="app" v-cloak>
    <#-- 계좌 히스토리   -->
    <ul class="list-group" v-if="depositAccountTransferLogs.length > 0">
        <li class="list-group-item d-flex justify-content-between" v-for="depositAccountTransferLog in depositAccountTransferLogs">
            <span>
              이체히스토리 식별자: {{depositAccountTransferLog.id}} 보내는 사람 계좌: {{depositAccountTransferLog.fromAccountNumber}} 받는 사람 은행코드: {{depositAccountTransferLog.toBankCode}} 받는 사람 계좌: {{depositAccountTransferLog.toAccount}} 송금액: {{depositAccountTransferLog.transferAmount}} 요청일시: {{depositAccountTransferLog.createdAt}}, 완료일시: {{depositAccountTransferLog.processedAt}}
            </span>
        </li>
    </ul>
    <div>
        <span>송금자: {{transferSender}}</span>
        <span>입금자: {{transferReceiver}}</span>
        <input type="text" v-model="transferAmount" placeholder="송금액">
        <button @click="requestTransfer">송금</button>
    </div>
    <#-- 유저 리스트 -->
    <ul class="list-group">
        <li class="list-group-item d-flex justify-content-between" v-for="depositAccountDto in depositAccountDtoList">
            <span>
              고객식별자: {{depositAccountDto.customerId}}    고객이름: {{depositAccountDto.customerName}}   고객계좌번호: {{depositAccountDto.accountNumber}}
            </span>
            <button @click="clickShowHistory(depositAccountDto.accountNumber)">계좌 히스토리 보기</button>
            <button @click="clickSetSender(depositAccountDto.accountNumber)">송금자로 세팅</button>
            <button @click="clickSetReceiver(depositAccountDto.accountNumber)">입금자로 세팅</button>
        </li>
    </ul>
</div>

<!-- JavaScript -->
<script src="/webjars/vue/2.6.14/dist/vue.min.js"></script>
<script src="/webjars/axios/0.21.1/dist/axios.min.js"></script>
<script>
    var vm = new Vue({
        el: '#app',
        data: {
            // 화면 노출용 데이터
            depositAccountDtoList: [],
            depositAccountTransferLogs: [],
            // 송금 API 요청용 데이터
            transferSender: null,
            transferReceiver: null,
            transferAmount: null
        },
        created: function () {
            console.log("created")
            // 계좌 리스트 조회
            axios.get('/transfer/depositAccountDtoList')
                .then(response => {
                    this.depositAccountDtoList = response.data;
                })
                .catch(error => {
                    alert('계좌 리스트 조회 요청 실패');
                    console.log(error);
                });
        },
        methods: {
            clickShowHistory: function (depositAccountNumber) {
                // 초기화
                this.depositAccountTransferLogs = [];
                // 계좌 히스토리 조회
                axios.get('/transfer/accountHistory/' + depositAccountNumber)
                    .then(response => {
                        this.depositAccountTransferLogs = response.data;
                    })
                    .catch(error => {
                        alert('계좌 히스토리 조회 요청 실패');
                        console.log(error);
                    });
            },
            clickSetSender: function (depositAccountNumber) {
                this.transferSender = depositAccountNumber;
            },
            clickSetReceiver: function (depositAccountNumber) {
                this.transferReceiver = depositAccountNumber;
            },
            requestTransfer : function() {
                // 입력값 검증
                if (this.validateTransfer() == false) {
                    return;
                }
                // 송금API 요청(멱등성 이슈는 생각하지 않음)
                axios.post('/transfer/register/delayed_transfer', {
                    fromAccount: this.transferSender,
                    toAccount: this.transferReceiver,
                    transferAmount: this.transferAmount,
                    requestedAt: new Date().toISOString() // 날짜형식 확인
                })
                    .then(response => {
                        alert('송금 요청 성공');
                    })
                    .catch(error => {
                        alert('송금 요청 실패');
                        console.log(error);
                    });
            },
            validateTransfer : function() {
                // 일단 기본적인 검증만 진행
                // 이를테면 자기자신에게 송금하는 등의 예외케이스는 생각하지 않음
                // 최훈진의 A계좌에서 B계좌로 송금하는 케이스는 가능?
                if (this.transferSender == null) {
                    alert('송금자를 선택해주세요');
                    return false;
                }
                if (this.transferReceiver == null) {
                    alert('입금자를 선택해주세요');
                    return false;
                }
                if (this.isNumeric(this.transferAmount) == false || this.transferAmount <= 0) {
                    alert('양수값의 송금액을 입력해주세요');
                    return false;
                }
                return true;
            },
            isNumeric : function(n) {
                return !isNaN(parseFloat(n)) && isFinite(n);
            }
        }
    })
</script>


</body>
</html>