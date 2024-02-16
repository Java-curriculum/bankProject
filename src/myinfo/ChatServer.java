package myinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/*한글 깨질 때 : properties-MS949로 변경*/

public class ChatServer {
	public static final int PORT = 8002;
	ServerSocket server;
	Vector<ClientThread> vc;
	
	public ChatServer() throws IOException {
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
				Socket sock = server.accept();//클라이언트 연결 요청 수락
				ClientThread ct = new ClientThread(sock);
				ct.start();//run
				vc.addElement(ct);//client가 접속될 때마다 vector로 묶어줌
			}//--while--
		} finally {
			if (server != null)
				server.close();
			System.out.println("**ChatServer를 종료합니다**");
		}//--finally--
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
	
	/*
	//접속된 모든 id 리스트 리턴 ex)aaa;bbb;홍길동;강호동;
	public String getIdList() {
		String list="";
		for (int i = 0; i < vc.size(); i++) {
			ClientThread ct = vc.get(i);
			list+=ct.id+";";
		}
		return list;
	}
	*/
	
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
					in = new BufferedReader(
							new InputStreamReader(sock.getInputStream()));
					out = new PrintWriter(sock.getOutputStream(),true);
					//클라이언트 소켓 주소 + 접속되었다는 문구 출력
					System.out.println(sock.toString() + " 접속되었습니다.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}//--ClientThread--
			
			@Override
			public void run() {
				try {
					sendAllMessage(id/*임시*/ + "님이 입장했습니다.");
					//클라이언트의 입력을 계속 읽음
					while(true) {
						String line = in.readLine();
						if(line==null)//클라이언트와 연결 끊김
							break; //종료
						else
							routine(line); //client가 보낸 메시지 넘김
					}
				} catch (Exception e) {
					removeClient(this);
					System.err.println(sock + id + "님의 연결이 끊어졌습니다.");
					sendAllMessage(id + "와의 상담을 종료했습니다.");
				}
			}//--run--
			
			
			public void routine(String line) {
				//CHATALL : 오늘은 목요일입니다.
				int idx = line.indexOf(ChatProtocol.MODE);
				String cmd = line.substring(0,idx);
				String data = line.substring(idx+1);
				if (cmd.equals(ChatProtocol.ID)) {
					id=data;
					//새로운 접속자 welcome 메세지 전송
					sendAllMessage(ChatProtocol.CHATALL+ChatProtocol.MODE + id + "님이 입장했습니다.");
				}else if (cmd.equals(ChatProtocol.CHATALL)) {
					sendAllMessage(ChatProtocol.CHATALL+ChatProtocol.MODE+ id + "\n" + data);
				}
			}//--routine--
	
			//클라이언트에서 사용된 자원을 해제하는 메소드
			public void closeAll() throws IOException {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
				if (sock != null)
					sock.close();
			}
			
			public void sendMessage(String msg) {
				out.println(msg); //클라이언트에게 메시지 전송
			}
			
		}//--ClientThread
	
	
	public static void main(String[] args) throws IOException {
		new ChatServer();
	}
	
	
}
