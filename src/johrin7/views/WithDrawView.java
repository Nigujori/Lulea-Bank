package johrin7.views;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import johrin7.Controller.BankControllerInterface;

/**Denna view-klass skapas av AccountView-klassen och är avsedd för att förändra modellen genom 
 * att ta ut pengar på ett konto. Den implementerar OptionsViews.
 * @author Johan Ringström användarnamn johrin7.*/

@SuppressWarnings("serial")
public class WithDrawView extends JFrame implements OptionView 
{
	private static final int FRAME_WIDTH = 550;
	private static final int FRAME_HIGHT = 100;
	
	private JLabel accountNumberLabel;
	private JTextField accountnumber;
	private JLabel withDrawLabel;
	private JTextField withDraw;
	private JButton withDrawButton;
	private BankControllerInterface bankController;
	private String accountNumberStr;
	private String personalNumberStr;
	private double creditLimit;
	
	/**Konstruktor som initierar bankController, accountNumberStr, creditLimit och personalNumberStr variablerna.
	 * @param bankController
	 * @param accountNr
	 * @param personalNumber
	 */
	public WithDrawView(BankControllerInterface bankController, String accountNr, String personalNumber, double creditLimit) 
	{
		this.bankController = bankController;
		this.accountNumberStr = accountNr;
		this.personalNumberStr = personalNumber;
		this.creditLimit = creditLimit;
		createView();
	}

	/**Skapar viewn med dess kontroller, fält och labels.
	 */
	public void createView() 
	{
		createTextFields();
		createWithDrawButton();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank uttagsfönster");
		setVisible(true);
	}
	
	/**Skapar fälten och labes.
	 */
	public void createTextFields() 
	{		
		accountNumberLabel = new JLabel("  Kontonummer");
		accountnumber = new JTextField();
		accountnumber.setEditable(false);
		withDrawLabel = new JLabel("  Summa uttag");
		withDraw = new JTextField();
	}
	
	/**Skapar knappen vars aktionListener som rymmer koden för uttag av pengar i ett lambdauttryck. 
	 */
	 private void createWithDrawButton() 
	 {
		 withDrawButton = new JButton("Gör uttag");
		 withDrawButton.addActionListener(e -> {	
		 Boolean bool = false;
		//Om deposit-textfältet är i ett format som går att tolka till en double görs ett uttag,
		 //annars skapas ett felmeddelande
		 if(withDraw.getText().matches(("[0-9]{1,13}(\\.[0-9]*)?"))) 
		 {
				 bool = bankController.withdraw(personalNumberStr, Integer.parseInt(accountNumberStr),  Double.parseDouble(withDraw.getText()));
				//Om det inte gick göra ett uttag.
				 if(!bool) 
				{
					JOptionPane.showMessageDialog(null, "Kreditgränsen för detta konto är " + this.creditLimit + "kr");
				}
		} else if(!bool) JOptionPane.showMessageDialog(null, "Det gick inte att göra ett uttag");
		 });
	 }
	 
	 /**Skapar de paneler som ska läggs till denna frame. Använder sig av GridLayout.
		*/
	 public void createPane() 
	{
		JPanel panelGrid = new JPanel(new GridLayout(3, 1));
		panelGrid.add(accountNumberLabel);
		panelGrid.add(accountnumber);
		accountnumber.setText(accountNumberStr);
		panelGrid.add(withDrawLabel);
		panelGrid.add(withDraw);
		panelGrid.add(withDrawButton);
		add(panelGrid);
		this.setLocation(0, 575);
	}
}
