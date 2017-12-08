package johrin7;

import java.util.ArrayList;

import johrin7.BankLogic.TypeOfAccount;

public interface BankControllerInterface {
	public boolean createCustomer(String forename, String surname, String pNo);
	public int createAccount(String pNo, TypeOfAccount typeOfAccount);
	public boolean deposit(String pNo, int accountId, double amount);
	public boolean withdraw(String pNo, int accountId, double amount);
	public boolean changeCustomerName(String name, String surname, String pNo);
	public ArrayList<String> deleteCustomer(String pNo); 
	public String closeAccount(String pNr, int accountId);
}
