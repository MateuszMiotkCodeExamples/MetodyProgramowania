package com.example.library;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FeeCalculator {
    private static final BigDecimal DAILY_FEE = new BigDecimal("0.50");

    public BigDecimal calculateLateFee(Book book, int daysOverdue) {
        if (daysOverdue <= 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return DAILY_FEE.multiply(BigDecimal.valueOf(daysOverdue)).setScale(2, RoundingMode.HALF_UP);
    }
}


