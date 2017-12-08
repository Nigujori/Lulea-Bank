package johrin7.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import johrin7.BankControllerInterface;
import johrin7.BankLogic;
import johrin7.BankLogic.TypeOfAccount;
import johrin7.BankModelInterface;
import johrin7.BankObserver;

public class CreateAccountView extends JFrame implements BankObserver {
	private static final int FRAME_WIDTH = 450;
	private static final int FRAME_HIGHT = 100;
	
	private JComboBox<TypeOfAccount> comboBoxAccountTypes;
	private JLabel accountTypeLabel;
	private JTextField accountType;
	private JLabel personalNumberLabel;
	private JTextField personalnumber;
	private JButton createButton;
	private BankControllerInterface bankController;
	private BankModelInterface bankModel;
	private String accountTypeStr;
	
	public CreateAccountView(BankControllerInterface bankController, BankModelInterface bankModel) {
		this.bankController = bankController;
		this.bankModel = bankModel;
		createView();
		bankModel.registerObserver(this);
	}
	
	public void createView() {
		createTextFields();
		createAccountButton();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank Create Customer");
		setVisible(true);
	}
	
	private void createTextFields() {		
		//accountTypeLabel = new JLabel("  Kontotyp");
		//accountType = new JTextField();
		comboBoxAccountTypes = new JComboBox<>(TypeOfAccount.values());
		comboBoxAccountTypes.addActionListener(e -> accountTypeStr = comboBoxAccountTypes.getSelectedItem().toString() );
		personalNumberLabel = new JLabel("  Personnummer");
		personalnumber = new JTextField();
		personalnumber.setEditable(false);
	}
	 private void createAccountButton() {
		 createButton = new JButton("Skapa konto");
		 createButton.addActionListener(e -> {
			 //String accountTypeStr = accountType.getText();
			 TypeOfAccount accountType = TypeOfAccount.valueOf(accountTypeStr.toUpperCase());
			 System.out.println("Accounttype "+ accountType);
			 System.out.println("Accounttypestr " +accountTypeStr);
			 if(!accountTypeStr.equals("")) {
				 System.out.println("Accounttype inif " +accountType);
				 bankController.createAccount(personalnumber.getText(),  accountType);
			 }
		});
	 }
	 
	 private void createPane() 
		{
			JPanel panelMain = new JPanel(new BorderLayout());
			JPanel panelGrid = new JPanel(new GridLayout(1, 2));
			panelGrid.add(personalNumberLabel);
			panelGrid.add(personalnumber);
			panelMain.add(panelGrid, BorderLayout.NORTH);
			panelMain.add(comboBoxAccountTypes, BorderLayout.CENTER);
			panelMain.add(createButton, BorderLayout.SOUTH);
			add(panelMain);
		}
	 
	 public void setPersonalNumber(String pNr) {
		 this.personalnumber.setText(pNr);
	 }

	@Override
	public void updateBank(Boolean bool) {
		if(!bool) JOptionPane.showMessageDialog(null, "De var inte möjligt att skapa detta konto.");
	}
}
