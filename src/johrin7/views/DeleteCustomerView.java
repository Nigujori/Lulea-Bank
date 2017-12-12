package johrin7.views;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import johrin7.Controller.BankControllerInterface;

@SuppressWarnings("serial")
public class DeleteCustomerView extends JFrame implements OptionView {
	private static final int FRAME_WIDTH = 550;
	private static final int FRAME_HIGHT = 100;
	
	private JLabel personalNumberLabel;
	private JTextField personalnumber;
	private JButton deleteButton;
	private BankControllerInterface bankController;
	CustomerSearchAndDisplayView customerSearchAndDisplayView;
	
	public DeleteCustomerView(BankControllerInterface bankController) {
		this.bankController = bankController;
		createView();
	}
	
	public void createView() {
		createTextFields();
		createDeleteButton();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank Delete Customer");
		setVisible(true);
	}
	
	public void createTextFields() {		
		personalNumberLabel = new JLabel("  Personnummer");
		personalnumber = new JTextField();
	}
	
	 private void createDeleteButton() {
		 deleteButton = new JButton("Ta bort kund");
		 deleteButton.addActionListener(e -> {
			 ArrayList<String> customer = bankController.deleteCustomer(personalnumber.getText());
			 if(customer != null) 
			 { 	
				 String customerInfo = customer.remove(0);
				 JOptionPane.showMessageDialog(null, "Kunden " + customerInfo + " är borttagen"
			 +" med följande konto/konton:\n" + this.presentAccount(customer));
			 } else 
			 {
				JOptionPane.showMessageDialog(null, "Det var inte möjligt att ta bort denna kund");
			 }
		});
	 }
	 
	 public void createPane() 
		{

			JPanel panelGrid = new JPanel(new GridLayout(3, 1));
			panelGrid.add(personalNumberLabel);
			panelGrid.add(personalnumber);
			panelGrid.add(deleteButton);
			add(panelGrid);
			this.setLocation(0, 475);
		}
		

	 private String presentAccount(ArrayList<String> accountList) 
	 {
		 String accountStr = "";
		 for(String account : accountList)
		 {
			 accountStr += account + "\n";
		 }
		 return accountStr;
	 }
}
