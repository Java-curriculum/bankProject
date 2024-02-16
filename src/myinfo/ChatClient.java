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

/*한글 깨질 때 : properties-MS949로 변경*/

public class ChatClient extends JFrame
implements ActionListener {

	JList li_ChatMb;
	JTextField tf_Write; //참가자, 채팅작성
	JButton btn_send, btn_close;  // 전송, 상담종료 버튼
	JTextArea ta_Chat; // 채팅 내용창
	JLabel lbl_ChatMb; // 라벨(참가자명, )
	Socket sock;
	BufferedReader in;
	PrintWriter out;
	
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
				ta_Chat.append(in.readLine()); // 첫 번째 라인을 읽어서 텍스트 영역에 추가
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
