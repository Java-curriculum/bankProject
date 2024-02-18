package myinfo;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

public class Myinfo extends JPanel implements ActionListener{

	JButton btn_update, btn_save;
	JPanel p_contents, p_chat;
	JLabel lbl_myinfo, lbl_info, lbl_chatTitle;
	String[] lblTitle = {"이름","아이디","비밀번호","가상계좌","휴대번호"};
	JTextField[] tf_info = new JTextField[4];
	JTextField tf_phone;
	JComboBox cb_mobile;
	JTextArea ta_chatGuide;
	JButton btn_chat;
	ImageIcon img_chat;
	MyinfoDAO dao = new MyinfoDAO();
	String user_id;
	Userbean User_data;
	String [] Telecom_data = {"SKT","KT","LG U+","알뜰폰"};
	int cnt = 0;
	
	public Myinfo(String id) {
		//패널 삽입
		user_id = id;
		p_contents = new JPanel();
		p_contents.setBorder(null);
		p_contents.setBackground(Color.white); // 패널 배경색
		p_contents.setBounds(0, 0, 826, 620);
		p_contents.setLayout(null); // 레이아웃 관리자 비활성화 후 직접 배치

		// 회원정보 라벨
		p_contents.add(lbl_myinfo = new JLabel("회원정보"));
		lbl_myinfo.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbl_myinfo.setBounds(35, 38, 85, 30);

		// 내정보에 필요한 내용 라벨
		int y1 = 0;
		for (String lblText : lblTitle) { // 라벨 이름 지정
			p_contents.add(lbl_info = new JLabel(lblText));
			lbl_info.setBounds(60, 115 + y1, 75, 30); // 위치, 사이즈
			lbl_info.setBackground(new Color(62, 192, 196)); // 버튼 배경색
			lbl_info.setFont(new Font("맑은 고딕", Font.PLAIN, 17)); // 폰트 설정
			y1 += 80; // 각 버튼 y축(높이) 지정
		} // --for--

		// 텍스트 필드 메소드 호출
		infoTf();

		// 수정, 저장 버튼
		p_contents.add(btn_update = new JButton("수정하기"));
		btn_update.setFont(new Font("맑은 고딕", Font.BOLD, 16)); // 폰트 설정
		btn_update.setBackground(Color.white);
		btn_update.setBounds(60, 525, 135, 48);
		btn_update.addActionListener(this);//액션 리스너

		
		p_contents.add(btn_save = new JButton("저장하기"));
		btn_save.setForeground(new Color(255, 255, 255));
		btn_save.setFont(new Font("맑은 고딕", Font.BOLD, 16)); // 폰트 설정
		btn_save.setBackground(new Color(62, 192, 196));
		btn_save.setBounds(212, 525, 135, 48);
		btn_save.addActionListener(this);//액션 리스너

		// 채팅 안내 & 버튼
		p_contents.add(p_chat = new JPanel());
		p_chat.setBorder(new LineBorder(new Color(208, 206, 206), 2)); // 패널 테두리 설정
		p_chat.setBackground(Color.white); // 배경색
		p_chat.setBounds(425, 171, 335, 353); // 위치, 사이즈
		p_chat.setLayout(null);

		p_chat.add(lbl_chatTitle = new JLabel("1:1 채팅상담")); // 채팅 타이틀
		lbl_chatTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18)); // 폰트 설정
		lbl_chatTitle.setBounds(30, 20, 120, 30); // 위치, 사이즈

		p_chat.add(ta_chatGuide = new JTextArea("궁금한 점이 있으신가요?\n아래 버튼을 눌러 1:1 채팅 상담을\n시작해보세요."));
		ta_chatGuide.setFont(new Font("맑은 고딕", Font.PLAIN, 17)); // 폰트 설정
		ta_chatGuide.setBounds(40, 71, 280, 70);

		p_chat.add(btn_chat = new JButton(new ImageIcon("src/myinfo/counseling.png"))); // 상담사 이미지 넣기
		btn_chat.setBorderPainted(false); // 테두리 없애기
		btn_chat.setFocusPainted(false); // 선택 시 테두리 없애기
		btn_chat.setBackground(Color.white); // 배경색 설정
		btn_chat.setBounds(97, 192, 145, 130); // 위치, 사이즈
		btn_chat.addActionListener(this);//액션 리스너
		
		setVisible(true);
	}// --MainInfo--

	
	
	//텍스트 필드 db데이터 삽입
	protected void infoTf() {
        int y2 = 0;
		try {
			User_data = dao.select_user(user_id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		//이름, id, 비밀번호, 가상계좌, 휴대번호, 통신사 값 가져오기
		ArrayList<String> data = new ArrayList<String>();
		data.add(User_data.getName());
		data.add(User_data.getId());
		data.add(User_data.getPw());
		data.add(User_data.getAccount());
		data.add(User_data.getPhone());
		data.add(User_data.getTelecom());
		
		//이름, id, 비밀번호, 가상계좌 텍스트 필드
        for (int i = 0; i < tf_info.length; i++) {
            p_contents.add(tf_info[i] = new JTextField());
            tf_info[i].setBounds(60, 147 + y2, 290, 35); // 위치, 사이즈
            tf_info[i].setFont(new Font("맑은 고딕", Font.PLAIN, 16)); // 폰트설정
            tf_info[i].setText(data.get(i));
            tf_info[i].setEnabled(false); // 텍스트 필드 비활성화
            y2 += 80; // y축 설정
        }//--for문
        
        //휴대번호 텍스트 필드
        p_contents.add(tf_phone = new JTextField());
		tf_phone.setFont(new Font("맑은 고딕", Font.PLAIN, 16)); // 폰트 설정
		tf_phone.setEnabled(false); // 텍스트 필드 비활성화
		tf_phone.setBounds(60, 468, 200, 35); // 위치, 사이즈
		tf_phone.setText(data.get(4));
		
		//통신사 콤보박스
		p_contents.add(cb_mobile = new JComboBox()); // 통신사 콤보박스
		for(int i = 0 ; i < Telecom_data.length; i++) {
			cb_mobile.addItem(Telecom_data[i]); // "SKT","KT","LG U+","알뜰폰"
			
			//db에서 받아온 회원정보(User_data)가 콤보박스 통신사 데이터와 일치하면 그것을 출력하라
			if(User_data.getTelecom().equals(Telecom_data[i])) {
				System.out.println(Telecom_data[i]);
				cnt = i;
				cb_mobile.setSelectedIndex(i);
			}
		}
		cb_mobile.setBackground(Color.white); // 배경색 설정
		cb_mobile.setBounds(265, 468, 82, 35); //위치, 사이즈
		cb_mobile.setEnabled(false);//콤보박스 비활성화
    }
	
	
	//버튼 액션 이벤트
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj==btn_update) { //수정하기 버튼
			//저장 버튼 클릭 시 이름, 비밀번호, 휴대번호, 통신사 활성화
			tf_info[0].setEnabled(true);
			tf_info[2].setEnabled(true);
			tf_phone.setEnabled(true);
			cb_mobile.setEnabled(true);
			
		}else if (obj==btn_save) { //저장하기 버튼
			ArrayList<String> Update_data = new ArrayList<>();
			Update_data.add(tf_info[0].getText());
			Update_data.add(tf_info[2].getText());
			Update_data.add(tf_phone.getText());
			String selectedItem = (String) cb_mobile.getSelectedItem();
			Update_data.add(selectedItem);
			Update_data.add(user_id);
			
			//저장 버튼 클릭 시 이름, 비밀번호, 휴대번호, 통신사 비활성화
			tf_info[0].setEnabled(false);
			tf_info[2].setEnabled(false);
			tf_phone.setEnabled(false);
			cb_mobile.setEnabled(false);
			dao.update_user(Update_data); //db에 수정 반영
			
		}else if (obj==btn_chat) {
			ChatClient chatClient = new ChatClient();
			chatClient.setList(user_id);
			System.out.println(user_id);
            chatClient.connect("127.0.0.1", 8002); // 서버에 연결
            chatClient.setVisible(true); // 채팅 클라이언트 창 표시

	}
	
}//--actionPerformed--
	
	//패널 전환하기 위함
	public JPanel getMainPanel() {
        return p_contents;
    }
	
}
