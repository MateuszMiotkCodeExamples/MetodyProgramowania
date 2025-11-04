package com.example.library;

import java.math.BigDecimal;

public class ReturnService {
    private final FeeCalculator feeCalculator;

    public ReturnService(FeeCalculator feeCalculator) {
        this.feeCalculator = feeCalculator;
    }

    public BigDecimal calculateFee(Book book, int daysOverdue) {
        return feeCalculator.calculateLateFee(book, daysOverdue);
    }
}


