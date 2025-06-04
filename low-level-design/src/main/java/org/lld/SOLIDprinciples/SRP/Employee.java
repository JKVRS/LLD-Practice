package org.lld.SOLIDprinciples.SRP;

/**
 * This class has to deal with employee only -> one reason to change
 * Its doesn't calculate salary, store itself or send Email or sms
 * We can create Seprate classes to handle those responsibility
 */
public class Employee {
    private String name;
    private String email;
    private double salary;

    public Employee(String name, String email, double salary){
        this.name=name;
        this.email=email;
        this.salary= salary;
    }

    public String getName() {return name;};
    public String getEmail() {return email;}
    public double getSalary(){return  salary;}
}

// This can handle the logic to calculate the payroll
// if any change required only we need to update this method only
class PayrollCalculator {
    public double calculatePayroll(Employee employee) {
        double base = employee.getSalary();
        double tax = base * 0.2; // Tax logic
        double fixBenifits = 10000;
        return base - tax + fixBenifits;
    }
}

// Responsibility to persist to DB
class EmployeeRepository {
    public void save(Employee employee){
        // JDBC or ORM connection to save
        System.out.println(employee.getName()+" save to Database");
    }
}

// Responsibility3 -> Payslip generation
class GeneratePaySlip{
    public String generatePaySlip(Employee employee, double netPay){
        return "Payslip for: " + employee.getName() + "\n" +
                "Email: " + employee.getEmail() + "\n" +
                "Net Pay: ₹" + netPay + "\n" +
                "----------------------------\n";
    }
}

// Responsibility4: Send email (only email sending)
class EmailService {
    public void SendPaySlip(Employee employee, String paySlip) {
        System.out.println("Sending payslip to: " + employee.getEmail());
        // Simulate email with a print
        System.out.println(paySlip);
    }
}
