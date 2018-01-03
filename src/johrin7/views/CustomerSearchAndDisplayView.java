package johrin7.views;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import johrin7.Controller.BankControllerInterface;

/**Detta är den view som visas först när applicationen startas. Den visar bankens kunder samt en ett sökfält
 * där man kan söka efter kunder efter på efternamn och personnummer. Den implementerar BankObserver 
 * och måste därför implementera updateBank() metoden som updaterar denna view om förändringar görs eller
 * försöker göras i modellen. Håller en reference till BankController som är länken till modellen.
 * Implementerar TableView interfacet och dess closeOptionView() metod som ser till att alla optionViews
 * som skapats av ett specifikt CustomerSearchAndDisplayViewinstans-objekt, vilken den sparar i 
 * ArrayListan optionViews, stängs om detta view-object blir inaktuellt. 
 * @author Johan Ringström användarnamn johrin7.*/

@SuppressWarnings("serial")
public class CustomerSearchAndDisplayView extends JFrame implements BankObserver, TableView{
	
	private static final int FRAME_WIDTH = 550;
	private static final int FRAME_HIGHT = 450;
	private static final String DEFAULT_SERARCH_WORD = "Sök efter kund";
	
	private JTextField searchField;
	private JButton searchButton;
	private BankControllerInterface bankController;
	private JTable customerTable;
	DefaultTableModel tableModel;
	private String personalNumber = "";
	TableRowSorter<TableModel> rowFinder;
	ArrayList<OptionView> optionViews = new ArrayList<>();
	ArrayList<CustomerView > tableViews = new ArrayList<>();
	
	/**Initierar bankController variabeln och registrerar sig som en observer av modellen.
	 * @param bankController
	 */
	public CustomerSearchAndDisplayView(BankControllerInterface bankController)
	{
		this.bankController = bankController;
		bankController.registerObserver(this);
		createView();
		this.updateBank(true);
		
	}
	
