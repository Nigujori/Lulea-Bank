package johrin7.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

public class AccountView extends JFrame implements BankObserver, TableView {
	private static final int FRAME_WIDTH = 550;
	private static final int FRAME_HIGHT = 550;
	private static final int TEXTFIELD_COLUMNS = 30;
	private static final int LABEL_WIDTH = 100;
	private static final int LABEL_HIGHT = 15;
	
	private JLabel fornameLabel;
	private JTextField forname;
	private JLabel surnameLabel;
	private JTextField surname;
	private JLabel personalNumberLabel;
	private JTextField personalNumber;
	private JLabel accountNumberLabel;
	private JTextField accountNumber;
	private JLabel balanceLabel;
	private JTextField balance;
	private JLabel accountTypeLabel;
	private JTextField accountType;
	private JLabel intrestLabel;
	private JTextField intrest;
	private JLabel creditLimitLabel;
	private JTextField creditLimit;
	private JTable customerTable;
	private DefaultTableModel tableModel;
	
	private BankControllerInterface bankController;
	private BankModelInterface bankModel;
	private int accountNumberInt;
	private String personalNumberStr;
	private JButton deletButton;
	String account;
	ArrayList<String> customer;
	ArrayList<OptionView> optionViews = new ArrayList<>();
	
	public AccountView(BankControllerInterface bankController, BankModelInterface bankModel, int accountNumber, String personalNumber) {
		this.accountNumberInt = accountNumber;
		this.personalNumberStr = personalNumber;
		this.bankController = bankController;
		this.bankModel = bankModel;
		createView();
		this.updateBank(true);
		bankModel.registerObserver(this);
	}
	
	public void createView() {
		createAccuntInformationLabels();
		createDeletButton();
		createMenu();
		createTable();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank kontofönster");
		setVisible(true);
	}
	
	private void createAccuntInformationLabels() {		
		fornameLabel = new JLabel("Förnamn");
		fornameLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		forname = new JTextField(TEXTFIELD_COLUMNS);
		forname.setEnabled(false);
		
		surnameLabel = new JLabel("Efternamn");
		surnameLabel .setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		surname = new JTextField(TEXTFIELD_COLUMNS);
		surname.setEnabled(false);
		
		personalNumberLabel = new JLabel("Personnummer");
		personalNumberLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		personalNumber = new JTextField(TEXTFIELD_COLUMNS);
		personalNumber.setEnabled(false);
		
		accountNumberLabel = new JLabel("Kontonummer");
		accountNumberLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		accountNumber = new JTextField(TEXTFIELD_COLUMNS);
		accountNumber.setEnabled(false);
		
		balanceLabel = new JLabel("Saldo");
		balanceLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		balance = new JTextField(TEXTFIELD_COLUMNS);
		balance.setEnabled(false);
		
		accountTypeLabel = new JLabel("Kontotyp");
		accountTypeLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		accountType = new JTextField(TEXTFIELD_COLUMNS);
		accountType.setEnabled(false);
		
		intrestLabel = new JLabel("Räntesats");
		intrestLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		intrest = new JTextField(TEXTFIELD_COLUMNS);
		intrest.setEnabled(false);
		
		creditLimitLabel = new JLabel("Kreditgräns");
		creditLimitLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		creditLimit = new JTextField(TEXTFIELD_COLUMNS);
		creditLimit.setEnabled(false);
	}
	
	private void createDeletButton() {
		deletButton = new JButton("Ta bort konto");
		deletButton.addActionListener(e -> {
				 int confirm = JOptionPane.showConfirmDialog(null, "Vill du ta bort " + accountType.getText() + " "  
				 + accountNumber.getText()); 
				 if(confirm == JOptionPane.YES_OPTION) {
					 account = bankController.closeAccount(personalNumberStr, accountNumberInt);
					 if(!account.equals(null)) {
						 String[] splitStrArray = account.split(" ");
						 JOptionPane.showMessageDialog(null,  splitStrArray[2] + splitStrArray[0] + " med saldot " +  splitStrArray[1]
								 + " och räntesatsen " +  splitStrArray[3] + " är borttagen " + ". Räntan blev " + splitStrArray[4]);
						 this.dispose();
					 }
				 }
		});
	}
	
