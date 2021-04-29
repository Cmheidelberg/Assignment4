package cheidelb_CSCI201L_Assignment4;

public class Stock {
	
	private String name;
	private String ticker;
	private String exchangeCode;
	private String tickerDate;
	private float close;
	private String high;
	private String low;
	private String volume;
	private String open;
	private String description;
	private String startDate;
	private String endDate;
	private String timestamp;
	private String last;
	private String prevClose;
	
	public Stock() {}
	
	
	//Setters
	public void setTickerDate(String n) {
		tickerDate = n;
	}
	
	public void setPrevClose(String pc) {
		prevClose = pc;
	}
	
	public void setTimestamp(String ts) {
		timestamp = ts;
	}
	
	public void setLast(String l) {
		last = l;
	}
	
	public void setTicker(String t) {
		ticker = t;
	}
	
	public void setExchangeCode(String ec) {
		exchangeCode = ec;
	}
	
	public void setClose(float n) {
		close = n;
	}
	
	public void sethigh(String s) {
		high = s;
	}
	
	public void setLow(String s) {
		low = s;
	}

	public void setVolume(String s) {
		volume = s;
	}
	
	public void setOpen(String s) {
		open = s;
	}
	
	public void setDescription(String s) {
		description = s;
	}
	
	public void setStartDate(String s) {
		startDate = s;
	}
	
	public void setEndDate(String s) {
		endDate = s;
	}
	
	public void setName(String s) {
		name = s;
	}


	
	//Getters
	public String getTickerDate() {
		return tickerDate;
	}
	
	public String getTicker() {
		return ticker;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public String getLast() {
		return last;
	}
	
	public String getPrevClose() {
		return prevClose;
	}
	
	public String getExchangeCode() {
		return exchangeCode;
	}
	
	public float getClose() {
		return close;
	}
	
	public String gethigh() {
		return high;
	}
	
	public String getLow() {
		return low;
	}

	public String getVolume() {
		return volume;
	}
	
	public String getOpen() {
		return open;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
		
	public String getName() {
		return name;
	}
		
}