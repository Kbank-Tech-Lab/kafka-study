package org.channel.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "deposit_account")
public class DepositAccount {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    private Customer owner;

    @Column(name = "account_number", nullable = false, unique = true, length = 14)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private Long balance;

    public DepositAccount(Customer owner, String accountNumber, Long balance) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}
