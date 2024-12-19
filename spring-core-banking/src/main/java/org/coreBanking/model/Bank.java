package org.coreBanking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "bank")
public class Bank {

    @Id
    @Column(name = "bank_code", nullable = false, length = 3)
    private String bankCode;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "maintenance_start_time", nullable = false)
    private LocalTime maintenanceStartTime;

    @Column(name = "maintenance_end_time", nullable = false)
    private LocalTime maintenanceEndTime;

    public Bank(String bankCode, String name, LocalTime maintenanceStartTime, LocalTime maintenanceEndTime) {
        this.bankCode = bankCode;
        this.name = name;
        this.maintenanceStartTime = maintenanceStartTime;
        this.maintenanceEndTime = maintenanceEndTime;
    }
}

