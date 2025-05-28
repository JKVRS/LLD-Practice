## Dependency Inversion Principle

**High-Level Module** and **Low-Level Module** should not depend on each other instead thy depend on abstraction (interface or abs.

- Lets you are creating Gmail Service.

**Low-Level Module - Gmail Integration**
```
class GmailClient {
    public void sendGmail(String toAddress, String subjectLine, String emailBody) {

    }
}
```
**High-Level Module - Application Email Service**
```
class EmailService {
    private GmailClient gmailClient;
    
    public EmailService() {
     this.gmailClinet = new GmailClient();
    }
     public void sendWelcomeEmail(String userEmail, String userName) {
        String subject = "Welcome, " + userName + "!";
        String body = "Thanks for signing up to our awesome platform. We're glad to have you!";
        this.gmailClient.sendGmail(userEmail, subject, body);
    }

    public void sendPasswordResetEmail(String userEmail) {
        String subject = "Reset Your Password";
        String body = "Please click the link below to reset your password...";
        this.gmailClient.sendGmail(userEmail, subject, body);
    }
}
```


## What is the problem now?

can we switch from gmail to outLook for sending emails
Suddenly you will have a problem.

**EmailService** - a High-Level Modules that handles business logic - is tightly coupled to GmailClient, (**Low-Level implementation detail**)

## You need to
**To support multiple Email providers**
* Gmail, Outlook, SES, etc..
* dynamically select a provider based on configuration

to switch EmailService quickly trun into if-else soup.

## The Dependency Inversion Principle

1. High-level modules should not depend on low-level modules. Both should depend on abstractions (e.g., interfaces).
2. Abstractions should not depend on details. Details (concrete implementations) should depend on abstractions.
3. both the high-level module and the low-level module depend on a shared abstraction (interface or abstract class).


**High-level modules define what they need (the contract/interface)**
**low-level modules provide the how (the implementation of that interface).**

