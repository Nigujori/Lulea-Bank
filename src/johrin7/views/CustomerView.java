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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import johrin7.Controller.BankControllerInterface;

/**Den view visar information om kunden och dess konton. Den implementerar BankObserver 
 * och måste därför implementera updateBank() metoden som updaterar denna view om förändringar görs eller
 * försöker göras i modellen. Håller en reference till BankController som är länken till modellen.
 * Implementerar TableView interfacet och dess closeOptionView() metod som ser till att alla optionViews
 * som skapats av ett specifikt CustomeryViewinstans-objekt, vilken den sparar i 
 * ArrayListan optionViews, stängs om detta view-object blir inaktuellt. 
 * @author Johan Ringström användarnamn johrin7.*/

@SuppressWarnings("serial")
public class CustomerView extends JFrame implements BankObserver, TableView
{
	
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
	private JTable customerTable;
	private DefaultTableModel tableModel;
	private String personalNumberStr;
	private String accountNumberStr;
	ArrayList<OptionView> optionViews = new ArrayList<>();
	ArrayList<AccountView > tableViews = new ArrayList<>();
	AccountView accountView;
	
	/**Initierar bankController- och personalNumberStr-variablerna  och registrerar sig som en observer av modellen.
	 * Updaterar tabellen när ett objekt skapas.
	 * @param bankController
	 * @param personalnumber som en String. 
	 */
	public CustomerView(BankControllerInterface bankController, String personalnumber) 
	{
		this.personalNumberStr = personalnumber;
		this.bankController = bankController;
		createView();
		this.updateBank(true);
		bankController.registerObserver(this);
	
	}
	
	/**Skapar viewn med dess fält, kontroller och paneler. Samt en WindowListener.
	 */
	public void createView() 
	{
		//En WindowListener skapas med en anonym class som implementerar windowClosing() och
	 	//windowClosed() metoderna som bestämmer vad som händer om fönstret stängs. I detta fall
		//stängs de av detta objekt skapade AvvountViews och optionsViews ner.
		this.addWindowListener(new WindowAdapter() 
		{
            @Override
            public void windowClosed(WindowEvent e) 
            {
            		closeOptionViews();
            		closeTableViews(); 
            }
            @Override
            public void windowClosing(WindowEvent e) 
            {
            		closeOptionViews();
            		closeTableViews();
            }
        });
		createTextFields();
		createChangeButton();
		createTable();
		createMenu();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank kundfönster");
		setVisible(true);
	}
	
