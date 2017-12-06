package johrin7.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.nio.ByteOrder;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import johrin7.BankControllerInterface;
import johrin7.BankModelInterface;
import johrin7.BankObserver;

public class CreateCustomerView extends JFrame implements BankObserver{
	private static final int FRAME_WIDTH = 450;
	private static final int FRAME_HIGHT = 100;
	
	private JLabel fornameLabel;
	private JTextField forname;
	private JLabel surnameLabel;
	private JTextField surname;
	private JLabel personalNumberLabel;
	private JTextField personalnumber;
	private JButton createButton;
	private BankControllerInterface bankController;
	private BankModelInterface bankModel;
	
	public CreateCustomerView(BankControllerInterface bankController, BankModelInterface bankModel) {
		this.bankController = bankController;
		this.bankModel = bankModel;
		createView();
		bankModel.registerObserver(this);
	}
	
	public void createView() {
		createTextFields();
		createButtonCreate();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank Create Customer");
		setVisible(true);
	}
	
	private void createTextFields() {		
		fornameLabel = new JLabel("  Förnamn");
		forname = new JTextField();
		surnameLabel = new JLabel("  Efternamn");
		surname = new JTextField();
		personalNumberLabel = new JLabel("  Personnummer");
		personalnumber = new JTextField();
	}
	 private void createButtonCreate() {
		 createButton = new JButton("Skapa kund");
		 createButton.addActionListener(e -> {bankController.createCustomer(forname.getText(),
				 surname.getText(), personalnumber.getText());
		});
	 }
	 
	 private void createPane() 
		{
			JPanel panelMain = new JPanel(new BorderLayout());
			JPanel panelGrid = new JPanel(new GridLayout(2, 3));
			panelGrid.add(fornameLabel);
			panelGrid.add(surnameLabel);
			panelGrid.add(personalNumberLabel);
			panelGrid.add(forname);
			panelGrid.add(surname);
			panelGrid.add(personalnumber);
			panelMain.add(panelGrid, BorderLayout.CENTER);
			panelMain.add(createButton, BorderLayout.SOUTH);
			
			add(panelMain);
		}

	@Override
	public void updateBank(Boolean bool) {
		if(!bool) JOptionPane.showMessageDialog(null, "De var inte möjligt att skapa denna kund.");
	}
}
