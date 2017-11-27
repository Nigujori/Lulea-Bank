package johrin7;

import java.util.ArrayList;

/** En abstrakt klass, som med andra ord inte kan skapa några objekt. Vill man skapa ett bankkonto-objekt måste en klass
 * ärva från denna klass vilken man sedan kan skapa specifika typer av bankkonton. T.ex. ett sparkonto
 * @authorJohan Ringström användarnamn johrin7*/

public abstract class Account {
	
	private ArrayList<Transaction> transactionList = new ArrayList<>();
	private double balance;
	private double interest;
	private String accountType;
	//Det sista skapade bankkontonummret. Används för att skapa individuella bankkonton. Är Statisk,
	// det vill säga är alltid samma för alla objekt eftersom det är ett klassfält.
	private static int lastAccountNr = 1000;
	private int bankAccountNumber;
	public static  enum TypeOfTransaction {WITHDRAW, DEPOSIT};
		
	/**Konstrukter som skapar ett default bankkonto-objekt.*/
		public Account() 
		{
			Account.lastAccountNr++; 
			this.balance = 0;
			this.bankAccountNumber = lastAccountNr; 
		}
		
		/**Förändrar kontots saldo. 
		 * @param amount summan med vilket saldot ska förändras.
		 * @param typeOfTransaction typ av transaktion, uttag eller insättning.
		 * @return boolean.
		 */
		public boolean changeAccountBalance(double amount, TypeOfTransaction typeOfTransaction) 
		{
				//Om transaktionen är ett uttag och det finns tillräckligt med 
				//pengar på kontot. Retunerar true om uttaget medgavs annars false.
				if(typeOfTransaction == TypeOfTransaction.WITHDRAW && this.balance >= amount ) 
				{
					this.balance -= amount;
					this.createTransaction(amount, this.balance);
					return  true;
				//Annars om det är en insättning görs en insättning och true retuneras.
				} 
				else if (typeOfTransaction == TypeOfTransaction.DEPOSIT) {
					this.balance += amount;
					this.createTransaction(amount, this.balance);
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
		
		/**Hämtar banknumret.
		 * @return banknummret i form av en int.
		 */
		public int getAccountNumber() 
		{
			return this.bankAccountNumber;
		}
		
		/**Hämter kontoinformationen
		 * @return en text-sträng. "Kontonummer saldo kontotyp räntesats(%) ränta"
		 */
		public String getAccountInfo() {
			String infoString = this.getAccountNumber() + " " +  this.getBalance() + " " + this.accountType + " " + this.interest * 100
					+ " " + this.getInterestAmount();
			return infoString;
		}
		
		/**Hämtar kontotypen.
		 * @return kontontypen som en sträng.
		 */
		public String getAccountType() 
		{
			return this.accountType;
		}
		
		/**Ändrar kontotypen.
		 * @param accountType
		 * @return
		 */
		public String setAccountType(String accountType)
		{
			return this.accountType = accountType;
		}
		
		/**Hämtar räntesatsen.
		 * @return räntesatsen som en double.
		 */
		public double getInterest() 
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
			return this.interest * this.getBalance();
		}
		
		public ArrayList<Transaction> getTransactions() 
		{
			return this.transactionList;
		}
		
		private void createTransaction(double transactionAmount, double balanceAfterTrans) 
		{
			Transaction trans = new Transaction(transactionAmount, balanceAfterTrans);
			transactionList.add(trans);
		}
		
		public String getTransactions(int accountNumber) 
		{
			String transString = "";
			for(Transaction trans : this.transactionList) {
				transString += trans.toString() + " ";
			}
			return transString;
		}
		
		
}
