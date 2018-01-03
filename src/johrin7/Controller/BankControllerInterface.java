package johrin7.Controller;

import java.util.ArrayList;

import johrin7.Model.BankLogic.TypeOfAccount;
import johrin7.views.BankObserver;


/**  BankControllerInterface. 
 * @authorJohan Ringström användarnamn johrin7
 * */
public interface BankControllerInterface {
	public ArrayList<String> getAllCustomers(); 
	public ArrayList<String> getCustomer(String pNo);
	public String getAccount(String pNo, int accountId);
	public ArrayList<String> getTransactions(String pNr, int accountId);
	public double getCreditLimit(String pNr, int accountNr);
	
	public int createAccount(String pNo, TypeOfAccount typeOfAccount);
	public boolean createCustomer(String forename, String surname, String pNo);
	public boolean deposit(String pNo, int accountId, double amount);
	public boolean withdraw(String pNo, int accountId, double amount);
	public boolean changeCustomerName(String name, String surname, String pNo);
	public ArrayList<String> deleteCustomer(String pNo); 
	public String closeAccount(String pNr, int accountId);
	public void registerObserver(BankObserver bo);
	//public void notifyBankObservers(Boolean bool);
}
