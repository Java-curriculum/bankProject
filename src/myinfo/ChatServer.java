package myinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/*�ѱ� ���� �� : properties-MS949�� ����*/

public class ChatServer {
	public static final int PORT = 8002;
	ServerSocket server;
	Vector<ClientThread> vc;
	
	public ChatServer() throws IOException {
		try {
			server = new ServerSocket(PORT);
			vc = new Vector<ClientThread>(); //������ Ŭ���̾�Ʈ ���ͷ� ���� 
		} catch (Exception e) {
			System.err.println("Error in Server");
			System.exit(1);//���������� ����
		}
		System.out.println("**Ŭ���̾�Ʈ ������ ��ٸ��� �ֽ��ϴ�**");
		try {
			while(true) {
				// �ټ��� Ŭ���̾�Ʈ���� ���������� �����ϱ� ���� while �̿�
				//����ϴٰ� client ���� ���� client, server Socket ����
				Socket sock = server.accept();//Ŭ���̾�Ʈ ���� ��û ����
				ClientThread ct = new ClientThread(sock);
				ct.start();//run
				vc.addElement(ct);//client�� ���ӵ� ������ vector�� ������
			}//--while--
		} finally {
			if (server != null)
				server.close();
			System.out.println("**ChatServer�� �����մϴ�**");
		}//--finally--
	}//--ChatServer--
	
	//���ӵ� ��� client���� �޽��� ����
	public void sendAllMessage(String msg) {
		for (int i = 0; i < vc.size(); i++) {
			ClientThread ct = vc.get(i);
			ct.sendMessage(msg);
		}
	}//--sendAllMessage--
	
	//client�� ���� ���� �� vector���� ����
	public void removeClient(ClientThread ct) {
		vc.remove(ct);
	}
	
	/*
	//���ӵ� ��� id ����Ʈ ���� ex)aaa;bbb;ȫ�浿;��ȣ��;
	public String getIdList() {
		String list="";
		for (int i = 0; i < vc.size(); i++) {
			ClientThread ct = vc.get(i);
			list+=ct.id+";";
		}
		return list;
	}
	*/
	
	//���ÿ� ��� ��û ������ ���� ���
	class ClientThread extends Thread{
			
			Socket sock;
			BufferedReader in;
			PrintWriter out;
			String id;
			
			public ClientThread(Socket sock) {//in�� outStream
				try {
					this.sock = sock;
					//Ŭ���̾�Ʈ���� ����� ��Ʈ�� ����
					in = new BufferedReader(
							new InputStreamReader(sock.getInputStream()));
					out = new PrintWriter(sock.getOutputStream(),true);
					//Ŭ���̾�Ʈ ���� �ּ� + ���ӵǾ��ٴ� ���� ���
					System.out.println(sock.toString() + " ���ӵǾ����ϴ�.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}//--ClientThread--
			
			@Override
			public void run() {
				try {
					sendAllMessage(id/*�ӽ�*/ + "���� �����߽��ϴ�.");
					//Ŭ���̾�Ʈ�� �Է��� ��� ����
					while(true) {
						String line = in.readLine();
						if(line==null)//Ŭ���̾�Ʈ�� ���� ����
							break; //����
						else
							routine(line); //client�� ���� �޽��� �ѱ�
					}
				} catch (Exception e) {
					removeClient(this);
					System.err.println(sock + id + "���� ������ ���������ϴ�.");
					sendAllMessage(id + "���� ����� �����߽��ϴ�.");
				}
			}//--run--
			
			
			public void routine(String line) {
				//CHATALL : ������ ������Դϴ�.
				int idx = line.indexOf(ChatProtocol.MODE);
				String cmd = line.substring(0,idx);
				String data = line.substring(idx+1);
				if (cmd.equals(ChatProtocol.ID)) {
					id=data;
					//���ο� ������ welcome �޼��� ����
					sendAllMessage(ChatProtocol.CHATALL+ChatProtocol.MODE + id + "���� �����߽��ϴ�.");
				}else if (cmd.equals(ChatProtocol.CHATALL)) {
					sendAllMessage(ChatProtocol.CHATALL+ChatProtocol.MODE+ id + "\n" + data);
				}
			}//--routine--
	
			//Ŭ���̾�Ʈ���� ���� �ڿ��� �����ϴ� �޼ҵ�
			public void closeAll() throws IOException {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
				if (sock != null)
					sock.close();
			}
			
			public void sendMessage(String msg) {
				out.println(msg); //Ŭ���̾�Ʈ���� �޽��� ����
			}
			
		}//--ClientThread
	
	
	public static void main(String[] args) throws IOException {
		new ChatServer();
	}
	
	
}
