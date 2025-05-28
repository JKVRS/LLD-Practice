package SOLIDprinciples.DIP;

// abstraction
public interface EmailClient {
    public abstract void emailSend(String to, String subject, String body);
}

// Low-Level modules
// Provide the concrete implementations (how)
class GmailClient implements EmailClient {
    @Override
    public void emailSend(String to , String subject, String body) {
        System.out.println("Connecting to Gmail SMTP server...");
        System.out.println("Sending email via Gmail to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        // ... actual Gmail API interaction logic ...
        System.out.println("Gmail email sent successfully!");
    }
}

class OutloolEmailClient implements EmailClient{
    @Override
    public void emailSend(String to, String subject, String body){
        System.out.println("Connecting to Outlook Exchange server...");
        System.out.println("Sending email via Outlook to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        // ... actual Outlook API interaction logic ...
        System.out.println("Outlook email sent successfully!");
    }
}

// High-Level Module (what it needs (abstraction (Abstract class or interface)))
class EmailService {
    private EmailClient emailClient;
    public EmailService(EmailClient emailClient) {
        this.emailClient=emailClient;
    }

    public void sendWelcomeEmail(String userEmail, String userName){
        String subject = "Welcome " + userName;
        String body = "Thank you for the signing up for our awesome platform. We are glad to have you!";
        this.emailClient.emailSend(userEmail, subject, body);
    }

    public void passwordResetEmail(String userEmail) {
        String subject = "You Password update request";
        String body = "Please click the link below to reset your password!";
        this.emailClient.emailSend(userEmail, subject, body);
    }
}

class EmailServiceExecutor{
    public static void main(String[] args){
        EmailClient emailClient = new GmailClient();
        EmailService gmailService = new EmailService(emailClient);
        gmailService.sendWelcomeEmail("manojkp124@gmail.com", "manojkp123");

        emailClient = new OutloolEmailClient();
        EmailService outlookService = new EmailService(emailClient);
        outlookService.sendWelcomeEmail("manojp@gmail.com", "kumarmnaojp93");
        outlookService.passwordResetEmail("manojkp@gmail.com");

    }
}


