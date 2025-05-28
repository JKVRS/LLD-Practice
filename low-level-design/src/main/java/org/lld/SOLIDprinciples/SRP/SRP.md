## Single Responsibility Principle

Why we need this principle?

* Situation: When we changed one part of the code and suddenly, five unrelated things broke?

The Problem GOD class
```
public class Employee {
  private String name;
  private String email;
  private double salary;
  
  // constructor, gettter, setters
  
  public void calculateSalary() {}
  
  public void saveToDatabase() {}
  
  public void generateSlip() {}
  
  public void sendPaysSlipEmial() {}
}
```

* Now, the above class is handling everything
* Calculating Salary
* Saving data to database
* Generating a payslip
* Sending an email

There are 4 distinct responsibility for one class.

The problem will arrive when, 
* changing the Salary calculation logic, this class changes
* if PaySlip format changes, this classes changes
* if the DB schema changes, this class changes
* if the email service API is replicated, this class Changes again.


-- This class is tightly coupled to 4 different reasons to change

## A class should have one, and only one, reason to change.

* In simple words: A class should do one thing and do it well.


## What exactly **responsibility**?

* its not a method, its not a function.
* **Its a reason for the class to change**


-- How many reasons might someone need to update this class in the future
* if the answer is more than one, than we are violating SRP.


## Real World Analogy
Let's consider a Restaurant.
* Cook the food. 
* Take orders.
* Clean the tables.
* Do the accounts.

Do we need one person to do this all responsibility. (No)

Everyone has responsibility.
Chef.
Waiter
Cleaner.
Accountant.

## Why Does SRP matter?

* Easier to read -> You immediately understand what does the class is supposed to do. No Surprises.
* Easier to test -> Smaller responsibilities means smaller test cases and fewer dependecies.
* Less Brittle -> Change in one responsibility don't ripple across unrelated partes of the code.

## Common Pitfalls while applying SRP
1. Over-Splitting Responsibility -> Breaking a class into too many tiny classes.
2. eg. TaxCalculator, BonusCalculator, BenefitsController, SalaryAggregator (Breaking them into too many small class do not make sense)
    - When all of these could be grouped into a cohesive **PayrollCalculator**.

Focus on **Cohesion not fragmentation**
- Group logic that changes together or belongs to same business concern.