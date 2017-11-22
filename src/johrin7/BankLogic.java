package johrin7;
/*Denna klass rymmer bankens logik. Den skapar och ter bort kunder och håller ett register med customers-objekt.
 *Det är genom dessa customers-objekt som Banklogic-klassen får möjlighet att ta del av kundernas uppgifter och deras 
 *konton.  */
import java.util.ArrayList;

public class BankLogic {
	//Array-lista med customer-objekt.
	private ArrayList<Customer> customerObjectList = new ArrayList<>();
	
	//Skapar ett customer-objekt
	public boolean createCustomer(String forename, String surname, String pNo) {
		//Om det inte finns något customer-objekt med samma personnummer så skapas ett som 
		//läggs till customerObjectList. Retunerar därefter true annars false.
		if((this.getCustomerObject(pNo)) == null) {
			Customer newCustomer = new Customer(forename, surname, pNo);
			customerObjectList.add(newCustomer);
			return true;
		}else return false;
	}
	//Hämtar alla kunder. (Jag valde att göra en ny lista för varje anrop till metoden, 
	//eftersom jag då enbart behöver tänka på att ta bort och lägga till i en lista från vilken denna
	//mer temporära lista skapas.
	public ArrayList<String> getAllCustomers() {
		//Skapar en lista som används för att lägga customer-objekten i.
		ArrayList<String> customerStrList = new ArrayList<>();
		//Går igenom customerObjectList och lägger namnen och personnumren i customerStrList. Retunerar sedan denna lista.
			for(Customer c : customerObjectList){
				customerStrList.add(c.getName()[0] + " " + c.nameArray[1] + " " + c.getPersonalNumber());
			} return customerStrList;
	}
	//Hämtar en specefik kund.
	public ArrayList<String> getCustomer(String pNo){
		Customer customer;
		//Om det finns ett object med matchande personnummer(Samtidigt läggs detta objekt i customer variabeln.)
		//retuneras en arrayList, som representerar detta object. [Namn och personnummer, konto1, konto2, konto...]
		//Retunerar null om ingen matchnig finns
		if((customer = this.getCustomerObject(pNo)) != null) {
			return customer.getCustomer();
		} else return null;
	}
	//Skapar ett konto till en specifik kund.
	public int createSavingsAccount(String pNo) {
		Customer customer;
		//Om det finns ett object med matchande personnummer(Samtidigt läggs detta objekt i customer variabeln.)
		//retuneras kontonumret i orm av en int, annars -1;
		if ((customer = this.getCustomerObject(pNo)) != null) {
			return customer.createAccount();
		} else return -1;
	}
	//Gör en insättning
	public boolean deposit(String pNo, int accountId, double amount) {
		Customer customer;
		//Om det finns ett object med matchande personnummer och ett konto med matchande kontonummer
		//görs en insättning på det kontot och retunerar true annars false.
		if((customer = this.getCustomerObject(pNo)) != null && isAccountToCustomer(pNo, accountId)) {
			customer.changeAccountBalance(accountId, amount, Customer.TypeOfTransaction.DEPOSIT); 
			return true;
		} else return false;
	}
	//Gör ett uttag.
	public boolean withdraw(String pNo, int accountId, double amount) {
		Customer customer;
		//Om det finns ett object med matchande personnummer och ett konto med matchande kontonummer
		//görs ett försök till uttag från kontot om det lyckas retuneras true annars false.
		if((customer = this.getCustomerObject(pNo)) != null && isAccountToCustomer(pNo, accountId)) {
			return customer.changeAccountBalance(accountId, amount, Customer.TypeOfTransaction.WITHDRAW);
		} else return false;
	}
	//En hjälp medtod för att hämta ett customer-objekt i customerObjectList.
	private Customer getCustomerObject(String pNo) {
		Customer customer = null;
		//Går igenom customerObjectList och om det finns ett objekt med matchande personnummer läggs detta
		//i customer variableln som retuneras. Om ingen matchning görs kommer objektet retuneras med ett värde av null.
		for(Customer c : customerObjectList){
	        if (c.getPersonalNumber().equals(pNo)) {
	        		customer = c;
	        }
		}
		return customer;
	}
    	//Ändrar en kunds namn.
	public boolean changeCustomerName(String name, String surname, String pNo) {
		Customer customer;
		//Om det finns ett object med matchande personnummer ändras namnet.
		if((customer = this.getCustomerObject(pNo)) != null) {
			customer.setName(name, surname); 
			return true;
		} else return false;
	}	
	//Tar bort en kund i registret.
	public ArrayList<String> deleteCustomer(String pNo){
		Customer customer;
		//Om det finns ett object med matchande personnummer(Samtidigt läggs detta objekt i customer variabeln.)
		//läggs representationen av customer-objektet i en temporär variabel, som sedan manipuleras och retuneras
		//annars retuneras null.
		if((customer = this.getCustomerObject(pNo)) != null) {
			ArrayList<String> tmp = customer.getCustomer();
			String accountInfo;
			//För alla index utom första(namn och personnummer indexet) lägger jag till den uträknade räntan till
			//getCustomer-array-listans strängar. 
			for (int i = 1; i < tmp.size(); i++) {
				//lägger information om det i forloopen aktuella kontot i accountInfo variabeln.
				accountInfo = tmp.get(i);
				//Skapar en ny förängd sträng. Hämtar kontonummerinformationen som en substring 
				//från accountInfo strängen, som sedan används för att hämta kontoinformationen.
				tmp.set(i, customer.getAccountInformation(Integer.parseInt(accountInfo.substring(0, accountInfo.indexOf(" ")))));
			}
			//Tar bort objectet, eller rättare sagt tar bort referensen till objectet. När det inte finns någon reference
			//till objektet, d.v.s. att det inte används, kommer det att bli garbage collected.
			customerObjectList.remove(customer);
			return tmp;
		} else return null;
	}
	//Avslutar et konto.
	public String closeAccount(String pNr, int accountId) {
		Customer customer;
		//Om det finns ett object med matchande personnummer som har ett bankkonto med matchande id
		//läggs informationen om detta i en temporär varaiabel som sedan retuneras, sedan tas kontot
		//bort från denna kunds kontolista, annars retuneras null.
		if((customer = this.getCustomerObject(pNr)) != null &&  isAccountToCustomer(pNr, accountId)) {
			String tmp = customer.getAccountInformation(accountId);
			customer.deleteAccount(accountId);
			return tmp;
		} else return null;
	}
	//Hämtar en strängrepresentation av en kunds konto. 
	public String getAccount(String pNo, int accountId) {
		Customer customer;
		//Om det finns ett object med matchande personnummer som har ett bankkonto med matchande id
		//hämtas information om detta konto som retuneras annars retuneras null;
		if((customer = this.getCustomerObject(pNo)) != null && isAccountToCustomer(pNo, accountId) ) {
			String tmp = customer.getAccountInformation(accountId);
			//Retunerar en substring av den ursprungliga kontoinformationen. 
			return customer.getAccountInformation(accountId).substring(0, tmp.lastIndexOf(" "));
		} else return null;
	}
	//Hjälpmetod för att avgöra om ett visst konto finns för en viss person.
 	private boolean isAccountToCustomer(String pNo, int accountId) {
 		//Går igenom en array list med kundinformationen och för varje index i denna lista testas om
 		// accountIdt matchar med något accountId i denna information om det gör det retuneras true annars false.
 		for (String a : this.getCustomer(pNo)) {
 			if(a.substring(0, a.indexOf(" ")).equals(Integer.toString(accountId))) {
 				return true;
 			}
 		} return false;
 	}
	
}

