package johrin7;

import java.util.ArrayList;

import johrin7.BankLogic.TypeOfAccount;


public interface BankModelInterface {
	public boolean createCustomer(String forename, String surname, String pNo);
	public ArrayList<String> getAllCustomers(); 
	public ArrayList<String> getCustomer(String pNo);
	public int createSavingsAccount(String pNo);
	public int createCreditAccount(String pNo);
	public int createAccount(String pNo, TypeOfAccount typeOfAccount);
	public boolean deposit(String pNo, int accountId, double amount);
	public boolean withdraw(String pNo, int accountId, double amount);
	public boolean changeCustomerName(String name, String surname, String pNo);
	public ArrayList<String> deleteCustomer(String pNo); 
	public String closeAccount(String pNr, int accountId);
	public String getAccount(String pNo, int accountId);
	public ArrayList<String> getTransactions(String pNr, int accountId);
	public void registerObserver(BankObserver bo);
	public void notifyBankObservers(Boolean bool);
}
