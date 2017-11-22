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
		//Parametern tar in ett negativt eller positivt värde att justera saldot med
		public boolean setBalance(double amount) {
			//Om summan är mindre än noll och saldot inte blir negativt efter en justering eller om summan är positivt
			//då justeras saldot. Då retuneras true annars false.
			if((amount < 0 && this.balance >= (amount*-1)) || amount > 0) {
				this.balance += amount;
				return true;
			} else return false;
		}
		//Retunerar saldot.
		public double getBalance() {
			return this.balance;
		}
		//Retunerar banknumret
		public int getAccountNumber() {
			return this.bankAccountNumber;
		}
		//Gör en sträng av bank-objects-informationen.
		public String getAccountInfo() {
			 String infoString = this.getAccountNumber() + " " +  this.getBalance();
			 return infoString;
		}
		
		//De abtrakta metodernas kropp(function) specificeras i de klasser som ärver denna klass.
		//Det vill säga de specifika kontotyperna som sparkonto eller lönekonto m.fl.	
		public abstract String getAccountType();
		
		public abstract double getInterest();
		
		public abstract double getInterestAmount();
}
