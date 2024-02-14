package logIn;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class ExchangePanel extends JPanel {
	
	private JTable table1;
	private JTable table2;
	
    public ExchangePanel() {
    	setBackground(new Color(255, 255, 255)); //�г� �� ����
    	setLayout(new BorderLayout()); //���̾ƿ� ����
    	
    	//table1 ������ �� �� �̸�
        String[] header1 = { "�̱� �޷�", "�Ϻ� ��ȭ", "�±� ��Ʈ", "ȣ�� �޷�", "ĳ���� �޷�", "������ ����", "�߱� ����", "���� ����", "���� �Ŀ��", "ȫ�� �޷�", "�������� �޷�", "�̰����� �޷�", "���ѹα� ��ȭ" };
		String[][] contents = {
				{ "�̱� �޷�", "�Ϻ� ��ȭ", "�±� ��Ʈ", "ȣ�� �޷�", "ĳ���� �޷�", "������ ����", "�߱� ����", "���� ����", "���� �Ŀ��", "ȫ�� �޷�", "�������� �޷�", "�̰����� �޷�", "���ѹα� ��ȭ" },
				{ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13" }
		};
		
		//table1 ������ �� �� �̸�
		String[] header2 = { "ȯ����ȣ", "��¥", "������ȭ", "��ȯ��ȭ", "�ݾ�" };
		Object[][] data = {
				{ "ȯ����ȣ", "��¥", "������ȭ", "��ȯ��ȭ", "�ݾ�" },
				{ "1", "2", "3", "4", "5" },
				{ "6", "7", "8", "9", "0" },
				{ "q", "w", "e", "r", "t" }
		};
		
		//table1 ���� �� ����
		table1 = new JTable(contents, header1);
		table1.setBounds(20, 55, 550, 50);
		
		//table2 ���� �� ����
		table2 = new JTable(data, header2);
		table2.setBounds(20, 335, 550, 49);
		
		//���̺��� �г��� �߾ӿ� �߰�
		add(table1, BorderLayout.CENTER);
		add(table2, BorderLayout.CENTER);
    } 
    
    // �гο� �׸���
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // �׷��� ������ ���� ��� �̰��� �׸��� �ڵ带 �߰�
    }
    
    // ǥ�� ������Ʈ�ϴ� �޼ҵ� �߰�
    public void updateTable1(String[][] contents, String[] header1) {
        DefaultTableModel model1 = new DefaultTableModel(contents, header1);
        table1.setModel(model1);
    }
    public void updateTable2(String[][] data, String[] header) {
        DefaultTableModel model2 = new DefaultTableModel(data, header);
        table2.setModel(model2);
    }
}

class TapPanel extends JPanel {
	
    public TapPanel() {
    	setBackground(new Color(62,192,196)); //�г� �� ����
    } 
    
    // �гο� �׸���
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // �׷��� ������ ���� ��� �̰��� �׸��� �ڵ带 �߰�
    }
}

public class HomeEx1 extends JFrame implements ActionListener {
	
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private static Connection connection = null;
	
	Label mymoneyl, exlistl, heldl, tradel, selll, exl, totall; // ���� ������ �ڻ�, ȯ�� ����, ���� ��ȭ, ��ȯ ��ȭ, ���� �Ÿ���, ȯ�� �ݾ�, �� ȯ����
	TextField sellTf, exTf, totalTf; // ���� �Ÿ���, ȯ�� �ݾ�, �� ȯ����
	Font font1 = new Font("Aharoni ����", Font.BOLD, 25);
	
	Color customColor = new Color(169, 219, 208);
	Color customColor2 = new Color(62,192,196);
	Color customColor3 = new Color(255, 255, 255);
	
	JButton rateBtn = new JButton("ȯ��"); //ȯ�� ��ư
	JButton exchangeBtn = new JButton("ȯ��"); //ȯ�� ��ư
	JButton walletBtn = new JButton("����"); //���� ��ư
	JButton tradeBtn = new JButton("�ŷ���"); //�ŷ��� ��ư
	JButton infoBtn = new JButton("�� ����"); //������ ��ư
	JButton outBtn = new JButton("�α׾ƿ�"); //�α׾ƿ� ��ư
	JButton moreBtn = new JButton("������ >"); //������ ��ư
	
