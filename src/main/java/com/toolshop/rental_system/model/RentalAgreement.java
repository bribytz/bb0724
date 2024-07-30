package com.toolshop.rental_system.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RentalAgreement {
    private Tool tool;
    private long rentalDayCount;
    private long discountPercent;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    /**
     * Number of days that are chargeable (so excludes holidays and weekends as
     * relevant)
     */
    private long chargeableDayCount;
    private double totalCharge;
    private double discountTotal;
    private double finalCharge;

}
