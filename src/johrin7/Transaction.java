package johrin7;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import johrin7.Account.TypeOfTransaction;

public class Transaction {
	//
	private final Date date;
	private final double transactionAmount;
	private final double balance;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//Format för datumet och tiden
	TypeOfTransaction transType;
	private static int lastTransactionNr = 10000;
	private final int transactionNumber;

	/**Konstruktor
	 * @param transactionAmount
	 * @param balanceAfterTrans
	 */
	public Transaction(double transactionAmount, double balanceAfterTrans)
	{
		Transaction.lastTransactionNr++;
		this.date = new Date();
		this.transactionAmount = transactionAmount;
		this.balance = balanceAfterTrans;
		//Om transactionssumman är positiv sätts transaktionen till deposit annars withdraw.
		if(transactionAmount > 0) 
		{
			this.transType = TypeOfTransaction.DEPOSIT;
		} else this.transType = TypeOfTransaction.WITHDRAW;
		this.transactionNumber = lastTransactionNr;
	}
	/**Hämtar transactionsdatumet.
	 * @return Datumet som ett Date-objekt.
	 */
	public Date getDate()  
	{
		return this.date;
	}
	/**Hämtar transaktionsSumman.
	 * @return en transaktionssumman som en double.
	 */
	public double getTransAmount() 
	{
		return this.transactionAmount;
	}
	
	public int getTransactionNr()
	{
		return this.transactionNumber;
	}
	
	public String getTransactionType()
	{
		return this.transType.toString().toLowerCase();
	}
	
	public String toString() {
		return dateFormat.format(date) + " " + transactionAmount + " " + balance;
	}
}
