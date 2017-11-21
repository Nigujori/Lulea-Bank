package johrin7;

import java.util.ArrayList;


public class Customer {
		String[] nameArray = new String[2];
		private String personalNumber;
		private ArrayList<BankAccount> accountList = new ArrayList<>();
		public static  enum TypeOfTransaction {WITHDRAW, DEPOSIT};

	public Customer(String foreName, String surname,  String personalNumber) {
		this.nameArray[0] = foreName;
		this.nameArray[1] = surname;
		this.personalNumber = personalNumber;
	}
	
	public String[] getName() {
		return this.nameArray;
	}
	
	public boolean setName(String foreName, String surname) {
		this.nameArray[0] = foreName;
		this.nameArray[1] = surname;
		return true;
	}
	
	public String getPersonalNumber() {
		return personalNumber;
	}
	
	
	public boolean deleteAccount(int accountNumber) {
		BankAccount bankAccount = getAccount(accountNumber);
		if(bankAccount != null) {
		this.accountList.remove(bankAccount);
		return true;
		} return false;
	}
	
	public int createAccount() {
		BankAccount savingsAccount = new SavingsAccount();
		this.accountList.add(savingsAccount);
		return savingsAccount.getAccountNumber();
	}
	//Fixa så att man kan välja vilken typ
	public int createAccount(Double interest) {
		BankAccount savingsAccount = new SavingsAccount(interest);
		this.accountList.add(savingsAccount);
		return savingsAccount.getAccountNumber();
	}
	
	public String getAccountInformation(int accountNumber ) {
		BankAccount bankAccount = getAccount(accountNumber);
		if(bankAccount != null) {
			return bankAccount.getAccountInfo();
		} else return "Bankkunden " + this.personalNumber + " har inget konto med kontonumret "
			+ accountNumber + " knutet till sig";
	}
	
	public boolean changeAccountBalance(int accountNumber, double amount, TypeOfTransaction typeOfTransaction) {
			if( typeOfTransaction == TypeOfTransaction.WITHDRAW &&  this.getAccount(accountNumber) != null) {
				//System.out.println( this.getAccount(accountNumber).setBalance(amount *= -1));
				return this.getAccount(accountNumber).setBalance(amount *= -1);
			} else if(getAccount(accountNumber) != null) {
				return getAccount(accountNumber).setBalance(amount);
			} else return false;
	}
	
	public ArrayList<String> getCustomer() {
		ArrayList<String> customerList = new ArrayList<>();
		customerList.add(this.nameArray[0] + " " + this.nameArray[1] + " " + this.personalNumber);
		if (accountList != null) {
			for(BankAccount ba : accountList){
				customerList.add(ba.getAccountNumber() + " " + ba.getBalance() + " " + ba.getAccountType() + " " + ba.getInterest()*100 );
			} return customerList;
		} else return customerList;
	}

	
	public double getInterestAmount(int accountNr) {
		BankAccount bankAccount = this.getAccount(accountNr);
		return bankAccount.getBalance() * bankAccount.getInterest() ;
	}
	
	
	private BankAccount getAccount(int accountNumber) {
		BankAccount bankAccount = null;
		for(BankAccount ba : accountList){
	        if (ba.getAccountNumber() == accountNumber) {
	        	bankAccount = ba;
	        }
		}
		return bankAccount;
	}
	
}