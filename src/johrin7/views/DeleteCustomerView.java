package johrin7.views;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import johrin7.Controller.BankControllerInterface;

/**Denna view-klass skapas av CustomerSearchAndDisplayView-klassen och är avsedd för att förändra modellen genom 
 * att ta bort till kunder. Den implementerar OptionsViews.
 * @author Johan Ringström användarnamn johrin7.*/

@SuppressWarnings("serial")
public class DeleteCustomerView extends JFrame implements OptionView 
{
	private static final int FRAME_WIDTH = 550;
	private static final int FRAME_HIGHT = 100;
	
	private JLabel personalNumberLabel;
	private JTextField personalnumber;
	private JButton deleteButton;
	private BankControllerInterface bankController;
	CustomerSearchAndDisplayView customerSearchAndDisplayView;
	
	/**Konstruktorn initierar bankController variabeln och skaper viewn.
	 * @param bankController
	 */
	public DeleteCustomerView(BankControllerInterface bankController) 
	{
		this.bankController = bankController;
		createView();
	}
	
	/**Skapar viewn med dess kontroller, fält och labels.
	 */
	public void createView() {
		createTextFields();
		createDeleteButton();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank Delete Customer");
		setVisible(true);
	}
	
	/**Skapar fälten och labes.
	 */
	public void createTextFields() 
	{		
		personalNumberLabel = new JLabel("  Personnummer");
		personalnumber = new JTextField();
	}
	
	/**Skapar knappen vars aktionListener som rymmer koden för bort tagandet av en kund i ett lambdauttryck. 
	 */
	 private void createDeleteButton() 
	 {
		 deleteButton = new JButton("Ta bort kund");
		 deleteButton.addActionListener(e -> {
			 ArrayList<String> customer = bankController.deleteCustomer(personalnumber.getText());
			 //Om kunden togs bort genereras ett meddelande med information om kunden
			 if(customer != null) 
			 { 	
				 String customerInfo = customer.remove(0);
				 JOptionPane.showMessageDialog(null, "Kunden " + customerInfo + " är borttagen"
			 +" med följande konto/konton:\n" + this.presentAccount(customer));
			 } 
		});
	 }
	 
	 /**Skapar de paneler som ska läggs till denna frame. Använder sig av GridLayout.
		*/
	 public void createPane() 
		{
			JPanel panelGrid = new JPanel(new GridLayout(3, 1));
			panelGrid.add(personalNumberLabel);
			panelGrid.add(personalnumber);
			panelGrid.add(deleteButton);
			add(panelGrid);
			this.setLocation(0, 475);
		}
		
	 /**En hjälpmetod för att skapa en presentation av konton.
	  * @param accountList
	  * @return
	  */
	 private String presentAccount(ArrayList<String> accountList) 
	 {
		 String accountStr = "";
		 //Skapar en sträng av en ArrayList med konton.
		 for(String account : accountList)
		 {
			 accountStr += account + "\n";
		 }
		 return accountStr;
	 }
}
