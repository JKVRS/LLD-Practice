## Open/Closed Principle
**Why?**
* Adding new feature to your codebase, can lead to editing existing classes
* This can introduce bug in already working and tested code.

This is a violation of OCP.

## Checkout feature for an e-commerce paltform.
1. Initially we have only one Payment Method: **Credit Card**

```
class PaymentProcessor {
    public void processCerditCardPaymnet(double amount) {
        // logic to process payment
    }
}
```
Now Checkout Service use this payment Processor
```
public class CheckoutService {
  void processPayment() {
  PaymentProcessor processor = new PaymentProcessor();
  processor.processCerditCardPaymnet(100.00);
  
  }
}
```
### Lets client comes along and says, "Hey we need to add PayPal payments too".

`Modifying existing class to handle the payment (PayPal)`
```
class PaymentProcessor {
    public void processCerditCardPaymnet(double amount) {
        // logic to process payment
    }
    public void processPayPalPaymnet(double amount) {
        // logic to process payment
    }
}
`Modifying the CheckoutService class`
```
Now Checkout Service use this payment Processor
```
public class CheckoutService {
  void processPayment(String paymentType) {
  PaymentProcessor processor = new PaymentProcessor();
  
  if("CrediCart".equals(paymentType)){
    processor.processCerditCardPaymnet(100.00);
  }
  else if("PayPal".equals(paymentType)){
    processor.processPayPalPaymnet(100.00);
  }
}
```
### Now it works, But guess what happens when the client wants to add UPI, Bitcoin or Apple Pay?
* We need to modify the PayProcessor to add the support for the above payment method
* When need to add if-else block to in Checkoutservice

## Each modification 
* Introduce bugs: (Might break the existing credit card or PayPal functionality)
* Testing Overhead: Every time we change the class, we need to re-test all its functionalities, not just the new one.
* Redundancy


# Open-closer Principle
* **software entities (classes, modules, functions, etc.) should be open for extension but closed for modification**.

1. **Open for Extension**: This means the behavior of the entity can be extended. As new requirements come (like new payment types), you should be able to add new behavior.
2. **Closed for Modification**: This means the existing, working code of the entity should not be changed. Once its writting, tested and working, you should'nt nee to go back and alter to new features.

**Adding new feautures without altering the exiting code** (# Abstraction)
* User Interface or Abstract class to solve (Payment Method) adding issues in existing working code.

```
    interface PaymentMethod {
        void processPayment(Double amount);
    }
    
    class CreditCardPayment implements PayMethod {
         @Ovveride
         void processPayment(Double amount) {
            // logic for credit car processing
         }
    }
    class PayPalPayment implements PayMethod {
        @Ovveride
         void processPayment(Double amount) {
            // logic for PayPal car processing
         }
    }
    class UPIPayment implements PayMethod {
        @Ovveride
         void processPayment(Double amount) {
            // logic for UPI payment processing
         }
    }
    
    class PaymentProcessor {
     public void process(PaymentMethod paymentMethod, double amount) {
        // no more if-else
        paymentMethod.processPayment(amount);
     }
    }
    
    class CheckoutService {
        public void ProcessPaymnet(PaymentMethod method, double amount) {
          PaymentProcessor processor = new PaymentProcessor();
          processor.process(method, amount);
        }
    }
    
    // uage in main (String[] args)
    ChekcoutService chekcout = new CheckOut();
    checkout.processPayment(new CreditCardPayment(), 100.00);
    checkout.processPayment(new PayPalPayment(), 100.00);
    checkout.processPayment(new UPIPayment(), 100.00); 
```

**Don't abstract things that are unlikely to change.**
**Apply OCP strategically where change is anticipated.**
**OCP primarily applies to adding new features and behavior**


## Are there specific design pattern that helps implements OCP
1. **Strategy Pattern**
   * As seen in our Payment eg. Allows algorithms to be selected at runtime.
2. **Decorator Pattern**
   * Allows adding responsibilities to object dynamically.
3. **Template Method Pattern**
   *
