package org.coreBanking.service;

import java.time.Clock;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.coreBanking.exception.CustomException;
import org.coreBanking.exception.ErrorCode;
import org.coreBanking.model.Bank;
import org.coreBanking.repository.BankRepository;
import org.springframework.stereotype.Service;

@Service
public class OtherBankServiceImpl implements OtherBankService {

    private final BankRepository bankRepository;
    private final Clock clock;

    public OtherBankServiceImpl(BankRepository bankRepository, Clock clock) {
        this.bankRepository = bankRepository;
        this.clock = clock;
    }

    @Override
    public List<LocalTime> getMaintenanceTime(String bankCode) {
        Bank bank = bankRepository.findById(bankCode)
            .orElseThrow(() -> new CustomException(ErrorCode.BANK_NOT_FOUND));

        return new ArrayList<>(List.of(bank.getMaintenanceStartTime(), bank.getMaintenanceEndTime()));
    }

    @Override
    public boolean isUnderMaintenance(String bankCode) {
        Bank bank = bankRepository.findById(bankCode)
            .orElseThrow(() -> new CustomException(ErrorCode.BANK_NOT_FOUND));

        LocalTime now = LocalTime.now(clock);

        return !now.isBefore(bank.getMaintenanceStartTime())
            || now.isBefore(bank.getMaintenanceEndTime());
    }

    @Override
    public void transferOtherBank(String bankCode, String accountNumber, Long amount) {
        Random random = new Random(10);

        try {
            Thread.sleep(Math.max((long) random.nextGaussian(1000, 1000), 0) + 2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (isUnderMaintenance(bankCode)) {
            throw new CustomException(ErrorCode.BANK_UNDER_MAINTENANCE);
        }

        // Some actions in other bank
    }
}
