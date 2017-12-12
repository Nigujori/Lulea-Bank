package johrin7.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
import johrin7.Model.BankObserver;

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
	
	public CustomerSearchAndDisplayView(BankControllerInterface bankController) {
		this.bankController = bankController;
		bankController.registerObserver(this);
		
	}
	
	public void createView() {
		createTable();
		createTextField();
		createButton();
		createMenu();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank Kundsök");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void createTextField() {
		searchField = new JTextField();
		searchField.setText(DEFAULT_SERARCH_WORD);
	}
	
	private void createButton() {
		searchButton = new JButton("Search");
		searchButton.addActionListener(e -> { String searchText = searchField.getText();
        if (searchText.length() == 0) {
            rowFinder.setRowFilter(null);
          } else if(searchText.matches(("[0-9]{1,13}(\\.[0-9]*)?")))
          {
            rowFinder.setRowFilter(RowFilter.regexFilter(searchText, 2));

          } else rowFinder.setRowFilter(RowFilter.regexFilter(searchText, 1));
		});
	}
	
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
			 if(!e.getValueIsAdjusting()) {
				 if(customerTable.getSelectedRow() != -1) {
					personalNumber =	 (String)customerTable.getValueAt(customerTable.getSelectedRow(), 2);
				 	CustomerView customerView = new CustomerView(bankController, personalNumber);
				 	tableViews.add(customerView);
				 	customerView.addWindowListener(new WindowAdapter() {
			            @Override
			            public void windowClosing(WindowEvent e) {
			                customerView.closeOptionViews();
			            }
			            @Override
			            public void windowClosed(WindowEvent e) {
			                customerView.dispose();
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
		JMenu file = new JMenu("File");
		menuBar.add(file);
		JMenuItem createItem = new JMenuItem("Create customer");
		createItem.addActionListener(e -> {
			new CreateCustomerView(bankController);
		});

		JMenuItem deletItem = new JMenuItem("Delete customer");
		deletItem.addActionListener(e -> new DeleteCustomerView(bankController));
		
		JMenuItem saveAllCustomerItem = new JMenuItem("Spara kunder till fil");
		createItem.addActionListener(e -> {
		});
		
		JMenuItem getAllCustomersItem = new JMenuItem("Hämta kunder från");
		createItem.addActionListener(e -> {
		});
		options.add(createItem);
		options.add(deletItem);
		file.add(saveAllCustomerItem);
		file.add(getAllCustomersItem);
	}
	
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
		scrollPane.setPreferredSize(new Dimension(500, 350));
		scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(scrollPane, BorderLayout.WEST);
		panelMain.add(panel);
		add(panelMain);	
	}

	@Override
	public void updateBank(Boolean bool) {
			if(bool) {
			tableModel.setNumRows(0);
			ArrayList<String> customerList= this.bankController.getAllCustomers();
			for(String customerStr : customerList) {
				String[] splitStr = customerStr.split(" ");
				tableModel.addRow(new Object[] { splitStr[0], splitStr[1], splitStr[2]});
			}
		} 
	}

	@Override
	public void closeOptionViews() {
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
