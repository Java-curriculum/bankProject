package testJava01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
//해야할것 홈화면에 서버에서 받은 정보를 띄어보자.@@(회원정보 띄우기) 
public class HomeScene extends JPanel implements ActionListener{
	//SceneManager를 위한 변수선언
	SceneManager sceneManager = SceneManager.getInstance();	
	
	private static final HomeScene instance = new HomeScene();
	//----------------------ui변수 선언
	JButton[] button = new JButton[7];//사이드 네비게이션 버튼
	String[] button_str = {"로고","환율","환전","지갑","거래소","내정보","로그아웃"};
	
	private HomeScene() {
		//HomeScene의 레이아웃 설정
		setBackground(Color.GRAY);
		setLayout(new BorderLayout());
				
		JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(7, 1)); // 5개의 버튼을 세로로 배치        
        //String[] button_str = new String[7];
        
        // 5개의 버튼을 생성하고 왼쪽에 위치시킴
        for (int i = 0; i < 7; i++) {       	
            button[i] = new JButton(button_str[i]);
            button[i].addActionListener(this);
            leftPanel.add(button[i]);
        }        
     // 왼쪽 JPanel을 왼쪽 상단에 배치하고, 높이를 화면의 높이와 동일하게 설정
        Dimension screenSize = new Dimension(1000,800);
        leftPanel.setPreferredSize(new Dimension(leftPanel.getPreferredSize().width, screenSize.height));         
        add(leftPanel,BorderLayout.WEST);                
        setVisible(true);
	}
	
	
		


	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == button[6]) {
			//로그아웃 버튼
			LogInScene loginScene = LogInScene.getInstance();
			sceneManager.switchToScene(loginScene);
		}
		else {
			System.out.println("로그아웃 버튼이 아닙니다.");
		}
		
	}
	//sceneManager함수01
	public static HomeScene getInstance() {
		return instance;
	}
}
