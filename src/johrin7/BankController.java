package johrin7;

import java.util.ArrayList;

import javax.swing.JFrame;

import johrin7.views.CustomerSearchAndDisplayView;

public class BankController implements BankControllerInterface {
	BankModelInterface bankModel;
	CustomerSearchAndDisplayView view;
	
	public BankController(BankModelInterface bankModel) {
		this.bankModel = bankModel;
		view = new CustomerSearchAndDisplayView(this, bankModel);
		view.createView();
		view.updateBank(true);
		
	}

	@Override
	public boolean createCustomer(String forename, String surname, String pNo) {
		return bankModel.createCustomer(forename, surname, pNo);
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
		return bankModel.changeCustomerName(name, surname, pNo);
	}

	@Override
	public ArrayList<String> deleteCustomer(String pNo) {
		return bankModel.deleteCustomer(pNo);
	}

	@Override
	public String closeAccount(String pNr, int accountId) {
		// TODO Auto-generated method stub
		return null;
	}

}
