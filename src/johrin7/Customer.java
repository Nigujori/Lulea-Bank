package johrin7;

import java.util.ArrayList;

public class Customer {
		String[] nameArray = new String[2];
		private String personalNumber;
		private ArrayList<SavingsAccount> accountList = new ArrayList();
		public static  enum TypeOfTransaction {WITHDRAW, DEPOSIT};

	public Customer(String foreName, String sureName,  String personalNumber) {
		this.nameArray[0] = foreName;
		this.nameArray[1] = sureName;
		this.personalNumber = personalNumber;
	}
	
	public String[] getName() {
		return this.nameArray;
	}
	
	public boolean setName(String foreName, String sureName) {
		this.nameArray[0] = foreName;
		this.nameArray[1] = sureName;
		return true;
	}
	
	public String getPersonalNumber() {
		return personalNumber;
	}
	
	
	public boolean deleteAccount(int accountNumber) {
		SavingsAccount savingsAccount = getAccount(accountNumber);
		this.accountList.remove(savingsAccount);
		return true;
	}
	
	public boolean createAccount(Double interest, SavingsAccount.BankAccountType bankAccountType) {
		SavingsAccount savingsAccount = new SavingsAccount();
		this.accountList.add(savingsAccount);
		return true;
	}
	
	public String getAccountInformation(int accountNumber ) {
		return getAccount(accountNumber).getAccountInfo();
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