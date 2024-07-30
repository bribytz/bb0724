package com.toolshop.rental_system.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.toolshop.rental_system.model.CheckoutRequest;
import com.toolshop.rental_system.model.RentalAgreement;

import jakarta.xml.bind.ValidationException;

@SpringBootTest
public class CheckoutServiceTests {
    @Autowired
    private CheckoutService checkoutService;

    // TODO fix this so it doesn't throw validationexception???
    @Test
    public void test1() throws ValidationException {
        CheckoutRequest request = CheckoutRequest.builder().toolCode("CHNS").rentalDayCount(2l).discountPercent(0l)
                .checkoutDate(LocalDate.of(2024, 7, 26)).build();

        RentalAgreement agreement = checkoutService.checkout(request);

        assertAll("agreement final state",
                () -> assertEquals(1, agreement.getChargeableDayCount()),
                () -> assertEquals(LocalDate.of(2024, 7, 27), agreement.getDueDate()));

    }

    @Test
    public void testToolNotFound() {
        CheckoutRequest request = CheckoutRequest.builder().toolCode("ABC").rentalDayCount(2l).discountPercent(0l)
                .checkoutDate(LocalDate.of(2024, 7, 26)).build();

        Throwable exception = assertThrows(ValidationException.class, () -> {
            checkoutService.checkout(request);
        });
        assertEquals(
                "Tool not found, please confirm tool code is correct. Please contact support if this issue persists",
                exception.getMessage());

    }

}
