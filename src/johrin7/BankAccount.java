package johrin7;

public abstract class BankAccount {
	private double balance;
	private static int lastAccountNr = 1000;
	private int bankAccountNumber;
		
		public BankAccount() {
			BankAccount.lastAccountNr++; 
			this.balance = 0;
			this.bankAccountNumber = lastAccountNr; 
		}
		
		public boolean setBalance(double amount) {
			if((amount < 0 && this.balance > (amount*-1)) || amount > 0) {
				this.balance += amount;
				return true;
			} else return false;
		}
		
		public double getBalance() {
			return this.balance;
		}
		
		public int getAccountNumber() {
			return this.bankAccountNumber;
		}
		
		public abstract String getAccountInfo();
		
		public abstract String getAccountType();
}
