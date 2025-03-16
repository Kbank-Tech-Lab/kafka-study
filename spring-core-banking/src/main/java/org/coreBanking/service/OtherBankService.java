package org.coreBanking.service;

import java.time.LocalTime;
import java.util.List;

public interface OtherBankService {

    List<LocalTime> getMaintenanceTime(String bankCode);
    boolean isUnderMaintenance(String bankCode);
    void transferOtherBank(String bankCode, String accountNumber, Long amount);
}