	private void createTable() {
		customerTable= new JTable();
		tableModel = new DefaultTableModel(0, 3);
		String header[] = new String[] { "Datum", "Summa", "Saldo efter transaktion"};
		tableModel.setColumnIdentifiers(header);
		 customerTable.setModel(tableModel);
		 customerTable.setCellSelectionEnabled(true);  
		 ListSelectionModel select= customerTable.getSelectionModel();  
		 select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	private void createMenu() 
	{
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu options = new JMenu("Options");
		menuBar.add(options);
		JMenuItem createItem = new JMenuItem("Uttag");
		createItem.addActionListener(e -> {
			
			WithDrawView optionView =new WithDrawView(bankController, accountNumber.getText(),  personalNumber.getText(), Double.parseDouble(creditLimit.getText()));
			this.optionViews.add(optionView);
		});

		JMenuItem deletItem = new JMenuItem("Insättning");
		deletItem.addActionListener(e -> {
			DepositView optionView = new DepositView(bankController, accountNumber.getText(), personalNumber.getText());
			this.optionViews.add(optionView);
			});
		options.add(createItem);
		options.add(deletItem);
	}
	
	private void createPane() 
	{
		JPanel panelMain = new JPanel();
		JPanel panelForname = new JPanel();
		JPanel panelSurname = new JPanel();
		JPanel panelPersonalNr = new JPanel();
		JPanel panelAccountNr = new JPanel();
		JPanel panelBalance = new JPanel();
		JPanel panelAccountType = new JPanel();
		JPanel panelIntrest = new JPanel();
		JPanel panelCreditLimit = new JPanel();
		panelForname.add(fornameLabel);
		panelForname.add(forname);
		panelSurname.add(surnameLabel);
		panelSurname.add(surname);
		panelPersonalNr.add(personalNumberLabel);
		panelPersonalNr.add(personalNumber);
		panelAccountNr.add(accountNumberLabel);
		panelAccountNr.add(accountNumber);
		panelBalance.add(balanceLabel);
		panelBalance.add(balance);
		panelAccountType.add(accountTypeLabel);
		panelAccountType.add(accountType);
		panelIntrest.add(intrestLabel);
		panelIntrest.add(intrest);
		panelCreditLimit.add(creditLimitLabel);
		panelCreditLimit.add(creditLimit);
		
		JScrollPane scrollPane = new JScrollPane(customerTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(500, 100));
		scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		panelMain.add(panelForname);
		panelMain.add(panelSurname);
		panelMain.add(panelPersonalNr);
		panelMain.add(panelAccountNr);
		panelMain.add(panelBalance);
		panelMain.add(panelAccountType);
		panelMain.add(panelIntrest);
		panelMain.add(panelCreditLimit);
		panelMain.add(scrollPane);
		
		panelMain.add(deletButton);
		add(panelMain);
	}
	
	public void closeOptionViews() {
		if(this.optionViews.size() != 0) {
		for(OptionView op : this.optionViews) 
		{
			if(op instanceof DepositView) 
			{
				((DepositView) op).dispose();
			} 
			if(op instanceof WithDrawView) 
			{
				((WithDrawView) op).dispose();
			}
		}
		}
	}

	@Override
	public void updateBank(Boolean bool) {
		account = this.bankModel.getAccount(this.personalNumberStr, this.accountNumberInt);
		if(account != null) 
		{
			ArrayList<String> transactions = bankModel.getTransactions(this.personalNumberStr, this.accountNumberInt);
			customer = this.bankModel.getCustomer(this.personalNumberStr);
			String[] customerInfo = customer.get(0).split(" ");
			String[] accountInfo = account.split(" ");
			
			if(personalNumber.getText().equals("")) 
		{
			this.forname.setText(customerInfo[0]);
			this.surname.setText(customerInfo[1]);
			this.personalNumber.setText(customerInfo[2]);
			
			this.accountNumber.setText(accountInfo[0]);
			this.balance.setText(accountInfo[1]);
			this.accountType.setText(accountInfo[2]);
			this.intrest.setText(accountInfo[3]);
			this.creditLimit.setText(""+this.bankModel.getCreditLimit(this.personalNumberStr, this.accountNumberInt));
		}
		if(bool) 
		{
			this.balance.setText(accountInfo[1]);
			tableModel.setNumRows(0);
			for(int i = 0; i < transactions.size(); i++) {
				String[] transInfo = transactions.get(i).toString().split(" ");
				tableModel.addRow(new Object[] { transInfo[0], transInfo[2], transInfo[3]});
			} 
		}
		}
	}
}
