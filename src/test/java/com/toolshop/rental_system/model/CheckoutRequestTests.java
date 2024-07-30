package com.toolshop.rental_system.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.xml.bind.ValidationException;

@SpringBootTest
public class CheckoutRequestTests {

    @Test
    public void testValidRequest() throws ValidationException {
        CheckoutRequest request = CheckoutRequest.builder().toolCode("ABC").rentalDayCount(1l).discountPercent(10l)
                .checkoutDate(LocalDate.of(2024, 7, 26)).build();

        assertTrue(request.isValid());

    }

    @Test
    public void testInvalidRentalDayCount() {
        CheckoutRequest request = CheckoutRequest.builder().toolCode("ABC").rentalDayCount(0l).discountPercent(0l)
                .checkoutDate(LocalDate.of(2024, 7, 26)).build();

        Throwable exception = assertThrows(ValidationException.class, () -> {
            request.isValid();
        });
        assertEquals("Please provide a rental day count.", exception.getMessage());

    }

    @Test
    public void testInvalidDiscountAmount() {
        CheckoutRequest request = CheckoutRequest.builder().toolCode("ABC").rentalDayCount(2l).discountPercent(101l)
                .checkoutDate(LocalDate.of(2024, 7, 26)).build();

        Throwable exception = assertThrows(ValidationException.class, () -> {
            request.isValid();
        });
        assertEquals("Please enter a discount amount. Discount amount should be a whole number between 0-100.",
                exception.getMessage());

    }
}
