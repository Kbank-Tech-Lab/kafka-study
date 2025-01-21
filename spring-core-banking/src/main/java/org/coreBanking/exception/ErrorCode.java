package org.coreBanking.exception;

public enum ErrorCode {

    // 시스템 관련 에러
    SYSTEM_ERROR("SYS0001", "A system error occurred"),
    INVALID_PARAM("SYS0002", "Invalid parameter"),

    // 계좌 관련 에러
    ACCOUNT_NOT_FOUND("COR0001", "Account not found"),

    // 고객 관련 에러
    CUSTOMER_NOT_FOUND("COR1001", "Customer not found"),

    // 송금 관련 에러
    INSUFFICIENT_BALANCE("COR2001", "Insufficient account balance"),
    TRANSFER_TO_SAME_ACCOUNT("COR2002", "Transfer to same account"),
    TRANSFER_TO_OTHER_BANK("COR2003", "Transfer to other bank"),
    TRANSFER_LOG_NOT_FOUND("COR2004", "Transfer log not found"),

    // 은행 관련 에러
    BANK_NOT_FOUND("COR3001", "Bank not found"),
    BANK_UNDER_MAINTENANCE("COR3002", "Bank under maintenance"),
    BANK_TRANSFER_TIMEOUT("COR3003", "Other bank transfer timed out"),
    BANK_TRANSFER_INTERRUPTED("COR3004", "Bank transfer was interrupted"),

    // 지연이체 관련 에러
    DELAYED_TRANSFER_NOT_FOUND("COR4001", "Delayed transfer not found");


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
