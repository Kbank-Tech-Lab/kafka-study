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
              {{depositAccountTransferLog.id}} - {{depositAccountTransferLog.name}}
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
        <li class="list-group-item d-flex justify-content-between" v-for="user in users">
            <span>
              {{user.id}} - {{user.name}} - {{user.depositAccountId}}
            </span>
            <button @click="clickShowHistory(user.depositAccountId)">계좌 히스토리 보기</button>
            <button @click="clickSetSender(user.id)">송금자로 세팅</button>
            <button @click="clickSetReceiver(user.id)">입금자로 세팅</button>
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
            users: [],
            depositAccountTransferLogs: [],
            // 송금 API 요청용 데이터
            transferSender: null,
            transferReceiver: null,
            transferAmount: null
        },
        created: function () {
            console.log("created")
        },
        methods: {
            clickShowHistory: function (depositAccountId) {
                // 초기화
                this.depositAccountTransferLogs = [];
                // 계좌 히스토리 조회
                axios.get('/api/depositAccountTransferLogs/' + depositAccountId)
                    .then(response => {
                        this.depositAccountTransferLogs = response.data;
                    })
                    .catch(error => {
                        alert('계좌 히스토리 조회 요청 실패');
                        console.log(error);
                    });
            },
            clickSetSender: function (depositAccountId) {
                this.transferSender = depositAccountId;
            },
            clickSetReceiver: function (depositAccountId) {
                this.transferReceiver = depositAccountId;
            },
            requestTransfer : function() {
                // 입력값 검증
                if (this.validateTransfer() == false) {
                    return;
                }
                // 송금API 요청(멱등성 이슈는 생각하지 않음)
                axios.post('/api/transfer', {
                    sender: this.transferSender,
                    receiver: this.transferReceiver,
                    amount: 1000
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
                if (this.transferSender == null) {
                    alert('송금자를 선택해주세요');
                    return false;
                }
                if (this.transferReceiver == null) {
                    alert('입금자를 선택해주세요');
                    return false;
                }
                if (this.transferAmount == null || this.transferAmount <= 0) {
                    alert('양수값의 송금액을 입력해주세요');
                    return false;
                }
                return true;
            }
        }
    })
</script>


</body>
</html>