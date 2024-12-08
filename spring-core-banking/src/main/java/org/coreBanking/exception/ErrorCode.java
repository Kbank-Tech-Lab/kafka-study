package org.coreBanking.exception;

public enum ErrorCode {

    // 시스템 관련 에러
    SYSTEM_ERROR("SYS0001", "A system error occurred"),
    INVALID_PARAM("SYS0002", "Invalid parameter"),

    // 계좌 관련 에러
    ACCOUNT_NOT_FOUND("COR0001", "Account not found"),
    INSUFFICIENT_BALANCE("COR0002", "Insufficient account balance"),
    TRANSFER_TO_SAME_ACCOUNT("COR0003", "Transfer to same account"),
    TRANSFER_TO_OTHER_BANK("COR0004", "Transfer to other bank"),

    // 고객 관련 에러
    CUSTOMER_NOT_FOUND("COR1001", "Customer not found");


    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
