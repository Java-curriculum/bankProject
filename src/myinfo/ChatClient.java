package myinfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.border.EmptyBorder;




public class ChatClient extends JFrame
implements ActionListener, Runnable {
	JTextField tf_ChatMb, tf_Write; //������, ä���ۼ�
	JButton btn_send, btn_close;  // ����, ������� ��ư
	JTextArea ta_Chat; // ä�� ����â
	JLabel lbl_ChatMb; // ��(�����ڸ�, )
	Socket sock;
	BufferedReader in;
	PrintWriter out;
	
	
	public ChatClient() {
		
		//������ â ����
		setTitle("ä�û��"); //â Ÿ��Ʋ
		setSize(386, 577); // â ������
        setLocationRelativeTo(null); // â ��� ���
		getContentPane().setBackground(new Color(169, 219, 208)); //��� ��
		getContentPane().setLayout(null); //���̾ƿ� ������ ��Ȱ��ȭ �� ���� ��ġ
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//���α׷� �����ϴ� ���
		
		
		//��� ��û�� �̸� �����ִ� ��
		JPanel p1 = new JPanel();
		p1.setBounds(0, 0, 370, 75); //�г� ������, ��ġ
		p1.setBackground(new Color(169, 219, 208)); //�г� ����
		p1.setLayout(null); //���̾ƿ� ������ ��Ȱ��ȭ �� ���� ��ġ
		getContentPane().add(p1);
		
		lbl_ChatMb = new JLabel("��ȭâ"); //��
		lbl_ChatMb.setFont(new Font("���� ���", Font.BOLD, 14)); //��Ʈ ����
		lbl_ChatMb.setBounds(12, 10, 115, 15); //������, ��ġ
		p1.add(lbl_ChatMb);
		
		tf_ChatMb = new JTextField(); //ä�� ��û�� �̸� ������
		tf_ChatMb.setBorder(new EmptyBorder(0, 0, 0, 0)); //�׵θ� ����
		tf_ChatMb.setBounds(0, 35, 370, 30); //������, ��ġ
		p1.add(tf_ChatMb);

		
		// ä�� ���� �����ִ� ��
		JPanel p2 = new JPanel();
		p2.setBounds(0, 74, 370, 410); //������, ��ġ
		p2.setBackground(new Color(238, 238, 238)); // �г� ���� ����
		p2.setLayout(null); //���̾ƿ� ������ ��Ȱ��ȭ �� ���� ��ġ
		getContentPane().add(p2);
		
		
		ta_Chat = new JTextArea(); //ä�� ������
		ta_Chat.setBounds(0, 0, 370, 352); //������, ��ġ
		p2.add(ta_Chat);
		
		tf_Write = new JTextField(); //ä�� �Է� â
		tf_Write.setBounds(0, 362, 295, 38); //������, ��ġ
		p2.add(tf_Write);
		
		btn_send = new JButton("�� ��");
		btn_send.setBounds(299, 362, 71, 38); //������, ��ġ
		btn_send.setForeground(new Color(0, 0, 0)); //��Ʈ ��
		btn_send.setFont(new Font("���� ���", Font.BOLD, 14)); //��Ʈ ����
		btn_send.setBackground(new Color(255, 255, 255)); //��ư ����
		p2.add(btn_send);
		
		
		//��� ���� ��ư
		btn_close = new JButton("��� ����");
		btn_close.setForeground(Color.WHITE); //��Ʈ ��
		btn_close.setFont(new Font("���� ���", Font.BOLD, 14)); //��Ʈ ����
		btn_close.setBackground(new Color(62, 192, 196)); //��ư ����
		btn_close.setBounds(134, 494, 101, 34); //������, ��ġ
		getContentPane().add(btn_close);
		
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
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btn_close) {
			new Thread(this).start();//run�޼ҵ� ȣ��
			
		}else if(e.getSource()==btn_send) {	
			String str = tf_Write.getText();
			ta_Chat.append(ChatProtocol.ID + " : "+ str + "\n");
			sendMessage(ChatProtocol.CHATALL + ChatProtocol.MODE + str);

		}
		tf_Write.setText("");
		tf_Write.requestFocus();
	}
	

	public void connect(String host, int port) {
		try {
			sock = new Socket(host, port);
			in = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(),true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void sendMessage(String msg) {
		out.println(msg);
	}
	
	public static void main(String[] args) {
		new ChatClient();
}
}
