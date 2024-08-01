package com.toolshop.rental_system.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toolshop.rental_system.date_utils.DateUtils;
import com.toolshop.rental_system.model.CheckoutRequest;
import com.toolshop.rental_system.model.RentalAgreement;
import com.toolshop.rental_system.model.Tool;
import com.toolshop.rental_system.repository.ToolRepository;

@Service
public class CheckoutService {
    @Autowired
    private ToolRepository toolInfoRepository;

    public RentalAgreement checkout(CheckoutRequest request) throws Exception {
        // validate request - throws exception if invalid
        request.isValid();

        // valid so fetch tool by code
        Tool tool = toolInfoRepository.findOneByToolCode(request.getToolCode());
        if (tool == null) {
            // This probably shouldn't happen because ideally the UI only lets users select
            // real tools
            throw new Exception(
                    "Tool not found, please confirm tool code is correct. Please contact support if this issue persists.");
        }

        return processRentalRequest(tool, request);
    }

    private RentalAgreement processRentalRequest(Tool tool, CheckoutRequest request) {
        RentalAgreement agreement = RentalAgreement.builder().tool(tool).rentalDayCount(request.getRentalDayCount())
                .checkoutDate(request.getCheckoutDate())
                .dueDate(request.getCheckoutDate().plusDays(request.getRentalDayCount()))
                .discountPercent(request.getDiscountPercent()).build();

        long chargeableDayCount = request.getRentalDayCount();

        LocalDate checkoutDate = request.getCheckoutDate();
        LocalDate dueDate = agreement.getDueDate();

        // subtract weekends if applicable
        if (!tool.getTypeInfo().isWeekendCharge()) {
            chargeableDayCount = chargeableDayCount
                    - DateUtils.queryOverRange(checkoutDate, dueDate,
                            DateUtils.WEEKEND_QUERY);
        }

        // subtract holidays if applicable
        if (!tool.getTypeInfo().isHolidayCharge()) {
            chargeableDayCount = chargeableDayCount
                    - DateUtils.rangeContainsAdjustedDate(checkoutDate, dueDate, DateUtils.LABOR_DAY_ADJUSTER);
            chargeableDayCount = chargeableDayCount - DateUtils.rangeContainsAdjustedDate(checkoutDate, dueDate,
                    DateUtils.OBSERVED_INDEPENDENCE_DAY_ADJUSTER);
        }

        agreement.setChargeableDayCount(chargeableDayCount);

        // apply discount, calculate total, and return
        BigDecimal totalCharge = tool.getTypeInfo().getDailyChargeAmount().multiply(new BigDecimal(chargeableDayCount));
        totalCharge = totalCharge.setScale(2, RoundingMode.HALF_UP);
        agreement.setPrediscountTotal(totalCharge);

        BigDecimal discountTotal = totalCharge
                .multiply(new BigDecimal(request.getDiscountPercent()).divide(new BigDecimal(100)));
        discountTotal = discountTotal.setScale(2, RoundingMode.HALF_UP);
        agreement.setDiscountTotal(discountTotal);

        agreement.setFinalCharge(totalCharge.subtract(discountTotal));

        return agreement;
    }
}
