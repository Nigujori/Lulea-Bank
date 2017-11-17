package johrin7;

import java.util.ArrayList;

import johrin7.SavingsAccount.BankAccountType;

public class Customer {
		String[] nameArray = new String[2];
		private String personalNumber;
		private ArrayList<String> customerList = new ArrayList<>();
		private ArrayList<SavingsAccount> accountList = new ArrayList<>();
		public static  enum TypeOfTransaction {WITHDRAW, DEPOSIT};

	public Customer(String foreName, String surname,  String personalNumber) {
		this.nameArray[0] = foreName;
		this.nameArray[1] = surname;
		this.personalNumber = personalNumber;
		this.customerList.add(this.nameArray[0] + " " + this.nameArray[1] + " " + this.personalNumber);
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
		SavingsAccount savingsAccount = getAccount(accountNumber);
		if(savingsAccount != null) {
		this.accountList.remove(savingsAccount);
		return true;
		} return false;
	}
	
	public int createAccount() {
		SavingsAccount savingsAccount = new SavingsAccount();
		this.accountList.add(savingsAccount);
		return savingsAccount.getAccountNumber();
	}
	
	public int createAccount(Double interest, BankAccountType bankAccountType) {
		SavingsAccount savingsAccount = new SavingsAccount(interest, bankAccountType);
		this.accountList.add(savingsAccount);
		return savingsAccount.getAccountNumber();
	}
	
	public String getAccountInformation(int accountNumber ) {
		SavingsAccount savingsAccount = getAccount(accountNumber);
		if(savingsAccount != null) {
			return savingsAccount.getAccountInfo();
		} else return "Bankkunden " + this.personalNumber + " har inget konto med kontonumret "
			+ accountNumber + " knutet till sig";
	}
	
	public boolean changeAccountBalance(int accountNumber, double amount, TypeOfTransaction typeOfTransaction) {
			if( typeOfTransaction == TypeOfTransaction.WITHDRAW ||  getAccount(accountNumber) != null 
				||  getAccount(accountNumber).getBalance() < amount) {
				getAccount(accountNumber).setBalance(amount *= -1);
				return true;
			} else if(getAccount(accountNumber) != null) {
				getAccount(accountNumber).setBalance(amount);
				return true;
			} else return false;
	}
	
	public ArrayList<String> getCustomer() {
		if (accountList != null) {
			for(SavingsAccount sa : accountList){
				customerList.add(sa.getAccountNumber() + " " + sa.getBalance() + " " + sa.getAccountType());
			} return customerList;
		} else return customerList;
	}

	
	
	private SavingsAccount getAccount(int accountNumber) {
		SavingsAccount savingsAccount = null;
		for(SavingsAccount sa : accountList){
	        if (sa.getAccountNumber() == accountNumber) {
	        	savingsAccount = sa;
	        }
		}
		return savingsAccount;
	}	
}