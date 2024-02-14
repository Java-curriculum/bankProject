

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RateDAO {
	private static Connection conn;
	private static final String USERNAME = "root";
	private static final String PASSWORD = "1234";
	private static final String URL = "jdbc:mysql://localhost:3306/test";
	
	//클래스와 DB 연동
	public RateDAO() {
		try {
//			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			System.out.println("DB연동 성공");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB연동 실패");
		}
	}
	
	
	//날짜와 기준 환율을 레코드에 삽입하는 메소드
	public static void insertRate(RateInfo ExchangeRate) {
		String sql = "insert into exchange_rate values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, ExchangeRate.getDAY());
			pstmt.setDouble(2, ExchangeRate.getUSD());
			pstmt.setDouble(3, ExchangeRate.getJPY());
			pstmt.setDouble(4, ExchangeRate.getTHB());
			pstmt.setDouble(5, ExchangeRate.getAUD());
			pstmt.setDouble(6, ExchangeRate.getCAD());
			pstmt.setDouble(7, ExchangeRate.getCHF());
			pstmt.setDouble(8, ExchangeRate.getCNY());
			pstmt.setDouble(9, ExchangeRate.getEUR());
			pstmt.setDouble(10, ExchangeRate.getGBP());
			pstmt.setDouble(11, ExchangeRate.getHKD());
			pstmt.setDouble(12, ExchangeRate.getNZD());
			pstmt.setDouble(13, ExchangeRate.getSGD());
			pstmt.setDouble(14, ExchangeRate.getKRW());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
//			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	
	//가장 최근에 삽입된 레코드를 가져오는 메소드
	public RateInfo getNewestRate() {
        RateInfo exchange = null;
//    	String day = LocalDate.now().minusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	String day = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	String sql = "SELECT * FROM exchange_rate ORDER BY DAY DESC LIMIT 1";
    	try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                exchange = new RateInfo();
                exchange.setDAY(Date.valueOf(LocalDate.now()));
                exchange.setUSD(rs.getDouble("USD"));
                exchange.setJPY(rs.getDouble("JPY"));
                exchange.setTHB(rs.getDouble("THB"));
                exchange.setAUD(rs.getDouble("AUD"));
                exchange.setCAD(rs.getDouble("CAD"));
                exchange.setCHF(rs.getDouble("CHF"));
                exchange.setCNY(rs.getDouble("CNY"));
                exchange.setEUR(rs.getDouble("EUR"));
                exchange.setGBP(rs.getDouble("GBP"));
                exchange.setHKD(rs.getDouble("HKD"));
                exchange.setNZD(rs.getDouble("NZD"));
                exchange.setSGD(rs.getDouble("SGD"));
                exchange.setKRW(rs.getDouble("KRW"));
                
            }
		} catch (Exception e) {
//			e.printStackTrace();
		}
    	return exchange;
    }
	
    // 레코드 수를 반환하는 메소드
	public int getRecordCount() {
		int count = 0;
        String sql = "SELECT COUNT(*) FROM exchange_rate";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return count;
    }

	
    // 가장 오래된 레코드를 삭제하는 메소드
    public void deleteOldestRecord() {
        String sql = "DELETE FROM exchange_rate ORDER BY DAY ASC LIMIT 1";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }
    
    // 가장 최근 레코드를 삭제하는 메소드
    public void deleteNewestRecord() {
    	String sql = "DELETE FROM exchange_rate ORDER BY DAY DESC LIMIT 1";
    	try {
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.executeUpdate();
    	} catch (SQLException e) {
//    		e.printStackTrace();
    	}
    }
    
    //환율 표를 출력하기 위한 환율 정보 받아오기
    public RateInfo getTodayRate() {
        RateInfo rateInfo = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
        	String sql = "SELECT * FROM exchange_rate WHERE DAY = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(LocalDate.now()));
            rs = pstmt.executeQuery();

            if (rs.next()) {
                rateInfo = new RateInfo();
                rateInfo.setDAY(rs.getDate("DAY"));
                rateInfo.setUSD(rs.getDouble("USD"));
                rateInfo.setJPY(rs.getDouble("JPY"));
                rateInfo.setTHB(rs.getDouble("THB"));
                rateInfo.setAUD(rs.getDouble("AUD"));
                rateInfo.setCAD(rs.getDouble("CAD"));
                rateInfo.setCHF(rs.getDouble("CHF"));
                rateInfo.setCNY(rs.getDouble("CNY"));
                rateInfo.setEUR(rs.getDouble("EUR"));
                rateInfo.setGBP(rs.getDouble("GBP"));
                rateInfo.setHKD(rs.getDouble("HKD"));
                rateInfo.setNZD(rs.getDouble("NZD"));
                rateInfo.setSGD(rs.getDouble("SGD"));
                rateInfo.setKRW(rs.getDouble("KRW"));
            }
        } catch (SQLException e) {
//            e.printStackTrace();
        }

        return rateInfo;
    }
    
    public List<RateInfo> getWeekRates(Date date) {
        List<RateInfo> weekRates = new ArrayList<>();
        
        // 데이터베이스에서 1주일치 데이터를 가져옵니다.
        // 이 부분은 실제 데이터베이스와 SQL 쿼리에 따라 구현해야 합니다.
        try {
	        String sql = "SELECT * FROM exchange_rate WHERE DAY >= DATE_SUB(?, INTERVAL 1 WEEK)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setDate(1, date);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            RateInfo rateInfo = new RateInfo();
	            rateInfo.setDAY(rs.getDate("DAY"));
	            rateInfo.setUSD(rs.getDouble("USD"));
	            rateInfo.setJPY(rs.getDouble("JPY"));
	            rateInfo.setTHB(rs.getDouble("THB"));
	            rateInfo.setAUD(rs.getDouble("AUD"));
	            rateInfo.setCAD(rs.getDouble("CAD"));
	            rateInfo.setCHF(rs.getDouble("CHF"));
	            rateInfo.setCNY(rs.getDouble("CNY"));
	            rateInfo.setEUR(rs.getDouble("EUR"));
	            rateInfo.setGBP(rs.getDouble("GBP"));
	            rateInfo.setHKD(rs.getDouble("HKD"));
	            rateInfo.setNZD(rs.getDouble("NZD"));
	            rateInfo.setSGD(rs.getDouble("SGD"));
	            rateInfo.setKRW(rs.getDouble("KRW"));
	            weekRates.add(rateInfo);
	        }
        
        }catch(Exception e){
//        	e.printStackTrace();
        }
        return weekRates;
    }
}