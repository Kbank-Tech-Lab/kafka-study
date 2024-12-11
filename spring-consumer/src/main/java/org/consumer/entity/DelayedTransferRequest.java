package org.consumer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "delayed_transfer_request")
@NoArgsConstructor
public class DelayedTransferRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "from_account", nullable = false)
    private String fromAccount;

    @Column(name = "to_bank_code", nullable = false)
    private String toBankCode;

    @Column(name = "to_account", nullable = false)
    private String toAccount;

    @Column(name = "transfer_amount", nullable = false)
    private Long transferAmount;

    @Column(name = "requested_at", nullable = false)
    private Timestamp requestedAt;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "processed_at")
    private Timestamp processedAt;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
