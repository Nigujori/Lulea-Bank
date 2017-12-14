package johrin7.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import johrin7.Controller.BankControllerInterface;
import johrin7.Model.BankLogic.TypeOfAccount;

/**Denna view-klass skapas av customerView-klassen och är avsedd för att förändra modellen genom 
 * att lägga till konton. Den implementerar OptionsViews.
 * @author Johan Ringström användarnamn johrin7.*/

@SuppressWarnings("serial")
public class CreateAccountView extends JFrame implements OptionView {
	private static final int FRAME_WIDTH = 550;
	private static final int FRAME_HIGHT = 100;
	
	private JComboBox<TypeOfAccount> comboBoxAccountTypes;
	private JLabel personalNumberLabel;
	private JTextField personalnumber;
	private JButton createButton;
	private BankControllerInterface bankController;
	private String accountTypeStr;
	
	/**Konstruktorn initierar bankController variabeln.
	 * @param bankController
	 */
	public CreateAccountView(BankControllerInterface bankController) {
		this.bankController = bankController;
		createView();
	}
	
	/**Skapar viewn med dess kontroller, fält och labels.
	 */
	public void createView() {
		createTextFields();
		createAccountButton();
		createPane();
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		setTitle("ToraUma Bank Create Account");
		setVisible(true);
	}
	
	/**Skapar fälten, comboboxen och labels och actionListenern, med hjälp av ett lambda uttryck, till comboboxen.
	 */
	public void createTextFields() {		
		comboBoxAccountTypes = new JComboBox<>(TypeOfAccount.values());
		comboBoxAccountTypes.addActionListener(e -> accountTypeStr = comboBoxAccountTypes.getSelectedItem().toString());
		personalNumberLabel = new JLabel("  Personnummer");
		personalnumber = new JTextField();
		personalnumber.setEditable(false);
	}
	
	/**Skapar knappen vars aktionListener som rymmer koden för skapandet av ett konto i ett lambda uttryck. 
	 */
	 private void createAccountButton() {
		 createButton = new JButton("Skapa konto");
		 createButton.addActionListener(e -> {
			 //Om en kontotyp är vald skapas ett nytt konto av den valda typen annars poppar en dialogruta upp
			 //som påtalar att en typ måste väljas.
			 if( accountTypeStr != null) {
				 TypeOfAccount accountType = TypeOfAccount.valueOf(accountTypeStr.toUpperCase());
					 bankController.createAccount(personalnumber.getText(),  accountType);
				 } else 
					 {
						 JOptionPane.showMessageDialog(null, "Var snäll och välj en kontotyp");
					 }
		});
	 }
	 
	 /**Skapar de paneler som ska läggs till denna frame. Använder sig av BorderLayout och GridLayout.
		 */
	 public void createPane() 
		{
			JPanel panelMain = new JPanel(new BorderLayout());
			JPanel panelGrid = new JPanel(new GridLayout(1, 2));
			panelGrid.add(personalNumberLabel);
			panelGrid.add(personalnumber);
			panelMain.add(panelGrid, BorderLayout.NORTH);
			panelMain.add(comboBoxAccountTypes, BorderLayout.CENTER);
			panelMain.add(createButton, BorderLayout.SOUTH);
			add(panelMain);
			this.setLocation(FRAME_WIDTH + 3, 475);
		}
	 
	 /**Updaterar viewns personalnumber fält med ett personnummer.
	  * @param pNr personnummer i form av en String.
	  */
	 public void setPersonalNumber(String pNr) {
		 this.personalnumber.setText(pNr);
	 }
}
