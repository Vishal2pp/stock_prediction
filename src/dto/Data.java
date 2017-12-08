package dto;
import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.Entity;
import org.hibernate.annotations.Table;


public class Data {
	BigDecimal high;
	BigDecimal low;
	BigDecimal open;
	BigDecimal close;
	long volume;
	float amt_change;
	float percent_change;
	String date;
	String symbol;
	
	public float getAmt_change() {
		return amt_change;
	}
	public void setAmt_change(float amt_change) {
		this.amt_change = amt_change;
	}
	public float getPercent_change() {
		return percent_change;
	}
	public void setPercent_change(float percent_change) {
		this.percent_change = percent_change;
	}
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
	public long getvolume() {
		return volume;
	}
	public void setVolume(long volume) {
		this.volume = volume;
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
		return "\nSymbol=" + symbol +"\nDate=" + date + "\nHigh=" + high + "\nLow=" + low + "\nOpen=" + open + "\nClose=" + close + "\nVolume="
				+ volume;
	}
	
}
