package myinfo;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JList;
//import javax.swing.AbstractListModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.JButton;


public class ChatClient extends JFrame
implements ActionListener, Runnable {

	JList li_ChatMb; //참가자
	JTextField tf_Write; // 채팅작성
	JButton btn_send, btn_close;  // 전송, 상담종료 버튼
	JTextArea ta_Chat; // 채팅 내용창
	JLabel lbl_ChatMb; // 라벨(참가자명, )
	Socket sock;
	BufferedReader in;
	PrintWriter out;
	String id;
	
	public ChatClient() {
		//프레임 창 설정
		setTitle("채팅상담"); //창 타이틀
		setSize(388, 577); // 창 사이즈
        setLocationRelativeTo(null); // 창 가운데 띄움
		getContentPane().setBackground(new Color(169, 219, 208)); //배경 색
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //프로그램 종료하는 방법
		getContentPane().setLayout(null); //레이아웃 관리자 비활성화 후 직접 배치
		
		//상담자명 출력
		JPanel p1 = new JPanel();
		p1.setBackground(new Color(169, 219, 208)); //패널 배경색
		p1.setBounds(0, 0, 372, 87); //위치, 사이즈
		p1.setLayout(null); //레이아웃 관리자 비활성화 후 직접 배치
		add(p1);
		
		
		lbl_ChatMb = new JLabel("대화창");
		lbl_ChatMb.setBounds(12, 10, 57, 15);  //위치, 사이즈
		lbl_ChatMb.setFont(new Font("맑은 고딕", Font.BOLD, 14)); //폰트 설정
		p1.add(lbl_ChatMb);
		
		//상담자명 JList로 출력
		/*name_list에 채팅 참여한 user와 admin 이름 삽입*/
		String[] name_list= {"user_name", "admin"}; //임시 리스트
		JList<String> li_ChatMb = new JList<>(name_list);
		li_ChatMb.setFont(new Font("맑은 고딕", Font.PLAIN, 12)); //폰트 설정
		li_ChatMb.setBounds(0, 35, 372, 42); //위치, 사이즈
		p1.add(li_ChatMb);
		
		//채팅창
		JPanel p2 = new JPanel();
		p2.setBackground(new Color(238, 238, 238)); //패널 배경색
		p2.setBounds(0, 86, 372, 395); // 위치, 사이즈
		p2.setLayout(null); //레이아웃 관리자 비활성화 후 직접 배치
		getContentPane().add(p2); 
		
		ta_Chat = new JTextArea(); //채팅 보여줌
		ta_Chat.setFont(new Font("맑은 고딕", Font.PLAIN, 14)); //폰트설정
		ta_Chat.setBounds(0, 0, 371, 340); //사이즈, 위치
		p2.add(ta_Chat);
		
		tf_Write = new JTextField(); //채팅 입력 창
		tf_Write.setBounds(0, 350, 280, 35); //사이즈, 위치
		p2.add(tf_Write);
		
		btn_send = new JButton("전 송");
		btn_send.setBackground(new Color(255, 255, 255)); //버튼 배경색
		btn_send.setFont(new Font("맑은 고딕", Font.BOLD, 14)); //폰트 설정
		btn_send.setBounds(286, 350, 80, 35); //사이즈, 위치
		p2.add(btn_send);
		
		btn_close = new JButton("상담 종료");
		btn_close.setBackground(new Color(62, 192, 196)); //버튼 배경색
		btn_close.setForeground(new Color(255, 255, 255));  //폰트 색
		btn_close.setFont(new Font("맑은 고딕", Font.BOLD, 14));  //폰트 설정
		btn_close.setBounds(142, 491, 97, 38); //사이즈, 위치
		add(btn_close);
		
		//버튼 이벤트 메소드 호출
		tf_Write.addActionListener(this); //엔터키 액션리스너
		btn_send.addActionListener(this); //전송 버튼 액션 리스너
		btn_close.addActionListener(this); // 상담 종료 버튼 액션 리스너
				
		setVisible(true);
	}
	
	public void run() {
		try {
			while(true) {
				ta_Chat.append(in.readLine()+"\n");
				tf_Write.requestFocus();
			}
		} catch (Exception e) {
			System.err.println("Error in run");
			e.printStackTrace();
			System.exit(1);
		}
	}//--run--
	
	
	public void connect(String host, int port) {
		try {
			sock = new Socket(host, port);
			in = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(),true);
			ta_Chat.append(in.readLine()+"\n");
			tf_Write.requestFocus();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Thread Start -> run 메서드 호출
		new Thread(this).start();
	}//--connect--
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj==btn_close) {
			 this.dispose();	
		}else if (obj==btn_send || obj==tf_Write) { //전송 버튼 또는 텍스트필(엔터)
			String str = tf_Write.getText().trim();
			if (str.isEmpty())
				return; //서버로 전송하지 않고 메소드 종료
			if (id==null) {
				//처음 아이디 입력
				id=str;
				setTitle(getTitle()+ "-" + "[" + id + "]");
				ta_Chat.setText("채팅상담을 시작합니다.\n");
				}
			out.println(str);//서버전송
			tf_Write.setText("");
			tf_Write.requestFocus();
			}
		}//--actionPerformed--
	
	public static void main(String[] args) {
		new ChatClient();
	}
}
