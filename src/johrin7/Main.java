package johrin7;

import java.io.*;
import johrin7.Controller.BankController;
import johrin7.Controller.BankControllerInterface;
import johrin7.Model.BankLogic;
import johrin7.Model.BankModelInterface;
import johrin7.views.CustomerSearchAndDisplayView;

/**Detta är den klass som håller main-metoden. Den skapar en instans av BankLogic och lägger in några
 * kunder samt konton och skapar sedan en BankController instans som skapar en instans av  huvud-viewn 
 * CustomerSearchAndDisplayViewn.
* @author Johan Ringström användarnamn johrin7.*/

public class Main
{
	public static void main(String[] args) throws FileNotFoundException
	{		
		BankModelInterface bank = new BankLogic();
		bank.createCustomer("Johan", "Ringström", "7802028353");
		bank.createSavingsAccount("7802028353");
		bank.createCreditAccount("7802028353");
		bank.createSavingsAccount("7802028353"); 
		
		bank.createCustomer("Zara", "Kadiri", "8311010744");
		bank.createSavingsAccount("8311010744");
		bank.createCreditAccount("8311010744");
		bank.createSavingsAccount("8311010744");
		
		bank.createCustomer("Nils", "Eriksson", "8210011212");
		bank.createCustomer("Hans", "Eriksson", "7510011212");
		bank.createSavingsAccount("8210011212");
		bank.createCreditAccount("8210011212");
		bank.createSavingsAccount("8210011212");
		new CustomerSearchAndDisplayView(new BankController(bank));
	}
}

