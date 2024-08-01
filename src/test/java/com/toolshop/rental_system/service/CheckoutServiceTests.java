package com.toolshop.rental_system.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.toolshop.rental_system.model.CheckoutRequest;
import com.toolshop.rental_system.model.RentalAgreement;

@SpringBootTest
public class CheckoutServiceTests {
        @Autowired
        private CheckoutService checkoutService;

        @Test
        public void testRequestWithWeekendAndNoHolidays() {
                CheckoutRequest request = CheckoutRequest.builder().toolCode("CHNS").rentalDayCount(2l)
                                .discountPercent(0l)
                                .checkoutDate(LocalDate.of(2024, 7, 25)).build();

                RentalAgreement agreement = assertDoesNotThrow(() -> checkoutService.checkout(request));

                assertAll("agreement final state",
                                () -> assertEquals(1, agreement.getChargeableDayCount()),
                                () -> assertEquals(LocalDate.of(2024, 7, 27), agreement.getDueDate()),
                                () -> assertEquals(new BigDecimal("0.00"), agreement.getDiscountTotal()),
                                () -> assertEquals(agreement.getPrediscountTotal(), agreement.getFinalCharge()),
                                () -> assertEquals(new BigDecimal("1.49"), agreement.getFinalCharge()));
        }

        @Test
        public void testRequestWithNoWeekendAndNoHolidays() {
                CheckoutRequest request = CheckoutRequest.builder().toolCode("CHNS").rentalDayCount(4l)
                                .discountPercent(0l)
                                .checkoutDate(LocalDate.of(2024, 7, 22)).build();

                RentalAgreement agreement = assertDoesNotThrow(() -> checkoutService.checkout(request));

                assertAll("agreement final state",
                                () -> assertEquals(4, agreement.getChargeableDayCount()),
                                () -> assertEquals(LocalDate.of(2024, 7, 26), agreement.getDueDate()),
                                () -> assertEquals(0, agreement.getDiscountPercent()));
        }

        @Test
        public void testRequestWithWeekendCharge() {
                CheckoutRequest request = CheckoutRequest.builder().toolCode("LADW").rentalDayCount(2l)
                                .discountPercent(0l)
                                .checkoutDate(LocalDate.of(2024, 7, 26)).build();

                RentalAgreement agreement = assertDoesNotThrow(() -> checkoutService.checkout(request));

                assertAll("agreement final state",
                                () -> assertEquals(2, agreement.getChargeableDayCount()),
                                () -> assertEquals(LocalDate.of(2024, 7, 28), agreement.getDueDate()));
        }

        @Test
        public void testToolNotFound() {
                CheckoutRequest request = CheckoutRequest.builder().toolCode("ABC").rentalDayCount(2l)
                                .discountPercent(0l)
                                .checkoutDate(LocalDate.of(2024, 7, 26)).build();

                Throwable exception = assertThrows(Exception.class, () -> {
                        checkoutService.checkout(request);
                });
                assertEquals(
                                "Tool not found, please confirm tool code is correct. Please contact support if this issue persists.",
                                exception.getMessage());

        }

}
