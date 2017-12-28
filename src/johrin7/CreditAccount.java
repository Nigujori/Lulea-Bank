package johrin7;


/** En specifik bankkonto typ som ärver av den abstrakta och mer allmänna bakkontoklassen
 * @author Johan Ringström användarnamn johrin7.*/
public class CreditAccount extends Account{
	
	private double interest;
	
	
	/**Konstruktor som ger superklassens metod det den vill ha.
	 */
	public CreditAccount()
	{
		super("Kreditkonto", 5000);
		this.interest = 0.005;
	}
	
	/**Konstruktor som ger superklassens metod det den vill ha samt en möjlig het att bestämma räntan.
	 */
	public CreditAccount(double interest)
	{
		super("Kreditkonto", 5000);
		this.interest = interest;
	}
	/**Utökar superklassens metod med kreditfunktions-beteendet där räntan på, de av bankkundens
	 * lånade pengar sätts högre än på de av densamme utlånade pengarna. 
	 * @param amount, summan med vilket saldot ska färändras i form av en double.
	 * @param typeOfTransaction vilken typ av transaktion som ska göras i form av en TypeOfTransaction.
	 */
	@Override
	public boolean changeAccountBalance(double amount, TypeOfTransaction typeOfTransaction) 
	{	
		//Transaktionen görs och utfallet läggs i en variabel.
		boolean bool = super.changeAccountBalance(amount, typeOfTransaction);
		//Om saldot är på minus efter transaktionen förändras räntesatsen till 7%,
		//annars till 0.5%;
		if(super.getBalance() < 0) 
		{
			this.setInterest(0.07);
		} else this.setInterest(0.005);
		return bool;
	}
	
	/**Hämtar räntesatsen.
	 * @return räntesatsen som en double.
	 */public double getInterest() 
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
			return this.interest * super.getBalance();
		}
		/**Lägger till konto information till den som redan kan hämtas med hjälp av getAccountInfo-metoden
		 * i superklassen.Här används Math.round-metoden för att avrunda beloppen. 
		 */
		@Override
		public String getAccountInfo()
		{
			String infoString = super.getAccountInfo() + " " + (double)Math.round(this.interest * 100 * 100000d) / 100000
					+ " " + (double)Math.round(this.getInterestAmount() * 100000d) / 100000;
			return infoString;
		}	

}
