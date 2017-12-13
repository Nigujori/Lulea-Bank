package johrin7.Controller;

import java.util.ArrayList;
import johrin7.Model.BankModelInterface;
import johrin7.Model.BankLogic.TypeOfAccount;
import johrin7.views.BankObserver;
import johrin7.views.CustomerSearchAndDisplayView;

/** Kntroller-klassen i MVC strukturen. Den skapar första swing-fönstret och updaterar denna med information 
 * från modellen. Eftersom jag har valt att använda mig av lambda uttryck så ligger själva listeners-koden
 * i de olika swing-klasserna i direkt anslutning till kontroller-objekten och det faktum att Banklogic-klassen 
 * har vissa drag av en controller-klass blir metoderna i denna klass inte mer än en förmedlare till Banklogic-
 * klassens metoder. Den implementerar BankControllerInterface. 
 * @authorJohan Ringström användarnamn johrin7
 * */
public class BankController implements BankControllerInterface {
	BankModelInterface bankModel;
	CustomerSearchAndDisplayView view;
	
	/**Konstruktorn. Skapar första viewn och uppdaterar densamma. Har en reference till modellen.
	 * @param bankModel
	 */
	public BankController(BankModelInterface bankModel) 
	{
		this.bankModel = bankModel;
		view = new CustomerSearchAndDisplayView(this);
		view.createView();
		view.updateBank(true);
	}
	
	/**Se information om metoderna i BankLogic-klassen. 
	 */

	@Override
	public boolean createCustomer(String forename, String surname, String pNo) 
	{
		return bankModel.createCustomer(forename, surname, pNo);
	}


	@Override
	public boolean deposit(String pNo, int accountId, double amount) 
	{
		return bankModel.deposit(pNo, accountId, amount);
	}

	@Override
	public boolean withdraw(String pNo, int accountId, double amount)
	{
		return bankModel.withdraw(pNo, accountId, amount);
	}

	@Override
	public boolean changeCustomerName(String name, String surname, String pNo) 
	{
		return bankModel.changeCustomerName(name, surname, pNo);
	}

	@Override
	public ArrayList<String> deleteCustomer(String pNo) 
	{
		return bankModel.deleteCustomer(pNo);
	}

	@Override
	public int createAccount(String pNo, TypeOfAccount typeOfAccount) 
	{
		return bankModel.createAccount(pNo, typeOfAccount);
	}

	@Override
	public String closeAccount(String pNr, int accountId) 
	{
		return bankModel.closeAccount(pNr, accountId);
	}

	@Override
	public ArrayList<String> getAllCustomers() 
	{
		return bankModel.getAllCustomers();
	}

	@Override
	public ArrayList<String> getCustomer(String pNo) 
	{
		return bankModel.getCustomer(pNo);
	}


	@Override
	public String getAccount(String pNo, int accountId) 
	{
		return bankModel.getAccount(pNo, accountId);
	}

	@Override
	public ArrayList<String> getTransactions(String pNr, int accountId) 
	{
		return bankModel.getTransactions(pNr, accountId);
	}

	@Override
	public double getCreditLimit(String pNr, int accountNr) 
	{
		return bankModel.getCreditLimit(pNr, accountNr);
	}

	@Override
	public void registerObserver(BankObserver bo) 
	{
		bankModel.registerObserver(bo);
		
	}

	@Override
	public void notifyBankObservers(Boolean bool) 
	{
		bankModel.notifyBankObservers(bool);
	}
}

