package johrin7;

public class Main {
	
	public static void main(String [ ] args)
	{
		BankLogic bank = new BankLogic();
		bank.createCustomer("johan","Ringström", "7504048252");
		bank.createCustomer("Zara","Kadiri", "8210010644");
		bank.createSavingsAccount("7504048252");
		bank.createSavingsAccount("8210010644");
		
		bank.deposit("7504048252", 1001, 300);
		
		bank.withdraw("7504048252", 1001, 50.50);
		
		//System.out.println(bank.getCustomer("8210010644")); 
		//System.out.println(bank.getCustomer("7504048252")); 
		
		bank.changeCustomerName("Nils", "Eriksson", "7504048252");
		
		System.out.println(bank.getCustomer("7504048252").get(0)); 
		
		System.out.println(bank.closeAccount("7504048252", 1001));
		
		System.out.println(bank.getCustomer("8210010644"));
		
		//for(String s : bank.deleteCustomer("7504048252")) {
		//	System.out.println(s);
		//}
		
		for (String s : bank.getAllCustomers()) {
			System.out.println(s);
		}
	   
	    
	    //System.out.println( customer.deleteAccount(1001)); 
	    
	   // System.out.println(savingsAccount.setBalance(10000));
	    //System.out.println(customer.getAccountInformation(1001)); 
	    
	}
}