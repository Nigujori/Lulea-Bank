package johrin7.Model;
/**Klassen genom vilken man skapar customer-objekt. Den ärver av Client-klassen.
 * Utan att skapa en bankkund kan bank-logik objektet inte skapa bankkonton. 
 * Är en del av implementeringen av factory-mönstret(pattern) och är den konkreta
 * implementeringen av Client-klassens abstrakta factory-metod. Läs mer om detta i Client-klassen.
 * @author Johan Ringström användarnamn johrin7. */

import johrin7.Model.BankLogic.TypeOfAccount;

 
public class Customer extends Client
{
	/**Konstruktor för att skapa ett customer-objekt.	
	 * @param foreName förnamn
	 * @param surname efternamn
	 * @param personalNumber personnumret i form av en String.
	 */
	public Customer(String foreName, String surname,  String personalNumber) 
	{
		super(foreName, surname, personalNumber);
	}
	/**Skapar ett konto av en viss typ beroende på parameterns värde.
	 * @param accountType kontotyp som ett enumvärde.
	 * @return ett Account-objekt.
	 */
	public Account createAccount(TypeOfAccount accountType) 
	{ 
		Account account;
		String accountTypeStr =accountType.toString();
		switch (accountTypeStr) {
		case "SPARKONTO": {
			account = new SavingsAccount();
			break;
		}
		case "KREDITKONTO": {
			account = new CreditAccount();
			break;
		}
		default : return null;
		}
		return account;
	}
}