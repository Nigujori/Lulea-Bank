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
		
	/**Konstrukter som skapar ett default bankkonto-objekt.*/
		public Account() 
		{
			Account.lastAccountNr++; 
			this.balance = 0;
			this.bankAccountNumber = lastAccountNr; 
		}
		
		/**Ändrar saldot.
		 * @param amount ett negativt eller positivt värde att justera saldot med.
		 * @return boolean
		 */
		public boolean setBalance(double amount) 
		{
			//Om summan är mindre än noll och saldot inte blir negativt efter en justering eller om summan är positivt
			//då justeras saldot. Då retuneras true annars false.
			if((amount < 0 && this.balance >= (amount*-1)) || amount > 0) 
			{
				this.balance += amount;
				this.createTransaction(amount, this.balance);
				return true;
			} 
			else return false;
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
			System.out.println("test");
			Transaction trans = new Transaction(transactionAmount, balanceAfterTrans);
			transactionList.add(trans);
		}
		
		
}
