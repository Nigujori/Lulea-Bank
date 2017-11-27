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
 
public class Customer {
		//En array med förnamnet i första indexet och efternamn i det andra.
		String[] nameArray = new String[2];
		private String personalNumber;
		//En array lista med kundens konton.
		private ArrayList<Account> accountList = new ArrayList<>();
		//Ett enum med två typer av transaktioner som möjliga värden.
		public static  enum TypeOfTransaction {WITHDRAW, DEPOSIT};
		
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
	
	/**Skapar ett sparkonto för åt en specifik kund och lääger detta till accountList.
	 * @return bankkontonummer som en int.
	 */
	public int createAccount() 
	{
		Account savingsAccount = new SavingsAccount();
		this.accountList.add(savingsAccount);
		return savingsAccount.getAccountNumber();
	}
	
	/**Ej klar! Kommer att behövas om mer än en typ av konto ska kunna skapas.
	 * @param interest som en double inte i procent.
	 * @param accountType kontotyp som en String.
	 * @return bankkontonummer som en int.
	 */
	public int createAccount(Double interest, String accountType) 
	{
		return 0;
	}
	
	/**Hämtar information om kontot.
	 * 
	 * @param accountNumberbankkontonummer som en int.
	 * @return en String med information om kontot. (Se getaccountInfo)
	 */
	public String getAccountInformation(int accountNumber ) 
	{
		Account bankAccount = this.getAccount(accountNumber);
		//Om bankkontot finns  retuneras informationen i form av en sträng annars retuneras ett felmeddelande.
		if(bankAccount != null) 
		{
			return bankAccount.getAccountInfo();
		} 
		else return "Bankkunden " + this.personalNumber + " har inget konto med kontonumret "
			+ accountNumber + " knutet till sig";
	}
	/**Förändrar ett specifikt kontos saldo. 
	 * @param accountNumber kontonummer som en int.
	 * @param amount summan med vilket saldot ska förändras.
	 * @param typeOfTransaction typ av transaktion, uttag eller insättning.
	 * @return boolean.
	 */
	public boolean changeAccountBalance(int accountNumber, double amount, TypeOfTransaction typeOfTransaction) 
	{
			//Om transaktionen är ett uttag och kontot finns ändras saldot om det finns tillräckligt med 
			//pengar. Retunerar true om uttaget medgavs annars false.
			if( typeOfTransaction == TypeOfTransaction.WITHDRAW &&  this.getAccount(accountNumber) != null) 
			{
				return this.getAccount(accountNumber).setBalance(amount *= -1);
			//Annars om kontot finns görs en insättning och true retuneraa annars retuneras false,
			} 
			else if(this.getAccount(accountNumber) != null) {
				return getAccount(accountNumber).setBalance(amount);
			} 
			else return false;
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
				customerList.add(ba.getAccountNumber() + " " + ba.getBalance() + " " + ba.getAccountType() + " " 
			+ ba.getInterest()*100);
			} 
			return customerList;
		} 
		else return customerList;
	}

	/**Hämtar information om räntan. Retuner räntan i form av en double.
	 * @param accountNr kontonumret som en int.
	 * @return räntan som en double.
	 */
	public double getInterestAmount(int accountNr) 
	{
		Account bankAccount;
		//Om kontot finns retuneras räntan annars retuneras -1.
		 if((bankAccount = this.getAccount(accountNr)) != null) 
		 {
			 return bankAccount.getInterestAmount();
		 }
		 else return -1;
	}
	
	/**Hjälp metod för att hitta ett specifikt konto.
	 * @param accountNumber
	 * @return ett Account objekt.
	 */
	private Account getAccount(int accountNumber) 
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
	
	public String getTransaction(int accountNumber) 
	{
		Account account = this.getAccount(accountNumber);
		return account.getTransactions().get(1).toString();
	}
}