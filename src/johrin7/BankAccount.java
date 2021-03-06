package johrin7;

/** En abstrakt klass, som med andra ord inte kan skapa några objekt. Vill man skapa ett bankkonto-objekt måste en klass
 * ärva från denna klass vilken man sedan kan skapa specifika typer av bankkonton. T.ex. ett sparkonto
 * @authorJohan Ringström användarnamn johrin7*/

public abstract class BankAccount {
	private double balance;
	//Det sista skapade bankkontonummret. Används för att skapa individuella bankkonton. Är Statisk,
	// det vill säga är alltid samma för alla objekt eftersom det är ett klassfält.
	private static int lastAccountNr = 1000;
	private int bankAccountNumber;
	//Ett enum med två typer av transaktioner som möjliga värden.
	public static  enum TypeOfTransaction {WITHDRAW, DEPOSIT};
		
	/**Konstrukter som skapar ett default bankkonto-objekt.*/
		public BankAccount() 
		{
			BankAccount.lastAccountNr++; 
			this.balance = 0;
			this.bankAccountNumber = lastAccountNr; 
		}
		
		/**Ändrar saldot.
		 * @param amount ett negativt eller positivt värde att justera saldot med.
		 * @return boolean
		 */
		/*public boolean setBalance(double amount) 
		{
			//Om summan är mindre än noll och saldot inte blir negativt efter en justering eller om summan är positivt
			//då justeras saldot. Då retuneras true annars false.
			if((amount < 0 && this.balance >= (amount*-1)) || amount > 0) 
			{
				this.balance += amount;
				return true;
			} 
			else return false;
		}*/
		/**Förändrar ett specifikt kontos saldo. 
		 * @param accountNumber kontonummer som en int.
		 * @param amount summan med vilket saldot ska förändras.
		 * @param typeOfTransaction typ av transaktion, uttag eller insättning.
		 * @return boolean.
		 */
		public boolean changeAccountBalance(double amount, TypeOfTransaction typeOfTransaction) 
		{
				//Om transaktionen är ett uttag och kontot finns ändras saldot om det finns tillräckligt med 
				//pengar. Retunerar true om uttaget medgavs annars false.
				if(typeOfTransaction == TypeOfTransaction.WITHDRAW && this.balance >= amount ) 
				{
					this.balance -= amount;
					return  true;//this.getAccount(accountNumber).setBalance(amount *= -1);
				//Annars om kontot finns görs en insättning och true retuneraa annars retuneras false,
				} 
				else if (typeOfTransaction == TypeOfTransaction.DEPOSIT) {
					this.balance += amount;
					return true;
				}
					return false;
		}
		
		/**Hämtar saldot.
		 * @return saldot i form av en double.
		 */
		public double getBalance() 
		{
			return this.balance;
		}
		
		/**Hämtar banknumret
		 * @return banknummret i form av en int.
		 */
		public int getAccountNumber() 
		{
			return this.bankAccountNumber;
		}
		
		/**Hämtar bankkontoinformation.
		 * @return en sträng som representerar bankkontot.(accountNumber saldot).
		 */
		public String getAccountInfo() {
			 String infoString = this.getAccountNumber() + " " +  this.getBalance();
			 return infoString;
		}
		
		/**De abtrakta metodernas kropp(function) specificeras i de klasser som ärver denna klass.
		 * Det vill säga de specifika kontotyperna som sparkonto eller lönekonto m.fl.	
		 * @return en String.
		 */
		public abstract String getAccountType();
		
		/**De abtrakta metodernas kropp(function) specificeras i de klasser som ärver denna klass.
		 * Det vill säga de specifika kontotyperna som sparkonto eller lönekonto m.fl.	
		 * @return en double.
		 */
		public abstract double getInterest();
		
		/**De abtrakta metodernas kropp(function) specificeras i de klasser som ärver denna klass.
		 * Det vill säga de specifika kontotyperna som sparkonto eller lönekonto m.fl.	
		 * @return en double.
		 */
		public abstract double getInterestAmount();
}
