package johrin7.views;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import johrin7.BankControllerInterface;

public class WithDrawView extends JFrame implements OptionView {
	private static final int FRAME_WIDTH = 450;
	private static final int FRAME_HIGHT = 100;
	
	private JLabel accountNumberLabel;
	private JTextField accountnumber;
	private JLabel withDrawLabel;
	private JTextField withDraw;
	private JButton withDrawButton;
	private BankControllerInterface bankController;
	private String accountNumberStr;
	private String personalNumberStr;
	private double creditLimit;
	
	public WithDrawView(BankControllerInterface bankController, String accountNr, String personalNumber, double creditLimit) {
		this.bankController = bankController;
		this.accountNumberStr = accountNr;
		this.personalNumberStr = personalNumber;
		this.creditLimit = creditLimit;
		createView();
	}

	
	public void createView() {
		createTextFields();
		createWithDrawButton();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank uttagsfönster");
		setVisible(true);
	}
	
	public void createTextFields() {		
		accountNumberLabel = new JLabel("  Kontonummer");
		accountnumber = new JTextField();
		accountnumber.setEditable(false);
		withDrawLabel = new JLabel("  Summa uttag");
		withDraw = new JTextField();
	}
	
	 private void createWithDrawButton() {
		 withDrawButton = new JButton("Gör uttag");
		 withDrawButton.addActionListener(e -> {	
			 Boolean bool = false;
			 if(withDraw.getText().matches(("[0-9]{1,13}(\\.[0-9]*)?"))) 
			 {
				bool = bankController.withdraw(personalNumberStr, Integer.parseInt(accountNumberStr),  Double.parseDouble(withDraw.getText()));
				if(!bool) {
					JOptionPane.showMessageDialog(null, "Kreditgränsen för detta konto är " + this.creditLimit + "kr");
				}
			 } else if(!bool) JOptionPane.showMessageDialog(null, "Det gick inte att göra ett uttag");
		});
	 }
	 
	 public void createPane() 
		{
			JPanel panelGrid = new JPanel(new GridLayout(3, 1));
			panelGrid.add(accountNumberLabel);
			panelGrid.add(accountnumber);
			accountnumber.setText(accountNumberStr);
			panelGrid.add(withDrawLabel);
			panelGrid.add(withDraw);
			panelGrid.add(withDrawButton);
			add(panelGrid);
		}
}
