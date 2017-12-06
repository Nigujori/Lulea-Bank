package johrin7.views;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import johrin7.BankControllerInterface;
import johrin7.BankLogic;
import johrin7.BankModelInterface;

public class CustomerSearchAndDisplayView extends JFrame {
	
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HIGHT = 250;
	private static final String DEFAULT_SERARCH_WORD = "Sök efter kund (Personnummer eller namn)";
	private static final int AREA_ROWS = 10;
	private static final int AREA_COLUMNS = 30;
	
	private JLabel searchLabel;
	private JTextField searchField;
	private JButton searchButton;
	private JTextArea resultArea;
	private String searchWord;
	private BankControllerInterface bankController;
	private BankModelInterface bankModel;
	ArrayList<String> customers;
	
	public CustomerSearchAndDisplayView(BankControllerInterface bankController, BankModelInterface bankModel) {
		this.bankController = bankController;
		this.bankModel = bankModel;
		searchWord = DEFAULT_SERARCH_WORD;
		resultArea = new JTextArea(AREA_ROWS, AREA_COLUMNS);
		resultArea.setText("test");
		resultArea.setEditable(false);
		createTextField();
		createButton();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
	}
	
	private void createTextField() {
		searchLabel = new JLabel("Search : ");
		final int FIELD_WIDTH = 10;
		searchField = new JTextField(FIELD_WIDTH);
		searchField.setText(DEFAULT_SERARCH_WORD);
	}
	
	private void createButton() {
		searchButton = new JButton("Search");
		searchButton.addActionListener(e -> {
			resultArea.setText("");
			bankModel.getAllCustomers().forEach(consumer -> resultArea.append(consumer + "\n"));
			});
	}
	
	private void createPane() {
		JPanel panel = new JPanel();
		panel.add(searchLabel);
		panel.add(searchField);
		panel.add(searchButton);
		panel.add(searchButton);
		JScrollPane scrollPane = new JScrollPane(resultArea);
		panel.add(scrollPane);
		add(panel);
		
	}
	
	public void update(ArrayList<String> customers) {
		this.customers = customers;
		String allCustomersStr = "";
		for(String customerStr : this.customers) {
			allCustomersStr += customerStr + "\n";
		}
		resultArea.setText(allCustomersStr);
	}
	
	
}
