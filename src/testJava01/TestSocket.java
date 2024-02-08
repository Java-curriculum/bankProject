package testJava01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
//소켓은 프로그램이 시작할때 main함수의 첫부분에 실행되어 생성되어 지고
//프로그램이 종료될때 까지 지속적으로 서버와 연결되어서 필요한 정보를 통신받고
//필요한 객체에게 정보를 전달하는 역할을 한다.
public class TestSocket implements Runnable{
	
	private static TestSocket instance;
	BufferedReader in;
	PrintWriter out;
	private Socket socket;
	
	 // 서버 주소와 포트 번호
    private static final int SERVER_PORT = 8003;
	
	public TestSocket() {
		connect();
	}
	
    public void connect() {
    	if(socket==null) {
    		try {
    			Socket sock = new Socket("127.0.0.1",SERVER_PORT);
    			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    			out = new PrintWriter(sock.getOutputStream(),true);
    			System.out.println("연결성공입니다.");
    		} catch (Exception e) {
    			System.out.println("연결실패입니다.");
    		}
    		new Thread(this).start();
    	}
    }
    
    
	@Override
	public void run() {
	}
	
	public static synchronized TestSocket getInstance() {
		if (instance == null) {
			instance = new TestSocket();
		}
		return instance;
	}
	// 아이디와 비밀번호를 서버에 보내고 이름을 반환받는 메서드
    public boolean sendCredentialsAndReceiveName(String id, String password) {
        if (out != null && in != null) {
            try {
                // 서버로 아이디와 비밀번호를 전송 ID:aaa;1234
            	
            	out.println(ChatProtocol3.ID+ChatProtocol3.MODE+id+";"+password);
            	System.out.println(ChatProtocol3.ID+id+ChatProtocol3.MODE+";"+password);
                
                
                
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
