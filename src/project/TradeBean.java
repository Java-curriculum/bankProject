package project;

import java.sql.Date;

public class TradeBean/*String : Dto, Vo*/ {

	private int trade_num;
	private String trader_account_1; //등록자
	private String trader_account_2; //신청사
	private Date day;
	private String sell_cur;
	private double sell_amt;
	private String buy_cur;
	private double buy_amt;
	private double rate;
	private boolean check1;
	private boolean check2;
	
	/*getXxx, setXxx*/
	public int getTradeNum() {
		return trade_num;
	}
	public void setTradeNum(int trade_num) {
		this.trade_num = trade_num;
	}
	public String getTraderAcc1() {
		return trader_account_1;
	}
	public void setTraderAcc1(String trader_account_1) {
		this.trader_account_1 = trader_account_1;
	}
	public String getTraderAcc2() {
		return trader_account_2;
	}
	public void setTraderAcc2(String trader_account_2) {
		this.trader_account_2 = trader_account_2;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public String getSellCur() {
		return sell_cur;
	}
	public void setSellCur(String sell_cur) {
		this.sell_cur = sell_cur;
	}
	public double getSellAmt() {
		return sell_amt;
	}
	public void setSellAmt(double sell_amt) {
		this.sell_amt = sell_amt;
	}
	public String getBuyCur() {
		return buy_cur;
	}
	public void setBuyCur(String buy_cur) {
		this.buy_cur = buy_cur;
	}
	public double getBuyAmt() {
		return buy_amt;
	}
	public void setBuyAmt(double buy_amt) {
		this.buy_amt = buy_amt;
	}	
	public Double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public boolean getCheck1() {
		return check1;
	}
	public void setCheck1(boolean check1) {
		this.check1 = check1;
	}
	public boolean getCheck2() {
		return check2;
	}
	public void setCheck2(boolean check2) {
		this.check2 = check2;
	}
}
