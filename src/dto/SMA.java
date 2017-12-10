package dto;

public class SMA {
	private String date;
	private float close;
	private float sma;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public float getClose() {
		return close;
	}
	public void setClose(float close) {
		this.close = close;
	}
	public float getSma() {
		return sma;
	}
	public void setSma(float sma) {
		this.sma = sma;
	}
}
