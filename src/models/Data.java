package models;
import java.math.BigDecimal;
import java.util.Date;

public class Data {
	BigDecimal high;
	BigDecimal low;
	BigDecimal open;
	BigDecimal close;
	BigDecimal adjustedClose;
	String date;
	String symbol;
	public BigDecimal getHigh() {
		return high;
	}
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	public BigDecimal getLow() {
		return low;
	}
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	public BigDecimal getOpen() {
		return open;
	}
	public void setOpen(BigDecimal open) {
		this.open = open;
	}
	public BigDecimal getClose() {
		return close;
	}
	public void setClose(BigDecimal close) {
		this.close = close;
	}
	public BigDecimal getAdjustedClose() {
		return adjustedClose;
	}
	public void setAdjustedClose(BigDecimal adjustedClose) {
		this.adjustedClose = adjustedClose;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	@Override
	public String toString() {
		return "\nSymbol=" + symbol +"\nDate=" + date + "\nHigh=" + high + "\nLow=" + low + "\nOpen=" + open + "\nClose=" + close + "\nAdjustedClose="
				+ adjustedClose;
	}
	
}