	JButton searchBtn = new JButton("�˻�"); //�˻� ��ư
	JButton doexBtn = new JButton("ȯ���ϱ�"); //ȯ���ϱ� ��ư
	
	Choice ch1;
	String heldmoney[] = { "�̱� �޷�", "�Ϻ� ��ȭ", "�±� ��Ʈ", "ȣ�� �޷�", "ĳ���� �޷�", "������ ����", "�߱� ����", "���� ����", "���� �Ŀ��", "ȫ�� �޷�", "�������� �޷�", "�̰����� �޷�", "���ѹα� ��ȭ" };
	
	Choice ch2;
	String trademoney[] = { "�̱� �޷�", "�Ϻ� ��ȭ", "�±� ��Ʈ", "ȣ�� �޷�", "ĳ���� �޷�", "������ ����", "�߱� ����", "���� ����", "���� �Ŀ��", "ȫ�� �޷�", "�������� �޷�", "�̰����� �޷�", "���ѹα� ��ȭ" };
	
	public HomeEx1() {
		
		getContentPane().setBackground(new Color(169, 219, 208)); //â ���� ����
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		ch1 = new Choice();
		ch2 = new Choice();
		
		mymoneyl = new Label("���� ������ �ڻ�");	
		exlistl = new Label("ȯ�� ����");
		heldl = new Label("���� ��ȭ");
		tradel = new Label("��ȯ ��ȭ");
		selll = new Label("���� �Ÿ���");
		exl = new Label("ȯ�� �ݾ�");
		totall = new Label("�� ȯ����");
		
		sellTf = new TextField("");
		exTf = new TextField("");
		totalTf = new TextField("");
		
		ch1.setBounds(110, 135, 120, 50);
		ch2.setBounds(330, 135, 120, 50);
	
		mymoneyl.setBounds(20, 20, 100	, 35);
		exlistl.setBounds(20, 300, 80, 35);
		heldl.setBounds(40, 130, 60, 35);
		tradel.setBounds(260, 130, 60, 35);
		selll.setBounds(30, 240, 80, 35);
		exl.setBounds(40, 180, 60, 35);
		totall.setBounds(250, 240, 60, 35);
		
		sellTf.setBounds(110, 245, 120, 25);
		exTf.setBounds(110, 185, 340, 25);
		totalTf.setBounds(320, 245, 130, 25);
	
		rateBtn.setBackground(customColor2);
		rateBtn.setBounds(0, 0, 80, 35);
		rateBtn.setBorder(BorderFactory.createEmptyBorder());
		
		exchangeBtn.setBackground(customColor2);
		exchangeBtn.setBounds(0, 50, 80, 35);
		exchangeBtn.setBorder(BorderFactory.createEmptyBorder());
		
		walletBtn.setBackground(customColor2);
		walletBtn.setBounds(0, 100, 80, 35);
		walletBtn.setBorder(BorderFactory.createEmptyBorder());
		
		tradeBtn.setBackground(customColor2);
		tradeBtn.setBounds(0, 150, 80, 35);
		tradeBtn.setBorder(BorderFactory.createEmptyBorder());
		
		infoBtn.setBackground(customColor2);
		infoBtn.setBounds(0, 200, 80, 35);
		infoBtn.setBorder(BorderFactory.createEmptyBorder());
		
		outBtn.setBackground(customColor2);
		outBtn.setBounds(0, 250, 80, 35);
		outBtn.setBorder(BorderFactory.createEmptyBorder());
		
		moreBtn.setBackground(customColor3);
		moreBtn.setBounds(400, 300, 80, 35);
		moreBtn.setBorder(BorderFactory.createEmptyBorder());
		moreBtn.addActionListener(this);
		
		searchBtn.setBackground(customColor3);
		searchBtn.setBounds(460, 135, 100, 75);
		
		doexBtn.setBackground(customColor2);
		doexBtn.setBounds(460, 240, 100, 35);
		
		for (int i = 0; i < heldmoney.length; i++) {
			ch1.add(heldmoney[i]);
		}
		
		for (int i = 0; i < trademoney.length; i++) {
			ch2.add(trademoney[i]);
		}
		
		rateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Object obj = e.getSource();
					if(obj==rateBtn)/*ȯ�� ��ư Ŭ�� ��*/ {
						dispose();
						new LoginEx1();
					}else {
					}
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		exchangeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Object obj = e.getSource();
					if(obj==exchangeBtn)/*ȯ�� ��ư Ŭ�� ��*/ {
						dispose();
						new LoginEx1();
					}else {
					}
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		walletBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Object obj = e.getSource();
					if(obj==walletBtn)/*���� ��ư Ŭ�� ��*/ {
						dispose();
						new LoginEx1();
					}else {
					}
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		tradeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Object obj = e.getSource();
					if(obj==tradeBtn)/*�ŷ��� ��ư Ŭ�� ��*/ {
						dispose();
						new LoginEx1();
					}else {
					}
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		infoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Object obj = e.getSource();
					if(obj==infoBtn)/*�� ���� ��ư Ŭ�� ��*/ {
						dispose();
						new LoginEx1();
					}else {
					}
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		outBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Object obj = e.getSource();
					if(obj==outBtn)/*�α׾ƿ� ��ư Ŭ�� ��*/ {
						dispose();
						new LoginEx1();
					}else {
					}
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
	
		ExchangePanel exchangePanel = new ExchangePanel();
        exchangePanel.setLayout(null);
        exchangePanel.setBounds(110, 10, 600, 430);
        add(exchangePanel);
        
        TapPanel tapPanel = new TapPanel();
        tapPanel.setLayout(null);
        tapPanel.setBounds(0, 0, 100, 450);
        add(tapPanel);
        
        exchangePanel.add(ch1);
        exchangePanel.add(ch2);
        
        exchangePanel.add(mymoneyl);
        exchangePanel.add(exlistl);
        exchangePanel.add(heldl);
        exchangePanel.add(tradel);
        exchangePanel.add(selll);
        exchangePanel.add(exl);
        exchangePanel.add(totall);
        
        exchangePanel.add(sellTf);
        exchangePanel.add(exTf);
        exchangePanel.add(totalTf);
        
        exchangePanel.add(moreBtn);
        exchangePanel.add(searchBtn);
        exchangePanel.add(doexBtn);

        tapPanel.add(rateBtn);
        tapPanel.add(exchangeBtn);
        tapPanel.add(walletBtn);
        tapPanel.add(tradeBtn);
        tapPanel.add(infoBtn);
        tapPanel.add(outBtn);
        
        // �����ͺ��̽� ����
        connect();
        
        // �����ͺ��̽����� ȯ�� ���� �����ͼ� ���̺� ǥ��
        List<String[]> exchangeData = fetchExchangeDataFromDatabase1();
        if (exchangeData != null) {
            String[][] exchangeArray = new String[exchangeData.size()][];
            for (int i = 0; i < exchangeData.size(); i++) {
                exchangeArray[i] = exchangeData.get(i);
            }
            String[] header1 = { "�̱� �޷�", "�Ϻ� ��ȭ", "�±� ��Ʈ", "ȣ�� �޷�", "ĳ���� �޷�", "������ ����", "�߱� ����", "���� ����", "���� �Ŀ��", "ȫ�� �޷�", "�������� �޷�", "�̰����� �޷�", "���ѹα� ��ȭ" };
            exchangePanel.updateTable1(exchangeArray, header1);
        }
        
        List<String[]> accountData = fetchExchangeDataFromDatabase2();
        if (accountData != null) {
            String[][] accountArray = new String[accountData.size()][];
            for (int i = 0; i < accountData.size(); i++) {
                accountArray[i] = accountData.get(i);
            }
            String[] header2 = { "ȯ����ȣ", "��¥", "������ȭ", "��ȯ��ȭ", "�ݾ�" };
            exchangePanel.updateTable2(accountArray, header2);
        }
        
        List<String[]> accountData2 = fetchExchangeDataFromDatabase3();
        if (accountData2 != null) {
            String[][] accountArray2 = new String[accountData2.size()][];
            for (int i = 0; i < accountData2.size(); i++) {
                accountArray2[i] = accountData2.get(i);
            }
            String[] header2 = { "ȯ����ȣ", "��¥", "������ȭ", "��ȯ��ȭ", "�ݾ�" };
            exchangePanel.updateTable2(accountArray2, header2);
        }
        
		setVisible(true);
		validate();
	}

