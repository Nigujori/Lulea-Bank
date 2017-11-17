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
	
	public Customer getCustomerObject(String pNo) {
		Customer customer = null;
		for(Customer c : customerObjectList){
	        if (c.getPersonalNumber().equals(pNo)) {
	        		customer = c;
	        }
		}
		return customer;
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
		if((customer = getCustomerObject(pNo)) != null) {
			return customer.getCustomer();
		} else return null;
	}
	
	public int createSavingsAccount(String pNo) {
		Customer customer;
		if ((customer = this.getCustomerObject(pNo))  != null) {
			return customer.createAccount();
		} else return -1;

	}
}
