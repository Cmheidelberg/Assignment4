package cheidelb_CSCI201L_Assignment4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class StockHelper {

	final static String token = "";
	
	public Stock getStockFromApi(String ticker) {
		
		String generalLineOut, dailyLineOut, iexLineOut;
		Stock[] tmpData;
		Stock td = null;
		Stock[] td2 = null;
		Stock outData = null;
		
		try {
	        URL general_url = new URL("https://api.tiingo.com/tiingo/daily/" + ticker + "?&token=" + token);
	        URLConnection general_connection = general_url.openConnection();
	        BufferedReader general_in = new BufferedReader(new InputStreamReader(general_connection.getInputStream()));
	
	        URL daily_url = new URL("https://api.tiingo.com/tiingo/daily/" + ticker + "/prices?&token=" + token);
	        URLConnection daily_connection = daily_url.openConnection();
	        BufferedReader daily_in = new BufferedReader(new InputStreamReader(daily_connection.getInputStream()));
        
	        URL iex_url = new URL("https://api.tiingo.com/iex/" + ticker + "?&token=" + token);
	        URLConnection iex_connection = iex_url.openConnection();
	        BufferedReader iex_in = new BufferedReader(new InputStreamReader(iex_connection.getInputStream()));
	        
	        generalLineOut = general_in.readLine();
	        dailyLineOut = daily_in.readLine();
	        iexLineOut = iex_in.readLine();
	       
//	        System.out.println(generalLineOut);
//	        System.out.println(dailyLineOut);
//	        System.out.println(iexLineOut);
	        daily_in.close();
	        general_in.close();
	        iex_in.close();
	        Gson gson = new Gson();
	      
	        if(dailyLineOut.length() > 3) {
	        
				try {
					tmpData = gson.fromJson(dailyLineOut, Stock[].class);
					outData = tmpData[0];
				}
				catch (JsonSyntaxException jse) {
					System.out.printf("The file is not formatted properly.\n%s\n",jse);
				}
	        }
	        
	        if(generalLineOut.length() > 3) {
				try {
					td = gson.fromJson(generalLineOut, Stock.class);
				}
				catch (JsonSyntaxException jse) {
					System.out.printf("The file is not formatted properly.\n%s\n",jse);
				}
	        }
	        
	        if(iexLineOut.length() > 3) {
				try {
					
					td2 = gson.fromJson(iexLineOut, Stock[].class);
				}
				catch (JsonSyntaxException jse) {
					System.out.printf("The file is not formatted properly.\n%s\n",jse);
				}
	        }
	       	        
	        outData.setDescription(td.getDescription());
	        outData.setName(td.getName());
	        outData.setTicker(td.getTicker());
	        outData.setExchangeCode(td.getExchangeCode());
	        outData.setStartDate(td.getStartDate());
	        outData.setLast(td2[0].getLast());
	        outData.setTimestamp(td2[0].getTimestamp());
	        outData.setPrevClose(td2[0].getPrevClose());
	         
		} catch (FileNotFoundException fnfe) {
			System.out.printf("Invalid ticker %s. Ignoring:%s\n",ticker,fnfe);
		} catch (IOException ioe) {
			System.out.printf("IOException, skipping %s. (this could be an invalid or overused token)\n", ticker);
			
		}
		
		 return outData;
		
		
	}
}
