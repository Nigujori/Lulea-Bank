package johrin7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/** En abstrakt klass, som med andra ord inte kan skapa några objekt. Vill man skapa ett bankkonto-objekt måste en klass
 * ärva från denna klass vilken man sedan kan skapa specifika typer av bankkonton. T.ex. ett sparkonto
 * @authorJohan Ringström användarnamn johrin7*/

public abstract class Account {
	
	private ArrayList<Transaction> transactionList = new ArrayList<>();
	private double balance;

	private String accountType;
	//Det sista skapade bankkontonummret. Används för att skapa individuella bankkonton. Är Statisk,
	// det vill säga är alltid samma för alla objekt eftersom det är ett klassfält.
	private static int lastAccountNr = 1000;
	private double creditLimit;
	private final int bankAccountNumber;
	public static  enum TypeOfTransaction {WITHDRAW, DEPOSIT};
		
	/**Konstrukter som skapar ett default bankkonto-objekt.*/
		public Account() 
		{
			Account.lastAccountNr++; 
			this.balance = 0;
			this.bankAccountNumber = lastAccountNr; 
			this.creditLimit = 0;
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
				if(typeOfTransaction == TypeOfTransaction.WITHDRAW &&  this.balance + this.creditLimit >= amount ) 
				{
					this.balance -= amount;
					this.createTransaction(-amount, this.balance);
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
			String infoString = this.getAccountNumber() + " " +  this.getBalance() + " " + this.accountType;
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

		private void createTransaction(double transactionAmount, double balanceAfterTrans) 
		{
			Transaction trans = new Transaction(transactionAmount, balanceAfterTrans);
			transactionList.add(trans);
		}
		
		public ArrayList<String> getTransactions() 
		{
			ArrayList<String> transactions = new ArrayList<>();
			for(Transaction trans : this.transactionList) {
				transactions.add(trans.toString());
			}
			return transactions;
		}
		
		public Transaction getTransaction(int transactionNr)
		{
			for(Transaction t : this.transactionList)
			{
				if(t.getTransactionNr() == transactionNr) 
				{
					return t;
				} 
			} return null;
		}
		
		public Transaction getLastTransaction(TypeOfTransaction typeOfTransaction)
		{
			if (this.transactionList.isEmpty())
			{
				return null;
			} 
			else 
			{
				for(int i = this.transactionList.size()-1; i > 0; i-- )
				{
					if(this.transactionList.get(i).transType == typeOfTransaction) 
					{
						return this.transactionList.get(i);
					} 
				} return null;
			}
			
			
		}
		
		
		public double setCreditLimit(double creditLimit)
		{
			this.creditLimit = creditLimit; 
			return this.creditLimit;
		}
		
		public abstract double getInterest(); 
		
		public abstract double setInterest(double interest); 
		
		public abstract double getInterestAmount();
}
