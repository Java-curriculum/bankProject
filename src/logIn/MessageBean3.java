package logIn;

public class MessageBean3 {
	
	private int ex_num;//번호(자동증가) 
	private String ex_account_num;//사용자 계좌
	private String day;//거래 날짜
	private String sell_cur;//판매 통화
	private String sell_amt;//판매 통화량
	private String buy_cur;//구매 통화
	private String buy_amt;//구매 통화량
	private String rate;
	
	public int getNo() {
		return ex_num;
	}
	public void setNo(int ex_num) {
		this.ex_num = ex_num;
	}
	
	
	public String getex_account_num() {
		return ex_account_num;
	}
	public void setex_account_num(String ex_account_num) {
		this.ex_account_num = ex_account_num;
	}
	
	
	public String getday() {
		return day;
	}
	public void setday(String day) {
		this.day = day;
	}
	
	
	public String getsell_cur() {
		return sell_cur;
	}
	public void setsell_cur(String sell_cur) {
		this.sell_cur = sell_cur;
	}
	
	
	public String getbuy_cur() {
		return buy_cur;
	}
	public void setbuy_cur(String buy_cur) {
		this.buy_cur = buy_cur;
	}
	
	
	public String getbuy_amt() {
		return buy_amt;
	}
	public void setbuy_amt(String buy_amt) {
		this.buy_amt = buy_amt;
	}	
	
	
	public String getsell_amt() {
		return sell_amt;
	}
	public void setsell_amt(String sell_amt) {
		this.sell_amt = sell_amt;
	}	
	
	
	public String getrate() {
		return rate;
	}
	public void setrate(String rate) {
		this.rate = rate;
	}	
}
