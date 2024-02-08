package testJava01;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

//각각의 씬을 표현하는 클래스
class Scene1 extends JPanel {
	SceneManager sceneManager = SceneManager.getInstance();
	Scene2 scene2 = Scene2.getInstance();
 private static final Scene1 instance = new Scene1();

 private Scene1() {
     setBackground(Color.RED);
     // 씬 1 초기화 코드
     JButton button = new JButton("Test");
     this.add(button);
     ActionListener listener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("버튼클릭");
			sceneManager.switchToScene(scene2);
		}
	};
	button.addActionListener(listener);
 }

 public static Scene1 getInstance() {
     return instance;
 }
}

class Scene2 extends JPanel {
    private static final Scene2 instance = new Scene2();

    private Scene2() {
        setBackground(Color.BLUE);
        // 씬 2 초기화 코드
    }

    public static Scene2 getInstance() {
        return instance;
    }
}

// 싱글톤 씬 전환 매니저
class SceneManager {
    private static SceneManager instance;
    JFrame frame;

    private SceneManager() {
        frame = new JFrame("Scene Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(new CardLayout());
        frame.setVisible(true);
    }
    public void switchToScene(JPanel scene) {
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        cardLayout.show(frame.getContentPane(), scene.getClass().getName());
    }

	public static SceneManager getInstance() {
		
		if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
	}
}
