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
	JTextField tf_ChatMb, tf_Write; //참가자, 채팅작성
	JButton btn_send, btn_close;  // 전송, 상담종료 버튼
	JTextArea ta_Chat; // 채팅 내용창
	JLabel lbl_ChatMb; // 라벨(참가자명, )
	Socket sock;
	BufferedReader in;
	PrintWriter out;
	
	
	public ChatClient() {
		
		//프레임 창 설정
		setTitle("채팅상담"); //창 타이틀
		setSize(386, 577); // 창 사이즈
        setLocationRelativeTo(null); // 창 가운데 띄움
		getContentPane().setBackground(new Color(169, 219, 208)); //배경 색
		getContentPane().setLayout(null); //레이아웃 관리자 비활성화 후 직접 배치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//프로그램 종료하는 방법
		
		
		//상담 신청자 이름 보여주는 곳
		JPanel p1 = new JPanel();
		p1.setBounds(0, 0, 370, 75); //패널 사이즈, 위치
		p1.setBackground(new Color(169, 219, 208)); //패널 배경색
		p1.setLayout(null); //레이아웃 관리자 비활성화 후 직접 배치
		getContentPane().add(p1);
		
		lbl_ChatMb = new JLabel("대화창"); //라벨
		lbl_ChatMb.setFont(new Font("맑은 고딕", Font.BOLD, 14)); //폰트 설정
		lbl_ChatMb.setBounds(12, 10, 115, 15); //사이즈, 위치
		p1.add(lbl_ChatMb);
		
		tf_ChatMb = new JTextField(); //채팅 신청자 이름 보여줌
		tf_ChatMb.setBorder(new EmptyBorder(0, 0, 0, 0)); //테두리 없앰
		tf_ChatMb.setBounds(0, 35, 370, 30); //사이즈, 위치
		p1.add(tf_ChatMb);

		
		// 채팅 내용 보여주는 곳
		JPanel p2 = new JPanel();
		p2.setBounds(0, 74, 370, 410); //사이즈, 위치
		p2.setBackground(new Color(238, 238, 238)); // 패널 배경색 설정
		p2.setLayout(null); //레이아웃 관리자 비활성화 후 직접 배치
		getContentPane().add(p2);
		
		
		ta_Chat = new JTextArea(); //채팅 보여줌
		ta_Chat.setBounds(0, 0, 370, 352); //사이즈, 위치
		p2.add(ta_Chat);
		
		tf_Write = new JTextField(); //채팅 입력 창
		tf_Write.setBounds(0, 362, 295, 38); //사이즈, 위치
		p2.add(tf_Write);
		
		btn_send = new JButton("전 송");
		btn_send.setBounds(299, 362, 71, 38); //사이즈, 위치
		btn_send.setForeground(new Color(0, 0, 0)); //폰트 색
		btn_send.setFont(new Font("맑은 고딕", Font.BOLD, 14)); //폰트 설정
		btn_send.setBackground(new Color(255, 255, 255)); //버튼 배경색
		p2.add(btn_send);
		
		
		//상담 종료 버튼
		btn_close = new JButton("상담 종료");
		btn_close.setForeground(Color.WHITE); //폰트 색
		btn_close.setFont(new Font("맑은 고딕", Font.BOLD, 14)); //폰트 설정
		btn_close.setBackground(new Color(62, 192, 196)); //버튼 배경색
		btn_close.setBounds(134, 494, 101, 34); //사이즈, 위치
		getContentPane().add(btn_close);
		
		//버튼 이벤트 메소드 호출
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
			new Thread(this).start();//run메소드 호출
			
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
