package myinfo;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class Myinfo extends JPanel {

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
	
	public Myinfo() {
		//패널 삽입
		p_contents = new JPanel();
		p_contents.setBorder(null);
		p_contents.setBackground(Color.white); // 패널 배경색
		p_contents.setBounds(198, 21, 826, 620);
		p_contents.setLayout(null); // 레이아웃 관리자 비활성화 후 직접 배치

		// 회원정보 라벨
		p_contents.add(lbl_myinfo = new JLabel("회원정보"));
		lbl_myinfo.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbl_myinfo.setBounds(35, 38, 75, 30);

		// 내정보에 필요한 내용 라벨
		int y1 = 0;
		for (String lblText : lblTitle) { // 라벨 이름 지정
			p_contents.add(lbl_info = new JLabel(lblText));
			lbl_info.setBounds(60, 115 + y1, 75, 30); // 위치, 사이즈
			lbl_info.setBackground(new Color(62, 192, 196)); // 버튼 배경색
			lbl_info.setFont(new Font("맑은 고딕", Font.PLAIN, 17)); // 폰트 설정
			y1 += 80; // 각 버튼 y축(높이) 지정
			// lbl_info.addActionListener(this);
		} // --for--

		// 내 정보에 필요한 텍스트 필드와 콤보박스
		int y2 = 0;
		for (int i = 0; i < tf_info.length; i++) {
			p_contents.add(tf_info[i] = new JTextField());
			tf_info[i].setBounds(60, 147 + y2, 290, 35); // 위치, 사이즈
			tf_info[i].setFont(new Font("맑은 고딕", Font.PLAIN, 16)); // 폰트설정
			tf_info[i].setEnabled(false); // 텍스트 필드 비활성화
			y2 += 80; // y축 설정
			//tf_info[i].addActionListener(this);
		} // --for--

		p_contents.add(tf_phone = new JTextField()); // 휴대전화 텍스트필드
		tf_phone.setFont(new Font("맑은 고딕", Font.PLAIN, 16)); // 폰트 설정
		tf_phone.setEnabled(false); // 텍스트 필드 비활성화
		tf_phone.setBounds(60, 468, 200, 35); // 위치, 사이즈

		p_contents.add(cb_mobile = new JComboBox()); // 통신사 콤보박스
		cb_mobile.setBackground(Color.white); // 배경색 설정
		cb_mobile.setBounds(265, 468, 82, 35); // 위치, 사이즈

		// 수정, 저장 버튼
		p_contents.add(btn_update = new JButton("수정하기"));
		btn_update.setFont(new Font("맑은 고딕", Font.BOLD, 16)); // 폰트 설정
		btn_update.setBackground(Color.white);
		btn_update.setBounds(60, 525, 135, 48);

		p_contents.add(btn_save = new JButton("저장하기"));
		btn_save.setForeground(new Color(255, 255, 255));
		btn_save.setFont(new Font("맑은 고딕", Font.BOLD, 16)); // 폰트 설정
		btn_save.setBackground(new Color(62, 192, 196));
		btn_save.setBounds(212, 525, 135, 48);

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
		setVisible(true);
	}// --MainInfo--

	
}
