package testJava01;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogInScene extends JPanel{
	SceneManager sceneManager = SceneManager.getInstance();
	TestSocket testSocket = TestSocket.getInstance();
	
	//각종 씬
	HomeScene homeScene = HomeScene.getInstance();
	
	private static final LogInScene instance = new LogInScene();
	//------------
	private JTextField usernameField;
    private JPasswordField passwordField;
	
	private LogInScene() {
        setSize(1000, 1000);
        setLayout(new BorderLayout());
        
        // 로그인 폼을 담을 패널
        JPanel loginFormPanel = new JPanel(new GridLayout(3, 2));

        // 아이디 입력 필드
        JLabel usernameLabel = new JLabel("아이디:");
        usernameField = new JTextField();
        loginFormPanel.add(usernameLabel);
        loginFormPanel.add(usernameField);
        
     // 비밀번호 입력 필드
        JLabel passwordLabel = new JLabel("비밀번호:");
        passwordField = new JPasswordField();
        loginFormPanel.add(passwordLabel);
        loginFormPanel.add(passwordField);

        // 로그인 버튼
        JButton loginButton = new JButton("로그인");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // TODO: 로그인 기능 구현
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);

                // 로그인 성공 시 다음 동작 수행 아이디 : 123, 비밀번호 : 789 입니다.
                logInRequest(username, password);
                JOptionPane.showMessageDialog(LogInScene.this, "로그인 성공!");
                
                //homeScene으로 이동
                sceneManager.switchToScene(homeScene);
            }
        });

        loginFormPanel.add(new JLabel()); // 빈 레이블 추가
        loginFormPanel.add(loginButton);

        // 로그인 폼 패널을 중앙에 배치
        add(loginFormPanel, BorderLayout.CENTER);
        
	}
	
	private boolean logInRequest(String username,String password) {
		//이곳은 로그인 처리 로직이다.
		boolean request = false;
		
		request = testSocket.sendCredentialsAndReceiveName(username, password);
				
		return request;
	}
	
	
	public static LogInScene getInstance() {
	     return instance;
	}
	

}
