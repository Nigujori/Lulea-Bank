package johrin7;

public class SavingsAccount extends BankAccount {
	
	private double interest;
	private String accountType;

		
		public SavingsAccount() {
			super();
			this.interest = 0.1;
			this.accountType = "Sparkonto";
		}
		
		public SavingsAccount(Double interest) {
			super();
			this.interest = interest;
		}
		
		public String getAccountInfo() {
			String infoString = "Kontonummer: " + super.getAccountNumber() + "\n" + "Saldo: " +
		super.getBalance() + "\n" + "Kontotyp: " + this.accountType + "\n" + "Räntesats: " + this.interest;
			return infoString;
		}
		
		public String getAccountType() {
			return this.accountType;
		}
}
