package com.toolshop.rental_system.service;

import java.time.LocalDate;
import java.time.temporal.TemporalQueries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toolshop.rental_system.model.CheckoutRequest;
import com.toolshop.rental_system.model.RentalAgreement;
import com.toolshop.rental_system.model.Tool;
import com.toolshop.rental_system.repository.ToolRepository;
import com.toolshop.rental_system.date_utils.DateUtils;
import com.toolshop.rental_system.date_utils.WeekendTemporalQuery;

import jakarta.xml.bind.ValidationException;

@Service
public class CheckoutService {
    @Autowired
    private ToolRepository toolInfoRepository;

    // TODO move to date utils? like i don't think we need a bunch of instances of
    // this?
    private WeekendTemporalQuery weekendQuery = new WeekendTemporalQuery();

    public RentalAgreement checkout(CheckoutRequest request) throws ValidationException {
        // validate request
        // TODO ugh this...is not my favorite, think about an alternate while keeping to
        // spec?
        request.isValid();

        // is valid so fetch tool by code
        Tool tool = toolInfoRepository.findOneByToolCode(request.getToolCode());
        if (tool == null) {
            // This probably shouldn't happen because ideally the UI only lets users select
            // real tools
            throw new ValidationException(
                    "Tool not found, please confirm tool code is correct. Please contact support if this issue persists");
        }

        return calculateTotal(tool, request);
    }

    // @VisibleForTesting
    // TODO rename? break up into smaller bits?
    private RentalAgreement calculateTotal(Tool tool, CheckoutRequest request) {
        RentalAgreement agreement = RentalAgreement.builder().tool(tool).rentalDayCount(request.getRentalDayCount())
                .checkoutDate(request.getCheckoutDate())
                .dueDate(request.getCheckoutDate().plusDays(request.getRentalDayCount() - 1)).build();

        long chargeableDayCount = request.getRentalDayCount();

        // subtract weekends if applicable
        if (!tool.getTypeInfo().isWeekendCharge()) {
            chargeableDayCount = chargeableDayCount
                    - DateUtils.queryOverRange(request.getCheckoutDate(), agreement.getDueDate(), weekendQuery);
        }

        // subtract holidays if applicable

        // apply discount + return

        agreement.setChargeableDayCount(chargeableDayCount);

        return agreement;
    }
}
