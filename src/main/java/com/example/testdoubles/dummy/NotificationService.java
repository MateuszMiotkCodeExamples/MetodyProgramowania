package com.example.testdoubles.dummy;

/**
 * System testowany - NotificationService
 */
public class NotificationService {
    private final EmailService emailService;
    private final SmsService smsService;
    private final AuditLogger auditLogger;
    
    public NotificationService(EmailService emailService, 
                               SmsService smsService, 
                               AuditLogger auditLogger) {
        this.emailService = emailService;
        this.smsService = smsService;
        this.auditLogger = auditLogger;
    }
    
    /**
     * Metoda do testowania - używa tylko emailService
     */
    public boolean notifyByEmail(String email, String message) {
        if (emailService.validateEmailAddress(email)) {
            emailService.sendEmail(email, "Notification", message);
            return true;
        }
        return false;
    }
}