	/**Skapar viewn med dess fält, kontroller och paneler.
	 */
	public void createView() 
	{
		createTable();
		createTextField();
		searchButton();
		createMenu();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank Kundsök");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**Skaper sökfältet.
	 */
	private void createTextField() {
		searchField = new JTextField();
		searchField.setText(DEFAULT_SERARCH_WORD);
	}
	
	/**Skapar sökknappen med actionListener och tillhörande lambda uttryck.
	 */
	private void searchButton() {
		searchButton = new JButton("Sök");
		searchButton.addActionListener(e -> { 
		String searchText = searchField.getText();
		//Om det inte står något i sökfältet sätts rowFinder variabeln till null, d.v.s inget sökfilter skapas,
		//annars om sökfiltret består av siffror söker man på värdet i radernas tredje kolumn annars söker man i
		//radernas andra kolumn. Tabellen uppdateras med de rader som matchar med sök termerna. Är känslig för 
		//versaler och gemener.
        if (searchText.length() == 0) {
            rowFinder.setRowFilter(null);
          } else if(searchText.matches(("[0-9]{1,13}(\\.[0-9]*)?")))
          {
            rowFinder.setRowFilter(RowFilter.regexFilter(searchText, 2));

          } else rowFinder.setRowFilter(RowFilter.regexFilter(searchText, 1));
		});
	}
	
	/**Skapar tabellen som visar bankens kunder. Har en ListSelectionListner med tillhörande lambda uttryck.
	 * Skapar även en TableRowSorter som sedan ges till tabellen för att den ska bli sökbar. Tabellen
	 * sätts även, med hjälp av ListSelectionModel.SINGLE_SELECTION, till att endast kan välja en rad.
	 */
	private void createTable() {
		customerTable= new JTable();
		tableModel = new DefaultTableModel(0, 3);
		String header[] = new String[] { "Förnamn", "Efternamn", "personnummer"};
		tableModel.setColumnIdentifiers(header);
		 customerTable.setModel(tableModel);
		 customerTable.setCellSelectionEnabled(true);  
		 rowFinder = new TableRowSorter<>(customerTable.getModel());
		 customerTable.setRowSorter(rowFinder);
		 ListSelectionModel select= customerTable.getSelectionModel();  
		 select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 select.addListSelectionListener(e -> { 
			 //ListSelectionListene r lyssnar efter flera olika typer events som händer på tabellen.
			 //Om det är den sista eventet(Säkerställer så att lyssnaren endast reagerar en gång vid 
			 //ett kick på en rad.)
			 if(!e.getValueIsAdjusting()) {
				 //Om den valda raden finns hämtas informationen i radens tredje kolumn, d.v.s. personnumret
				 //och en CustomerView skapas, som läggs till tablesViews. 
				 if(customerTable.getSelectedRow() != -1) {
					personalNumber =	 (String)customerTable.getValueAt(customerTable.getSelectedRow(), 2);
				 	CustomerView customerView = new CustomerView(bankController, personalNumber);
				 	tableViews.add(customerView);
				 	customerTable.clearSelection();
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
		JMenu options = new JMenu("Hantera kunder");
		menuBar.add(options);
		JMenu file = new JMenu("Fil");
		menuBar.add(file);
		JMenu help = new JMenu("Hjälp");
		menuBar.add(help);
		
		JMenuItem createItem = new JMenuItem("Skapa kund");
		createItem.addActionListener(e -> {
			//Skapar en CreateCustomerView.
			new CreateCustomerView(bankController);
		});

		JMenuItem deletItem = new JMenuItem("Radera kund");
		deletItem.addActionListener(e -> {
			//Skapar en DeleteCustomerView.
			new DeleteCustomerView(bankController);
			});
		
		JMenuItem saveAllCustomerItem = new JMenuItem("Spara kunder till fil");
		saveAllCustomerItem.addActionListener(e -> {
			//Implementeras i uppgift 4.
		});
		
		JMenuItem getAllCustomersItem = new JMenuItem("Hämta kunder från");
		getAllCustomersItem.addActionListener(e -> {
			//Implementeras i uppgift 4.
		});
		/**Öppnar en rtf fil med instruktioner för hur banksystemet fungerar.
		 */
		JMenuItem helpItem = new JMenuItem("Hjälp");
		helpItem.addActionListener(e -> {
			if (Desktop.isDesktopSupported()) {
			    try {
			        File myFile = new File("src/johrin7/Hjalp.rtf");
			        Desktop.getDesktop().open(myFile);
			    } catch (IOException ex) {}
			}
		});
		
		options.add(createItem);
		options.add(deletItem);
		file.add(saveAllCustomerItem);
		file.add(getAllCustomersItem);
		help.add(helpItem);
	}
	
	/**Skapar de paneler som ska läggs till denna frame. Använder sig av FlowLayout och BorderLayout.
	 */
	private void createPane() 
	{
		JPanel panelMain = new JPanel();
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelNorth = new JPanel(new BorderLayout());
		panelNorth.add(searchField, BorderLayout.CENTER);
		panelNorth.add(searchButton, BorderLayout.EAST);
		panel.add(panelNorth, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(customerTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//scrollPane.setPreferredSize(new Dimension(500, 350));
		scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(scrollPane, BorderLayout.WEST);
		panelMain.add(panel);
		add(panelMain);	
	}

	/**Implementationen av udateBank() som ansvarar för uppdaterandet av viewn när modellen förändras. 
	 */
	@Override
	public void updateBank(Boolean bool) 
	{ 	//Om modellen har förändras sätts radantalet till 0(Alla rader tas bort) och nya rader skapas
		//av den information som getAllCustomers ger. Om en ny kund läggs till kommer tabellen med andra ord
		//updateras automatiskt.
		if(bool) 
		{
			tableModel.setNumRows(0);
			ArrayList<String> customerList= this.bankController.getAllCustomers();
			for(String customerStr : customerList) {
				String[] splitStr = customerStr.split(" ");
				tableModel.addRow(new Object[] { splitStr[0], splitStr[1], splitStr[2]});
			}
		} else 
			{
			 JOptionPane.showMessageDialog(null, "Kontrollera att informationen i fälten är korrekt ifyllda");
			}
	}

	/**Stänger ner de optionViews som ett specefikt objekt har skapat.
	 */
	@Override
	public void closeOptionViews() 
	{	//Om objektet skapat optionViews-objekt så stängs dessa ner.
		if(this.optionViews.size() != 0) {
			for(OptionView op : this.optionViews) 
			{
				if(op instanceof CreateCustomerView) 
				{
					((CreateCustomerView) op).dispose();
				} 
				if(op instanceof DeleteCustomerView) 
				{
					((DeleteCustomerView) op).dispose();
				}
			}
		}
	}
}
