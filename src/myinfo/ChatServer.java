package myinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class ChatServer {
	String user_id;
	public static final int PORT = 8002;
	ServerSocket server;
	Vector<ClientThread> vc;
	
	public ChatServer(){
		try {
			server = new ServerSocket(PORT);
			vc = new Vector<ClientThread>(); //접속한 클라이언트 벡터로 묶기 
		} catch (Exception e) {
			System.err.println("Error in Server");
			System.exit(1);//비정상적인 종료
		}
		System.out.println("**클라이언트 접속을 기다리고 있습니다**");
		try {
			while(true) {
				// 다수의 클라이언트에게 지속적으로 서비스하기 위해 while 이용
				//대기하다가 client 접속 순간 client, server Socket 만듦
				Socket sock = server.accept(); //클라이언트 연결 요청 수락
				ClientThread ct = new ClientThread(sock);
				ct.start();//run
				vc.addElement(ct);//client가 접속될 때마다 vector로 묶어줌
			}//--while--
			
		} catch (Exception e) {
			System.err.println("**ChatServer를 종료합니다**");
		}//--catch--
	}//--ChatServer--
	
	//접속된 모든 client에게 메시지 전송
	public void sendAllMessage(String msg) {
		for (int i = 0; i < vc.size(); i++) {
			ClientThread ct = vc.get(i);
			ct.sendMessage(msg);
		}
	}//--sendAllMessage--

	//client가 접속 해제 시 vector에서 제거
	public void removeClient(ClientThread ct) {
		vc.remove(ct);
	}
	

	
	//동시에 상담 신청 들어왔을 때를 대비
	class ClientThread extends Thread{
			
			Socket sock;
			BufferedReader in;
			PrintWriter out;
			String id;
			
			public ClientThread(Socket sock) {//in과 outStream
				try {
					this.sock = sock;
					//클라이언트와의 입출력 스트림 생성
					in = new BufferedReader(new InputStreamReader(
									sock.getInputStream()));
					out = new PrintWriter(
							sock.getOutputStream(), true);
					//클라이언트 소켓 주소 + 접속되었다는 문구 출력
					System.out.println(sock + " 접속되었습니다.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}//--ClientThread--
			
			
			@Override
			public void run() {
				try {
					//client에게 최초 보내는 메세지
					out.println("사용하실 아이디를 입력하세요");
					//client에서 보낸 아이디를 저장
					id = in.readLine();
					//접속한 모든 client에게 welocome 메시지 보내기
					sendAllMessage("[" + id + "]님이 입장하였습니다.");
					String line = "";
					
					
					//클라이언트의 입력을 계속 읽음
					while(true) {
						//client에게 메세지 올 때까지 대기상태
						line = in.readLine();
						if (line==null) {
							removeClient(this);
							break;
						}
						sendAllMessage("[" + id + "]" + line);
				} //--while--
					in.close();
					out.close();
					sock.close();
				}catch (Exception e) {
					removeClient(this);
					System.err.println(sock + id + "님의 연결이 끊어졌습니다.");
					sendAllMessage(id + "와의 상담을 종료했습니다.");
				}
			}//--run--
			
			
			//Client에게 메시지 보내는 기능
			public void sendMessage(String msg) {
				out.println(msg);
			}
		}//--ClientThread
	
	
	public static void main(String[] args) throws IOException {
		new ChatServer();
	}
}
