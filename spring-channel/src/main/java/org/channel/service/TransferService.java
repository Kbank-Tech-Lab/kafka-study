package org.channel.service;

import org.channel.domain.Customer;
import org.channel.domain.DelayedTransferRequest;
import org.channel.domain.DepositAccount;
import org.channel.domain.TransferLog;
import org.channel.dto.DepositAccountDto;
import org.channel.repository.CustomerRepository;
import org.channel.repository.DepositAccountRepository;
import org.channel.repository.TransferLogRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransferService {
    private final CoreBankingApiClient coreBankingApiClient;
    private final CustomerRepository customerRepository;
    private final DepositAccountRepository depositAccountRepository;
    private final TransferLogRepository transferLogRepository;

    public TransferService(CoreBankingApiClient coreBankingApiClient, CustomerRepository customerRepository, DepositAccountRepository depositAccountRepository, TransferLogRepository transferLogRepository) {
        this.coreBankingApiClient = coreBankingApiClient;
        this.customerRepository = customerRepository;
        this.depositAccountRepository = depositAccountRepository;
        this.transferLogRepository = transferLogRepository;
        // insertTestData();
    }
    public List<TransferLog> getTransferLogsByFromAccountId(UUID accountId, Integer curPage, Integer pageCount) {
        // 계좌이체내역 조회
        return transferLogRepository.findTransferLogsByFromAccountId(accountId, PageRequest.of(curPage, pageCount));
    }

    public List<TransferLog> getTransferLogsByToAccountId(UUID accountId, Integer curPage, Integer pageCount) {
        // 계좌이체내역 조회
        return transferLogRepository.findTransferLogsByToAccountId(accountId, PageRequest.of(curPage, pageCount));
    }

    public List<TransferLog> getTransferLogsByAccountId(String accountNumber) {
        // 계좌이체내역 조회
        return transferLogRepository.findTransferLogsByAccountId(accountNumber);
    }

    public List<DepositAccountDto> getDepositAccountDtoList() {
        return depositAccountRepository.getDepositAccountDtoList();
    }

    public String registerDelayedTransfer(DelayedTransferRequest request) {
        // core-banking 서버에 지연이체등록API 전송
        HttpStatusCode responseStatusCode = coreBankingApiClient.registerDelayedTransfer(request);

        if (responseStatusCode.is2xxSuccessful()) {
            return "00";
        } else if (responseStatusCode.isError()){
            throw new RuntimeException("지연이체등록 실패 (네트워크 오류 혹은 core-banking 오류)");
        } else {
            throw new RuntimeException("지연이체등록 실패 (http 응답코드가 비정상)");
        }
    }

    private void insertTestData() {
        Customer customer1 = new Customer("customer1");
        Customer customer2 = new Customer("customer2");
        Customer customer3 = new Customer("customer3");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        DepositAccount depositAccount1 = new DepositAccount(customer1, "00000020005323", 1000L);
        DepositAccount depositAccount2 = new DepositAccount(customer2, "00000020005324", 2000L);
        DepositAccount depositAccount3 = new DepositAccount(customer3, "00000020005325", 3000L);
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
