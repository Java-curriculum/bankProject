package myinfo;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JList;
//import javax.swing.AbstractListModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.JButton;

/*�ѱ� ���� �� : properties-MS949�� ����*/

public class ChatClient extends JFrame
implements ActionListener {

	JList li_ChatMb;
	JTextField tf_Write; //������, ä���ۼ�
	JButton btn_send, btn_close;  // ����, ������� ��ư
	JTextArea ta_Chat; // ä�� ����â
	JLabel lbl_ChatMb; // ��(�����ڸ�, )
	Socket sock;
	BufferedReader in;
	PrintWriter out;
	
	public ChatClient() {
		//������ â ����
		setTitle("ä�û��"); //â Ÿ��Ʋ
		setSize(388, 577); // â ������
        setLocationRelativeTo(null); // â ��� ���
		getContentPane().setBackground(new Color(169, 219, 208)); //��� ��
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //���α׷� �����ϴ� ���
		getContentPane().setLayout(null); //���̾ƿ� ������ ��Ȱ��ȭ �� ���� ��ġ
		
		//����ڸ� ���
		JPanel p1 = new JPanel();
		p1.setBackground(new Color(169, 219, 208)); //�г� ����
		p1.setBounds(0, 0, 372, 87); //��ġ, ������
		p1.setLayout(null); //���̾ƿ� ������ ��Ȱ��ȭ �� ���� ��ġ
		add(p1);
		
		
		lbl_ChatMb = new JLabel("��ȭâ");
		lbl_ChatMb.setBounds(12, 10, 57, 15);  //��ġ, ������
		lbl_ChatMb.setFont(new Font("���� ���", Font.BOLD, 14)); //��Ʈ ����
		p1.add(lbl_ChatMb);
		
		//����ڸ� JList�� ���
		/*name_list�� ä�� ������ user�� admin �̸� ����*/
		String[] name_list= {"user_name", "admin"}; //�ӽ� ����Ʈ
		JList<String> li_ChatMb = new JList<>(name_list);
		li_ChatMb.setFont(new Font("���� ���", Font.PLAIN, 12)); //��Ʈ ����
		li_ChatMb.setBounds(0, 35, 372, 42); //��ġ, ������
		p1.add(li_ChatMb);
		
		//ä��â
		JPanel p2 = new JPanel();
		p2.setBackground(new Color(238, 238, 238)); //�г� ����
		p2.setBounds(0, 86, 372, 395); // ��ġ, ������
		p2.setLayout(null); //���̾ƿ� ������ ��Ȱ��ȭ �� ���� ��ġ
		getContentPane().add(p2); 
		
		ta_Chat = new JTextArea(); //ä�� ������
		ta_Chat.setFont(new Font("���� ���", Font.PLAIN, 14)); //��Ʈ����
		ta_Chat.setBounds(0, 0, 371, 340); //������, ��ġ
		p2.add(ta_Chat);
		
		tf_Write = new JTextField(); //ä�� �Է� â
		tf_Write.setBounds(0, 350, 280, 35); //������, ��ġ
		p2.add(tf_Write);
		
		btn_send = new JButton("�� ��");
		btn_send.setBackground(new Color(255, 255, 255)); //��ư ����
		btn_send.setFont(new Font("���� ���", Font.BOLD, 14)); //��Ʈ ����
		btn_send.setBounds(286, 350, 80, 35); //������, ��ġ
		p2.add(btn_send);
		
		btn_close = new JButton("��� ����");
		btn_close.setBackground(new Color(62, 192, 196)); //��ư ����
		btn_close.setForeground(new Color(255, 255, 255));  //��Ʈ ��
		btn_close.setFont(new Font("���� ���", Font.BOLD, 14));  //��Ʈ ����
		btn_close.setBounds(142, 491, 97, 38); //������, ��ġ
		add(btn_close);
		
		//��ư �̺�Ʈ �޼ҵ� ȣ��
		btn_send.addActionListener(this);
		btn_close.addActionListener(this);
				
		setVisible(true);
	}
	
	public void run() {
		try {
			String host = "127.0.0.1";
			int port = 8002;
			connect(host,port);
			while(true) {
				ta_Chat.append(in.readLine()); // ù ��° ������ �о �ؽ�Ʈ ������ �߰�
				String line = in.readLine();
				if(line==null)
					break;
				else
					routine(line);
			}//--while--
		}catch (Exception e) {
			e.printStackTrace();
		}
	}//--run--
	
	
	public void routine(String line) {
		int idx = line.indexOf(ChatProtocol.MODE);
		String cmd = line.substring(0,idx);
		String data = line.substring(idx+1);
		
		if (cmd.equals(ChatProtocol.CHAT)|| cmd.equals(ChatProtocol.CHATALL)) {
			ta_Chat.append(data+"\n");
		}
	} //--routine--
	
	
	public void connect(String host, int port) {
		try {
			sock = new Socket(host, port);
			in = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(),true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//--connect--
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btn_close) {
			this.dispose();
		}else if (e.getSource()==btn_send) {
			String str = tf_Write.getText();
			sendMessage(ChatProtocol.CHATALL + ChatProtocol.MODE+str);
			}
		}//--actionPerformed--
	
	
	public void sendMessage(String msg) {
		out.println(msg); // Client -> Server
	}	
		
	public static void main(String[] args) {
		new ChatClient();
	}
}
