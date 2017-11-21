package johrin7;

import java.util.ArrayList;

public class BankLogic {
	private ArrayList<Customer> customerObjectList = new ArrayList<>();
	
	//public BankLogic() {
		//this.customerObjectList = null;
		//this.customerStrList = null;
	//}
	
	public boolean createCustomer(String forename, String surname, String pNo) {
		if((this.getCustomerObject(pNo)) == null) {
			Customer newCustomer = new Customer(forename, surname, pNo);
			customerObjectList.add(newCustomer);
			return true;
		}else return false;
	}
	
	
	public ArrayList<String> getAllCustomers() {
		ArrayList<String> customerStrList = new ArrayList<>();
		if (customerObjectList != null) {
			for(Customer c : customerObjectList){
				customerStrList.add(c.getName()[0] + " " + c.nameArray[1] + " " + c.getPersonalNumber());
			} return customerStrList;
		} else return customerStrList;
	}
	
	public ArrayList<String> getCustomer(String pNo){
		Customer customer;
		if((customer = this.getCustomerObject(pNo)) != null) {
			return customer.getCustomer();
		} else return null;
	}
	
	public int createSavingsAccount(String pNo) {
		Customer customer;
		if ((customer = this.getCustomerObject(pNo)) != null) {
			return customer.createAccount();
		} else return -1;
	}
	
	public boolean deposit(String pNo, int accountId, double amount) {
		Customer customer;
		if((customer = this.getCustomerObject(pNo)) != null && this.getAccount(pNo, accountId) != null) {
			customer.changeAccountBalance(accountId, amount, Customer.TypeOfTransaction.DEPOSIT); 
			return true;
		} else return false;
	}
	
	public boolean withdraw(String pNo, int accountId, double amount) {
		Customer customer;
		if((customer = this.getCustomerObject(pNo)) != null && this.getAccount(pNo, accountId) != null) {
			return customer.changeAccountBalance(accountId, amount, Customer.TypeOfTransaction.WITHDRAW);
		} else return false;
	}
	
	private Customer getCustomerObject(String pNo) {
		Customer customer = null;
		for(Customer c : customerObjectList){
	        if (c.getPersonalNumber().equals(pNo)) {
	        		customer = c;
	        }
		}
		return customer;
	}
    	
	public boolean changeCustomerName(String name, String surname, String pNo) {
		Customer customer;
		if((customer = this.getCustomerObject(pNo)) != null) {
			customer.setName(name, surname); 
			return true;
		} else return false;
	}	
	public ArrayList<String> deleteCustomer(String pNo){
		Customer customer;
		if((customer = this.getCustomerObject(pNo)) != null) {
			ArrayList<String> tmp = customer.getCustomer();
			String accountInfo;
			for (int i = 1; i < tmp.size(); i++) {
				accountInfo = tmp.get(i);
				tmp.set(i, accountInfo  + " " + customer.getInterestAmount(Integer.parseInt(accountInfo.substring(0, accountInfo.indexOf(" ")))));
			}
			customerObjectList.remove(customer);
			return tmp;
		} else return null;
	}
	
	public String closeAccount(String pNr, int accountId) {
		Customer customer;
		if((customer = this.getCustomerObject(pNr)) != null) {
			String tmp = customer.getAccountInformation(accountId);
			customer.deleteAccount(accountId);
			return tmp;
		} else return null;
	}
	
	public String getAccount(String pNo, int accountId) {
		Customer customer;
		if((customer = this.getCustomerObject(pNo)) != null && isAccountToCustomer(pNo, accountId) ) {
			String tmp = customer.getAccountInformation(accountId);
			return tmp;
		} else return null;
	}
	
	 	private boolean isAccountToCustomer(String pNo, int accountId) {
	for (String a : this.getCustomer(pNo)) {
		if(a.substring(0, a.indexOf(" ")).equals(Integer.toString(accountId))) {
		return true;
		}
	} return false;
}
	
}
