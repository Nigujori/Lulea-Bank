package johrin7;

import java.util.ArrayList;

import javax.swing.JFrame;

import johrin7.views.CustomerSearchAndDisplayView;

public class BankController implements BankControllerInterface {
	BankModelInterface bankModel;
	JFrame view;
	
	public BankController(BankModelInterface bankModel) {
		this.bankModel = bankModel;
		view = new CustomerSearchAndDisplayView(this, bankModel);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setVisible(true);
	}

	@Override
	public boolean createCustomer(String forename, String surname, String pNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int createSavingsAccount(String pNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createCreditAccount(String pNr) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deposit(String pNo, int accountId, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean withdraw(String pNo, int accountId, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeCustomerName(String name, String surname, String pNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> deleteCustomer(String pNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String closeAccount(String pNr, int accountId) {
		// TODO Auto-generated method stub
		return null;
	}

}
