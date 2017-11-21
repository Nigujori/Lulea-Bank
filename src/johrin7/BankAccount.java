package johrin7;
/*En abstrakt klass, som med andra ord inte kan skapa några objekt. Vill man skapa ett bankkonto-objekt måste en klass
 * ärva från denna klass vilken man sedan kan skapa specifika typer av bankkonton. T.ex. ett sparkonto*/

public abstract class BankAccount {
	private double balance;
	//Det sista skapade bankkontonummret. Används för att skapa individuella bankkonton. Är Statisk
	//, det vill säga är alltid samma för alla objekt eftersom det är ett klassfält.
	private static int lastAccountNr = 1000;
	private int bankAccountNumber;
		
	//Konstrukter som skapar ett default bankkonto-objekt.
		public BankAccount() {
			BankAccount.lastAccountNr++; 
			this.balance = 0;
			this.bankAccountNumber = lastAccountNr; 
		}
		//
		public boolean setBalance(double amount) {
			//
			if((amount < 0 && this.balance >= (amount*-1)) || amount > 0) {
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
		
		public abstract double getInterest();
}
