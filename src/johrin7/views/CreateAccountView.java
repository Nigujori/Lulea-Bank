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
import johrin7.BankLogic.TypeOfAccount;


public class CreateAccountView extends JFrame implements OptionView {
	private static final int FRAME_WIDTH = 450;
	private static final int FRAME_HIGHT = 100;
	
	private JComboBox<TypeOfAccount> comboBoxAccountTypes;
	private JLabel personalNumberLabel;
	private JTextField personalnumber;
	private JButton createButton;
	private BankControllerInterface bankController;
	private String accountTypeStr;
	
	public CreateAccountView(BankControllerInterface bankController) {
		this.bankController = bankController;
		createView();
	}
	
	public void createView() {
		createTextFields();
		createAccountButton();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank Create Account");
		setVisible(true);
	}
	
	public void createTextFields() {		
		comboBoxAccountTypes = new JComboBox<>(TypeOfAccount.values());
		comboBoxAccountTypes.addActionListener(e -> accountTypeStr = comboBoxAccountTypes.getSelectedItem().toString() );
		personalNumberLabel = new JLabel("  Personnummer");
		personalnumber = new JTextField();
		personalnumber.setEditable(false);
	}
	 private void createAccountButton() {
		 createButton = new JButton("Skapa konto");
		 createButton.addActionListener(e -> {
			 if( accountTypeStr != null) {
				 TypeOfAccount accountType = TypeOfAccount.valueOf(accountTypeStr.toUpperCase());
					 bankController.createAccount(personalnumber.getText(),  accountType);
				 } else 
					 {
						 JOptionPane.showMessageDialog(null, "Var snäll och välj en kontotyp");
					 }
		});
	 }
	 
	 public void createPane() 
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
}
