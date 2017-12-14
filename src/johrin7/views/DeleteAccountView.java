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
 * att ta bort till konton. Den implementerar OptionsViews.
 * @author Johan Ringström användarnamn johrin7.*/

@SuppressWarnings("serial")
public class DeleteAccountView extends JFrame implements OptionView {
	private static final int FRAME_WIDTH = 550;
	private static final int FRAME_HIGHT = 100;
	
	private JLabel personalNumberLabel;
	private JTextField personalnumber;
	private JLabel accountNumberLabel;
	private JTextField accountnumber;
	private JButton deleteButton;
	private BankControllerInterface bankController;
	private String account = null;
	
	/**Konstruktorn initierar bankController variabeln och skaper viewn.
	 * @param bankController
	 */
	public DeleteAccountView(BankControllerInterface bankController) 
	{
		this.bankController = bankController;
		createView();
	}
	
	/**Skapar viewn med dess kontroller, fält och labels.
	 */
	public void createView() 
	{
		createTextFields();
		createDeleteButton();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank Delete account");
		setVisible(true);
	}
	
	/**Skapar fälten och labes.
	 */
	public void createTextFields() 
	{		
		personalNumberLabel = new JLabel("  Personnummer");
		personalnumber = new JTextField();
		personalnumber.setEditable(false);
		accountNumberLabel = new JLabel("  Kontonummer");
		accountnumber = new JTextField();
	}
	
	/**Skapar knappen vars aktionListener som rymmer koden för bort tagandet av ett konto i ett lambdauttryck. 
	 */
	 private void createDeleteButton() 
	 {
		 deleteButton = new JButton("Ta bort konto");
		 deleteButton.addActionListener(e -> {
			 			int accountInt;
			 			//Om kontonummerfältet inte är siffror eller är tomt sätts variabeln accountInt till 1. 
			 			//Vilket kommer att generara en dialogruta med ett felmeddelande när updatemetoden anropas.
			 			// Annars tas texten i accountnumber-textfältet och görs till en int och läggs i samma variabel.
			 			if(accountnumber.getText().equals(null) || !accountnumber.getText().matches(("[0-9]+"))) 
			 			{
			 				accountInt = 1;
			 			} else accountInt =Integer.parseInt(accountnumber.getText());
					 	
			 			account = bankController.closeAccount(personalnumber.getText(), accountInt);
			 			//Om ett konto togs bort genereras ett meddelande med information om det borttagna kontot.
					 	if(account != null) 
							 {
								 String[] splitStrArray = account.split(" ");
								 JOptionPane.showMessageDialog(null,  splitStrArray[2] + splitStrArray[0] + " med saldot " +  splitStrArray[1]
								 + " och räntesatsen " +  splitStrArray[3] + " är borttagen " + ". Räntan blev " + splitStrArray[4]);
							 } 
						});
					 }
	 
	 /**Skapar de paneler som ska läggs till denna frame. Använder sig av BorderLayout och GridLayout.
		*/
	 public void createPane() 
	{
		JPanel panelGrid = new JPanel(new GridLayout(3, 1));
		panelGrid.add(personalNumberLabel);
		panelGrid.add(personalnumber);
		panelGrid.add(accountNumberLabel);
		panelGrid.add(accountnumber);
		panelGrid.add(deleteButton);
		add(panelGrid);
		this.setLocation(FRAME_WIDTH + 3, 475);
	}
	 
	 /**Sätter värdet i personalnumber-textfältet.
	  * @param personalnumber som en String.
	  */
	 public void setPersonalNumber(String personalnumber) 
	 {
		 this.personalnumber.setText(personalnumber);
	 }
}
