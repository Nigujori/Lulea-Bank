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
 * att sätta in pengar på ett konto. Den implementerar OptionsViews.
 * @author Johan Ringström användarnamn johrin7.*/

@SuppressWarnings("serial")
public class DepositView extends JFrame implements OptionView {
	private static final int FRAME_WIDTH = 550;
	private static final int FRAME_HIGHT = 100;
	
	private JLabel accountNumberLabel;
	private JTextField accountnumber;
	private JLabel depositLabel;
	private JTextField deposit;
	private JButton depositButton;
	private BankControllerInterface bankController;
	private String accountNumberStr;
	private String personalNumberStr;
	
	/**Konstruktor som initierar bankController, accountNumberStr och personalNumberStr variablerna.
	 * @param bankController
	 * @param accountNr
	 * @param personalNumber
	 */
	public DepositView(BankControllerInterface bankController, String accountNr, String personalNumber) 
	{
		this.bankController = bankController;
		this.accountNumberStr = accountNr;
		this.personalNumberStr = personalNumber;
		createView();
	}
	
	/**Skapar viewn med dess kontroller, fält och labels.
	 */
	public void createView() 
	{
		createTextFields();
		createDepositButton();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank Insättningsfönster");
		setVisible(true);
	}
	
	/**Skapar fälten och labes.
	 */
	public void createTextFields() 
	{		
		accountNumberLabel = new JLabel("  Kontonummer");
		accountnumber = new JTextField();
		accountnumber.setText(accountNumberStr);
		accountnumber.setEditable(false);
		depositLabel = new JLabel("  Summa insättning");
		deposit = new JTextField();
	}
	
	/**Skapar knappen vars aktionListener som rymmer koden för insättning av pengar i ett lambdauttryck. 
	 */
	 private void createDepositButton() 
	 {
		 depositButton = new JButton("Gör insättning");
		 depositButton.addActionListener(e -> 
		 {	
			 Boolean bool = false;
			 //Om deposit-textfältet är i ett format som går att tolka till en double görs en insättning,
			 //annars skapas ett felmeddelande
			 if(deposit.getText().matches(("[0-9]{1,13}(\\.[0-9]*)?"))) 
			 {
				 bool = bankController.deposit(personalNumberStr, Integer.parseInt(accountNumberStr),  Double.parseDouble(deposit.getText()));
			 } else if(!bool) JOptionPane.showMessageDialog(null, "Det gick inte att göra insättningen");
		});
	 }
	 
	 /**Skapar de paneler som ska läggs till denna frame. Använder sig av GridLayout.
		*/
	 public void createPane() 
		{
			JPanel panelGrid = new JPanel(new GridLayout(3, 1));
			panelGrid.add(accountNumberLabel);
			panelGrid.add(accountnumber);
			panelGrid.add(depositLabel);
			panelGrid.add(deposit);
			panelGrid.add(depositButton);
			add(panelGrid);
			this.setLocation(0, 575);
		}
}
