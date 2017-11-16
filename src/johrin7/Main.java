package johrin7;

public class Main {
	
	public static void main(String [ ] args)
	{
		Customer customer = new Customer("Johan", "Ringström", "7504048252");
		customer.createAccount();
	    System.out.println(customer.getAccountInformation(1001)); 
	    System.out.println(savingsAccount.setBalance(10000));
	    System.out.println(customer.getAccountInformation(1001)); 
	    
	}
}