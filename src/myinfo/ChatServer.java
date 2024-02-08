package myinfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;



public class ChatServer {
	public static final int PORT = 8002;
	ServerSocket server;
	Vector<ClientThread> vc;
	
	public ChatServer() {
		try {
			server = new ServerSocket(PORT);
			vc = new Vector<ClientThread>();
		} catch (Exception e) {
			//e.printStackTrace();
			System.err.println("Error in Server");
			System.exit(1);//비정상적인 종료
		}
		System.out.println("**ChatServer**");
		System.out.println("**클라이언트 접속을 기다리고 있습니다**");
		try {
			while(true) {
				//대기하다가 client 접속 순간 client, server Socket 만듢
				Socket sock = server.accept();
				ClientThread ct = new ClientThread(sock);
				ct.start();//run
				vc.addElement(ct);//client가 접속될 때마다 vector로 묶어줌
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.err.println("Error in Socket");
		}
	}
	
	//접속된 모든 client에게 메시지 전송
	public void sendAllMessage(String msg) {
		for (int i = 0; i < vc.size(); i++) {
			ClientThread ct = vc.get(i);
			ct.sendMessage(msg);
		}
	}
	
	//client가 접속 해제 시 vector에서 제거
	public void removeClient(ClientThread ct) {
		vc.remove(ct);
	}
	
	
	//접속된 모든 id 리스트 리턴 ex)aaa;bbb;홍길동;강호동;
	public String getIdList() {
		String list="";
		for (int i = 0; i < vc.size(); i++) {
			ClientThread ct = vc.get(i);
			list+=ct.id+";";
		}
		return list;
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
					in = new BufferedReader(
							new InputStreamReader(sock.getInputStream()));
					out = new PrintWriter(sock.getOutputStream(),true);
					System.out.println(sock.toString() + " 접속되었습니다.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void run() {
				try {
					sendMessage(id/*임시*/ + "님이 입장했습니다.");
					while(true) {
						String line = in.readLine();
						if(line==null)//socket 연결 끊김
							break; //종료
						else
							routine(line); //client가 보낸 메시지 넘김
					}
				} catch (Exception e) {
					removeClient(this);
					System.err.println(sock + id + "님이 끊어졌습니다.");
				}
			}
			
			public void routine(String line) {
				//CHATALL : 오늘은 목요일입니다.
				int idx = line.indexOf(ChatProtocol.MODE);
				String cmd = line.substring(0,idx);
				String data = line.substring(idx+1);
				if (cmd.equals(ChatProtocol.ID)) {
					id=data;
					
					//새로운 접속자가 추가 되었기 때문에 리스트 재전송
					sendAllMessage(ChatProtocol.CHATLIST+ChatProtocol.MODE+getIdList());
					//새로운 접속자 welcome 메세지 전송
					sendAllMessage(ChatProtocol.CHATALL+ChatProtocol.MODE+"[" + id + "]님이 입장하였습니다.");
				}else if (cmd.equals(ChatProtocol.CHATALL)) {
					sendAllMessage(ChatProtocol.CHATALL+ChatProtocol.MODE+"[" + id + "]" + data);
				}
			}//--routine--
	
			
			public void sendMessage(String msg) {
				out.println(msg);
			}
			
		}//--ClientThread
	
	
	public static void main(String[] args) {
		new ChatServer();
	}
	
	
}
