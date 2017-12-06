package johrin7;

import java.util.ArrayList;

public interface BankControllerInterface {
	public boolean createCustomer(String forename, String surname, String pNo);
	public int createSavingsAccount(String pNo);
	public int createCreditAccount(String pNr);
	public boolean deposit(String pNo, int accountId, double amount);
	public boolean withdraw(String pNo, int accountId, double amount);
	public boolean changeCustomerName(String name, String surname, String pNo);
	public ArrayList<String> deleteCustomer(String pNo); 
	public String closeAccount(String pNr, int accountId);
}
