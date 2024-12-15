package org.channel.service;

import lombok.RequiredArgsConstructor;
import org.channel.dto.Customer;
import org.channel.dto.DelayedTransferRequest;
import org.channel.dto.DepositAccount;
import org.channel.dto.TransferLog;
import org.channel.repository.CustomerRepository;
import org.channel.repository.DepositAccountRepository;
import org.channel.repository.TransferLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final ApiClient apiClient;
    private final CustomerRepository customerRepository;
    private final DepositAccountRepository depositAccountRepository;
    private final TransferLogRepository transferLogRepository;

    public List<TransferLog> getTransferLogsByFromAccountId(UUID accountId, Integer curPage, Integer pageCount) {
        // 계좌이체내역 조회
        return transferLogRepository.findTransferLogsByFromAccountId(accountId, PageRequest.of(curPage, pageCount));
    }

    public List<TransferLog> getTransferLogsByToAccountId(UUID accountId, Integer curPage, Integer pageCount) {
        // 계좌이체내역 조회
        return transferLogRepository.findTransferLogsByToAccountId(accountId, PageRequest.of(curPage, pageCount));
    }

    public List<TransferLog> getTransferLogsByAccountId(UUID accountId) {
        // 계좌이체내역 조회
        return transferLogRepository.findTransferLogsByAccountId(accountId);
    }

    public String registerDelayedTransfer(DelayedTransferRequest request) {
        // producer 서버에 지연이체 요청
        apiClient.registerDelayedTransfer(request);
        // 지연이체등록 성공시 성공 메시지 리턴
        return "00";
    }

    public void insertTestData() {
        Customer customer1 = new Customer("customer1");
        Customer customer2 = new Customer("customer2");
        Customer customer3 = new Customer("customer3");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        DepositAccount depositAccount1 = new DepositAccount(customer1, "000000020005323", 1000L);
        DepositAccount depositAccount2 = new DepositAccount(customer2, "000000020005324", 2000L);
        DepositAccount depositAccount3 = new DepositAccount(customer3, "000000020005325", 3000L);
        depositAccountRepository.save(depositAccount1);
        depositAccountRepository.save(depositAccount2);
        depositAccountRepository.save(depositAccount3);

        TransferLog transferLog1 = TransferLog.of(depositAccount1.getAccountNumber(), "87", depositAccount2.getAccountNumber(), 500L);
        TransferLog transferLog2 = TransferLog.of(depositAccount2.getAccountNumber(), "87", depositAccount1.getAccountNumber(), 2000L);
        TransferLog transferLog3 = TransferLog.of(depositAccount2.getAccountNumber(), "87", depositAccount3.getAccountNumber(), 1000L);
        TransferLog transferLog4 = TransferLog.of(depositAccount3.getAccountNumber(), "87", depositAccount2.getAccountNumber(), 1500L);
        TransferLog transferLog6 = TransferLog.of(depositAccount1.getAccountNumber(), "87", depositAccount3.getAccountNumber(), 1000L);
        TransferLog transferLog5 = TransferLog.of(depositAccount3.getAccountNumber(), "87", depositAccount1.getAccountNumber(), 2000L);
        transferLogRepository.save(transferLog1);
        transferLogRepository.save(transferLog2);
        transferLogRepository.save(transferLog3);
        transferLogRepository.save(transferLog4);
        transferLogRepository.save(transferLog5);
        transferLogRepository.save(transferLog6);


    }
}
