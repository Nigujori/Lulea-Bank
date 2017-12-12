package johrin7.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/** En specifik bankkonto typ som ärver av den abstrakta och mer allmänna bakkontoklassen
 * @author Johan Ringström användarnamn johrin7.*/

public class SavingsAccount extends Account {

		private double interest;
		/**Default konstruktor som skapar ett sparkonto utan parametrar.
		 */
		public SavingsAccount() 
		{
			super();
			this.interest = 0.01;
		}
		/**Konstruktor med vilken du kan specificera vilken ränta som kontot ska ha.
		 */
		public SavingsAccount(Double interest)
		{
			super();
			this.interest = interest;
		}
		/**Utökar superklassens metod med antal-utlån-per-år-restriktions beteendet. 
		 * @param amount, summan med vilket saldot ska färändras i form av en double.
		 * @param typeOfTransaction vilken typ av transaktion som ska göras i form av en TypeOfTransaction.
		 */
		@Override
		public boolean changeAccountBalance(double amount, TypeOfTransaction typeOfTransaction) 
		{
			long diffInSeconds;
			Transaction transaction;
			//Om ett sista uttag hittas läggs detta i transactions varaiabeln och används sedan för att hämta 
			//uttagsdatumet som sedan gämförs med dagens datum för att få reda på hur lång tid i sekunder som förflutit
			//sedan detta gjordes. Annars görs ett uttagsförsök som vanligt.
			if((transaction = super.getLastTransaction(TypeOfTransaction.WITHDRAW)) != null) {
				Date withdrawDate = transaction.getDate();
				Date currentDate   = new Date();
				long duration  = withdrawDate.getTime() - currentDate.getTime();
				diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
				//Om skillnaden i sekunder är kortare än ett år läggs en uttagsavgift på 2% på uttagssumman.
				//Annars görs ett uttag, om ett sådant medges, utan avgifter.
				if (diffInSeconds <= 31556926) {
					return super.changeAccountBalance(amount += amount* 0.02, typeOfTransaction);
				} 
				else {
					super.changeAccountBalance(amount, typeOfTransaction);
				}
			}
			return super.changeAccountBalance(amount, typeOfTransaction);
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
