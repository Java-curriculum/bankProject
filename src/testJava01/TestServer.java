package testJava01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;



//이곳은 임시 서버 입니다.
public class TestServer {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
	
	   static final int PORT = 8003;
	   Vector<ClientThread3> vc;
	   ServerSocket server;
	  
	   public TestServer() {
	      try {
	         vc = new Vector<ClientThread3>();
	         server = new ServerSocket(PORT);
	         
	      } catch (Exception e) {
	         System.err.println("Error in Server");
	         e.printStackTrace();
	         System.exit(1);
	      }
	      System.out.println("****************************************");
	      System.out.println("*Welcome Chat Server 3.0...");
	      System.out.println("*클라이언트 접속을 기다리고 있습니다.");
	      System.out.println("****************************************");
	  	  
	      //소켓 연결중입니다.
	      try {
	         while (true) {
	            Socket sock = server.accept();
	            ClientThread3 ct = new ClientThread3(sock);
	            ct.start();
	            vc.addElement(ct);
	         }
	      } catch (Exception e) {
	         System.err.println("Error in Socket");
	         e.printStackTrace();
	      }
	   }

	   public void removeClient(ClientThread3 ct) {
	      vc.remove(ct);
	   }

	   
	   
	   class ClientThread3 extends Thread {

	      Socket sock;
	      BufferedReader in;
	      PrintWriter out;
	      String id = "익명";

	      public ClientThread3(Socket sock) {
	         try {
	            this.sock = sock;
	            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	            out = new PrintWriter((sock.getOutputStream()), true);
	            System.out.println(sock + " 접속됨...");
	         } catch (Exception e) {
	            e.printStackTrace();
	         }
	      }

	      @Override
	      public void run() {
	         try {
	            while (true) {
	               String line = in.readLine();
	               if (line == null)
	                  break;
	               else
	                  routine(line);
	            }
	         } catch (Exception e) {
	            removeClient(this);
	            System.err.println(sock + "[" + id + "] 끊어짐...");
	         }
	      }

	      public void routine(String line) {
	    	  //client 에서 보낸 명령문을 처리하는 함수
	         int idx = line.indexOf(ChatProtocol3.MODE);
	         String cmd = line.substring(0, idx); 
	         String data = line.substring(idx + 1); 
	         //테스트용 로그인 구현해보기
	         if (cmd.equals(ChatProtocol3.ID)) {
		            idx = data.indexOf(';');
		            cmd = data.substring(0, idx);//aaa
		            data = data.substring(idx+1);//1234
		            
		            if(login_chk(cmd, data)) {
		            	//true를 반환한다.
		            	out.println(true);
		               
		            }
		            else {
		               //로그인 실패
		               sendMessage(ChatProtocol3.ID+ChatProtocol3.MODE + "F");
		            }
	         }
	         
	      }

	      public void sendMessage(String msg) {
	         out.println(msg);
	      }
	      
	      public boolean login_chk(String id, String pwd) {
	          Connection con = null;
	          PreparedStatement pstmt = null;
	          ResultSet rs = null;
	          String sql = null;
	          boolean flag = false;
	          try {
	             con = pool.getConnection();
	             sql = "select count(id) from your_table where id = ? and password = ?";
	             pstmt = con.prepareStatement(sql);
	             pstmt.setString(1, id);
	             pstmt.setString(2, pwd);
	             rs = pstmt.executeQuery();
	             if(rs.next() && rs.getInt(1) == 1) {
	                flag = true;
	             }
	          } catch (Exception e) {
	             e.printStackTrace();
	          } finally {
	             pool.freeConnection(con, pstmt, rs);
	          }
	          return flag;
	       }
	      
	   }

			
		public static void main(String[] args) {
			new TestServer();
		}
}
