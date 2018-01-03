package johrin7.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import johrin7.Controller.BankControllerInterface;

/**Denna view-klass skapas av customerSearchAndDisplayView-klassen och är avsedd för att förändra modellen genom 
 * att lägga till kunder. Den implementerar OptionsViews.
 * @author Johan Ringström användarnamn johrin7.*/

@SuppressWarnings("serial")
public class CreateCustomerView extends JFrame implements OptionView{
	private static final int FRAME_WIDTH = 550;
	private static final int FRAME_HIGHT = 100;
	
	private JLabel fornameLabel;
	private JTextField forname;
	private JLabel surnameLabel;
	private JTextField surname;
	private JLabel personalNumberLabel;
	private JTextField personalnumber;
	private JButton createButton;
	private BankControllerInterface bankController;
	
	/**Konstruktorn initierar bankController variabeln och skapar viewn.
	 * @param bankController
	 */
	public CreateCustomerView(BankControllerInterface bankController) 
	{
		this.bankController = bankController;
		createView();
	}
	
	/**Skapar viewn med dess kontroller, fält och labels.
	 */
	public void createView() 
	{
		createTextFields();
		createCustomerButton();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank Create Customer");
		setVisible(true);
	}
	
	/**Skapar fälten och labes.
	 */
	public void createTextFields() 
	{		
		fornameLabel = new JLabel("  Förnamn");
		forname = new JTextField();
		surnameLabel = new JLabel("  Efternamn");
		surname = new JTextField();
		personalNumberLabel = new JLabel("  Personnummer");
		personalnumber = new JTextField();
	}
	
	/**Skapar knappen vars aktionListener som rymmer koden för skapandet av ett kunder i ett lambda uttryck. 
	 */
	 private void createCustomerButton() 
	 {
		 createButton = new JButton("Skapa kund");
		 createButton.addActionListener(e -> {
			bankController.createCustomer(forname.getText(), surname.getText(), personalnumber.getText());
		 });
	 }
	 
	 /**Skapar de paneler som ska läggs till denna frame. Använder sig av BorderLayout och GridLayout.
		*/
	 public void createPane() 
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
			this.setLocation(0, 475);
		}
}
