package myinfo;

import java.sql.Connection;
import java.sql.*;

public class DB_Connect {
	public Connection getConnection() {
		 // MySQL 데이터베이스 연결 정보
        String url = "jdbc:mysql://localhost:3306/hse"; // 데이터베이스 URL
        String username = "root"; // 데이터베이스 사용자 이름
        String password = "1234"; // 데이터베이스 암호

        // 데이터베이스 연결 객체
        Connection conn = null;

        try {
            // JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 데이터베이스 연결
            conn = DriverManager.getConnection(url, username, password);
            
            if (conn != null) {
                System.out.println("MySQL 데이터베이스에 성공적으로 연결되었습니다.");
                // 이제 여기에서 쿼리를 실행하거나 데이터베이스 작업을 수행할 수 있습니다.
            }
        } catch (SQLException e) {
            System.out.println("MySQL 데이터베이스 연결 실패!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        }
        return conn; // 연결 반환
	}
}
