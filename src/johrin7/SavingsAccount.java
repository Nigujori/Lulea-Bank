package johrin7;
/** En specifik bankkonto typ som ärver av den abstrakta och mer allmänna bakkontoklassen
 * @author Johan Ringström användarnamn johrin7.*/

public class SavingsAccount extends Account {

		/**Default konstruktor som skapar ett sparkonto utan parametrar.
		 */
		private final String accountType = "Sparkonto";
		
		public SavingsAccount() 
		{
			super();
			super.setInterest(0.01);
			super.setAccountType(accountType);
		}
		/**Konstruktor med vilken du kan specificera vilken ränta som kontot ska ha.
		 */
		public SavingsAccount(Double interest)
		{
			super();
			super.setInterest(interest);
			super.setAccountType(accountType);
		}
		
		/*@Override
		public boolean setBalance(double amount) {
				
			return true;
		}*/
		
}
