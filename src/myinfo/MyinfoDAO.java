package myinfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//select, updata 기능


public class MyinfoDAO {
	Userbean userData = new Userbean();
	DB_Connect dbcon = new DB_Connect();
	PreparedStatement pstmt=null;
	

	//내 정보 텍스트 필드에 회원정보 뿌려줌
	public Userbean select_user(String userId) throws ClassNotFoundException, SQLException {
		//실행 쿼리
		String id = "'"+userId+"'";
		System.out.println(id);
		Connection conn = dbcon.getConnection();
		String sql = "SELECT member.id, member.pwd, member.name, member.phone_num, member.telecom, account.account_num FROM member INNER JOIN account ON member.id=account.id WHERE member.id="+id;
		System.out.println(sql);
		//PreparedStatement 생성
		pstmt = conn.prepareStatement(sql);
		//결과를 담을 ResultSet 생성 후 결과 담기
		ResultSet rs = pstmt.executeQuery(sql);
		
		//ResultSet에 담긴 결과를 ArrayList에 담기
		ArrayList<Userbean> user_list = new ArrayList<Userbean>();
		while(rs.next()) {
			userData.setName(rs.getString("name"));
			userData.setId(rs.getString("id"));
			userData.setPw(rs.getString("pwd"));
			userData.setAccount(rs.getString("account_num"));
			userData.setPhone(rs.getString("phone_num"));
			userData.setTelecom(rs.getString("telecom"));
			}

		pstmt.close();
		rs.close();
		return userData;	//userData 반환
	}
	
	//텍스트 필드 입력 후 db 수정
	public void update_user(ArrayList<String> Update_data) {
		Connection conn = dbcon.getConnection();
		//필요한 객체를 담을 지역 변수 미리 만들기
				try {
					//실행 쿼리
					String sql = "UPDATE member SET name=?, pwd=?, phone_num=?, telecom=?"
							+"WHERE id='"+Update_data.get(4)+"'";
					//PreparedStatement 생성
					pstmt = conn.prepareStatement(sql);
					//?에 필요한값 바인딩하기 
					pstmt.setString(1,Update_data.get(0));
					pstmt.setString(2, Update_data.get(1));
					pstmt.setString(3, Update_data.get(2));
					pstmt.setString(4, Update_data.get(3));
		
					//sql 문 실행하기 (UPDATE)
					System.out.println("입력 데이터:"+Update_data.get(0)+"2:"+Update_data.get(1)
					+"3:"+Update_data.get(2)+"4:"+Update_data.get(3)+"5:"+Update_data.get(4));
					pstmt.executeUpdate();
				} catch (Exception e) {
					System.out.println("Exception 오류 발생 : MyinfoDAO");
					e.printStackTrace();
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();
						if (conn != null)
							conn.close();
					} catch (SQLException e) {
						System.out.println("SQLException 오류 발생 : MyinfoDAO");
					}
				}
	}
		

}
