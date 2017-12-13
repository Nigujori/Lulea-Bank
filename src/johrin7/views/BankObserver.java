package johrin7.views;

/**Alla views som observerar modellen måste implementera denna metod som anropas varje gång en förändring 
 * görs av modellen.
 * @author Johan Ringström användarnamn johrin7.*/
public interface BankObserver 
{
	public void updateBank(Boolean bool);
}
