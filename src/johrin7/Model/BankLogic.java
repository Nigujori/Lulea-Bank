package johrin7.Model;
/**Denna klass rymmer bankens logik. Den skapar och tar bort kunder och håller ett register med customers-objekt.
 *Det är genom dessa customers-objekt som Banklogic-klassen får möjlighet att ta del av kundernas uppgifter och deras 
 *konton. Implementerar BankModelInterface som på så sätt kräver vilka metoder som måste implementeras.
 *Eftersom denna klass kan sägas knyta ihop hela modellen till en bank med dess logik agerar denna klass som
 *en representant för alla modellens klasser. Det är denna klass som implementerar, de för MVC strukturens
 *så viktiga metoderna, registerObserver, unRegisterObserver och notifyBankObservers. Den håller därför även
 *en arrayList av bankObservers, views i detta fall, som vill veta om och hur modellen förändras.
 *@author Johan Ringström användarnamn johrin7.*/
import java.util.ArrayList;

import johrin7.Model.Account.TypeOfTransaction;
import johrin7.views.BankObserver;

public class BankLogic implements BankModelInterface {
	
	//Array-lista med customer-objekt.
	private ArrayList<Client> customerObjectList = new ArrayList<>();
	private ArrayList<BankObserver> bankObservers = new ArrayList<>();
	public static  enum TypeOfAccount {SPARKONTO, KREDITKONTO};
	
	/**Skapar ett customer-objekt
	 * @param forename
	 * @param surname
	 * @param pNo
	 * @return boolean 
	 */
	public boolean createCustomer(String forename, String surname, String pNo) 
	{
		//Om det inte finns något customer-objekt med samma personnummer så skapas ett som 
		//läggs till customerObjectList. Retunerar därefter true annars false. 
		if((this.getCustomerObject(pNo)) == null && !pNo.isEmpty() && pNo.matches("[0-9]+") && !forename.isEmpty() && !surname.isEmpty()) 
		{
			Client newCustomer = new Customer(forename, surname, pNo);
			customerObjectList.add(newCustomer);
			this.notifyBankObservers(true);
			return true;
		}
		else {
		this.notifyBankObservers(false); 
		return false;
		}
	}
	
	/**Hämtar alla kunder. (Jag valde att göra en ny lista för varje anrop till metoden, 
	 * eftersom jag då enbart behöver tänka på att ta bort och lägga till i en lista från vilken denna
	 * mer temporära lista skapas.
	 * @return array list med alla kunder. [förnamn efternamn personnummer, ...]
	 */
	public ArrayList<String> getAllCustomers() 
	{
		//Skapar en lista som används för att lägga customer-objekten i.
		ArrayList<String> customerStrList = new ArrayList<>();
		//Går igenom customerObjectList och lägger namnen och personnumren i customerStrList. Retunerar sedan denna lista.
			for(Client c : customerObjectList)
			{
				customerStrList.add(c.getName()[0] + " " + c.getName()[1] + " " + c.getPersonalNumber());
			} 
			return customerStrList;
	}
	
	/**Hämtar en specefik kund.
	 * @param pNo personnumret som en String
	 * @return array list som reprsenterar kund-objektets information. [Namn och personnummer, konto1, konto2, konto...]
	 */
	public ArrayList<String> getCustomer(String pNo)
	{
		Client customer;
		//Om det finns ett object med matchande personnummer(Samtidigt läggs detta objekt i customer variabeln.)
		//retuneras en arrayList, som representerar detta object. Retunerar null om ingen matchnig finns.
		if((customer = this.getCustomerObject(pNo)) != null) 
		{
			return customer.getCustomer();
		} 
		else return null;
	}
	
	/**Skapar ett sparkonto till en specifik kund.
	 * @param pNo personnumret som en String.
	 * @return kontonumret som en int.
	 */
	public int createSavingsAccount(String pNo) 
	{
		Client customer;
		//Om det finns ett object med matchande personnummer(Samtidigt läggs detta objekt i customer variabeln.)
		//retuneras kontonumret annars -1;
		if ((customer = this.getCustomerObject(pNo)) != null) {
			return customer.openAcount(TypeOfAccount.SPARKONTO);
		} else return -1;
	}
	/**Skapar ett kreditkonto till en specifik kund.
	 * @param pNo personnumret som en String.
	 * @return kontonumret som en int.
	 */
	public int createCreditAccount(String pNr)
	{
		Client customer;
		//Om det finns ett object med matchande personnummer(Samtidigt läggs detta objekt i customer variabeln.)
		//retuneras kontonumret annars -1;
		if ((customer = this.getCustomerObject(pNr)) != null) {
			return customer.openAcount(TypeOfAccount.KREDITKONTO);
		} else return -1;
	 	}
	
