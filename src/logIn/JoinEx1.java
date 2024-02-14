package logIn;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class JoinPanel extends JPanel {
   
    public JoinPanel() {
       setBackground(new Color(255, 255, 255)); //�г� �� ����
    } 
    
    // �гο� �׸���
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // �׷��� ������ ���� ��� �̰��� �׸��� �ڵ带 �߰��մϴ�.
    }
}


public class JoinEx1 extends JFrame implements ActionListener {
   
   TextField idTf, pwTf, checkpwTf, eTf, nameTf, phTf;
   Label idl, pwl, checkpwl, el, namel, phl, msgl;
   
   JButton duBtn = new JButton();
   JButton joinBtn = new JButton();
   JButton cancelBtn = new JButton();
   
   Color customColor = new Color(169, 219, 208);
   Color customColor2 = new Color(62,192,196);
   Color customColor3 = new Color(255, 255, 255);
   
   String title = "ȸ������";
   Font font = new Font("Aharoni ����", Font.BOLD, 25);
   
   Choice ch;
   String telecom[] = { "��Ż� ����", "KT", "LG U+", "SKT" };
   
   public JoinEx1() {
      getContentPane().setBackground(new Color(169, 219, 208)); //â ���� ����
      setSize(450, 400);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(null);
      
      ch = new Choice();
      msgl = new Label(title);
        
      idl = new Label("���̵�");
      pwl = new Label("��й�ȣ");
      checkpwl = new Label("��й�ȣ Ȯ��");
      el = new Label("�̸���");
      namel = new Label("�̸�");
      phl = new Label("��ȭ��ȣ");
      
      idTf = new TextField("");
      pwTf = new TextField("");
      checkpwTf = new TextField("");
      eTf = new TextField("");
      nameTf = new TextField("");
      phTf = new TextField("");
      
      duBtn = new JButton("�ߺ�");
      joinBtn = new JButton("����");
      cancelBtn = new JButton("���");
      
      ch.setBounds(285, 245, 90, 50);
   
      idl.setBounds(60, 40, 50, 35);
      idTf.setBounds(130, 40, 180, 35);
      
      pwl.setBounds(60, 80, 50, 35);
      pwTf.setBounds(130, 80, 245, 35);
      
      checkpwl.setBounds(40, 120, 80, 35);
      checkpwTf.setBounds(130, 120, 245, 35);
      
      el.setBounds(60, 160, 50, 35);
      eTf.setBounds(130, 160, 245, 35);
      
      namel.setBounds(60, 200, 50, 35);
      nameTf.setBounds(130, 200, 245, 35);
      
      phl.setBounds(60, 240, 50, 35);
      phTf.setBounds(130, 240, 150, 35);
      
      duBtn.setBounds(315, 40, 60, 35);
      duBtn.setBackground(customColor3);
      
      joinBtn.setBackground(customColor2);
      joinBtn.setBounds(130, 290, 160, 35);
      joinBtn.setBorder(BorderFactory.createEmptyBorder());
      
      cancelBtn.setBackground(customColor3);
      cancelBtn.setBounds(295, 290, 80, 35);
      cancelBtn.setBorder(BorderFactory.createEmptyBorder());
      
      msgl.setBounds(180, 30, 200, 35);
      msgl.setFont(font);
      msgl.setForeground(customColor3);
      joinBtn.addActionListener(this);
      
      for (int i = 0; i < telecom.length; i++) {
         ch.add(telecom[i]);
      }
      
      //JLabel�� �߰��Ͽ� ������ ��� ���� �׸�
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(font);
        titleLabel.setForeground(new Color(255, 255, 255)); // ���� ���� ����
        titleLabel.setBounds(280, 50, 300, 35);
        add(titleLabel);
        
        JoinPanel joinPanel = new JoinPanel();
        joinPanel.setLayout(null);
        joinPanel.setBounds(100, 100, 450, 350);
        add(joinPanel);
        
        joinPanel.add(ch);
      
        joinPanel.add(idl);
        joinPanel.add(idTf);
      
        joinPanel.add(pwl);
        joinPanel.add(pwTf);
        
        joinPanel.add(checkpwl);
        joinPanel.add(checkpwTf);
      
        joinPanel.add(el);
        joinPanel.add(eTf);
      
        joinPanel.add(namel);
        joinPanel.add(nameTf);
      
        joinPanel.add(phl);
        joinPanel.add(phTf);
      
        joinPanel.add(duBtn);
        joinPanel.add(joinBtn);
        joinPanel.add(cancelBtn);
      
        joinPanel.add(msgl);
      
	    setVisible(true);
	    validate();
      
      joinBtn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
        	 //���̵�, ��й�ȣ, �̸���, �̸�, ��ȭ��ȣ ��� �ԷµǾ����� Ȯ��
        	 String id = idTf.getText().trim();
             String pw = pwTf.getText().trim();
             String checkpw = checkpwTf.getText().trim();
             String email = eTf.getText().trim();
             String name = nameTf.getText().trim();
             String phone = phTf.getText().trim();
             String telecom = ch.getSelectedItem(); // ���õ� ��Ż� ��������
             
             //��� �Է� �ʵ忡 ���� �ִ��� Ȯ��
             if (id.isEmpty() || pw.isEmpty() || checkpw.isEmpty() || email.isEmpty() || name.isEmpty() || phone.isEmpty()
                     || telecom.equals("��Ż� ����")) { // ��Ż� ���� ���� Ȯ�� �߰�
                 // �Է� �ʵ� �� �ϳ��� ������� ��� �޽��� ǥ��
                 msgl.setText("������ ��� �Է����ּ���.");
                 msgl.setForeground(Color.RED);
              } else {
            	// �����ͺ��̽��� ����� ���� ����
                  DatabaseManager dbManager = new DatabaseManager();
                  boolean isSuccess = dbManager.insertUser(id, pw, checkpw, email, name, phone);
                  
                  if (isSuccess) {
                	  msgl.setText("������ �Ϸ�Ǿ����ϴ�.");
                      msgl.setForeground(Color.GREEN);
                  } else {
                	  msgl.setText("���Կ� �����߽��ϴ�. �ٽ� �õ����ּ���.");
                      msgl.setForeground(Color.RED);
                  }
                  dispose();
                  new HomeEx1();
              }
         }
      });
      
      cancelBtn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               Object obj = e.getSource();
               if(obj==cancelBtn)/*��� ��ư Ŭ�� ��*/ {
                  dispose();
                  new LoginEx1();
               }else if(obj==cancelBtn) {
                  System.out.println("��� ��ư�� �ƴմϴ�.");
               }
            }catch (Exception e2) {
               e2.printStackTrace();
         }
      }
      });
      
      // �ߺ� Ȯ�� ��ư ������ ����
      duBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idTf.getText().trim(); // �Էµ� ���̵� ��������

                // �����ͺ��̽� �Ŵ��� �ν��Ͻ� ����
                DatabaseManager dbManager = new DatabaseManager();

                // �ߺ� Ȯ�� ����
                boolean isDuplicate = dbManager.checkDuplicateId(id);

                // ����� ���� �޽��� ����
                if (isDuplicate) {
                    msgl.setText("�ߺ��� ���̵��Դϴ�.");
                    msgl.setForeground(Color.RED);
                } else {
                    msgl.setText("��� ������ ���̵��Դϴ�.");
                    msgl.setForeground(Color.GREEN);
                }
            }
        });
   }
   
   public class DatabaseManager {
      
      // JDBC �����ͺ��̽� ���� ����
      Connection conn=null; //DB Ŀ�ؼ� ���� ��ü
	  private static final String DB_URL = "jdbc:mysql://localhost:3306/test"; // �����ͺ��̽� URL
	  private static final String DB_USER = "root"; // �����ͺ��̽� ����� �̸�
	  private static final String DB_PASSWORD = "1234"; // �����ͺ��̽� ��ȣ
      
      //���̵� �ߺ� Ȯ�� �޼���
      public boolean checkDuplicateId(String id) {
         boolean isDuplicate = false;
         Connection conn = null;
         PreparedStatement pstmt = null;
         ResultSet rs = null;
         
         try {
            
            // JDBC ����̹� �ε�
                Class.forName("com.mysql.cj.jdbc.Driver");
                
            //�����ͺ��̽� ����
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            //SQL ���� �ۼ�
            String sql = "SELECT COUNT(*) FROM member WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            
            // ���� ���� �� ��� ��������
               rs = pstmt.executeQuery();
               
               // ����� �ִ� ��� �ߺ� ���� Ȯ��
               if (rs.next()) {
                   int count = rs.getInt(1);
                   if (count > 0) {
                       isDuplicate = true;
                   }
               }
         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            // ���� �� ���ҽ� ����
            try {
               if (rs != null) rs.close();
                   if (pstmt != null) pstmt.close();
                   if (conn != null) conn.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
         return isDuplicate;
      }

    //����� ������ �����ͺ��̽��� �����ϴ� �޼���
	public boolean insertUser(String id, String pw, String checkpw, String email, String name, String phone) {
		try {
			// JDBC ����̹� �ε�
            Class.forName("com.mysql.cj.jdbc.Driver");

            // �����ͺ��̽� ����
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // SQL ���� �ۼ�
            String sql = "INSERT INTO member (id, pwd, checkpw, email, name, phone_num) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pw);
            pstmt.setString(3, checkpw);
            pstmt.setString(4, email);
            pstmt.setString(5, name);
            pstmt.setString(6, phone);

            // ���� ����
            int rowsInserted = pstmt.executeUpdate();

            // ���Ե� ���� �ִٸ� ����
            if (rowsInserted > 0) {
                return true;
            }
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ���� �� ���ҽ� ����
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		return false; //����
	}
   }
   
   
   @Override
   public void actionPerformed(ActionEvent e) {
      
   }

   
   public static void main(String[] args) {
      new JoinEx1();
   }
}