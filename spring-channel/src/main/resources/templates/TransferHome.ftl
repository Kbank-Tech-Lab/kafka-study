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

    <#-- 유저 리스트 -->
    <ul class="list-group">
        <li class="list-group-item d-flex justify-content-between" v-for="user in users">
            <span>
              {{user.id}} - {{user.name}} - {{user.depositAccountId}}
            </span>
            <button @click="clickShowHistory(user.depositAccountId})">계좌 히스토리 보기</button>
        </li>
    </ul>
</div>

<!-- JavaScript -->
<script src="/webjars/vue/2.6.14/dist/vue.min.js"></script>
<script src="/webjars/axios/0.21.1/dist/axios.min.js"></script>
<script src="/webjars/sockjs-client/1.5.1/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/2.3.4/stomp.min.js"></script>
<script>
    var vm = new Vue({
        el: '#app',
        data: {
            users: [],
            depositAccountTransferLogs: [],
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
                        console.log(error);
                    });
            }
        }
    })
</script>


</body>
</html>