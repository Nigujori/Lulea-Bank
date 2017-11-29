package johrin7;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import johrin7.Account.TypeOfTransaction;

public class CreditAccount extends Account{
	
	private final String accountType = "Kreditkonto";
	private double interest;
	private double  creditLimit = 5000;
	
	public CreditAccount() 
	{
		super();
		super.setAccountType(accountType);
		super.setCreditLimit(creditLimit);
		this.interest = 0.005;
	}
	/**Konstruktor med vilken du kan specificera vilken ränta som kontot ska ha.
	 */
	public CreditAccount(Double interest, double creditLimit)
	{
		super();
		super.setAccountType(accountType);
		super.setCreditLimit(creditLimit);
		this.interest = 0.005;
	}
	
	@Override
	public boolean changeAccountBalance(double amount, TypeOfTransaction typeOfTransaction) 
	{
		if((super.getBalance() < 0)) 
		{
			this.setInterest(0.07);
		}
		else this.setInterest(0.005);
		
		boolean bool = super.changeAccountBalance(amount, typeOfTransaction);
		if(super.getBalance() < 0) 
		{
			this.setInterest(0.07);
		}
		return bool;
	}
	
	public double getCreditLimit()
	{
		return this.creditLimit;
	}
	
	/**Hämtar räntesatsen.
	 * @return räntesatsen som en double.
	 */public double getInterest() 
		{
			return this.interest;
		}
		
		/**Ändrar räntesatsen.
		 * @param interest räntesatsen inte i %.
		 * @return ny räntesats i double.
		 */
		public double setInterest(double interest) 
		{
			return this.interest = interest;
		}
		
		/**Hämtar räntan.
		 * @return räntan som en double.	
		 */
		public double getInterestAmount(){
			return this.interest * super.getBalance();
		}
		
		@Override
		public String getAccountInfo()
		{
			String infoString = super.getAccountInfo() + " " + (double)Math.round(this.interest * 100 * 100000d) / 100000
					+ " " + (double)Math.round(this.getInterestAmount() * 100000d) / 100000;
			return infoString;
		}	

}
