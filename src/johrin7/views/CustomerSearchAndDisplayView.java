package johrin7.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.GenericArrayType;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import johrin7.BankControllerInterface;
import johrin7.BankModelInterface;
import johrin7.BankObserver;

public class CustomerSearchAndDisplayView extends JFrame implements BankObserver, TableView{
	
	private static final int FRAME_WIDTH = 550;
	private static final int FRAME_HIGHT = 300;
	private static final String DEFAULT_SERARCH_WORD = "Sök efter kund";
	
	private JTextField searchField;
	private JButton searchButton;
	private String searchWord;
	private BankControllerInterface bankController;
	private BankModelInterface bankModel;
	private JTable customerTable;
	DefaultTableModel tableModel;
	private int rows = 0;
	private String personalNumber = "";
	ArrayList<OptionView> optionViews = new ArrayList<>();
	ArrayList<CustomerView > tableViews = new ArrayList<>();
	
	public CustomerSearchAndDisplayView(BankControllerInterface bankController, BankModelInterface bankModel) {
		this.bankController = bankController;
		this.bankModel = bankModel;
		bankModel.registerObserver(this);
		
	}
	
	public void createView() {
		searchWord = DEFAULT_SERARCH_WORD;
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
		searchButton.addActionListener(e -> {;
			});
	}
	
	private void createTable() {
		customerTable= new JTable();
		tableModel = new DefaultTableModel(0, 3);
		String header[] = new String[] { "Förnamn", "Efternamn", "personnummer"};
		tableModel.setColumnIdentifiers(header);
		 customerTable.setModel(tableModel);
		 customerTable.setCellSelectionEnabled(true);  
		 ListSelectionModel select= customerTable.getSelectionModel();  
		 select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 select.addListSelectionListener(e -> { 
			 if(!e.getValueIsAdjusting()) {
				 if(customerTable.getSelectedRow() != -1) {
					personalNumber =	 (String)customerTable.getValueAt(customerTable.getSelectedRow(), 2);
				 	CustomerView customerView = new CustomerView(bankController, bankModel, personalNumber);
				 	tableViews.add(customerView);
				 	customerView.addWindowListener(new WindowAdapter() {
			            @Override
			            public void windowClosing(WindowEvent e) {
			                customerView.closeOptionViews();
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
		JMenuItem createItem = new JMenuItem("Create customer");
		createItem.addActionListener(e -> {
			new CreateCustomerView(bankController);
		});

		JMenuItem deletItem = new JMenuItem("Delete customer");
		deletItem.addActionListener(e -> new DeleteCustomerView(bankController, this));
		options.add(createItem);
		options.add(deletItem);
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
		scrollPane.setPreferredSize(new Dimension(500, 200));
		scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(scrollPane, BorderLayout.WEST);
		panelMain.add(panel);
		add(panelMain);	
	}

	@Override
	public void updateBank(Boolean bool) {
			if(bool) {
			tableModel.setNumRows(0);
			ArrayList<String> customerList= this.bankModel.getAllCustomers();
			for(String customerStr : customerList) {
				String[] splitStr = customerStr.split(" ");
				tableModel.addRow(new Object[] { splitStr[0], splitStr[1], splitStr[2]});
			}
		} 
	}
	
	public void closeTableView(String personalNumber) 
	{
		if(this.tableViews.size() != 0) 
		{
			for(CustomerView cs : this.tableViews) 
			{
				if(cs.getPersonalNumber().equals(personalNumber))
				{
					cs.dispose();
				} 
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
	
	public void unRegisterObserver(String personalNumber) {
		{
			if(this.tableViews.size() != 0) 
			{
				for(CustomerView cs : this.tableViews) 
				{
					if(cs.getPersonalNumber().equals(personalNumber))
					{
						bankModel.unRegisterObserver(cs);;
					} 
				}
			}
		}	
	}
}
