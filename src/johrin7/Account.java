package johrin7;

import java.util.ArrayList;


/** En abstrakt klass, som med andra ord inte kan skapa några objekt. Vill man skapa ett bankkonto-objekt måste en klass
 * ärva från denna klass vilken man sedan kan skapa specifika typer av bankkonton. T.ex. ett sparkonto. 
 * Skapar transaaction-objekt och sparar dem i en Arraylist.
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
		
	/**Konstrukter som skapar ett default Sparkonto-objekt.*/
		public Account() 
		{
			Account.lastAccountNr++; 
			this.balance = 0;
			this.bankAccountNumber = lastAccountNr; 
			this.creditLimit = 0;
			this.accountType = "Sparkonto";
		}
		/**Konstruktor med parametrar
		 * @param typeOfTransaction i form av en String.
		 * @param creditLimit i form av en double.
		 */
		public Account(String typeOfTransaction, double creditLimit) 
		{
			Account.lastAccountNr++; 
			this.balance = 0;
			this.bankAccountNumber = lastAccountNr; 
			this.creditLimit = creditLimit;
			this.accountType = typeOfTransaction;
			
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
		 * @return kontontypen som en String.
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
		/**Skapar en transaktion som läggs till i transaktions listan.
		 * @param transactionAmount som en double.
		 * @param balanceAfterTrans som en double.
		 */
		private void createTransaction(double transactionAmount, double balanceAfterTrans) 
		{
			Transaction trans = new Transaction(transactionAmount, balanceAfterTrans);
			transactionList.add(trans);
		}
		/**Hämtar en transaktionerna som är knutet till ett spacifikt konto.
		 * @return ArrayList<String> med transaktionsinformationen.
		 */
		public ArrayList<String> getTransactions() 
		{
			ArrayList<String> transactions = new ArrayList<>();
			//För varje transaktion i listan läggs denna till transactions variabeln som en String.
			for(Transaction trans : this.transactionList) {
				transactions.add(trans.toString());
			}
			return transactions;
		}
		
		/**Hämtar sista transaktionen av någon typ.
		 * @param typeOfTransaction som en TypeOfTransaktion.
		 * @return en Transaction.
		 */
		public Transaction getLastTransaction(TypeOfTransaction typeOfTransaction)
		{	//Om den listan med transaktioner är tom retuneras null.
			if (this.transactionList.isEmpty())
			{
				return null;
			} 
			//Annars gås listan igenom baklänges för att hitta sista stransaktion av angiven typ. 
			//Om ingen hittas retuneras null. 
			else 
			{
				for(int i = this.transactionList.size()-1; i > 0; i-- )
				{
					if(this.transactionList.get(i).getTransactionType().equals(typeOfTransaction.toString().toLowerCase())) 
					{
						return this.transactionList.get(i);
					} 
				} return null;
			}
		}
		
		/**Förändrar kriditgränsen.
		 * @param creditLimit som en double.
		 * @return creditLimit som en double.
		 */
		public double setCreditLimit(double creditLimit)
		{
			this.creditLimit = creditLimit; 
			return this.creditLimit;
		}
		/**Hämtar kreditgränsen
		 * @return creditLimit som en double.
		 */
		public double getCreditLimit()
		{
			return this.creditLimit;
		}
		/**Abstrakt metod-stub. Jag tycker inte att det är självklart i detta fall vilka metoder som ska 
		 * vara abstrakta. I princip är kontotypernas beteende så pass lika att alla metoder kunde 
		 * få sin implementering i superklassen. Om beteende sedan behövs korrigeras lite kan man
		 * Override:a dessa och använda superklassens redan implementerade kod samt nödvändiga förändringar.
		 * Som jag gör med t.ex. toString() och changeAccountBalance() metoderna. På så sätt kan mer kod återvinnas.
		 * Dessa har jag valt att göra abstrakta eftersom de har en aningens tydligare koppling till subclasserna. 
		 * @return double.
		 */
		public abstract double getInterest(); 
		/**Abstrakt metod-stub.
		 * @return double.
		 */
		public abstract double setInterest(double interest); 
		/**Abstrakt metod-stub.
		 * @return double.
		 */
		public abstract double getInterestAmount();
}
