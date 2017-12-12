package johrin7.views;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import johrin7.Controller.BankControllerInterface;

@SuppressWarnings("serial")
public class DeleteAccountView extends JFrame implements OptionView {
	private static final int FRAME_WIDTH = 550;
	private static final int FRAME_HIGHT = 100;
	
	private JLabel personalNumberLabel;
	private JTextField personalnumber;
	private JLabel accountNumberLabel;
	private JTextField accountnumber;
	private JButton deleteButton;
	private BankControllerInterface bankController;
	private String account = null;
	
	public DeleteAccountView(BankControllerInterface bankController) {
		this.bankController = bankController;
		createView();
	}
	
	public void createView() {
		createTextFields();
		createDeleteButton();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank Delete account");
		setVisible(true);
	}
	
	public void createTextFields() {		
		personalNumberLabel = new JLabel("  Personnummer");
		personalnumber = new JTextField();
		personalnumber.setEditable(false);
		accountNumberLabel = new JLabel("  Kontonummer");
		accountnumber = new JTextField();
	}
	
	 private void createDeleteButton() {
		 deleteButton = new JButton("Ta bort konto");
		 deleteButton.addActionListener(e -> {
			 
			 if(accountnumber.getText().equals(null) || !accountnumber.getText().matches(("[0-9]+"))) 
				 {
				 JOptionPane.showMessageDialog(null, "Det var inte möjligt att ta bort detta konto. "
					 		+ "Kontrollera kontonumret");
				 } else 
					 {
					 	account = bankController.closeAccount(personalnumber.getText(), Integer.parseInt(accountnumber.getText()));
					 	if(account != null) 
							 {
								 String[] splitStrArray = account.split(" ");
								 JOptionPane.showMessageDialog(null,  splitStrArray[2] + splitStrArray[0] + " med saldot " +  splitStrArray[1]
								 + " och räntesatsen " +  splitStrArray[3] + " är borttagen " + ". Räntan blev " + splitStrArray[4]);
							 } else 
								 {
									 JOptionPane.showMessageDialog(null, "Det var inte möjligt att ta bort detta konto. "
									 		+ "Kontrollera kontonumret");
								 }
						 }
			 
				
		});
	 }
	 
	 public void createPane() 
		{
			JPanel panelGrid = new JPanel(new GridLayout(3, 1));
			panelGrid.add(personalNumberLabel);
			panelGrid.add(personalnumber);
			panelGrid.add(accountNumberLabel);
			panelGrid.add(accountnumber);
			panelGrid.add(deleteButton);
			add(panelGrid);
			this.setLocation(FRAME_WIDTH + 3, 475);
		}
	 
	 public void setPersonalNumber(String personalnumber) {
		 this.personalnumber.setText(personalnumber);
	 }
	 
	 public void setAccountNumber(String accountnumber) {
		 this.accountnumber.setText(accountnumber);
	 }
}
