package johrin7.Model;

import java.util.ArrayList;

import johrin7.Model.BankLogic.TypeOfAccount;

public abstract class Client {
	//En array med förnamnet i första indexet och efternamn i det andra.
	private String[] nameArray = new String[2];
	private String personalNumber;
	//En array lista med kundens konton.
	private ArrayList<Account> accountList = new ArrayList<>();
	
	public Client(String foreName, String surname,  String personalNumber) 
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
	
	public int openAcount(TypeOfAccount accountType) {
		Account account = createAccount(accountType);
		this.accountList.add(account);
		return account.getAccountNumber();
	}
	
	
	public abstract Account createAccount(TypeOfAccount accountType) ;
}
