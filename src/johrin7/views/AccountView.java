package johrin7.views;

import java.awt.Dimension;
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
import johrin7.Controller.BankControllerInterface;

/**Skapar en view som visar konto information och dess transaktioner. Implementerar BankObserver 
 * och måste därför implementera updateBank() metoden som updaterar denna view om förändringar görs eller
 * försöker göras i modellen. Håller en reference till BankController som är länken till modellen.
 * Implementerar TableView interfacet och dess closeOptionView() metod som ser till att alla optionViews
 * som skapats av ett specifikt AccountView-instans-objekt, vilken den sparar i ArrayListan optionViews,
 * stängs om detta view-object stängs ner. 
 * @author Johan Ringström användarnamn johrin7.*/

@SuppressWarnings("serial")
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
	private int accountNumberInt;
	private String personalNumberStr;
	private JButton deletButton;
	private String account;
	private ArrayList<String> customer;
	private ArrayList<OptionView> optionViews = new ArrayList<>();
	
	/**Konstruktorn. Uppdaterar sig själv vid skapandet genom att anropa bankUpdateMetoden.
	 * Registrerar sig som en observer av modellen.
	 * @param bankController
	 * @param accountNumber som en int.
	 * @param personalNumber somen String.
	 */
	public AccountView(BankControllerInterface bankController, int accountNumber, String personalNumber) 
	{
		this.accountNumberInt = accountNumber;
		this.personalNumberStr = personalNumber;
		this.bankController = bankController;
		createView();
		this.updateBank(true);
		bankController.registerObserver(this);
	}
	/**Skapar viewn med dess kontroller, fält och labels. Lägger till en som bestämmer vad som händer om detta
	 * fönster stängs.
	 */
	public void createView() 
	{
		//En WindowListener skapas med en anonym class som implementerar windowClosing() och
	 	//windowClosed() metoderna som bestämmer vad som händer om fönstret stängs. 
	 	this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeOptionViews();
            }
            @Override
            public void windowClosed(WindowEvent e) {
            		closeOptionViews();
            }
        });
		createAccuntInformationLabels();
		createDeletButton();
		createMenu();
		createTable();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank kontofönster");
		setVisible(true);
	}
	
	/**Skapar fält och labels.
	 */
	private void createAccuntInformationLabels() 
	{		
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
	
	/**Skaper en ta bort konto knappen med actionlistener och ett lambda uttryck som beskriver vad som ska hända
	 * när knappen aktiveras.
	 */
	private void createDeletButton() 
	{
		deletButton = new JButton("Ta bort konto");
		deletButton.addActionListener(e -> {
				 int confirm = JOptionPane.showConfirmDialog(null, "Vill du ta bort " + accountType.getText() + " "  
				 + accountNumber.getText()); 
				 //Om svaret på dialogrutan är ja så avslutas ett konto annars händer inget.
				 if(confirm == JOptionPane.YES_OPTION) {
					 account = bankController.closeAccount(personalNumberStr, accountNumberInt);
					 //Om avslutandet är framgångsrikt så presenteras en dialogruta med information om det avslutade kontot
					 //och viewn stängs.
					 if(!account.equals(null)) {
						 String[] splitStrArray = account.split(" ");
						 JOptionPane.showMessageDialog(null,  splitStrArray[2] + splitStrArray[0] + " med saldot " +  splitStrArray[1]
								 + " och räntesatsen " +  splitStrArray[3] + " är borttagen " + ". Räntan blev " + splitStrArray[4]);
					 }
				 }
		});
	}
	
	/**Skapar en tabell som visar kontona som hör till kunden. 
	 */
	private void createTable() 
	{
		customerTable= new JTable();
		tableModel = new DefaultTableModel(0, 3);
		String header[] = new String[] { "Datum", "Summa", "Saldo efter transaktion"};
		tableModel.setColumnIdentifiers(header);
		 customerTable.setModel(tableModel);
		 customerTable.setCellSelectionEnabled(true);  
		 ListSelectionModel select = customerTable.getSelectionModel();  
		 select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	/**Skapar en meny med dess val och actionListeners för dessa lambda uttryck som beskriver vad som ska hända
	 * när menyvalet aktiveras.
	 */
	private void createMenu() 
	{
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu options = new JMenu("Hantera saldo");
		menuBar.add(options);
		JMenu file = new JMenu("Fil");
		menuBar.add(file);
		JMenuItem createItem = new JMenuItem("Uttag");
		createItem.addActionListener(e -> {
			//Skapar en WithDrawView och lägger till det till ArrayListan optionViews.
			WithDrawView optionView =new WithDrawView(bankController, accountNumber.getText(),  personalNumber.getText(), Double.parseDouble(creditLimit.getText()));
			this.optionViews.add(optionView);
		});

		JMenuItem deletItem = new JMenuItem("Insättning");
		deletItem.addActionListener(e -> {
			//Skapar en DepositView och lägger till det till ArrayListan optionViews.
			DepositView optionView = new DepositView(bankController, accountNumber.getText(), personalNumber.getText());
			this.optionViews.add(optionView);
			});
		
		JMenuItem saveTransactionsItem = new JMenuItem("Spara transaktioner");
		deletItem.addActionListener(e -> {
			//Ska implementeras i uppgift 4.
			});
		
		JMenuItem getTransactionsItem = new JMenuItem("Hämta transaktioner");
		deletItem.addActionListener(e -> {
			//Ska implementeras i uppgift 4.
			});
		options.add(createItem);
		options.add(deletItem);
		file.add(saveTransactionsItem);
		file.add(getTransactionsItem);
		
	}
	
	/**Skapar de paneler som ska läggs till denna frame. Använder sig av FlowLayout.
	 */
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

	/**Implementationen av udateBank() som ansvarar för uppdaterandet av viewn när modellen förändras. 
	 */
	@Override
	public void updateBank(Boolean bool) 
	{
		account = this.bankController.getAccount(this.personalNumberStr, this.accountNumberInt);
		//Om hämtningen av bankkonton lyckades hämtas de transaktioner som är gjorda och kund- samt
		//kontoinformations strängarna splitras upp och varje del läggs i en, för respektive informations-
		//sträng, String Array.
		if(account != null) 
		{
			ArrayList<String> transactions = bankController.getTransactions(this.personalNumberStr, this.accountNumberInt);
			customer = this.bankController.getCustomer(this.personalNumberStr);
			String[] customerInfo = customer.get(0).split(" ");
			String[] accountInfo = account.split(" ");
			//Om informationsfälten är tomma så fylls de på med information från modellen.
			if(personalNumber.getText().equals("")) 
			{	
				this.forname.setText(customerInfo[0]);
				this.surname.setText(customerInfo[1]);
				this.personalNumber.setText(customerInfo[2]);
				
				this.accountNumber.setText(accountInfo[0]);
				this.balance.setText(accountInfo[1]);
				this.accountType.setText(accountInfo[2]);
				this.intrest.setText(accountInfo[3]);
				this.creditLimit.setText(""+this.bankController.getCreditLimit(this.personalNumberStr, this.accountNumberInt));
			}
			//Om ett försök till förändring av modellen lyckades uppdateras saldot och tabellistan med 
			//transaktioner.
			if(bool) 
			{
				this.balance.setText(accountInfo[1]);
				//Tabellens rad antal sätt till 0. (Alla rader tas bort)
				tableModel.setNumRows(0);
				//Tabellen får nya rader som fylls med värden från den förändrade modellen.
				for(int i = 0; i < transactions.size(); i++) 
				{
					String[] transInfo = transactions.get(i).toString().split(" ");
					tableModel.addRow(new Object[] { transInfo[0], transInfo[2], transInfo[3]});
				} 
			} 
		} else dispose();
	}
	
	/**Stänger ner de optionViews som ett specefikt objekt har skapat.
	 */
	@Override
	public void closeOptionViews() 
	{	//Om objektet skapat optionViews objekt så stängs dessa ner.
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
}
