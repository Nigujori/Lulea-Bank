package johrin7;
/** En specifik bankkonto typ som ärver av den abstrakta och mer allmänna bakkontoklassen
 * @author Johan Ringström användarnamn johrin7.*/

public class SavingsAccount extends Account {
	
	//Räntan anges inte i procentform.
	private double interest;
	private String accountType;

		/**Default konstruktor som skapar ett sparkonto utan parametrar.
		 */
		public SavingsAccount() 
		{
			super();
			this.interest = 0.01;
			this.accountType = "Sparkonto";
		}
		/**Konstruktor med vilken du kan specificera vilken ränta som kontot ska ha.
		 */
		public SavingsAccount(Double interest)
		{
			super();
			this.interest = interest;
		}
		/**Hämter kontoinformationen
		 * @return en text-sträng. "Kontonummer saldo kontotyp räntesats(%) ränta"
		 */
		public String getAccountInfo() {
			String infoString = super.getAccountInfo() + " " + this.accountType + " " + this.interest * 100
					+ " " + this.getInterestAmount();
			return infoString;
		}
		/**Hämtar kontotypen.
		 * @return kontontypen som en sträng.
		 */
		public String getAccountType() {
			return this.accountType;
		}
		/**Hämtar räntesatsen.
		 * @return räntesatsen som en double.
		 */
		public double getInterest() {
			return this.interest;
		}
		/**Hämtar räntan.
		 * @return räntan som en double.	
		 */
		public double getInterestAmount(){
			return this.interest * this.getBalance();
		}
}
