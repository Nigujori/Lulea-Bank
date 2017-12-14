package johrin7.views;

/**TableView interfacet som kräver av de som implementerar stå för implemationenen av closeOptionViews.
 * Det vill säga alla som implementerar detta interfacet måste ha en lösning på hur de optionsviews som
 * denna tabelView-klass skapar stängs när detta fönsterstängs på något sätt.
 * @author Johan Ringström användarnamn johrin7.*/
public interface TableView {
	public void closeOptionViews();
}
