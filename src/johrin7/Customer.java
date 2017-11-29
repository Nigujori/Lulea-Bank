package johrin7;
/**Klassen genom vilken man skapar customer-objekt som håller information om bankens kunder.
 * Den håller även infomation om kundens olika konton. Utan att skapa en bankkund kan bank-logik objektet inte 
 * skapa bankkonton. Eftersom SavingsAccount och eventuellt andra framtida kontotyper ärver av Account klassen
 * kan man utnyttja polymorphism dynamic binding. Ett Account objekt kan ta flera olika former och beroende på
 * vilken form den antagit bestäms bestäms vilken implementation som en viss metod kommer att ha. I detta fall
 * läggs alla kontoobjekt in i accountList som typen Account men flera av dess metoder är abstrakta och
 * får sin implementation i dess subklasser.
 * @author Johan Ringström användarnamn johrin7. */

import java.util.ArrayList;

import johrin7.BankLogic.TypeOfAccount;

 
public class Customer {
		//En array med förnamnet i första indexet och efternamn i det andra.
		String[] nameArray = new String[2];
		private String personalNumber;
		//En array lista med kundens konton.

		private ArrayList<Account> accountList = new ArrayList<>();
		//Ett enum med två typer av transaktioner som möjliga värden.
		
	/**Konstruktor för att skapa ett customer-objekt.	
	 * @param foreName förnamn
	 * @param surname efternamn
	 * @param personalNumber personnumret i form av en String.
	 */
	public Customer(String foreName, String surname,  String personalNumber) 
	{
		this.nameArray[0] = foreName;
		this.nameArray[1] = surname;
		this.personalNumber = personalNumber;
	}
	/**Hämtar kundens namn.
	 * @return en Strin array [förnamn, efternamn].
	 */
	public String[] getName() 
	{
		return this.nameArray;
	}
	
	/**Ändrar kundens namn.
	 * @param foreName förnamn.
	 * @param surname efternamn.
	 * @return boolean.
	 */
	public boolean setName(String foreName, String surname) 
	{
		this.nameArray[0] = foreName;
		this.nameArray[1] = surname;
		return true;
	}
	
	/**Hämtar kundens personnummer.
	 * @return en String med personnumret.
	 */
	public String getPersonalNumber() 
	{
		return personalNumber;
	}
	
	/**Tar bort ett specifikt konto från ett customer-objekt 
	 * @param accountNumber kontonumret som en int.
	 * @return boolean.
	 */
	public boolean deleteAccount(int accountNumber) 
	{
		Account bankAccount = this.getAccount(accountNumber);
		//Om det finns ett konto med angivet kontonummer så tas detta bort och true retuneras annars false.
		if(bankAccount != null) 
		{
		this.accountList.remove(bankAccount);
		return true;
		} 
		return false;
	}
	
	/**Skapar en specifik typ av konto.
	 * @param accountType kontotyp som en String.
	 * @return bankkontonummer som en int.
	 */
	public int createAccount(TypeOfAccount accountType) 
	{ 
		Account account;
		String accountTypeStr =accountType.toString();
		switch (accountTypeStr) {
		case "SAVINGSACCOUNT": {
			account = new SavingsAccount();
			break;
		}
		case "CREDITACCOUNT": {
			account = new CreditAccount();
			break;
		}
		default : return -1;
		}
		this.accountList.add(account);
		return account.getAccountNumber();
	}
	
	/**Hämtar ett specifik customers objects information.
	 * @return en array list med information om kunden. [förnamn efternamn och personnumret, kontonummer saldo kontotyp räntesats(%) räntan, ...]
	 */
	public ArrayList<String> getCustomer() 
	{
		//Skapar ett ett nytt customerList object.
		ArrayList<String> customerList = new ArrayList<>();
		//Lägger till namn och personnummer först i denna array-sträng-lista.
		customerList.add(this.nameArray[0] + " " + this.nameArray[1] + " " + this.personalNumber);
		//Om kunden har ett eller flera konto knytet till sig läggs dessa till efter den första namn- 
		//och personnummer-strängen. Listan retuneras sedan annars retuneras listan med endast namn-strängen.
		if (accountList != null) 
		{
			for(Account ba : accountList)
			{
				String tmp = ba.getAccountInfo();
				customerList.add(ba.getAccountInfo().substring(0, tmp.lastIndexOf(" ")));
			} 
			return customerList;
		} 
		else return customerList;
	}

	
	/**Metod för att hitta ett specifikt konto.
	 * @param accountNumber
	 * @return ett Account objekt.
	 */
	public Account getAccount(int accountNumber) 
	{
		Account bankAccount = null;
		//Om kontot finns i kundens kontolista retuneras detta konto annars retuneras null;
		for(Account ba : accountList)
		{
	        if (ba.getAccountNumber() == accountNumber) 
	        {
	        	bankAccount = ba;
	        }
		}
		return bankAccount;
	}
}