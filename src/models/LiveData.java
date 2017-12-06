package models;

import java.math.BigDecimal;

public class LiveData {
	private String symbol; 
	private BigDecimal price;
	private BigDecimal openingPrice;
	private BigDecimal ask;
	private BigDecimal bid;
	private BigDecimal previousClose;
	
	public BigDecimal getPreviousClose() {
		return previousClose;
	}
	public void setPreviousClose(BigDecimal previousClose) {
		this.previousClose = previousClose;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getOpeningPrice() {
		return openingPrice;
	}
	public void setOpeningPrice(BigDecimal openingPrice) {
		this.openingPrice = openingPrice;
	}
	public BigDecimal getAsk() {
		return ask;
	}
	public void setAsk(BigDecimal ask) {
		this.ask = ask;
	}
	public BigDecimal getBid() {
		return bid;
	}
	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}
}
