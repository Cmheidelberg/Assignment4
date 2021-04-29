package cheidelb_CSCI201L_Assignment4;

public class Favorite {


	private int idFavorite;
	private int idUser;
	private String ticker;
	private String name;
	
	public Favorite() {	}
	
	public void setIdFavorite(int i) {
		idFavorite = i;
	}
	
	public void setIdUser(int i) {
		idUser = i;
	}
	
	public void setTicker(String u) {
		ticker = u;
	}
	
	public void setName(String e) {
		name = e;
	}
	
	
	public int getIdFavorite() {
		return idFavorite;
	}
	
	public int getIdUser() {
		return idUser;
	}
	
	public String getTicker() {
		return ticker;
	}
	
	public String getName() {
		return name;
	}
}
