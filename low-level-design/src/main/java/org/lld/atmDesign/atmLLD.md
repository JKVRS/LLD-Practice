# ATM Low Level Design

## Requirements: 
1. The ATM system should support basic operations such as balance inquiry, cash withdrawal, and cash deposit.
2. Users should be able to authenticate themselves using a card and a PIN (Personal Identification Number).
3. The system should interact with a bank's backend system to validate user accounts and perform transactions.
4. The ATM should have a cash dispenser to dispense cash to users.
5. The system should handle concurrent access and ensure data consistency.
6. The ATM should have a user-friendly interface for users to interact with.


## Entities and actor:
1. USER
2. ACCOUNT
3. BANK
4. Card
5. ATM
6. Transaction
### Actors: USER , ATM

## Entities Attributes:
### Bank 
* int id
* String bankName;
* List<User> users
* List<ATM> atm

### User 
* int id
* String name
* String email
* String dob
* List<Account> accounts

### Account
* int accountNumber
* User accountHolderName
* double balance
* AccountType accountType
* LocalDateTime accountCreationDate
* LocalDateTime accountModificationDate
* List<Card> cards
* List<Transaction> transactions

### Card
* int cardNumber
* CardType cardType
* int cardPin
* int cvv
* String expireDate
* double limit
* Account account


### Transaction
* String trxId
* TransactionType transactionType
* double amount
* LocalDateTime trxDate
* TransactionStatus trxStatus
* Account account

### ATM
* int id
* Bank bank
* Dispenser dispenser
* ATMStatus atmStatus

### Dispenser 
* int dispenserId;
* Map<Denomination, Integer> cashInventory;
* double totalCashAvailable;

## RelationShip and Mapping
* Bank : User - (m:m) ----> has a relationship ( weak )  -> but as per requirement we are considering, 
                                                            only one bank so (1:m) we can consider 
* Bank : ATM - (m:m) ----> has a relationship ( weak) --> but as per requirement we are considering only 
                                                          one bank so (1:m) we can consider
* User : Account - (1:m)  ---> has a relationship ( strong) --> if User is deleted then account automatic will be closed
* Account : Card - (1:m)  ---> has a relationship (Strong) --> if account is deleted then card will not exist
* Account : Transaction - (1:m) -->
* ATM : Dispenser - ( 1:1) --> has a relationship (Strong)

## Design Pattern
* Singleton : to create ATM
* Facade : to interact with bank's backend System
* Observer : to Notify the user
* Strategy : for transaction mode (Future Requirement)

## Services :
* BankingService
  1. createUser()
  2. getUserDetails()
  3. createAccount()
  4. getAccountDetail()
  5. validateUserAccount()
  6. withdraw()
  7. deposit()
* ATMService
  1. balanceInquiry()
  2. withdrawCash()
  3. depositCase()
* AuthenticationService
  1. AuthenticateUser()
* AccountService (this is not in requirement but we split the bank service to avoid more burden from bank service)

## We need to follow Design principle as we can (SOLID)



