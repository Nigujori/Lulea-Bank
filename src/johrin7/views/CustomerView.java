package johrin7.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import johrin7.BankControllerInterface;
import johrin7.BankModelInterface;
import johrin7.BankObserver;
import johrin7.Customer;

public class CustomerView extends JFrame implements BankObserver{
	
	private static final int FRAME_WIDTH = 550;
	private static final int FRAME_HIGHT = 450;
	private static final int TEXTFIELD_COLUMNS = 30;
	private static final int LABEL_WIDTH = 100;
	private static final int LABEL_HIGHT = 15;
	
	private JLabel fornameLabel;
	private JTextField forname;
	private JLabel surnameLabel;
	private JTextField surname;
	private JLabel personalNumberLabel;
	private JTextField personalnumber;
	private JButton changeCustomerButton;
	private BankControllerInterface bankController;
	private BankModelInterface bankModel;
	private JTable customerTable;
	private DefaultTableModel tableModel;
	private String personalNumberStr;
	
	public CustomerView(BankControllerInterface bankController, BankModelInterface bankModel, String personalnumber) {
		this.personalNumberStr = personalnumber;
		this.bankController = bankController;
		this.bankModel = bankModel;
		createView();
		this.updateBank(true);
		bankModel.registerObserver(this);
	
	}
	
	public void createView() {
		createTextFields();
		createChangeButton();
		//createTable();
		createMenu();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank kundfönster");
		setVisible(true);
	}
	
	private void createTextFields() {		
		fornameLabel = new JLabel("Förnamn");
		fornameLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		forname = new JTextField(TEXTFIELD_COLUMNS);
		surnameLabel = new JLabel("Efternamn   ");
		surnameLabel .setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		surname = new JTextField(TEXTFIELD_COLUMNS);
		personalNumberLabel = new JLabel("Personnummer");
		personalNumberLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		personalnumber = new JTextField(TEXTFIELD_COLUMNS);
		personalnumber.setEnabled(false);
	}
	 private void createChangeButton() {
		 changeCustomerButton = new JButton("Ändra kund information");
		 changeCustomerButton.setPreferredSize(new Dimension(100, 25));
		 changeCustomerButton.addActionListener(e -> {
			 bankController.changeCustomerName(forname.getText(),
				 surname.getText(), personalnumber.getText());
		});
	 }
	
	/*private void createTable() {
		customerTable= new JTable();
		tableModel = new DefaultTableModel(0, 5);
		String header[] = new String[] { "Kontonummer", "Saldo", "Kontotyp", "Ränta", "Räntesats"};
		tableModel.setColumnIdentifiers(header);
		 customerTable.setModel(tableModel);
		 customerTable.setCellSelectionEnabled(true);  
		 ListSelectionModel select= customerTable.getSelectionModel();  
		 select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 select.addListSelectionListener(e -> {JOptionPane.showMessageDialog(null, customerTable.getValueAt(customerTable.getSelectedRow(), 2));});
	}*/
	
	private void createMenu() 
	{
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu options = new JMenu("Options");
		menuBar.add(options);
		JMenuItem createItem = new JMenuItem("Lägg till bankkonto");
		//createItem.addActionListener(e -> new CreateCustomerView(bankController, bankModel));

		JMenuItem deletItem = new JMenuItem("Ta bort bankkonto");
		//deletItem.addActionListener(e -> new DeleteCustomerView(bankController, bankModel));
		options.add(createItem);
		options.add(deletItem);
	}
	
	
	private void createPane() 
	{
		JPanel panelMain = new JPanel();
		JPanel panelCustomer = new JPanel(new BorderLayout());
		JPanel panelForname = new JPanel();
		JPanel panelSurname = new JPanel();
		JPanel panelPersonalnumber = new JPanel();
		JPanel panelChangeButton = new JPanel();
		panelForname.add(fornameLabel);
		panelForname.add(forname);
		panelSurname.add(surnameLabel);
		panelSurname.add(surname);
		panelPersonalnumber.add(personalNumberLabel);
		panelPersonalnumber.add(personalnumber);
		panelChangeButton.add(changeCustomerButton);
		panelCustomer.add(panelForname, BorderLayout.NORTH);
		panelCustomer.add(panelSurname, BorderLayout.CENTER);
		panelCustomer.add(panelPersonalnumber, BorderLayout.SOUTH);
		
		JPanel panelTable = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(customerTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(500, 200));
		scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelTable.add(scrollPane, BorderLayout.CENTER);

		panelMain.add(panelCustomer);
		panelMain.add(panelChangeButton);
		panelMain.add(panelTable);
		add(panelMain);
		
	}

	@Override
	public void updateBank(Boolean bool) {
			//tableModel.setRowCount(0);
			if(personalnumber.getText().equals("")) {
			ArrayList<String> customer = this.bankModel.getCustomer(this.personalNumberStr);
			String[] customerInfo = customer.get(0).split(" ");
			this.forname.setText(customerInfo[0]);
			this.surname.setText(customerInfo[1]);
			this.personalnumber.setText(customerInfo[2]);
			/*for(String customerStr : customer) {
				String[] splitStr = customerStr.split(" ");
				tableModel.addRow(new Object[] { splitStr[0], splitStr[1], splitStr[2]});
			} */
		}
	}
}

