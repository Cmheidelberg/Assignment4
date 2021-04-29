package cheidelb_CSCI201L_Assignment4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class CustomDatabase {
	Connection conn = null;
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public CustomDatabase() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/assignment_four?user=root&password=root");
			st = conn.createStatement();
			
		} catch(SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		}
	}
	
	
	public ResultSet getResultSet(String querery) {
		try {
			return st.executeQuery(querery);
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		}
		return null;
	}
	
	
	public User getUser(int id) {
		String querey = "SELECT * FROM users WHERE idusers=\""+id+"\"";
		System.out.println(querey);
		
		User user = new User();
		try {
			ResultSet rs = getResultSet(querey);
		if(rs.next()) {
			user.setId(rs.getInt("idusers"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
		}else {
			System.out.println("No user with that username and password found");
		}
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle);
		}
		return user;
	}
	
	
	public String getValidatedIduser(String username, String password) {
		String querey = "SELECT idusers FROM users WHERE username=\""+ username+"\" and password=\""+password+"\"";
		System.out.println(querey);
		
		String id = null;
		try {
		ResultSet rs = getResultSet(querey);
		if(rs.next()) {
			id = rs.getString("idusers");
		}else {
			System.out.println("No user with that username and password found");
		}
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle);
		}
		
		return id;
	}
	
	
	public String getIduser (String username) {
		String querey = "SELECT idusers FROM users WHERE username=\""+ username+"\"";
		System.out.println(querey);
		
		String id = null;
		try {
		ResultSet rs = getResultSet(querey);
		if(rs.next()) {
			id = rs.getString("idusers");
		}else {
			System.out.println("No user with that id found");
		}
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle);
		}
		return id;
	}
	
	
	public String getIduserFromEmail (String email) {
		String querey = "SELECT idusers FROM users WHERE email=\""+ email+"\"";
		System.out.println(querey);
		
		String id = null;
		try {
			ResultSet rs = getResultSet(querey);
			if(rs.next()) {
				id = rs.getString("idusers");
			}else {
				System.out.println("No user with that email found");
			}
			} catch (SQLException sqle) {
				System.out.println("SQLE: " + sqle);
		}
		return id;
	}
	
	public String initializeNewUser(String username, String email, String password) {
		
		//Get the iduser value
		int count = getUsersCount();
		
		String querey = String.format("INSERT INTO users (`idusers`, `username`, `password`, `email`) VALUES ('%s', '%s', '%s', '%s')",count,username,password,email);
		System.out.println(querey);
		
		try {
			st.execute(querey);
			return "valid";
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle);
			return "invalid";
		}
	}
	
	public void addFavorite(int iduser, String ticker) {
		String query = "INSERT INTO favorites(iduser,ticker,name) VALUES (\""+iduser+"\",\""
						+ticker+"\",\"not saved\")";
		System.out.println(query);
		
		try {
			st.execute(query);
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle);
		}
	}
	
	
	public void removeFavorite(int iduser, String ticker) {
		String query = "DELETE FROM favorites WHERE ticker=\""+ticker+"\" and iduser=\""+iduser+"\"";
		System.out.println(query);
		
		try {
			st.execute(query);
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle);
		}
	}
	
	
	public float getBalance(int id) {
		String querey = "SELECT balance FROM users WHERE idusers=\""+ id+"\"";
		System.out.println(querey);
		
		float balance = -1;
		try {
		ResultSet rs = getResultSet(querey);
		if(rs.next()) {
			balance = rs.getFloat("balance");
		}else {
			System.out.println("No user with that id found");
		}
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle);
		}
		return balance;
	}
	
	
	public float[] getPortfolioSummary(int iduser, String ticker) {
		String querey = "SELECT SUM(quantity),SUM(totalcost) FROM purchases WHERE ticker=\""+ticker+
						"\" AND iduser=\""+iduser+"\"";
		System.out.println(querey);
		
		float[] outp = new float[3];
		try {
			ResultSet rs = getResultSet(querey);
			if(rs.next()) {
				outp[0] = rs.getFloat("SUM(quantity)");
				outp[1] = rs.getFloat("SUM(totalcost)") / rs.getFloat("SUM(quantity)");
				outp[2] = rs.getFloat("SUM(totalcost)");
				
			}else {
				System.out.println("No user with that id found");
			}
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle);
		}
		return outp;
		
	}
	
	
	public void buyStock(int iduser, String ticker, int amount, float price) {
		String query = String.format(
				"INSERT INTO purchases(iduser,ticker,quantity,unitcost,totalcost) "+
				"VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\")", iduser, ticker, amount, price, price*amount);

		System.out.println(query);

		String query2 = "UPDATE users SET balance = balance-\""+price*amount+"\" WHERE idusers=\""+iduser+"\"";
		System.out.println(query2);
		try {
			st.execute(query);
			st.execute(query2);
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle);
		}
		
	}
	
	
	public void sellStock(int id,String ticker,int amount,float last) {
		String query = String.format("SELECT * FROM purchases WHERE iduser=\""+id+"\" AND ticker=\""+ticker+"\"");

		System.out.println(query);
		ArrayList<String[]> purchases = new ArrayList<String[]>();
		
		float profit = 0;
		
		try {
			ResultSet rs = getResultSet(query);
			while(rs.next()) {
				String[] arr = new String[6];
				arr[0] = rs.getString("idpurchase");
				arr[1] = rs.getString("iduser");
				arr[2] = rs.getString("ticker");
				arr[3] = rs.getString("quantity");
				arr[4] = rs.getString("unitcost");
				arr[5] = rs.getString("totalcost");
				purchases.add(arr);
				
			}
			
			for (String[] p: purchases) {
				if(amount > 0) {
					int idpurchase = Integer.parseInt(p[0]);
					int pQuantity = Integer.parseInt(p[3]);
					float unitCost = Float.parseFloat(p[4]);
					System.out.println("amt: " + amount + " pQuantity: " + pQuantity);
					int removeFromP = pQuantity-amount >= 0  ? amount : pQuantity;
					amount -= removeFromP;
					profit += unitCost*removeFromP;
					String updateQuery;
					
					//Delete whole purchase
					System.out.println("removeFromP: " + removeFromP + " for: " + p[2]);
					if(removeFromP == pQuantity) {
						System.out.println("Delete");
						updateQuery = "DELETE FROM purchases WHERE idpurchase=\""+idpurchase+"\"";

					//Update Purchase
					} else {
						System.out.println("Update");
						int newQuantity = pQuantity-removeFromP;
						updateQuery = "UPDATE purchases SET quantity=\""+newQuantity+"\","
								+ " totalcost=\""+newQuantity*unitCost+"\" WHERE idpurchase=\""+idpurchase+"\"";
					}
					
					System.out.println(updateQuery);
					st.execute(updateQuery);
				}
			}
			
			String updateBalance = "UPDATE users SET balance = balance+\""+profit+"\" WHERE idusers=\""+id+"\"";
			st.execute(updateBalance);
			
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle);
		}

	}
	
	
	public ArrayList<String> getPortfolioTickers(int iduser) {
		String querey = "SELECT DISTINCT ticker FROM assignment_four.purchases WHERE iduser=\""+iduser+"\"";
		System.out.println(querey);
		ArrayList<String> tickers = new ArrayList<String>();
		try {
			ResultSet rs = getResultSet(querey);
			while(rs.next()) {
				tickers.add(rs.getString("ticker"));
				
			}
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle);
		}
		return tickers;
	}
	
	
	public int getUsersCount() {
		String querey = "SELECT COUNT(*) FROM users";
		System.out.println(querey);
		
		int count = -1;
		try {
			ResultSet rs = getResultSet(querey);
			if(rs.next()) {
				count = rs.getInt("COUNT(*)");
			}else {
				System.out.println("Could not get count???");
			}
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle);
		}
			return count;
	}
	
	
	public ArrayList<Favorite> getFavorites(int idUser) {
		String querey = "SELECT * FROM favorites WHERE iduser=" + idUser;
		System.out.println(querey);
		
		ArrayList<Favorite> favorites = new ArrayList<Favorite>();
		
		try {
			ResultSet rs = getResultSet(querey);
			while(rs.next()) {
				Favorite currFav = new Favorite();
				currFav.setIdFavorite(rs.getInt("idfavorite"));
				currFav.setIdUser(rs.getInt("iduser"));
				currFav.setTicker(rs.getString("ticker"));
				currFav.setName(rs.getString("name"));
				
				favorites.add(currFav);
			}
		} catch (SQLException sqle) {
			System.out.println("SQLE: " + sqle);
		}
		
		return favorites;	
	}
	
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		}
	}
}
