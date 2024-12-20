package org.coreBanking.service;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.coreBanking.config.DynamicClock;
import org.coreBanking.model.Bank;
import org.coreBanking.model.Customer;
import org.coreBanking.model.DelayedTransferRequest;
import org.coreBanking.model.DelayedTransferRequest.Status;
import org.coreBanking.model.DepositAccount;
import org.coreBanking.repository.BankRepository;
import org.coreBanking.repository.CustomerRepository;
import org.coreBanking.repository.DelayedTransferRequestRepository;
import org.coreBanking.repository.DepositAccountRepository;
import org.coreBanking.repository.TransferLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceImpl implements TestService {

    private final DynamicClock dynamicClock;
    private final BankRepository bankRepository;
    private final CustomerRepository customerRepository;
    private final DelayedTransferRequestRepository delayedTransferRequestRepository;
    private final DepositAccountRepository depositAccountRepository;
    private final TransferLogRepository transferLogRepository;

    private final int customerNum = 200;
    private final int accountNum = 1000;
    private final int delayedTransferRequestNum = 3000;

    public TestServiceImpl(DynamicClock dynamicClock, BankRepository bankRepository, CustomerRepository customerRepository,
        DelayedTransferRequestRepository delayedTransferRequestRepository,
        DepositAccountRepository depositAccountRepository,
        TransferLogRepository transferLogRepository) {
        this.dynamicClock = dynamicClock;
        this.bankRepository = bankRepository;
        this.customerRepository = customerRepository;
        this.delayedTransferRequestRepository = delayedTransferRequestRepository;
        this.depositAccountRepository = depositAccountRepository;
        this.transferLogRepository = transferLogRepository;
    }

    @Override
    @Transactional
    public void init(int seed) {

        Random random = new Random(seed);

        // 1. 모든 테이블 데이터 삭제
        delayedTransferRequestRepository.deleteAll();
        bankRepository.deleteAll();
        depositAccountRepository.deleteAll();
        customerRepository.deleteAll();
        transferLogRepository.deleteAll();

        // 2. 테스트 데이터 삽입
        // 은행
        List<Bank> bankList = new ArrayList<>();
        bankList.add(new Bank("001", "한국은행", LocalTime.of(23, 50), LocalTime.of(0, 20)));
        bankList.add(new Bank("002", "산업은행", LocalTime.of(23, 50), LocalTime.of(0, 20)));
        bankList.add(new Bank("003", "기업은행", LocalTime.of(23, 50), LocalTime.of(0, 20)));
        bankList.add(new Bank("004", "국민은행", LocalTime.of(23, 50), LocalTime.of(0, 20)));
        bankList.add(new Bank("020", "우리은행", LocalTime.of(23, 50), LocalTime.of(0, 20)));
        bankList.add(new Bank("088", "신한은행", LocalTime.of(23, 50), LocalTime.of(0, 20)));
        bankList.add(new Bank("089", "케이뱅크", LocalTime.of(23, 50), LocalTime.of(0, 20)));
        bankList.add(new Bank("090", "카카오뱅크", LocalTime.of(23, 50), LocalTime.of(0, 20)));
        bankList.add(new Bank("092", "토스뱅크", LocalTime.of(23, 50), LocalTime.of(0, 20)));
        bankRepository.saveAll(bankList);

        // 고객
        List<Customer> customerList = new ArrayList<>();
        for (int i = 0; i < customerNum; i++) {
            customerList.add(new Customer(Integer.toString(i)));
        }
        customerRepository.saveAll(customerList);

        // 계좌
        List<DepositAccount> depositAccountList = new ArrayList<>();
        for (int i = 0; i < accountNum; i++) {
            long randomBalance = 0L;
            double mean = 50000.0;
            double stdDev = 20000.0;
            while (true) {
                randomBalance = (long) random.nextGaussian(mean, stdDev);
                if (randomBalance >= 0) {
                    break;
                }
            }
            depositAccountList.add(new DepositAccount(customerList.get(random.nextInt(customerNum)), String.format("%012d", i), randomBalance));
        }
        depositAccountRepository.saveAll(depositAccountList);

        // 지연이체 신청내역
        List<DelayedTransferRequest> delayedTransferRequestList = new ArrayList<>();
        for (int i = 0; i < delayedTransferRequestNum; i++) {
            DelayedTransferRequest delayedTransferRequest = new DelayedTransferRequest();

            delayedTransferRequest.setFromAccount(depositAccountList.get(random.nextInt(accountNum)).getAccountNumber());
            delayedTransferRequest.setToBankCode(bankList.get(random.nextInt(bankList.size())).getBankCode());
            delayedTransferRequest.setToAccount(depositAccountList.get(random.nextInt(accountNum)).getAccountNumber());

            long randomAmount = 0L;
            double mean = 3000.0;
            double stdDev = 500.0;
            while (true) {
                randomAmount = (long) random.nextGaussian(mean, stdDev);
                if (randomAmount >= 0) {
                    break;
                }
            }
            delayedTransferRequest.setTransferAmount(randomAmount);
            delayedTransferRequest.setRequestedAt(new Timestamp(System.currentTimeMillis()));
            delayedTransferRequest.setStatus(Status.PENDING);

            delayedTransferRequestList.add(delayedTransferRequest);
        }
        delayedTransferRequestRepository.saveAll(delayedTransferRequestList);

        // 3. 시간 조작
        setClock(13, 50, 0);
    }

    @Override
    @Transactional
    public void setClock(int hour, int min, int sec) {
        // 현재 시간
        Clock systemClock = Clock.systemDefaultZone();

        // 오늘 날짜의 23시 45분
        LocalDate today = LocalDate.now(systemClock.getZone());
        LocalTime targetTime = LocalTime.of(hour, min, sec);
        Instant targetInstant = today.atTime(targetTime).atZone(systemClock.getZone()).toInstant();

        // 현재 시간과 목표 시간의 차이 계산
        Instant now = Instant.now(systemClock);
        Duration offsetDuration = Duration.between(now, targetInstant);

        // Offset 적용된 Clock 생성
        dynamicClock.setClock(Clock.offset(systemClock, offsetDuration));
    }
}
