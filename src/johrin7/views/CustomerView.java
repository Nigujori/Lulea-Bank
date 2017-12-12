package johrin7.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

public class CustomerView extends JFrame implements BankObserver, TableView{
	
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
	private String accountNumberStr;
	ArrayList<OptionView> optionViews = new ArrayList<>();
	
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
		createTable();
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
		 changeCustomerButton.setPreferredSize(new Dimension(450, 25));
		 changeCustomerButton.addActionListener(e -> {
			 bankController.changeCustomerName(forname.getText(),
				 surname.getText(), personalnumber.getText());
		});
	 }
	
	private void createTable() {
		customerTable= new JTable();
		tableModel = new DefaultTableModel(0, 4);
		String header[] = new String[] { "Kontonummer", "Saldo", "Kontotyp", "Räntesats"};
		tableModel.setColumnIdentifiers(header);
		 customerTable.setModel(tableModel);
		 customerTable.setCellSelectionEnabled(true);  
		 ListSelectionModel select= customerTable.getSelectionModel();  
		 select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 select.addListSelectionListener(e -> {
			 if(!e.getValueIsAdjusting()) {
				 if(customerTable.getSelectedRow() != -1) {
					accountNumberStr = (String)customerTable.getValueAt(customerTable.getSelectedRow(), 0);
					AccountView accountView = new AccountView(bankController, bankModel, Integer.parseInt(accountNumberStr), this.personalNumberStr);
					accountView.addWindowListener(new WindowAdapter() {
			            @Override
			            public void windowClosed(WindowEvent e) {
			                accountView.closeOptionViews();
			            }
			            @Override
			            public void windowClosing(WindowEvent e) {
			            		System.out.println("in->");
			            		accountView.dispose();
			            }
			        });
				 }
			 }
			});
	}
	
	private void createMenu() 
	{
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu options = new JMenu("Options");
		menuBar.add(options);
		JMenuItem createItem = new JMenuItem("Lägg till bankkonto");
		createItem.addActionListener(e -> {
			CreateAccountView createAccountView = new CreateAccountView(bankController);
			createAccountView.setPersonalNumber(personalNumberStr);
			this.optionViews.add(createAccountView);
		});

		JMenuItem deletItem = new JMenuItem("Ta bort bankkonto");
		deletItem.addActionListener(e -> {
			DeleteAccountView deleteAccountView = new DeleteAccountView(bankController);
			deleteAccountView.setPersonalNumber(personalNumberStr);
			this.optionViews.add(deleteAccountView);
			});
		options.add(createItem);
		options.add(deletItem);
	}
	
	
	private void createPane() 
	{
		JPanel panelMain = new JPanel();
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
		
		JScrollPane scrollPane = new JScrollPane(customerTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(500, 200));
		scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		panelMain.add(panelForname);
		panelMain.add(panelSurname);
		panelMain.add(panelPersonalnumber);
		panelMain.add(panelChangeButton);
		panelMain.add(scrollPane);
		add(panelMain);
	}

	@Override
	public void updateBank(Boolean bool) {
		ArrayList<String> customer = this.bankModel.getCustomer(this.personalNumberStr);
			if(personalnumber.getText().equals("")) {
				String[] customerInfo = customer.get(0).split(" ");
				this.forname.setText(customerInfo[0]);
				this.surname.setText(customerInfo[1]);
				this.personalnumber.setText(customerInfo[2]);
			}
			tableModel.setNumRows(0);
			for(int i = 1; i < customer.size(); i++) {
				String[] splitStr = customer.get(i).split(" ");
				tableModel.addRow(new Object[] { splitStr[0], splitStr[1], splitStr[2], splitStr[3]});
			} 
	}


	@Override
	public void closeOptionViews() {
		if(this.optionViews.size() != 0) {
		for(OptionView op : this.optionViews) 
		{
			if(op instanceof CreateAccountView) 
			{
				((CreateAccountView) op).dispose();
			} 
			if(op instanceof DeleteAccountView) 
			{
				((DeleteAccountView) op).dispose();
			}
		}
		}
	}
	public String getPersonalNumber() {
		return this.personalNumberStr;
	}
	
}

