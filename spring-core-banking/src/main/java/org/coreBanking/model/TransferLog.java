package org.coreBanking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "delayed_transfer_id")
    private Integer delayedTransferId;

    @Column(name = "requested_at")
    private Timestamp requestedAt;

    @Column(name = "processed_at")
    private Timestamp processedAt;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @Column(name = "error_code", length = 7)
    private String errorCode;
}
