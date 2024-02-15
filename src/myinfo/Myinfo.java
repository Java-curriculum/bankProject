package myinfo;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class Myinfo extends JPanel {

	JButton btn_update, btn_save;
	JPanel p_contents, p_chat;
	JLabel lbl_myinfo, lbl_info, lbl_chatTitle;
	String[] lblTitle = {"�̸�","���̵�","��й�ȣ","�������","�޴��ȣ"};
	JTextField[] tf_info = new JTextField[4];
	JTextField tf_phone;
	JComboBox cb_mobile;
	JTextArea ta_chatGuide;
	JButton btn_chat;
	ImageIcon img_chat;
	
	public Myinfo() {
		//�г� ����
		p_contents = new JPanel();
		p_contents.setBorder(null);
		p_contents.setBackground(Color.white); // �г� ����
		p_contents.setBounds(198, 21, 826, 620);
		p_contents.setLayout(null); // ���̾ƿ� ������ ��Ȱ��ȭ �� ���� ��ġ

		// ȸ������ ��
		p_contents.add(lbl_myinfo = new JLabel("ȸ������"));
		lbl_myinfo.setFont(new Font("���� ���", Font.BOLD, 18));
		lbl_myinfo.setBounds(35, 38, 75, 30);

		// �������� �ʿ��� ���� ��
		int y1 = 0;
		for (String lblText : lblTitle) { // �� �̸� ����
			p_contents.add(lbl_info = new JLabel(lblText));
			lbl_info.setBounds(60, 115 + y1, 75, 30); // ��ġ, ������
			lbl_info.setBackground(new Color(62, 192, 196)); // ��ư ����
			lbl_info.setFont(new Font("���� ���", Font.PLAIN, 17)); // ��Ʈ ����
			y1 += 80; // �� ��ư y��(����) ����
			// lbl_info.addActionListener(this);
		} // --for--

		// �� ������ �ʿ��� �ؽ�Ʈ �ʵ�� �޺��ڽ�
		int y2 = 0;
		for (int i = 0; i < tf_info.length; i++) {
			p_contents.add(tf_info[i] = new JTextField());
			tf_info[i].setBounds(60, 147 + y2, 290, 35); // ��ġ, ������
			tf_info[i].setFont(new Font("���� ���", Font.PLAIN, 16)); // ��Ʈ����
			tf_info[i].setEnabled(false); // �ؽ�Ʈ �ʵ� ��Ȱ��ȭ
			y2 += 80; // y�� ����
			//tf_info[i].addActionListener(this);
		} // --for--

		p_contents.add(tf_phone = new JTextField()); // �޴���ȭ �ؽ�Ʈ�ʵ�
		tf_phone.setFont(new Font("���� ���", Font.PLAIN, 16)); // ��Ʈ ����
		tf_phone.setEnabled(false); // �ؽ�Ʈ �ʵ� ��Ȱ��ȭ
		tf_phone.setBounds(60, 468, 200, 35); // ��ġ, ������

		p_contents.add(cb_mobile = new JComboBox()); // ��Ż� �޺��ڽ�
		cb_mobile.setBackground(Color.white); // ���� ����
		cb_mobile.setBounds(265, 468, 82, 35); // ��ġ, ������

		// ����, ���� ��ư
		p_contents.add(btn_update = new JButton("�����ϱ�"));
		btn_update.setFont(new Font("���� ���", Font.BOLD, 16)); // ��Ʈ ����
		btn_update.setBackground(Color.white);
		btn_update.setBounds(60, 525, 135, 48);

		p_contents.add(btn_save = new JButton("�����ϱ�"));
		btn_save.setForeground(new Color(255, 255, 255));
		btn_save.setFont(new Font("���� ���", Font.BOLD, 16)); // ��Ʈ ����
		btn_save.setBackground(new Color(62, 192, 196));
		btn_save.setBounds(212, 525, 135, 48);

		// ä�� �ȳ� & ��ư
		p_contents.add(p_chat = new JPanel());
		p_chat.setBorder(new LineBorder(new Color(208, 206, 206), 2)); // �г� �׵θ� ����
		p_chat.setBackground(Color.white); // ����
		p_chat.setBounds(425, 171, 335, 353); // ��ġ, ������
		p_chat.setLayout(null);

		p_chat.add(lbl_chatTitle = new JLabel("1:1 ä�û��")); // ä�� Ÿ��Ʋ
		lbl_chatTitle.setFont(new Font("���� ���", Font.BOLD, 18)); // ��Ʈ ����
		lbl_chatTitle.setBounds(30, 20, 120, 30); // ��ġ, ������

		p_chat.add(ta_chatGuide = new JTextArea("�ñ��� ���� �����Ű���?\n�Ʒ� ��ư�� ���� 1:1 ä�� �����\n�����غ�����."));
		ta_chatGuide.setFont(new Font("���� ���", Font.PLAIN, 17)); // ��Ʈ ����
		ta_chatGuide.setBounds(40, 71, 280, 70);

		p_chat.add(btn_chat = new JButton(new ImageIcon("src/myinfo/counseling.png"))); // ���� �̹��� �ֱ�
		btn_chat.setBorderPainted(false); // �׵θ� ���ֱ�
		btn_chat.setFocusPainted(false); // ���� �� �׵θ� ���ֱ�
		btn_chat.setBackground(Color.white); // ���� ����
		btn_chat.setBounds(97, 192, 145, 130); // ��ġ, ������
		setVisible(true);
	}// --MainInfo--

	
}
