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

import com.toolshop.rental_system.enums.ToolType;
import com.toolshop.rental_system.model.CheckoutRequest;
import com.toolshop.rental_system.model.RentalAgreement;

@SpringBootTest
public class RequiredCasesTests {
    @Autowired
    private CheckoutService checkoutService;

    @Test
    public void test1() {
        CheckoutRequest request = CheckoutRequest.builder().toolCode("JAKR").rentalDayCount(5l)
                .discountPercent(101l)
                .checkoutDate(LocalDate.of(2015, 9, 3)).build();

        Throwable exception = assertThrows(Exception.class, () -> {
            checkoutService.checkout(request);
        });
        assertEquals(
                "Please enter a discount amount. Discount amount should be a whole number between 0-100.",
                exception.getMessage());
    }

    @Test
    public void test2() {
        CheckoutRequest request = CheckoutRequest.builder().toolCode("LADW").rentalDayCount(3l)
                .discountPercent(10l)
                .checkoutDate(LocalDate.of(2020, 7, 2)).build();

        RentalAgreement agreement = assertDoesNotThrow(() -> checkoutService.checkout(request));

        assertAll("agreement final state",
                () -> assertEquals("LADW", agreement.getTool().getToolCode()),
                () -> assertEquals(ToolType.LADDER, agreement.getTool().getTypeInfo().getType()),
                () -> assertEquals("Werner", agreement.getTool().getBrand()),
                () -> assertEquals(2, agreement.getChargeableDayCount()),
                () -> assertEquals(LocalDate.of(2020, 7, 5), agreement.getDueDate()),
                () -> assertEquals(10, agreement.getDiscountPercent()),
                () -> assertEquals(new BigDecimal("0.40"), agreement.getDiscountTotal()),
                () -> assertEquals(new BigDecimal("3.98"), agreement.getPrediscountTotal()),
                () -> assertEquals(new BigDecimal("3.58"), agreement.getFinalCharge()));
    }

    @Test
    public void test3() {
        CheckoutRequest request = CheckoutRequest.builder().toolCode("CHNS").rentalDayCount(5l)
                .discountPercent(25l)
                .checkoutDate(LocalDate.of(2015, 7, 2)).build();

        RentalAgreement agreement = assertDoesNotThrow(() -> checkoutService.checkout(request));

        assertAll("agreement final state",
                () -> assertEquals("CHNS", agreement.getTool().getToolCode()),
                () -> assertEquals(ToolType.CHAINSAW, agreement.getTool().getTypeInfo().getType()),
                () -> assertEquals("Stihl", agreement.getTool().getBrand()),
                () -> assertEquals(3, agreement.getChargeableDayCount()),
                () -> assertEquals(LocalDate.of(2015, 7, 7), agreement.getDueDate()),
                () -> assertEquals(25, agreement.getDiscountPercent()),
                () -> assertEquals(new BigDecimal("1.12"), agreement.getDiscountTotal()),
                () -> assertEquals(new BigDecimal("4.47"), agreement.getPrediscountTotal()),
                () -> assertEquals(new BigDecimal("3.35"), agreement.getFinalCharge()));
    }

    @Test
    public void test4() {
        CheckoutRequest request = CheckoutRequest.builder().toolCode("JAKD").rentalDayCount(6l)
                .discountPercent(0l)
                .checkoutDate(LocalDate.of(2015, 9, 3)).build();

        RentalAgreement agreement = assertDoesNotThrow(() -> checkoutService.checkout(request));

        assertAll("agreement final state",
                () -> assertEquals("JAKD", agreement.getTool().getToolCode()),
                () -> assertEquals(ToolType.JACKHAMMER, agreement.getTool().getTypeInfo().getType()),
                () -> assertEquals("DeWalt", agreement.getTool().getBrand()),
                () -> assertEquals(3, agreement.getChargeableDayCount()),
                () -> assertEquals(LocalDate.of(2015, 9, 9), agreement.getDueDate()),
                () -> assertEquals(0, agreement.getDiscountPercent()),
                () -> assertEquals(new BigDecimal("0.00"), agreement.getDiscountTotal()),
                () -> assertEquals(new BigDecimal("8.97"), agreement.getPrediscountTotal()),
                () -> assertEquals(new BigDecimal("8.97"), agreement.getFinalCharge()));
    }

    @Test
    public void test5() {
        CheckoutRequest request = CheckoutRequest.builder().toolCode("JAKR").rentalDayCount(9l)
                .discountPercent(0l)
                .checkoutDate(LocalDate.of(2015, 7, 2)).build();

        RentalAgreement agreement = assertDoesNotThrow(() -> checkoutService.checkout(request));

        assertAll("agreement final state",
                () -> assertEquals("JAKR", agreement.getTool().getToolCode()),
                () -> assertEquals(ToolType.JACKHAMMER, agreement.getTool().getTypeInfo().getType()),
                () -> assertEquals("Ridgid", agreement.getTool().getBrand()),
                () -> assertEquals(5, agreement.getChargeableDayCount()),
                () -> assertEquals(LocalDate.of(2015, 7, 11), agreement.getDueDate()),
                () -> assertEquals(0, agreement.getDiscountPercent()),
                () -> assertEquals(new BigDecimal("0.00"), agreement.getDiscountTotal()),
                () -> assertEquals(new BigDecimal("14.95"), agreement.getPrediscountTotal()),
                () -> assertEquals(new BigDecimal("14.95"), agreement.getFinalCharge()));
    }

    @Test
    public void test6() {
        CheckoutRequest request = CheckoutRequest.builder().toolCode("JAKR").rentalDayCount(4l)
                .discountPercent(50l)
                .checkoutDate(LocalDate.of(2020, 7, 2)).build();

        RentalAgreement agreement = assertDoesNotThrow(() -> checkoutService.checkout(request));

        assertAll("agreement final state",
                () -> assertEquals("JAKR", agreement.getTool().getToolCode()),
                () -> assertEquals(ToolType.JACKHAMMER, agreement.getTool().getTypeInfo().getType()),
                () -> assertEquals("Ridgid", agreement.getTool().getBrand()),
                () -> assertEquals(1, agreement.getChargeableDayCount()),
                () -> assertEquals(LocalDate.of(2020, 7, 6), agreement.getDueDate()),
                () -> assertEquals(50, agreement.getDiscountPercent()),
                () -> assertEquals(new BigDecimal("1.50"), agreement.getDiscountTotal()),
                () -> assertEquals(new BigDecimal("2.99"), agreement.getPrediscountTotal()),
                () -> assertEquals(new BigDecimal("1.49"), agreement.getFinalCharge()));
    }
}
