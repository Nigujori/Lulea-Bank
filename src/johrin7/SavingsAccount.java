package johrin7;

public class SavingsAccount {
	
	private double interest;
	private double balance;
	private static int lastAccountNr = 1000;
	private int bankAccountNumber;
	public static enum BankAccountType {SPARKONTO, LÖNEKONTO};
	private String bankAccountTypeStr;
		
		public SavingsAccount() {
			SavingsAccount.lastAccountNr++; 
			this.interest = 0.1;
			this.balance = 0;
			this.bankAccountNumber = lastAccountNr; 
			this.bankAccountTypeStr = BankAccountType.SPARKONTO.toString().toLowerCase();
		}
		
		public SavingsAccount(Double interest, BankAccountType bankAccount) {
			this.interest = interest;
			this.balance = 0;
			this.bankAccountNumber = lastAccountNr +1; 
			this.bankAccountTypeStr = bankAccount.toString().toLowerCase();
			SavingsAccount.lastAccountNr++;
		}
		
		public double setBalance(double amount) {
			this.balance += amount;
			return this.balance;
		}
		
		public double getBalance() {
			return this.balance;
		}
		
		public int getAccountNumber() {
			return this.bankAccountNumber;
		}
		
		public String getAccountInfo() {
			String infoString = "Kontonummer: " + this.bankAccountNumber + "\n" + "Saldo: " +
		balance + "\n" + "Kontotyp: " + this.bankAccountTypeStr + "\n" + "Räntesats: " + this.interest;
			return infoString;
		}
		
		public String getAccountType() {
			return this.bankAccountTypeStr;
		}
		
		
}
