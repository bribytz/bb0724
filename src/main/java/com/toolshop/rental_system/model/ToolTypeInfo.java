package com.toolshop.rental_system.model;

import java.math.BigDecimal;

import com.toolshop.rental_system.enums.ToolType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToolTypeInfo {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private ToolType type;
    private BigDecimal dailyChargeAmount;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;
}
