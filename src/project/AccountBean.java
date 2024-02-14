package project;

public class AccountBean {

	private String account_num;
	private String id;
	private double usd;
	private double jpy;
	private double thb;
	private double aud;
	private double cad;
	private double chf;
	private double cny;
	private double eur;
	private double gbp;
	private double hkd;
	private double nzd;
	private double sgd;
	private double krw;
	
	/*getXxx, setXxx*/
	public String getAcc() {
		return account_num;
	}
	public void setAcc(String account_num) {
		this.account_num = account_num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public double getUSD() {
		return usd;
	}
	public void setUSD(double usd) {
		this.usd = usd;
	}
	public double getJPY() {
		return jpy;
	}
	public void setJPY(double jpy) {
		this.jpy = jpy;
	}
	public double getTHB() {
		return thb;
	}
	public void setTHB(double thb) {
		this.thb = thb;
	}
	public double getAUD() {
		return aud;
	}
	public void setAUD(double aud) {
		this.aud = aud;
	}
	public double getCAD() {
		return cad;
	}
	public void setCAD(double cad) {
		this.cad = cad;
	}
	public double getCHF() {
		return chf;
	}
	public void setCHF(double chf) {
		this.chf = chf;
	}
	public double getCNY() {
		return cny;
	}
	public void setCNY(double cny) {
		this.cny = cny;
	}
	public double getEUR() {
		return eur;
	}
	public void setEUR(double eur) {
		this.eur = eur;
	}
	public double getGBP() {
		return gbp;
	}
	public void setGBP(double gbp) {
		this.gbp = gbp;
	}
	public double getHKD() {
		return hkd;
	}
	public void setHKD(double hkd) {
		this.hkd = hkd;
	}
	public double getNZD() {
		return nzd;
	}
	public void setNZD(double nzd) {
		this.nzd = nzd;
	}
	public double getSGD() {
		return sgd;
	}
	public void setSGD(double sgd) {
		this.sgd = sgd;
	}
	public double getKRW() {
		return krw;
	}
	public void setKRW(double krw) {
		this.krw = krw;
	}
	
	@Override
	public String toString() {
		return "ExchangeRate [USD=" + usd + ", JPY=" + jpy + ", THB=" + thb + ", AUD=" + aud + ", CAD=" + cad + ", CHF=" + chf + ", CNY=" + cny + ", EUR=" + eur + ", GBP=" + gbp + ", HKD=" + hkd + ", NZD=" + nzd + ", SGD=" + sgd + ", KRW=" + krw + "]"; 
	}
	
	
	
}