	public int createAccount(String pNr, TypeOfAccount typeOfAccount) {
		Client customer;
		if ((customer = this.getCustomerObject(pNr)) != null) {
			int accountNumber =customer.openAcount(typeOfAccount);
			notifyBankObservers(true);
			return accountNumber;
		} else
			{
				notifyBankObservers(false);
				return -1;
			}
	 }
	/**Gör en insättning
	 * @param pNo personnumret som en String.
	 * @param accountId kontonumret som en int.
	 * @param amount summan som ska sättas in.
	 * @return boolean.
	 */
	public boolean deposit(String pNo, int accountId, double amount) 
	{
		Client customer;
		//Om det finns ett object med matchande personnummer och ett konto med matchande kontonummer
		//görs en insättning på det kontot och retunerar true annars false.
		if((customer = this.getCustomerObject(pNo)) != null && isAccountToCustomer(pNo, accountId)) {
			Boolean bool = customer.getAccount(accountId).changeAccountBalance(amount, TypeOfTransaction.DEPOSIT);
			notifyBankObservers(bool);
			return  bool;
		} else { 
			notifyBankObservers(false);
			return false;
			}
	}
	/**Gör ett uttag.
	 * @param pNo personnumret som en String.
	 * @param accountId kontonumret som en int.
	 * @param amount summan som ska sättas in.
	 * @return boolean.
	 */
	public boolean withdraw(String pNo, int accountId, double amount) 
	{
		Client customer;
		Boolean bool = false;
		//Om det finns ett object med matchande personnummer och ett konto med matchande kontonummer
		//görs ett försök till uttag från kontot om det lyckas retuneras true annars false.
		if((customer = this.getCustomerObject(pNo)) != null && isAccountToCustomer(pNo, accountId)) 
		{
			bool = customer.getAccount(accountId).changeAccountBalance(amount, TypeOfTransaction.WITHDRAW);
			notifyBankObservers(bool);
			return bool;
		} 
		else 
		{
			notifyBankObservers(bool);
			return false;
		}
	}
    	/**Ändrar en kunds namn.
    	 * @param name i form av en String.
    	 * @param surname i form av en String.
    	 * @param pNo personnumret som en String.
    	 * @return boolean.
    	 */
	public boolean changeCustomerName(String name, String surname, String pNo) 
	{
		Client customer;
		System.out.println(pNo);
		//Om det finns ett object med matchande personnummer ändras namnet.
		if((customer = this.getCustomerObject(pNo)) != null  && !pNo.isEmpty() && !name.isEmpty() && !surname.isEmpty()) 
		{
			customer.setName(name, surname); 
			this.notifyBankObservers(true);
			return true;
		} 
		else this.notifyBankObservers(false); return false;
	}	
	
	/**Tar bort en kund i registret.
	 * @param pNo personnumret som en String.
	 * @return array list med information om den borttagna kunden och dess konton.  [Namn personnummer, konto1, konto2, ...]
	 */
	public ArrayList<String> deleteCustomer(String pNo)
	{
		Client customer;
		//Om det finns ett object med matchande personnummer(Samtidigt läggs detta objekt i customer variabeln.)
		//läggs representationen av customer-objektet i en temporär variabel, som sedan manipuleras och retuneras
		//annars retuneras null.
		if((customer = this.getCustomerObject(pNo)) != null) 
		{
			ArrayList<String> tmp = customer.getCustomer();
			String accountInfo;
			//För alla index utom första(namn och personnummer indexet) lägger jag till den uträknade räntan till
			//getCustomer-array-listans strängar. 
			for (int i = 1; i < tmp.size(); i++) 
			{
				//lägger information om det i forloopen aktuella kontot i accountInfo variabeln.
				accountInfo = tmp.get(i);
				//Skapar en ny förängd sträng. Hämtar kontonummerinformationen som en substring 
				//från accountInfo strängen, som sedan används för att hämta kontoinformationen.
				tmp.set(i, customer.getAccount(Integer.parseInt(accountInfo.substring(0, accountInfo.indexOf(" ")))).getAccountInfo());  //   .getAccountInformation(;
			}
			//Tar bort objectet, eller rättare sagt tar bort referensen till objectet. När det inte finns någon reference
			//till objektet, d.v.s. att det inte används, kommer det att bli garbage collected.
			customerObjectList.remove(customer);
			this.notifyBankObservers(true);
			return tmp;
		} 
		else this.notifyBankObservers(false); return null;
	}
	
