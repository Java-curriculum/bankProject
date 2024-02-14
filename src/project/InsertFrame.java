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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import project.ApplyFrame3.DialogBox;

public class InsertFrame extends JFrame
implements ActionListener{

	JLabel label;
	JButton insBtn, EscBtn;
	List list;
	JTextField tf0, tf1, tf2, tf3, tf4, tf5, tf6;
	JPanel p1;
	TradeMgr mgr;
	DialogBox check;
	TestFrameTrade5 awt;
	TradeBean bean;
	String TradeNum;
	JFrame frame;
	private JComboBox<String> currencyComboBox;
	private JComboBox<String> exchangeComboBox;
	
	
	public InsertFrame(TestFrameTrade5 testFrameTrade5, String TradeNum) {
		frame = new JFrame();
		setSize(500,500);
		this.awt = testFrameTrade5;
		this.TradeNum = TradeNum;
		setTitle("InsertForm");
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
			
		label.setText("게시글 작성");
		label.setBackground(Color.GRAY);
		p1.setLayout(new GridLayout(0, 1));

		int No = bean.getTradeNum();
		JPanel p5 = new JPanel();
		p5.add(new JLabel("등록자 계정 ID  :")); 
		tf1 = new JTextField("admin",15); // 추후에 본인 로그인 ID가 자동으로 들어가도록 수정
		tf1.setEditable(false);
		p5.add(tf1);
		p1.add(p5);
		
		tf0 = new JTextField(Integer.toString(No),15);
//		tf1 = new JTextField("admin2",15);
//		tf0.setEditable(false);
//		tf1.setEditable(false);
//		JPanel p6 = new JPanel();
//		p6.add(tf1);
//		p1.add(p6);
		
		JPanel p6 = new JPanel();
		p6.add(new JLabel("등록 날짜"));
		
        Date currentDate = Date.valueOf(LocalDate.now());

        // SimpleDateFormat을 사용하여 원하는 형식의 문자열로 변환
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(currentDate);
		tf2 = new JTextField(dateString,15);
		tf2.setEditable(false);
		p6.add(tf2);
		p1.add(p6);
		
		
		
		JPanel p7 = new JPanel();
		p7.add(new JLabel("보유 통화  :"));
		String[] currencies = {"USD", "JPY", "THB", "AUD", "CAD", "CHF", "CNY", "EUR", "GBP", "HKD", "NZD", "SGD", "KRW"}; 
		currencyComboBox = new JComboBox<>(currencies);
		p7.add(currencyComboBox);
		p1.add(p7);
				
		
	    JPanel p8 = new JPanel();
	    JLabel AmtLabel = new JLabel("현재 계좌 잔여량: ");
	    p8.add(AmtLabel);
	    String AccMo = (String) currencyComboBox.getSelectedItem();
	    JTextField AmtTextField = new JTextField(String.valueOf(getAccAmt(tf1.getText(), AccMo)),5);
	    AmtTextField.setEditable(false); // 사용자가 편집할 수 없도록 설정
	    p8.add(AmtTextField);
	    p1.add(p8);
	    
	    currencyComboBox.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            updateRemainingAmount();
	        }

	        private void updateRemainingAmount() {
	            String currency = (String) currencyComboBox.getSelectedItem();
	            double remainingAmount = getAccAmt(tf1.getText(), currency); // 현재 작업 중인 인스턴스의 메서드 호출
	            AmtTextField.setText(Double.toString(remainingAmount));
	        }
	    });
	    
		
	    
		
		JPanel p9 = new JPanel();
		p9.add(new JLabel("등록 수량 :"));
		tf3 = new JTextField(10);
		p9.add(tf3);
		p1.add(p9);
		
	    JPanel p10 = new JPanel();
	    JLabel totalLabel = new JLabel("₩: ");
	    p10.add(totalLabel);
	    JTextField totalTextField = new JTextField(20);
	    totalTextField.setEditable(false); // 사용자가 편집할 수 없도록 설정
	    p10.add(totalTextField);
	    p1.add(p10);

	    tf3.getDocument().addDocumentListener(new DocumentListener() {
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	            updateTotalAmount();
	        }

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	            updateTotalAmount();
	        }

	        @Override
	        public void changedUpdate(DocumentEvent e) {
	            updateTotalAmount();
	        }

	        private void updateTotalAmount() {
	            String exchangeCurrency = (String) currencyComboBox.getSelectedItem();
	            String exchangeAmountStr = tf3.getText().trim(); // 공백을 제거한 후 문자열을 가져옴
	            if (exchangeAmountStr.isEmpty()) {
	                return; // 빈 문자열이면 처리하지 않음
	            }
	            
	            double exchangeAmount = 0.0;
//	            try {
//	                exchangeAmount = Double.parseDouble(tf3.getText());
//	            } catch (NumberFormatException ex) {
//	            	JOptionPane.showMessageDialog(null,"올바른 숫자를 입력하여 주세요.");
//	            }
//	            
	            try {
	                exchangeAmount = Double.parseDouble(exchangeAmountStr);
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(null,"올바른 숫자를 입력하여 주세요.");
	                return; // 숫자로 변환할 수 없는 경우 처리하지 않음
	            }

	            double exchangeRate = getExchangeRate(exchangeCurrency);
	            double totalAmount = exchangeAmount * exchangeRate;
	            DecimalFormat df = new DecimalFormat("#,###");
	            String TAmount = df.format(totalAmount);
	            totalTextField.setText("약 " + TAmount + " 원");
	            
	            double entAmount = Double.parseDouble(tf3.getText());
	            double AmtText = Double.parseDouble(AmtTextField.getText());
	            if (entAmount > AmtText) {
	                JOptionPane.showMessageDialog(null, "보유 수량은 잔여 수량보다 클 수 없습니다.");
	                SwingUtilities.invokeLater(() -> tf3.setText(""));  // 입력된 값을 지우거나 다른 처리를 수행할 수 있습니다.
	            
	            }
	        }
	        
	    });
			
	    JPanel p11 = new JPanel();
	    p11.add(new JLabel("교환 통화 :"));
	    String[] exchangeCurrencies = {"USD", "JPY", "THB", "AUD", "CAD", "CHF", "CNY", "EUR", "GBP", "HKD", "NZD", "SGD", "KRW"};; // 교환 화폐 목록
	    exchangeComboBox = new JComboBox<>(exchangeCurrencies);
	    p11.add(exchangeComboBox);
	    p1.add(p11);
		
		JPanel p12 = new JPanel();
		p12.add(new JLabel("교환 수량 :"));
		tf5 = new JTextField(10);
		p12.add(tf5);
		p1.add(p12);			

	    JPanel p13 = new JPanel();
	    JLabel totalLabel1 = new JLabel("₩: ");
	    p13.add(totalLabel1);
	    JTextField totalTextField1 = new JTextField(20);
	    totalTextField1.setEditable(false); // 사용자가 편집할 수 없도록 설정
	    p13.add(totalTextField1);
	    p1.add(p13);

	    tf5.getDocument().addDocumentListener(new DocumentListener() {
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	            updateTotalAmount();
	        }

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	            updateTotalAmount();
	        }

	        @Override
	        public void changedUpdate(DocumentEvent e) {
	            updateTotalAmount();
	        }

	        private void updateTotalAmount() {

	            String exchangeCurrency = (String) exchangeComboBox.getSelectedItem();
	            double exchangeAmount = 0.0;
	            try {
	                exchangeAmount = Double.parseDouble(tf5.getText());
	            } catch (NumberFormatException ex) {
	            	JOptionPane.showMessageDialog(null,"올바른 숫자를 입력하여 주세요.");
	            }

	            double exchangeRate = getExchangeRate(exchangeCurrency);
	            double totalAmount = exchangeAmount * exchangeRate;
	            DecimalFormat df = new DecimalFormat("#,###");
	            String TAmount = df.format(totalAmount);
	            totalTextField1.setText("약 " + TAmount + " 원");
	        }
	    });
		
		
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    
	    insBtn = new JButton("저장");
	    insBtn.addActionListener(this);
	    buttonPanel.add(insBtn);
	    
	    EscBtn = new JButton("취소");
	    EscBtn.addActionListener(this);
	    buttonPanel.add(EscBtn);
	    
	    p1.add(buttonPanel);


	    add(p1, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		if(obj==insBtn/*등록버튼*/) {
			
			TradeBean bean = new TradeBean();
			String Currency = (String) currencyComboBox.getSelectedItem();
		    String exchange = (String) exchangeComboBox.getSelectedItem();
		    if(Currency.equals(exchange)) {
		    	JOptionPane.showMessageDialog(null, "보유 외화와 교환 외화는 같을 수 없습니다. 수정하여 주십시오.");   	
		    }else {					
			bean.setTraderAcc1(tf1.getText());
			bean.setSellCur((String) currencyComboBox.getSelectedItem());
			bean.setSellAmt((int) Double.parseDouble(tf3.getText()));
			bean.setBuyCur((String) exchangeComboBox.getSelectedItem());
			bean.setBuyAmt((int) Double.parseDouble(tf5.getText()));
			bean.setDay(java.sql.Date.valueOf(LocalDate.now()));
		    }
            
			if(mgr.insert(bean)) {
				check = new DialogBox(null, "등록이 완료되었습니다.", "확인");
                awt.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "complete"));
				dispose();
//	            trade2.removeAll();
//				viewList(trade2);
//	            trade2.revalidate();
//	            trade2.repaint();
			}
		}else if(obj==EscBtn/*취소버튼*/) {
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
	
	private Double getAccAmt(String id, String mo) {
		double AccMo = mgr.selectAccMo(id,mo);	
		return AccMo;
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
