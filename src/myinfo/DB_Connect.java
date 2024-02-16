package myinfo;

import java.sql.Connection;
import java.sql.*;

public class DB_Connect {
    public static void main(String[] args) {
        // MySQL �����ͺ��̽� ���� ����
        String url = "jdbc:mysql://localhost:3306/hse"; // �����ͺ��̽� URL
        String username = "root"; // �����ͺ��̽� ����� �̸�
        String password = "1234"; // �����ͺ��̽� ��ȣ

        // �����ͺ��̽� ���� ��ü
        Connection conn = null;

        try {
            // JDBC ����̹� �ε�
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // �����ͺ��̽� ����
            conn = DriverManager.getConnection(url, username, password);
            
            if (conn != null) {
                System.out.println("MySQL �����ͺ��̽��� ���������� ����Ǿ����ϴ�.");
                // ���� ���⿡�� ������ �����ϰų� �����ͺ��̽� �۾��� ������ �� �ֽ��ϴ�.
            }
        } catch (SQLException e) {
            System.out.println("MySQL �����ͺ��̽� ���� ����!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC ����̹��� ã�� �� �����ϴ�.");
            e.printStackTrace();
        } finally {
            // ���� �ݱ�
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}