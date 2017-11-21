package johrin7;

import java.util.ArrayList;

public class BankLogic {
	private ArrayList<String> customerStrList = new ArrayList<>();
	private ArrayList<Customer> customerObjectList = new ArrayList<>();
	
	//public BankLogic() {
		//this.customerObjectList = null;
		//this.customerStrList = null;
	//}
	
	public boolean createCustomer(String forename, String surname, String pNo) {
		Customer newCustomer = new Customer(forename, surname, pNo);
		customerObjectList.add(newCustomer);
		return true;
	}
	
	
	public ArrayList<String> getAllCustomers() {
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
		if((customer = this.getCustomerObject(pNo)) != null && this.isAccountToCustomer(pNo, accountId)) {
			customer.changeAccountBalance(accountId, amount, Customer.TypeOfTransaction.DEPOSIT); 
			return true;
		} else return false;
	}
	
	public boolean withdraw(String pNo, int accountId, double amount) {
		Customer customer;
		if((customer = this.getCustomerObject(pNo)) != null && this.isAccountToCustomer(pNo, accountId)) {
			customer.changeAccountBalance(accountId, amount, Customer.TypeOfTransaction.WITHDRAW); 
			return true;
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
	
    	private boolean isAccountToCustomer(String pNo, int accountId) {
    		for (String a : this.getCustomer(pNo)) {
    			if(a.substring(0, a.indexOf(" ")).equals(Integer.toString(accountId))) {
    			return true;
    			}
    		} return false;
    	}
}
