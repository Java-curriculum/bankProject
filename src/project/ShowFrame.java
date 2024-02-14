package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.InsertFrame.DialogBox;

public class ShowFrame extends JFrame
implements ActionListener{

	JLabel label;
	JButton AplBtn, EscBtn;
	List list;
	JTextField tf0, tf1, tf2, tf3, tf4, tf5, tf6;
	JPanel p1;
	TradeMgr mgr;
	DialogBox check;
	TestFrameTrade5 awt;
	TradeBean bean;
	String TradeNum;
	JFrame frame;
	String id;
	ApplyFrame3 af;
	JPanel trade2;
	
	public ShowFrame(TestFrameTrade5 testFrameTrade5, String TradeNum, JPanel trade2) {
		frame = new JFrame();
		setSize(500,500);
		this.awt = testFrameTrade5;
		this.TradeNum = TradeNum;
		this.trade2 = trade2;
		setTitle("ShowForm");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE); // X버튼을 눌러서 창을 닫았을시 프로그램 종료
		setVisible(true);
		
		
		System.out.println("----------");
		System.out.println(TradeNum);
		mgr = new TradeMgr();
		bean = mgr.selectNO(TradeNum);
		p1=new JPanel();
	    label = new JLabel(); // label 초기화
		ApplyForm(bean);
		add(label, BorderLayout.NORTH);	
		validate();
	}
	

	public void ApplyForm(TradeBean bean) {
			
		label.setText("상세보기");
		label.setBackground(Color.YELLOW);
		p1.setLayout(new GridLayout(0, 1));
		id = (String) bean.getTraderAcc1();
		int No = bean.getTradeNum();
		bean.setTradeNum(No);
		JPanel p4 = new JPanel();
		p4.add(new JLabel("등록자  :"));
		tf0 = new JTextField(Integer.toString(No),15);
		tf1 = new JTextField("admin",15);
//		tf1 = new JTextField(bean.getTraderAcc1(), 15);
		tf1.setEditable(false);
		p4.add(tf1);
		p1.add(p4);

		JPanel p5 = new JPanel();
		p5.add(new JLabel("등록 통화  :"));
		tf2 = new JTextField(bean.getSellCur(),5);
	    tf2.setEditable(false);
	    p5.add(tf2);
		p1.add(p5);
		

		JPanel p6 = new JPanel();
		p6.add(new JLabel("등록 수량  :"));
		tf3 = new JTextField(Double.toString(bean.getSellAmt()),10);
		tf3.setEditable(false);
		p6.add(tf3);
		p1.add(p6);
		
	    JPanel p7 = new JPanel();
	    JLabel totalLabel = new JLabel("₩: ");
	    p7.add(totalLabel);
	    JTextField totalTextField = new JTextField(20);
	    
        String exchangeCurrency = tf2.getText();
        String exchangeAmountStr = tf3.getText().trim();
        
        if (exchangeAmountStr.isEmpty()) {
            return;
        }
        double exchangeAmount = 0.0;       
        try {
            exchangeAmount = Double.parseDouble(exchangeAmountStr);
        } catch (NumberFormatException ex) {
            return;
        }

        double exchangeRate = getExchangeRate(exchangeCurrency);
        double totalAmount = exchangeAmount * exchangeRate;
        DecimalFormat df = new DecimalFormat("#,###");
        String TAmount = df.format(totalAmount);
       totalTextField.setText("약 " + TAmount + " 원");

	    totalTextField.setEditable(false); // 사용자가 편집할 수 없도록 설정
	    p7.add(totalTextField);
	    p1.add(p7);

		

	    JPanel p8 = new JPanel();
	    p8.add(new JLabel("구매 통화 :"));
		tf4 = new JTextField(bean.getBuyCur(),5);
	    tf4.setEditable(false);
	    p8.add(tf4);
	    p1.add(p8);
		
		JPanel p9 = new JPanel();
		p9.add(new JLabel("구매 수량  :"));
		tf5 = new JTextField(Double.toString(bean.getBuyAmt()),10);
		tf5.setEditable(false);
		p9.add(tf5);
		p1.add(p9);
		
	    JPanel p11 = new JPanel();
	    JLabel totalLabel1 = new JLabel("₩: ");
	    p11.add(totalLabel1);
	    JTextField totalTextField1 = new JTextField(Double.toString(bean.getBuyAmt()),20);

        String exchangeCurrency1 = tf4.getText();
        String exchangeAmountStr1 = tf5.getText().trim();
        
        if (exchangeAmountStr1.isEmpty()) {
            return;
        }
        double exchangeAmount1 = 0.0;       
        try {
            exchangeAmount1 = Double.parseDouble(exchangeAmountStr1);
        } catch (NumberFormatException ex) {
            return;
        }

        double exchangeRate1 = getExchangeRate(exchangeCurrency1);
        double totalAmount1 = exchangeAmount1 * exchangeRate1;
        DecimalFormat df1 = new DecimalFormat("#,###");
        String TAmount1 = df1.format(totalAmount1);
        totalTextField1.setText("약 " + TAmount1 + " 원");
	    
        totalTextField1.setEditable(false);
	    p11.add(totalTextField1);
	    p1.add(p11);


	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    
	    if(bean.getCheck1()==false) {
	    AplBtn = new JButton("교환 신청");
	    AplBtn.addActionListener(this);
	    buttonPanel.add(AplBtn);
	    }
	    
	    EscBtn = new JButton("취소");
	    EscBtn.addActionListener(this);
	    buttonPanel.add(EscBtn);
	    
	    
	    p1.add(buttonPanel);

	    add(p1, BorderLayout.CENTER);
		
}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		if(obj==AplBtn/*교환 신청 버튼*/) {
			System.out.println("버튼 눌림");
			tf0.getText();
			System.out.println(tf0.getText());
			af = new ApplyFrame3(this, tf0.getText());
			af.setVisible(true);

		}else if(obj==EscBtn/*취소버튼*/) {
			System.out.println("버튼 눌림");
            trade2.removeAll();
			awt.viewList(trade2,null, null);
            trade2.revalidate();
            trade2.repaint();
			dispose();
		}
		
	}
	
	private double getExchangeRate(String currency) {
		
		   Double rate = mgr.selectRate(currency);
		    if (rate != null) {
		        return rate;
		    } else {
		        JOptionPane.showMessageDialog(null, "선택한 화폐에 대한 환율 정보를 찾을 수 없습니다.");
		        return 0.0;
		    }
		
}	
	
	
    // DialogBox 클래스는 사용자 정의 다이얼로그를 나타냅니다.
    // 여기서는 메시지와 버튼 텍스트를 입력으로 받아 다이얼로그를 생성합니다.
    class DialogBox extends JDialog {
        public DialogBox(Frame parent, String message, String buttonText) {
            super(parent, true);
            JPanel panel = new JPanel();
            JLabel label = new JLabel(message);
            panel.add(label);
            JButton button = new JButton(buttonText);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose(); // 다이얼로그를 닫습니다.
                }
            });
            panel.add(button);
            getContentPane().add(panel);
            setSize(300, 150);
            setLocationRelativeTo(null);
            setVisible(true);
            }
    }
    
	
	
	
	
}
