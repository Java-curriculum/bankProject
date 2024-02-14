
import java.sql.Date;

public class RateInfo {
	private Date DAY;
	private double USD;
	private double JPY;
	private double THB;
	private double AUD;
	private double CAD;
	private double CHF;
	private double CNY;
	private double EUR;
	private double GBP;
	private double HKD;
	private double NZD;
	private double SGD;
	private double KRW;
	
	public Date getDAY() {
		return DAY;
	}
	public void setDAY(Date dAY) {
		this.DAY = dAY;
	}
	public double getUSD() {
		return USD;
	}
	public void setUSD(double uSD) {
		this.USD = uSD;
	}
	public double getJPY() {
		return JPY;
	}
	public void setJPY(double jPY) {
		this.JPY = jPY;
	}
	public double getTHB() {
		return THB;
	}
	public void setTHB(double tHB) {
		this.THB = tHB;
	}
	public double getAUD() {
		return AUD;
	}
	public void setAUD(double aUD) {
		this.AUD = aUD;
	}
	public double getCAD() {
		return CAD;
	}
	public void setCAD(double cAD) {
		this.CAD = cAD;
	}
	public double getCHF() {
		return CHF;
	}
	public void setCHF(double cHF) {
		this.CHF = cHF;
	}
	public double getCNY() {
		return CNY;
	}
	public void setCNY(double cNY) {
		this.CNY = cNY;
	}
	public double getEUR() {
		return EUR;
	}
	public void setEUR(double eUR) {
		this.EUR = eUR;
	}
	public double getGBP() {
		return GBP;
	}
	public void setGBP(double gBP) {
		this.GBP = gBP;
	}
	public double getHKD() {
		return HKD;
	}
	public void setHKD(double hKD) {
		this.HKD = hKD;
	}
	public double getNZD() {
		return NZD;
	}
	public void setNZD(double nZD) {
		this.NZD = nZD;
	}
	public double getSGD() {
		return SGD;
	}
	public void setSGD(double sGD) {
		this.SGD = sGD;
	}
	public double getKRW() {
		return KRW;
	}
	public void setKRW(double kRW) {
		this.KRW = kRW;
	}
	
	public double getRate(String cur_unit) {
	    switch (cur_unit) {
	        case "USD": return getUSD();
	        case "JPY": return getJPY();
	        case "THB": return getTHB();
	        case "AUD": return getAUD();
	        case "CAD": return getCAD();
	        case "CHF": return getCHF();
	        case "CNY": return getCNY();
	        case "EUR": return getEUR();
	        case "GBP": return getGBP();
	        case "HKD": return getHKD();
	        case "NZD": return getNZD();
	        case "SGD": return getSGD();
	        case "KRW": return getKRW();
	        default: return 0;
	    }
	}
	
	//일일 환율 비교를 위한 메소드
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RateInfo that = (RateInfo) obj;
        return Double.compare(that.USD, USD) == 0 &&
               Double.compare(that.JPY, JPY) == 0;
    }
}