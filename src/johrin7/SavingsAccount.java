package johrin7;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/** En specifik bankkonto typ som ärver av den abstrakta och mer allmänna bakkontoklassen
 * @author Johan Ringström användarnamn johrin7.*/

public class SavingsAccount extends Account {

		/**Default konstruktor som skapar ett sparkonto utan parametrar.
		 */
		private double interest;
		private final String accountType = "Sparkonto";
		int i =0;
		
		public SavingsAccount() 
		{
			super();
			super.setAccountType(accountType);
			this.interest = 0.01;
		}
		/**Konstruktor med vilken du kan specificera vilken ränta som kontot ska ha.
		 */
		public SavingsAccount(Double interest)
		{
			super();
			super.setAccountType(accountType);
			this.interest = interest;
		}
		
		@Override
		public boolean changeAccountBalance(double amount, TypeOfTransaction typeOfTransaction) 
		{
			long diffInSeconds;
			Transaction transaction;
			if((transaction = super.getLastTransaction(TypeOfTransaction.WITHDRAW)) != null) {
				Date startDate = transaction.getDate();
				Date endDate   = new Date();
				long duration  = endDate.getTime() - startDate.getTime();
				diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
				if (diffInSeconds <= 31556926) {
					//System.out.println("-------->" + super.getTransactions().get(0).toString());
					//System.out.println("/-------->" + diffInSeconds + " " + i++);
					return super.changeAccountBalance(amount += amount* 0.02, typeOfTransaction);
				} 
				else {
					super.changeAccountBalance(amount, typeOfTransaction);
				}
			}
			return super.changeAccountBalance(amount, typeOfTransaction);
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
