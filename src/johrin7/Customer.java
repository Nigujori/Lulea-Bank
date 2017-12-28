package johrin7;
/**Klassen genom vilken man skapar customer-objekt som håller information om bankens kunder.
 * Den håller även infomation om kundens olika konton. Utan att skapa en bankkund kan bank-logik objektet inte 
 * skapa bankkonton. Eftersom SavingsAccount och creditAccount ärver av Account klassen
 * kan man utnyttja polymorphism och dynamic binding. Ett Account objekt kan ta flera olika former och beroende på
 * vilken form den antagit bestäms vilken implementation som en viss metod kommer att ha. 
 * @author Johan Ringström användarnamn johrin7. */

import johrin7.BankLogic.TypeOfAccount;

 
public class Customer extends Client{
	
	
		
	/**Konstruktor för att skapa ett customer-objekt.	
	 * @param foreName förnamn
	 * @param surname efternamn
	 * @param personalNumber personnumret i form av en String.
	 */
	public Customer(String foreName, String surname,  String personalNumber) 
	{
		super(foreName, surname, personalNumber);
	}
	/**Skapar en specifik typ av konto.
	 * @param accountType kontotyp som en String.
	 * @return bankkontonummer som en int.
	 */
	public Account createAccount(TypeOfAccount accountType) 
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
		default : return null;
		}
		return account;
	}
	
	
}