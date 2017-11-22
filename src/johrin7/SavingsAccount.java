package johrin7;
/*En specifik bankkonto typ som ärver av den abstrakta och mer allmänna bakkontoklassen*/

public class SavingsAccount extends BankAccount {
	
	//Räntan anges inte i procentform.
	private double interest;
	private String accountType;

		//Default konstruktor som skapar ett sparkonto utan parametrar.
		public SavingsAccount() {
			super();
			this.interest = 0.01;
			this.accountType = "Sparkonto";
		}
		//Konstruktor med vilken du kan specificera vilken ränta som kontot ska ha.
		public SavingsAccount(Double interest) {
			super();
			this.interest = interest;
		}
		//Ger dig kontoinformationen som en text-sträng.
		public String getAccountInfo() {
			String infoString = super.getAccountInfo() + " " + this.accountType + " " + this.interest * 100
					+ " " + this.getInterestAmount();
			return infoString;
		}
		//Hämtar kontotypen.
		public String getAccountType() {
			return this.accountType;
		}
		//Hämtar räntesatsen.
		public double getInterest() {
			return this.interest;
		}
		//Hämtar räntan.
		public double getInterestAmount(){
			return this.interest * this.getBalance();
		}
}
