package com.toolshop.rental_system.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

import com.toolshop.rental_system.utils.FormattingUtils;

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
    private BigDecimal prediscountTotal;
    private BigDecimal discountTotal;
    private BigDecimal finalCharge;

    public String prettyPrint() {
        StringBuilder builder = new StringBuilder();
        builder.append("Tool code: ");
        builder.append(tool.getToolCode());
        builder.append("\nTool type: ");
        builder.append(StringUtils.capitalize(tool.getTypeInfo().getType().toString().toLowerCase()));
        builder.append("\nTool brand: ");
        builder.append(tool.getBrand());
        builder.append("\nRental days: ");
        builder.append(rentalDayCount);
        builder.append("\nCheck out date: ");
        builder.append(FormattingUtils.formatDate(checkoutDate));
        builder.append("\nDue date: ");
        builder.append(FormattingUtils.formatDate(dueDate));
        builder.append("\nDaily rental charge: ");
        builder.append(FormattingUtils.formatCurrency(tool.getTypeInfo().getDailyChargeAmount()));
        builder.append("\nCharge days: ");
        builder.append(chargeableDayCount);
        builder.append("\nPre-discount charge: ");
        builder.append(FormattingUtils.formatCurrency(prediscountTotal));
        builder.append("\nDiscount percent: ");
        builder.append(FormattingUtils.formatPercent(discountPercent));
        builder.append("\nDiscount amount: ");
        builder.append(FormattingUtils.formatCurrency(discountTotal));
        builder.append("\nFinal charge: ");
        builder.append(FormattingUtils.formatCurrency(finalCharge));

        return builder.toString();
    }

}
