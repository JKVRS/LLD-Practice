package org.lld.atmDesign;

import java.time.LocalDateTime;
import java.util.*;

class Bank {

    private int id;
    private String bankName;
    private List<User> users;
    private ATM  atm;

    public Bank(int id, String bankName) {
        this.id = id;
        this.bankName = bankName;
        users = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public String getBankName() {
        return bankName;
    }

    public List<User> getUsers() {
        return users;
    }

    public ATM getAtm() {
        return atm;
    }
}
class User {

    private int id;
    private String name;
    private String email;
    private String dob;
    private List<Account> accounts;

    public User(int id, String name, String email, String dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.accounts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
class Account {

    private int accountNumber;
    private User accountHolderName;
    private String bankName;
    private double balance;
    private AccountType accountType;
    private LocalDateTime accountCreationDate;
    private LocalDateTime accountModificationDate;
    private List<Card> cards;
    private List<Transaction> transactions;

    public Account(int accountNumber, User accountHolderName, double balance, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.accountType = accountType;
        this.accountCreationDate = LocalDateTime.now();
        this.cards = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public User getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public LocalDateTime getAccountCreationDate() {
        return accountCreationDate;
    }

    public LocalDateTime getAccountModificationDate() {
        return accountModificationDate;
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
    public void setBalance(double remainBalance){
       this.balance = remainBalance;
    }
}

enum AccountType{
    SAVING,CURRENT
}
class Card {

    private long cardNumber;
    private CardType cardType;
    private int cardPin;
    private int cvv;
    private String expireDate;
    private double limit;
    private Account account;
    private CardStatus cardStatus;

    public Card(long cardNumber, CardType cardType, int cardPin,
                int cvv, String expireDate, double limit,
                Account account, CardStatus cardStatus) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.cardPin = cardPin;
        this.cvv = cvv;
        this.expireDate = expireDate;
        this.limit = limit;
        this.account = account;
        this.cardStatus = cardStatus;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public CardType getCardType() {
        return cardType;
    }

    public int getCardPin() {
        return cardPin;
    }

    public int getCvv() {
        return cvv;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public double getLimit() {
        return limit;
    }

    public Account getAccount() {
        return account;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }
}
enum  CardStatus{
    WORKING,CLOSED,BLOCK
}
enum CardType {
    DEBIT,CREDIT
}
class Transaction {

    private String trxId;
    private TransactionType transactionType;
    private double amount;
    private LocalDateTime trxDate;
    private TransactionStatus trxStatus;
    private Account account;

    public Transaction(TransactionType type, double amount, Account account, TransactionStatus status) {
        this.trxId = UUID.randomUUID().toString();
        this.transactionType = type;
        this.amount = amount;
        this.account = account;
        this.trxStatus = status;
        this.trxDate = LocalDateTime.now();
    }

    public double getAmount() {
        return amount;
    }

    public TransactionStatus getTrxStatus() {
        return trxStatus;
    }

}

enum TransactionType{
    ATM,ONLINE
}

enum TransactionStatus{
    COMPLETE,FAILED,PENDING
}
class ATM {

    private int id;
    private Bank bank;
    private List<Dispenser> dispenser;
    private ATMStatus atmStatus;
    private double totalCashAvailable;

    public ATM(int id, Bank bank, ATMStatus atmStatus) {
        this.id = id;
        this.bank = bank;
        this.dispenser = new ArrayList<>();
        this.atmStatus = atmStatus;
    }

    public int getId() {
        return id;
    }

    public Bank getBank() {
        return bank;
    }

    public List<Dispenser> getDispenser() {
        return dispenser;
    }

    public ATMStatus getAtmStatus() {
        return atmStatus;
    }

    public  void setDispenser(List<Dispenser> dispenser){
        this.dispenser.addAll(dispenser);
    }
}
enum ATMStatus {
    OPEN,CLOSED,MAINTENANCE
}
class Dispenser {

    private int dispenserId;
    private int numberOfNote;
    private Denomination denomination;

    public Dispenser(int dispenserId, int numberOfNote, Denomination denomination) {
        this.dispenserId = dispenserId;
        this.numberOfNote = numberOfNote;
        this.denomination = denomination;

    }

    public int getDispenserId() {
        return dispenserId;
    }

    public int getNumberOfNote() {
        return numberOfNote;
    }

    public Denomination getDenomination() {
        return denomination;
    }
    public void dispense(int notesToDispense) {
        if (notesToDispense <= numberOfNote) {
            numberOfNote -= notesToDispense;
        }
    }

    public void displayStatus() {
        System.out.println(denomination + " Notes Remaining: " + numberOfNote);
    }

    public int getNoteValue() {
        return switch (denomination) {
            case FIVE_HUNDRED -> 500;
            case HUNDRED -> 100;
            case FIFTY -> 50;
        };
    }
}
enum Denomination{
    FIVE_HUNDRED,HUNDRED,FIFTY
}
interface TransactionService{

}

class BankService{
    private Map<User,Account> userAccountMap;
    private List<ATM> atmList;


    public BankService(){
        userAccountMap = new HashMap<>();
        atmList = new ArrayList<>();
    }
    public void OpenAccount(User user,Account account){
        userAccountMap.put(user,account);
        System.out.println("Account has been crated for the user "+ user);
    }

    public void credit(User user, Account account, double amount){

    }
    public void debit(User user,Account account, double amount) throws InterruptedException {
        if (account.getBalance() < amount) {
            System.out.println("Insufficient funds.");
            return;
        }
        Account acnt = userAccountMap.get(user) ;//-= amount;
        double balance = acnt.getBalance()-amount;
        acnt.setBalance(balance);
        Thread.sleep(2000);
        System.out.println("Debited ₹" + amount);
        acnt.getTransactions().add(new Transaction(TransactionType.ATM, amount, account, TransactionStatus.COMPLETE));
        System.out.println("Entry in Transaction completed of the amount :"+ amount);
    }
    public double fetchBalance(Account account){
       return account.getBalance();
    }

    public boolean validateCard(Card card, int pin){
        return card.getCardStatus() == CardStatus.WORKING && card.getCardPin() == pin;
    }
}
class ATMService{

    private BankService bankService;

    public ATMService(BankService bankService){
         this.bankService = bankService;
    }
    public void balanceInquire(Card card){
        Account account = card.getAccount();
        System.out.println("Available balance: ₹" + bankService.fetchBalance(account));
    }
    public void cashWithdraw(User user, Card card, int pin, double amount) throws InterruptedException {
        if (!bankService.validateCard(card, pin)) {
            System.out.println("Authentication failed.");
            return;
        }
        bankService.debit(user,card.getAccount(), amount);
    }
    public void cashDeposit()
    {
        return;
    }

    private void authenticate(){
      return;
    }
}




public class ATMMain {

    public static void main(String[] args) throws InterruptedException {

        User vijay = new User(10,"Vijay","abc@gmail.com","01/10/1998");
        User ram = new User(20,"Ram","xyz@gmai;.com","12/01/1996");

        Bank unionBank = new Bank(1,"UnionBank");
        ATM atm = new ATM(11,unionBank,ATMStatus.OPEN);
        Dispenser d1 = new Dispenser(500,20,Denomination.FIVE_HUNDRED);
        Dispenser d2 = new Dispenser(100,20,Denomination.HUNDRED);
        Dispenser d3 = new Dispenser(50,20,Denomination.FIFTY);
        List<Dispenser> dispensers = Arrays.asList(d1,d2,d3);
        atm.setDispenser(dispensers);
        System.out.println("UnionBank ATM is ready and In-Service !!");

        Account vijayAccount = new Account(21121130,vijay,20000.00,AccountType.SAVING);
        System.out.println("Bank Account has been open for " + vijay.getName());
        Thread.sleep(1000);
        System.out.println("Create ATM/DEBIT card !!");
        Card vijayCard = new Card(41405410,CardType.DEBIT,1998,520,"12/30",5000.00,vijayAccount,CardStatus.WORKING);
        System.out.println("ATM Card has been issued for the User "+ vijay.getName());
        Thread.sleep(2000);

        
        BankService bankService = new BankService();
        bankService.OpenAccount(vijay, vijayAccount);

        ATMService atmService = new ATMService(bankService);

        System.out.println("---- ATM Transactions ----");
        atmService.balanceInquire(vijayCard);
        Thread.sleep(1000);
        atmService.cashWithdraw(vijay,vijayCard, 1998, 500);
        Thread.sleep(3000);
        atmService.balanceInquire(vijayCard);
//        atmService.cashDeposit(vijayCard, 1998, 2000);
//        atmService.balanceInquiry(vijayCard);

    }

}
