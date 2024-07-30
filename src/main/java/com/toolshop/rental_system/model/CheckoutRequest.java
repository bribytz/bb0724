package com.toolshop.rental_system.model;

import java.time.LocalDate;

import jakarta.xml.bind.ValidationException;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckoutRequest {
    private String toolCode;
    // using [Long] for both fields because per spec, seems like the backend
    // should initially accept null as a value and then reject during a validation
    // step
    private Long rentalDayCount;
    private Long discountPercent;
    private LocalDate checkoutDate;

    /**
     * Validates that rentalDayCount and discountPercent have values and are
     * non-zero.
     * DiscountPercent is also capped at a value of 100
     * 
     * @return true if rentalDayCount and discountPercent pass validation
     * @throws ValidationException if validation fails due to invalid rentalDayCount
     *                             or discountPercent values
     */
    public boolean isValid() throws ValidationException {
        if (rentalDayCount == null || rentalDayCount <= 0) {
            throw new ValidationException("Please provide a rental day count.");
        } else if (discountPercent == null || discountPercent < 0 || discountPercent > 100) {
            throw new ValidationException(
                    "Please enter a discount amount. Discount amount should be a whole number between 0-100.");
        } else {
            return true;
        }
    }
}
