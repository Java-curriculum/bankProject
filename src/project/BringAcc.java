package project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class BringAcc { //계좌 잔금 받아오기

	private DBConnectionMgr pool;

	public BringAcc() {
	try { 
		pool = DBConnectionMgr.getInstance();
		Connection con = pool.getConnection(); 

	} catch (Exception e) {
		e.printStackTrace();
		}
	}
	
	
	//Account 테이블 1개 검색 select	
	public AccountBean select(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		AccountBean bean = new AccountBean();
		try {
			con = pool.getConnection();
			sql = "select * from account where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id); 
			rs = pstmt.executeQuery();
			if(rs.next()) {
				
				bean.setAcc(rs.getString(1));
				bean.setId(rs.getString(2));
				bean.setUSD(rs.getDouble(3));
				bean.setJPY(rs.getDouble(4));
				bean.setTHB(rs.getDouble(5));
				bean.setAUD(rs.getDouble(6));
				bean.setCAD(rs.getDouble(7));
				bean.setCHF(rs.getDouble(8));
				bean.setCNY(rs.getDouble(9));
				bean.setEUR(rs.getDouble(10));
				bean.setGBP(rs.getDouble(11));
				bean.setHKD(rs.getDouble(12));
				bean.setNZD(rs.getDouble(13));
				bean.setSGD(rs.getDouble(14));
				bean.setKRW(rs.getDouble(15));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}
	
		//ExchangeRate 테이블 1개 검색 select	
		public ExchangeRateBean ExSelect(String date) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			ExchangeRateBean bean = new ExchangeRateBean();
			try {
				con = pool.getConnection();
				sql = "select * from exchange_rate where date = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, date); 
				rs = pstmt.executeQuery();
				if(rs.next()) {
					
					bean.setDAY(rs.getDate(1));
					bean.setUSD(rs.getDouble(2));
					bean.setJPY(rs.getDouble(3));
					bean.setTHB(rs.getDouble(4));
					bean.setAUD(rs.getDouble(5));
					bean.setCAD(rs.getDouble(6));
					bean.setCHF(rs.getDouble(7));
					bean.setCNY(rs.getDouble(8));
					bean.setEUR(rs.getDouble(9));
					bean.setGBP(rs.getDouble(10));
					bean.setHKD(rs.getDouble(11));
					bean.setNZD(rs.getDouble(12));
					bean.setSGD(rs.getDouble(13));
					bean.setKRW(rs.getDouble(14));
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return bean;
		}
	
	
	
	
	public static void main(String[] args) {
		
		BringAcc mgr = new BringAcc();	
		AccountBean bean = new AccountBean();
		bean = mgr.select("admin"); /** 로그인시 해당 로그인 계정의 id를 받아올수 있도록 수정해야함 */
		
		ExchangeRateBean bean1 = new ExchangeRateBean();
		String date = (java.sql.Date.valueOf(LocalDate.now()).toString());
		bean1 = mgr.ExSelect(date); //DB date에 해당되는 값이 없으면 null과 기본값 반환.
		
		
				System.out.println("-----  보유 자산 -----");
				System.out.print("계좌 번호 : " + bean.getAcc() + "\t 오늘 날짜 : " + bean1.getDAY() + "\n");
				System.out.println("--------------------------");
				System.out.print("USD : " + (bean.getUSD() * bean1.getUSD()) + "($) \t");
				System.out.print("JPY : " + (bean.getJPY() * bean1.getJPY()) + "(¥) \t");
				System.out.print("THB : " + (bean.getTHB() * bean1.getTHB()) + "(฿) \t");
				System.out.print("AUD : " + (bean.getAUD() * bean1.getAUD()) + "(A$) \t");
				System.out.print("CAD : " + (bean.getCAD() * bean1.getCAD()) + "($) \t");
				System.out.print("CHF : " + (bean.getCHF() * bean1.getCHF()) + "(SFr) \t");
				System.out.print("CNY : " + (bean.getCNY() * bean1.getCNY()) + "(¥)\n");
				System.out.print("EUR : " + (bean.getEUR() * bean1.getEUR()) + "(€) \t");
				System.out.print("GBP : " + (bean.getGBP() * bean1.getGBP()) + "(£) \t");
				System.out.print("HKD : " + (bean.getHKD() * bean1.getHKD()) + "($) \t");
				System.out.print("NZD : " + (bean.getNZD() * bean1.getNZD()) + "($) \t");
				System.out.print("SGD : " + (bean.getSGD() * bean1.getSGD()) + "(S$) \t");
				System.out.print("KRW : " + (bean.getKRW() * bean1.getKRW()) + "(₩) \n");
				
	
	}
	
	
}


//System.out.print(bean.getAcc() + " , \t"); 
//System.out.print(bean.getId() + " , \t"); 
//System.out.print(bean.getUSD() + " , \t"); 
//System.out.print(bean.getJPY() + " , \t"); 
//System.out.print(bean.getTHB() + " , \t"); 
//System.out.print(bean.getAUD() + " , \t"); 
//System.out.print(bean.getCAD() + " , \t"); 
//System.out.print(bean.getCHF() + " , \t"); 
//System.out.print(bean.getCNY() + " , \t"); 
//System.out.print(bean.getEUR() + " , \t"); 
//System.out.print(bean.getGBP() + " , \t"); 
//System.out.print(bean.getHKD() + " , \t"); 
//System.out.print(bean.getNZD() + " , \t"); 
//System.out.print(bean.getSGD() + " , \t"); 
//System.out.println(bean.getKRW());


//System.out.print(bean1.getDAY() + " , \t"); 
//System.out.print(bean1.getUSD() + " , \t"); 
//System.out.print(bean1.getJPY() + " , \t"); 
//System.out.print(bean1.getTHB() + " , \t"); 
//System.out.print(bean1.getAUD() + " , \t");
//System.out.print(bean1.getCAD() + " , \t"); 
//System.out.print(bean1.getCHF() + " , \t"); 
//System.out.print(bean1.getCNY() + " , \t"); 
//System.out.print(bean1.getEUR() + " , \t"); 
//System.out.print(bean1.getGBP() + " , \t"); 
//System.out.print(bean1.getHKD() + " , \t"); 
//System.out.print(bean1.getNZD() + " , \t"); 
//System.out.print(bean1.getSGD() + " , \t"); 
//System.out.println(bean1.getKRW());


//bean = mgr.select("admin2");
//
//System.out.println("\n -----  보유 자산 -----");
//System.out.print("계좌 번호 : " + bean.getAcc() + "\t 오늘 날짜 : " + bean1.getDAY() + "\n");
//System.out.println("--------------------------");
//System.out.print("USD : " + (bean.getUSD() * bean1.getUSD()) + " \t");
//System.out.print("JPY : " + (bean.getJPY() * bean1.getJPY()) + " \t");
//System.out.print("THB : " + (bean.getTHB() * bean1.getTHB()) + " \t");
//System.out.print("AUD : " + (bean.getAUD() * bean1.getAUD()) + " \t");
//System.out.print("CAD : " + (bean.getCAD() * bean1.getCAD()) + " \t");
//System.out.print("CHF : " + (bean.getCHF() * bean1.getCHF()) + " \t");
//System.out.print("CNY : " + (bean.getCNY() * bean1.getCNY()) + "\n");
//System.out.print("EUR : " + (bean.getEUR() * bean1.getEUR()) + " \t");
//System.out.print("GBP : " + (bean.getGBP() * bean1.getGBP()) + " \t");
//System.out.print("HKD : " + (bean.getHKD() * bean1.getHKD()) + " \t");
//System.out.print("NZD : " + (bean.getNZD() * bean1.getNZD()) + " \t");
//System.out.print("SGD : " + (bean.getSGD() * bean1.getSGD()) + " \t");
//System.out.print("KRW : " + (bean.getKRW() * bean1.getKRW()) + " \t");