	/**Skaper fält och labels.
	 */
	private void createTextFields() 
	{		
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
	
	/**Skapar knappen med vilken ddu kan ändra kundinformationen och dess actionListener med dess 
	 * tillhörande lambdauttryck. 
	 */
	 private void createChangeButton() 
	 {
		 changeCustomerButton = new JButton("Ändra kund information");
		 changeCustomerButton.setPreferredSize(new Dimension(450, 25));
		 changeCustomerButton.addActionListener(e -> {
			 //Förändrar kundinformationen enligt det som skrivits in i namnfälten.
			 bankController.changeCustomerName(forname.getText(),
				 surname.getText(), personalnumber.getText());
		});
	 }
	
	 /**Skapar tabellen som visar bankens konton. Har en ListSelectionListner med tillhörande lambda uttryck.
	  Tabellen sätts även, med hjälp av ListSelectionModel.SINGLE_SELECTION,till att man endast kan välja en rad.
	 */
	private void createTable() 
	{
		customerTable= new JTable();
		tableModel = new DefaultTableModel(0, 4);
		String header[] = new String[] { "Kontonummer", "Saldo", "Kontotyp", "Räntesats"};
		tableModel.setColumnIdentifiers(header);
		 customerTable.setModel(tableModel);
		 customerTable.setCellSelectionEnabled(true);  
		 ListSelectionModel select= customerTable.getSelectionModel();  
		 select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 select.addListSelectionListener(e -> {
			//ListSelectionListene r lyssnar efter flera olika typer events som händer på tabellen.
			 //Om det är den sista eventet(Säkerställer så att lyssnaren endast reagerar en gång vid 
			 //ett kick på en rad.)
			 if(!e.getValueIsAdjusting()) {
				//Om den valda raden finns hämtas informationen i radens första kolumn, d.v.s. kontonumret
				 //och en AccountView skapas, som läggs till tablesViews. 
				 if(customerTable.getSelectedRow() != -1) {
					accountNumberStr = (String)customerTable.getValueAt(customerTable.getSelectedRow(), 0);
					AccountView accountView = new AccountView(bankController, Integer.parseInt(accountNumberStr), this.personalNumberStr);
					tableViews.add(accountView);
				 }
			 	}
			});
	}
	
	/**En meny med tillhörande menyval skapas med tillhörande actionListiners och lambdauttryck.
	 */
	private void createMenu() 
	{
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu options = new JMenu("Hantera konton");
		menuBar.add(options);
		JMenuItem createItem = new JMenuItem("Skapa bankkonto");
		createItem.addActionListener(e -> {
			//Skapar CreateAccountView och sätter dess personnummerfält till det aktuella personumret.
			//Lägger till detta objekt till optionViews ArrayListan
			CreateAccountView createAccountView = new CreateAccountView(bankController);
			createAccountView.setPersonalNumber(personalNumberStr);
			this.optionViews.add(createAccountView);
		});

		JMenuItem deletItem = new JMenuItem("Radera bankkonto");
		deletItem.addActionListener(e -> {
			//Skapar DeleteAccountView och sätter dess personnummerfält till det aktuella personumret.
			//Lägger till detta objekt till optionViews ArrayListan
			DeleteAccountView deleteAccountView = new DeleteAccountView(bankController);
			deleteAccountView.setPersonalNumber(personalNumberStr);
			this.optionViews.add(deleteAccountView);
			});
		options.add(createItem);
		options.add(deletItem);
	}
	
	/**Skapar de paneler som ska läggs till denna frame. Använder sig av FlowLayout.
	 */
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
		this.setLocation(FRAME_WIDTH + 3, 0);
	}

	/**Implementationen av udateBank() som ansvarar för uppdaterandet av viewn när modellen förändras. 
	 */
	@Override
	public void updateBank(Boolean bool) 
	{
		ArrayList<String> customer = this.bankController.getCustomer(this.personalNumberStr);
		//Om det gick att hämta kunden ifråga, annars stängs det aktuella fönstret ner. 
		if(customer != null) 
		{	//Om informationsfälten är tomma så fylls de på med information från modellen.
			if(personalnumber.getText().equals("")) {
				String[] customerInfo = customer.get(0).split(" ");
				this.forname.setText(customerInfo[0]);
				this.surname.setText(customerInfo[1]);
				this.personalnumber.setText(customerInfo[2]);
			}
			//Om modellen har förändras sätts radantalet till 0(Alla rader tas bort) och nya rader skapas
			//av den information som getAllCustomers ger. Om en ny kund läggs till kommer tabellen med andra ord
			//updateras automatiskt.
			tableModel.setNumRows(0);
			for(int i = 1; i < customer.size(); i++) {
				String[] splitStr = customer.get(i).split(" ");
				tableModel.addRow(new Object[] { splitStr[0], splitStr[1], splitStr[2], splitStr[3]});
			} 
		} else dispose();	
	}

	/**Stänger ner de optionViews som ett specefikt objekt har skapat.
	 */
	@Override
	public void closeOptionViews() 
	{
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
	
	/**Stänger ner de tabelViews som ett specefikt objekt har skapat.
	 */
	public void closeTableViews() 
	{
		if(this.optionViews.size() != 0) {
			for(AccountView tv : this.tableViews) 
			{
				tv.dispose();
			}
		}
	}
}

