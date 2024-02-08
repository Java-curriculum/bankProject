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
			System.exit(1);//���������� ����
		}
		System.out.println("**ChatServer**");
		System.out.println("**Ŭ���̾�Ʈ ������ ��ٸ��� �ֽ��ϴ�**");
		try {
			while(true) {
				//����ϴٰ� client ���� ���� client, server Socket ����
				Socket sock = server.accept();
				ClientThread ct = new ClientThread(sock);
				ct.start();//run
				vc.addElement(ct);//client�� ���ӵ� ������ vector�� ������
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.err.println("Error in Socket");
		}
	}
	
	//���ӵ� ��� client���� �޽��� ����
	public void sendAllMessage(String msg) {
		for (int i = 0; i < vc.size(); i++) {
			ClientThread ct = vc.get(i);
			ct.sendMessage(msg);
		}
	}
	
	//client�� ���� ���� �� vector���� ����
	public void removeClient(ClientThread ct) {
		vc.remove(ct);
	}
	
	
	//���ӵ� ��� id ����Ʈ ���� ex)aaa;bbb;ȫ�浿;��ȣ��;
	public String getIdList() {
		String list="";
		for (int i = 0; i < vc.size(); i++) {
			ClientThread ct = vc.get(i);
			list+=ct.id+";";
		}
		return list;
	}
	
	//���ÿ� ��� ��û ������ ���� ���
	class ClientThread extends Thread{
			
			Socket sock;
			BufferedReader in;
			PrintWriter out;
			String id;
			
			public ClientThread(Socket sock) {//in�� outStream
				try {
					this.sock = sock;
					in = new BufferedReader(
							new InputStreamReader(sock.getInputStream()));
					out = new PrintWriter(sock.getOutputStream(),true);
					System.out.println(sock.toString() + " ���ӵǾ����ϴ�.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void run() {
				try {
					sendMessage(id/*�ӽ�*/ + "���� �����߽��ϴ�.");
					while(true) {
						String line = in.readLine();
						if(line==null)//socket ���� ����
							break; //����
						else
							routine(line); //client�� ���� �޽��� �ѱ�
					}
				} catch (Exception e) {
					removeClient(this);
					System.err.println(sock + id + "���� ���������ϴ�.");
				}
			}
			
			public void routine(String line) {
				//CHATALL : ������ ������Դϴ�.
				int idx = line.indexOf(ChatProtocol.MODE);
				String cmd = line.substring(0,idx);
				String data = line.substring(idx+1);
				if (cmd.equals(ChatProtocol.ID)) {
					id=data;
					
					//���ο� �����ڰ� �߰� �Ǿ��� ������ ����Ʈ ������
					sendAllMessage(ChatProtocol.CHATLIST+ChatProtocol.MODE+getIdList());
					//���ο� ������ welcome �޼��� ����
					sendAllMessage(ChatProtocol.CHATALL+ChatProtocol.MODE+"[" + id + "]���� �����Ͽ����ϴ�.");
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
