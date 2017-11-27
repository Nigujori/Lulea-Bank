package johrin7;

import java.util.*;

public class Transaction {
	private final Date date;
	private final double transactionAmount;
	private final double balance;
	
	public Transaction(double transactionAmount, double balanceAfterTrans)
	{
		this.date = new Date();
		this.transactionAmount = transactionAmount;
		this.balance = balanceAfterTrans;
	}
	
	public String toString() {
		return date.toString() + " " + transactionAmount + " " + balance;
	}
}