	public void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public List<String[]> fetchExchangeDataFromDatabase1(String id) {
        List<String[]> exchangeData = new ArrayList<>();
        try {
            if (connection != null) {
                String query = "SELECT * FROM account WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String[] row = {
                            resultSet.getString("account_num"),
                            resultSet.getString("id"),
                            resultSet.getString("USD"),
                            resultSet.getString("JPY"),
                            resultSet.getString("THB"),
                            resultSet.getString("AUD"),
                            resultSet.getString("CAD"),
                            resultSet.getString("CHF"),
                            resultSet.getString("CNY"),
                            resultSet.getString("EUR"),
                            resultSet.getString("GBP"),
                            resultSet.getString("HKD"),
                            resultSet.getString("NZD"),
                            resultSet.getString("SGD"),
                            resultSet.getString("KRW")
                    };
                    exchangeData.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exchangeData;
    }
	//�ֽ� 3���� ȯ�� ����
	public List<String[]> fetchExchangeDataFromDatabase2() {
        List<String[]> accountData = new ArrayList<>();
        try {
            if (connection != null) {
                String query = "SELECT * FROM ex_history ORDER BY day DESC LIMIT 3";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String[] row = {
                            resultSet.getString("ex_account_num"),
                            resultSet.getString("day"),
                            resultSet.getString("sell_cur"),
                            resultSet.getString("buy_cur"),
                            resultSet.getString("buy_amt")
                    };
                    accountData.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountData;
    }
	//��ü ȯ�� ����
	public List<String[]> fetchExchangeDataFromDatabase3() {
        List<String[]> exchangeData = new ArrayList<>();
        try {
            if (connection != null) {
                String query = "SELECT * FROM ex_history";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String[] row = {
                            resultSet.getString("ex_account_num"),
                            resultSet.getString("day"),
                            resultSet.getString("sell_cur"),
                            resultSet.getString("buy_cur"),
                            resultSet.getString("buy_amt")
                    };
                    exchangeData.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exchangeData;
    }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		// �����ͺ��̽����� ��ü ȯ�� ���� ��������
	    List<String[]> exchangeData = fetchExchangeDataFromDatabase3();
		if (exchangeData != null) {
		//ǥ �� ����
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] { "ȯ����ȣ", "��¥", "������ȭ", "��ȯ��ȭ", "�ݾ�" });
		for (String[] row : exchangeData) {
            model.addRow(row);
        }
		
		//ǥ ����
		JTable table2 = new JTable(model);
		
		//ǥ�� ���� �г� ����
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.add(new JScrollPane(table2), BorderLayout.CENTER);
		
		//��� â ����
		JDialog dialog = new JDialog();
		dialog.setTitle("ȯ�� ����");
		dialog.setModal(true);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setLayout(new BorderLayout());
		dialog.add(tablePanel, BorderLayout.CENTER);
		dialog.setSize(800, 500); //��� â ũ�� ����
		dialog.setLocationRelativeTo(null); //ȭ�� �߾ӿ� ��ġ
		
		//ǥ�� ��� â�� ���� �߰��ϰ� ���ϴ� ��ġ�� �̵�
		JScrollPane scrollPane = new JScrollPane(table2);
		dialog.setLayout(null);
		scrollPane.setBounds(0, 150, 750, 100); //��� â �� ǥ ��ġ �� ũ�� ����
		dialog.add(scrollPane);
		
		//�ݱ� ��ư�� ������ �г� ����
		JButton closeButton = new JButton("�ݱ�");
		closeButton.setBounds(650, 400 , 100, 30);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose(); //��� â �ݱ�
			}
		});
		
		dialog.add(tablePanel);
		dialog.add(closeButton);
		dialog.setVisible(true);
	} else {
		// �����ͺ��̽����� ȯ�� ������ �������� ���� ��쿡 ���� ó��
		System.out.println("�����ͺ��̽� ���� ����");
	}
}


	public static void main(String[] args) {
		new HomeEx1();
	}
}
