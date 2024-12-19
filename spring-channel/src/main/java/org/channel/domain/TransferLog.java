package org.channel.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transfer_log")
public class TransferLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "from_account_number", nullable = false)
    private String fromAccountNumber;

    @Column(name = "to_bank_code", nullable = false)
    private String toBankCode;

    @Column(name = "to_account", nullable = false)
    private String toAccount;

    @Column(name = "transfer_amount", nullable = false)
    private Long transferAmount;

    @Column(name = "processed_at")
    private Timestamp processedAt;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public static TransferLog of(String fromAccountNumber, String toBankCode, String toAccount, Long transferAmount) {
        TransferLog transferLog = new TransferLog();
        transferLog.fromAccountNumber = fromAccountNumber;
        transferLog.toBankCode = toBankCode;
        transferLog.toAccount = toAccount;
        transferLog.transferAmount = transferAmount;
        return transferLog;
    }
    @PrePersist
    public void prePersist() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        this.createdAt = now;
        this.updatedAt = now;
    }


    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
