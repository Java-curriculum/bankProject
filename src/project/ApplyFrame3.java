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

public class ApplyFrame3 extends JFrame
implements ActionListener{

	JLabel label;
	JButton CompleteBtn, EscBtn;
	List list;
	JTextField tf0, tf1, tf2, tf3, tf4, tf5, tf6;
	JPanel p1;
	TradeMgr mgr;
	DialogBox check;
	ShowFrame awt;
	TradeBean bean;
	String TradeNum;
	JFrame frame;
	
	public ApplyFrame3(ShowFrame showFrame, String TradeNum) {
		frame = new JFrame();
		setSize(500,500);
		this.awt = showFrame;
		this.TradeNum = TradeNum;
		setTitle("교환 신청 ver3.0");
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
			
		label.setText("교환 신청");
		label.setBackground(Color.RED);
		p1.setLayout(new GridLayout(0, 1));
		JPanel p6 = new JPanel();
		int No = bean.getTradeNum();
		p6.add(new JLabel("신청인 ID: "));
		
		tf0 = new JTextField(Integer.toString(No),15);
		tf1 = new JTextField("admin2",15);
		tf0.setEditable(false);
		tf1.setEditable(false);
		p6.add(tf1);
		p1.add(p6);
		
		JPanel p7 = new JPanel();
		p7.add(new JLabel("신청 날짜"));
		
        Date currentDate = Date.valueOf(LocalDate.now());

        // SimpleDateFormat을 사용하여 원하는 형식의 문자열로 변환
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(currentDate);
		tf2 = new JTextField(dateString,15);
		tf2.setEditable(false);
		p7.add(tf2);
		p1.add(p7);
		
		JPanel p8 = new JPanel();
		p8.add(new JLabel("내 보유 통화"));
		tf3 = new JTextField(bean.getBuyCur(),5);
		tf3.setEditable(false);
		p8.add(tf3);
		p1.add(p8);
		
		JPanel p9 = new JPanel();
		p9.add(new JLabel("나의 교환 통화"));
		tf4 = new JTextField(bean.getSellCur(),5);
		tf4.setEditable(false);
		p9.add(tf4);
		p1.add(p9);
		
		JPanel p10 = new JPanel();
		JLabel AmtLabel = new JLabel("내 보유 수량");
		p10.add(AmtLabel);
		String AccMo = tf3.getText();
		JTextField AmtTextField = new JTextField(String.valueOf(getAccAmt(tf1.getText(), AccMo)),5);
		AmtTextField.setEditable(false);
		p10.add(AmtTextField);
		p1.add(p10);
		
		JPanel p11 = new JPanel();
		JLabel totalLabel = new JLabel("₩: ");
		p11.add(totalLabel);
		double TotalAmt = getAccAmt(tf1.getText(),AccMo) * getExchangeRate(AccMo);
        DecimalFormat df1 = new DecimalFormat("#,###");
        String TAmount1 = df1.format(TotalAmt);
		JTextField totalTextField = new JTextField("약 "+TAmount1 + " 원",10);
		totalTextField.setEditable(false);
		p11.add(totalTextField);
		p1.add(p11);
		
		JPanel p12 = new JPanel();
		p12.add(new JLabel("교환 통화 수량"));
		tf5 = new JTextField(String.valueOf(bean.getSellAmt()),10);
		tf5.setEditable(false);
		p12.add(tf5);
		p1.add(p12);
		
		JPanel p13 = new JPanel();
		JLabel totalLabel1 = new JLabel("₩: ");
		p13.add(totalLabel1);
		String AccMo1 = tf4.getText();
		double TotalAmt1 = bean.getSellAmt() * getExchangeRate(AccMo1);
        DecimalFormat df2 = new DecimalFormat("#,###");
        String TAmount2 = df2.format(TotalAmt1);
		JTextField totalTextField1 = new JTextField("약 "+TAmount2 + " 원",10);
		totalTextField1.setEditable(false);
		p13.add(totalTextField1);
		p1.add(p13);
		
		
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    
	    CompleteBtn = new JButton("신청");
	    CompleteBtn.addActionListener(this);
	    buttonPanel.add(CompleteBtn);
	    
	    EscBtn = new JButton("취소");
	    EscBtn.addActionListener(this);
	    buttonPanel.add(EscBtn);
	    
	    p1.add(buttonPanel);
	    add(p1, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		if(obj==CompleteBtn/*신청버튼*/) {
            int result = JOptionPane.showConfirmDialog(this, "신청하시겠습니까? \n 화폐:  " + bean.getSellCur() + " => " + bean.getBuyCur() +"\n 수량 :  " 
            		+ bean.getSellAmt() + " => " + bean.getBuyAmt() + "\n 화폐 및 수량을 정확히 확인하시고 확인 버튼을 눌러주세요.", "확인", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                check = new DialogBox(null, "신청이 완료되었습니다.", "확인");
                awt.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "complete"));
                bean.setCheck1(true);
                mgr.updateCheck(bean);
                dispose();
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
