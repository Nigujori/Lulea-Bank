package johrin7.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import johrin7.BankControllerInterface;
import johrin7.BankModelInterface;
import johrin7.BankObserver;

public class DeleteCustomerView extends JFrame implements BankObserver{
	private static final int FRAME_WIDTH = 450;
	private static final int FRAME_HIGHT = 100;
	
	private JLabel personalNumberLabel;
	private JTextField personalnumber;
	private JButton deleteButton;
	private BankControllerInterface bankController;
	private BankModelInterface bankModel;
	
	public DeleteCustomerView(BankControllerInterface bankController, BankModelInterface bankModel) {
		this.bankController = bankController;
		this.bankModel = bankModel;
		createView();
		bankModel.registerObserver(this);
	}
	
	public void createView() {
		createTextFields();
		createDeleteButton();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank Delete Customer");
		setVisible(true);
	}
	
	private void createTextFields() {		
		personalNumberLabel = new JLabel("  Personnummer");
		personalnumber = new JTextField();
	}
	
	 private void createDeleteButton() {
		 deleteButton = new JButton("Ta bort kund");
		 deleteButton.addActionListener(e -> {
			 ArrayList<String> customer = bankController.deleteCustomer(personalnumber.getText());
			 if(customer != null) {
			 JOptionPane.showMessageDialog(null, "Kunden " + customer.toString()
			 +" med " +  (customer.size() -1) + " kont/konton är borttagen");
			 } 
		});
	 }
	 
	 private void createPane() 
		{

			JPanel panelGrid = new JPanel(new GridLayout(3, 1));
			panelGrid.add(personalNumberLabel);
			panelGrid.add(personalnumber);
			panelGrid.add(deleteButton);
			add(panelGrid);
		}
	 
	 @Override
		public void updateBank(Boolean bool) {
			if(!bool) JOptionPane.showMessageDialog(null, "Det var inte möjligt att ta bort denna kund");
		}

}
