package com.example.testdoubles.mock;

/**
 * Implementacja Mock z oczekiwaniami
 */
class PaymentGatewayMock implements PaymentGateway {
    // Oczekiwania (expectations) - definiowane przed testem
    private String expectedCardNumber;
    private Double expectedAmount;
    private boolean expectAuthorize = false;
    private boolean expectCapture = false;
    private String expectedTransactionId;
    private boolean expectRefund = false;
    
    // Stany weryfikacji
    private boolean authorizeWasCalled = false;
    private boolean captureWasCalled = false;
    private boolean refundWasCalled = false;
    private String actualCardNumber;
    private Double actualAmount;
    private String actualTransactionId;
    
    // Konfiguracja oczekiwań
    public void expectAuthorize(String cardNumber, double amount) {
        this.expectAuthorize = true;
        this.expectedCardNumber = cardNumber;
        this.expectedAmount = amount;
    }
    
    public void expectCapture(double amount) {
        this.expectCapture = true;
        this.expectedAmount = amount;
    }
    
    public void expectRefund(String transactionId, double amount) {
        this.expectRefund = true;
        this.expectedTransactionId = transactionId;
        this.expectedAmount = amount;
    }
    
    @Override
    public boolean authorize(String cardNumber, double amount) {
        this.authorizeWasCalled = true;
        this.actualCardNumber = cardNumber;
        this.actualAmount = amount;
        
        if (!expectAuthorize) {
            throw new AssertionError("Unexpected call to authorize()");
        }
        if (!cardNumber.equals(expectedCardNumber)) {
            throw new AssertionError("Expected card: " + expectedCardNumber + 
                                   " but got: " + cardNumber);
        }
        if (expectedAmount == null || Double.compare(amount, expectedAmount) != 0) {
            throw new AssertionError("Expected amount: " + expectedAmount + 
                                   " but got: " + amount);
        }
        return true;
    }
    
    @Override
    public String capture(String authorizationId, double amount) {
        this.captureWasCalled = true;
        this.actualAmount = amount;
        
        if (!expectCapture) {
            throw new AssertionError("Unexpected call to capture()");
        }
        if (expectedAmount == null || Double.compare(amount, expectedAmount) != 0) {
            throw new AssertionError("Expected capture amount: " + expectedAmount + 
                                   " but got: " + amount);
        }
        return "TXN-" + System.currentTimeMillis();
    }
    
    @Override
    public void refund(String transactionId, double amount) {
        this.refundWasCalled = true;
        this.actualTransactionId = transactionId;
        this.actualAmount = amount;
        
        if (!expectRefund) {
            throw new AssertionError("Unexpected call to refund()");
        }
        if (!transactionId.equals(expectedTransactionId)) {
            throw new AssertionError("Expected transaction: " + expectedTransactionId + 
                                   " but got: " + transactionId);
        }
        if (expectedAmount == null || Double.compare(amount, expectedAmount) != 0) {
            throw new AssertionError("Expected refund amount: " + expectedAmount + 
                                   " but got: " + amount);
        }
    }
    
    // Weryfikacja końcowa - wszystkie oczekiwania spełnione
    public void verify() {
        if (expectAuthorize && !authorizeWasCalled) {
            throw new AssertionError("Expected authorize() to be called but it wasn't");
        }
        if (expectCapture && !captureWasCalled) {
            throw new AssertionError("Expected capture() to be called but it wasn't");
        }
        if (expectRefund && !refundWasCalled) {
            throw new AssertionError("Expected refund() to be called but it wasn't");
        }
    }
}

