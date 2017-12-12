package johrin7.views;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import johrin7.BankControllerInterface;

public class DepositView extends JFrame implements OptionView {
	private static final int FRAME_WIDTH = 450;
	private static final int FRAME_HIGHT = 100;
	
	private JLabel accountNumberLabel;
	private JTextField accountnumber;
	private JLabel depositLabel;
	private JTextField deposit;
	private JButton depositButton;
	private BankControllerInterface bankController;
	private String accountNumberStr;
	private String personalNumberStr;
	
	public DepositView(BankControllerInterface bankController, String accountNr, String personalNumber) {
		this.bankController = bankController;
		this.accountNumberStr = accountNr;
		this.personalNumberStr = personalNumber;
		createView();
	}
	
	public void createView() {
		createTextFields();
		createDepositButton();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank Insättningsfönster");
		setVisible(true);
	}
	
	public void createTextFields() {		
		accountNumberLabel = new JLabel("  Kontonummer");
		accountnumber = new JTextField();
		accountnumber.setText(accountNumberStr);
		accountnumber.setEditable(false);
		depositLabel = new JLabel("  Summa insättning");
		deposit = new JTextField();
	}
	
	 private void createDepositButton() {
		 depositButton = new JButton("Gör insättning");
		 depositButton.addActionListener(e -> 
		 {	
			 Boolean bool = false;
			 if(deposit.getText().matches(("[0-9]{1,13}(\\.[0-9]*)?"))) 
			 {
				 bool = bankController.deposit(personalNumberStr, Integer.parseInt(accountNumberStr),  Double.parseDouble(deposit.getText()));
			 } else if(!bool) JOptionPane.showMessageDialog(null, "Det gick inte att göra insättningen");
		});
	 }
	 
	 public void createPane() 
		{
			JPanel panelGrid = new JPanel(new GridLayout(3, 1));
			panelGrid.add(accountNumberLabel);
			panelGrid.add(accountnumber);
			panelGrid.add(depositLabel);
			panelGrid.add(deposit);
			panelGrid.add(depositButton);
			add(panelGrid);
		}
}