	/**Avslutar et konto.
	 * @param pNr personnumret som en String.
	 * @param accountId kontonumret som en int.
	 * @return en String med information om det avslutade kontot. "kontonummer saldo kontotyp räntesats(%) ränta"
	 */
	public String closeAccount(String pNr, int accountId) 
	{
		Client customer;
		//Om det finns ett object med matchande personnummer som har ett bankkonto med matchande id
		//läggs informationen om detta i en temporär varaiabel som sedan retuneras, sedan tas kontot
		//bort från denna kunds kontolista, annars retuneras null.
		if((customer = this.getCustomerObject(pNr)) != null &&  isAccountToCustomer(pNr, accountId) && accountId != 0) 
		{
			String tmp = customer.getAccount(accountId).getAccountInfo();
			customer.deleteAccount(accountId);
			notifyBankObservers(true);
			return tmp;
		} 
		else {
			notifyBankObservers(false);
			return null;
		}
	}
	
	/**Hämtar en strängrepresentation av en kunds konto. 
	 * @param pNr personnumret som en String.
	 * @param accountId kontonumret som en int.
	 * @return en String med kontots information. "kontonummer saldo kontotyp räntesats(%)"
	 */
	public String getAccount(String pNo, int accountId) 
	{
		Client customer;
		//Om det finns ett object med matchande personnummer som har ett bankkonto med matchande id
		//hämtas information om detta konto som retuneras annars retuneras null;
		if((customer = this.getCustomerObject(pNo)) != null && isAccountToCustomer(pNo, accountId) ) 
		{
			String tmp = customer.getAccount(accountId).getAccountInfo();
			//Retunerar en substring av den ursprungliga kontoinformationen. 
			return customer.getAccount(accountId).getAccountInfo().substring(0, tmp.lastIndexOf(" "));
		} 
		else return null;
	}
	/**Hämtar en String ArrayLista med alla transactioner knutet till ett visst konto.
	 * @param pNr personnumret i form av en String.
	 * @param accountId bankkontonumret som en int.
	 * @return en String ArrayLista om kontot finns annars null;
	 */
	public ArrayList<String> getTransactions(String pNr, int accountId)
	{
		Client customer;
		//Om det finns ett object med matchande personnummer som har ett bankkonto med matchande id
		//hämtas information om detta konto som retuneras annars retuneras null;
		if((customer = this.getCustomerObject(pNr)) != null && isAccountToCustomer(pNr, accountId))
		{
			return  customer.getAccount(accountId).getTransactions();
		} 
		else return null;
	}
	/**Hjälpmetod för att avgöra om ett visst konto finns för en viss person.
	 * @param pNr personnumret som en String.
	 * @param accountId kontonumret som en int.
	 * @return boolean.
	 */
 	private boolean isAccountToCustomer(String pNo, int accountId) 
 	{
 		//Går igenom en array list med kundinformationen och för varje index i denna lista testas om
 		// accountIdt matchar med något accountId i denna information om det gör det retuneras true annars false.
 		for (String a : this.getCustomer(pNo)) 
 		{
 			if(a.substring(0, a.indexOf(" ")).equals(Integer.toString(accountId))) 
 			{
 				return true;
 			}
 		} 
 		return false;
 	}
 	
 	/**En hjälp medtod för att hämta ett customer-objekt i customerObjectList.
	 * @param pNo personnumret som en String.
	 * @return Ett Customer-objekt
	 */
	private Client getCustomerObject(String pNo) 
	{
		Client customer = null;
		//Går igenom customerObjectList och om det finns ett objekt med matchande personnummer läggs detta
		//i customer variableln som retuneras. Om ingen matchning görs kommer objektet retuneras med ett värde av null.
		for(Client c : customerObjectList)
		{
	        if (c.getPersonalNumber().equals(pNo)) 
	        {
	        		customer = c;
	        }
		}
		return customer;
	}
	/**Hämtar ett kontos kreditgräns
	 * @param pNo personnumret som en String.
	 * @param accountNr kontonummer som en int.
	 * @return kreditgränsen som en double.
	 */
	public double getCreditLimit(String pNr, int accountNr)
	{
		return getCustomerObject(pNr).getAccount(accountNr).getCreditLimit();
	}
	
	/**Registrerar prenumeranter(observers) som blir uppdaterade om modellen förändras.
	 * @param Bankobserver
	 */
	@Override
	public void registerObserver(BankObserver bo) 
	{		//Lägger till en BankObserver till bankObserver-arrayListan.
			this.bankObservers.add(bo);
	}
	
	/**Avregistrerar prenumeranter(observers) till att bli uppdaterade om modellen förändras.
	 * @param Bankobserver
	 */
	public void unRegisterObserver(BankObserver bo)
	{	//Tar bort en BankObserver från bankObserver-arrayListan.
		this.bankObservers.remove(bo);
	}
	
	/**Notifierar prenumeranterna(observers) om att en förändring tagit plats. 
	 * @param Boolean som indekerar om ett försök till förändring gjorts men misslyckats.
	 */
	public void notifyBankObservers(Boolean bool) 
	{
		//Lambda forloop. För varje bankObserver i bankObservers uppdatera denna observer.
			bankObservers.forEach(bo -> bo.updateBank(bool));	
	}
	
}

