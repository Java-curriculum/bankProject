package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;

import java.awt.FlowLayout;
import java.text.DecimalFormat;
//거래소 게시판(TradeMgr, TradeBean, MFrame) - 등록,수정,삭제 : 완성
//추가해야할것
//0. 보유수량보다 더 높은 금액 입력 못하게 막기 + 계좌에서 보유수량 들고오기 - 완성
//1. 신청버튼 활성화 + 기능 -> 상세보기 버튼 : 누르면 수정할수없는 내부 거래 내역 보여주고
//안에 신청 버튼이 있어서 누르면 계좌 연동으로 신청 가능 - 완성
//2. 화폐를 원(\)으로 바꿨을때 대략의 가격 표시 - 완성
/**3. 모르겠는점 : 어째서 마지막 확인 버튼을 눌렀을때 바로 list(check1=true)가 초기화 되지않는지
          + 상세보기 버튼 또한 한번더 리스트가 초기화 되기전까지 제대로된 코드가 실행되지않는지*/
//4. 누군가가 거래 신청을 했을때 DB 작동될 동작 - 완성
//5.아이디가 다르면 수정, 삭제가 안일어나게   상세보기 버튼을 누른 창에 삭제 수정 옮기기 
//-> 옮기고 나면 등록자와 같은 아이디 일때 (수정 삭제 - 활성화, 교환 - 비활), 등록자와 다른 아이디일때 (수정 삭제 - 비활, 교환 - 활성화)
//6. UI 개선 - 연동된 계좌 잔고, 검색
//7. 같은 외화일때 저장 안되게 - 완성

public class TestFrameTrade5 extends JFrame implements ActionListener {

	JButton b1, b2, b3, b4, b5, b6, b7,b8, b9, b10;
	List list;
	JLabel label;
	JPanel menuPanel, tradePanel, trade1, trade2, trade3;
	JTextField tf0, tf1, tf2, tf3, tf4, tf5;
	JPanel p1, p2, p3, p4;
	JButton insBtn, upBtn, EscBtn, AplBtn;
	Vector<TradeBean> vlist;
	TradeMgr mgr;
	String id;
	private JComboBox<String> currencyComboBox;
	private JComboBox<String> exchangeComboBox;
	ApplyFrame3 af;
	InsertFrame If;
	UpdateFrame Uf;
	ShowFrame Sf;
	JFrame frame;
	
    String selectedCurrency; // 선택한 보유 통화 저장 변수
    String selectedExchange; // 선택한 교환 통화 저장 변수
	
	
	JButton rateBtn; //환율 버튼
	JButton exchangeBtn; //환전 버튼
	JButton walletBtn; //지갑 버튼
	JButton tradeBtn; //거래소 버튼
	JButton infoBtn; //내정보 버튼
	JButton outBtn; //로그아웃 버튼
	JButton moreBtn; //더보기 버튼
	JButton newBtn; // 새로고침 버튼
	JButton searchBtn; //검색 버튼
	JButton doexBtn; //환전하기 버튼

	
	
	public TestFrameTrade5() {
		
		frame = new JFrame();
		setSize(800,600);
		setTitle("TestFrame Trade Ver5.0");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE); // X버튼을 눌러서 창을 닫았을시 프로그램 종료
		setVisible(true); // 창을 띄울지 말지 여부를 결정
		
		mgr = new TradeMgr();
		
		frame.setLayout(new GridLayout(1, 2));
		
	    menuPanel =  new JPanel(new GridLayout(8, 1));
		tradePanel = new JPanel(new GridLayout(3, 1));
		
		frame.add(menuPanel);
		frame.add(tradePanel);

	    rateBtn = new JButton("환전");
	    exchangeBtn = new JButton("환율");
	    walletBtn = new JButton("지갑");
	    tradeBtn = new JButton("거래소");
	    infoBtn = new JButton("내정보");
	    outBtn = new JButton("로그아웃");
	    
	    rateBtn.addActionListener(this);
	    exchangeBtn.addActionListener(this);
	    walletBtn.addActionListener(this);
	    tradeBtn.addActionListener(this);
	    infoBtn.addActionListener(this);
	    outBtn.addActionListener(this);

	    
	    menuPanel.add(new JLabel("Logo"));
	    menuPanel.add(rateBtn);
	    menuPanel.add(exchangeBtn);
	    menuPanel.add(walletBtn);
	    menuPanel.add(tradeBtn);
	    menuPanel.add(infoBtn);
	    menuPanel.add(new JLabel());
	    menuPanel.add(outBtn);
		
		
		trade1 = new JPanel();
		trade2 = new JPanel(new BorderLayout());
		trade3 = new JPanel();
		
		tradePanel.add(trade1,BorderLayout.NORTH);
		tradePanel.add(trade2,BorderLayout.CENTER);
		tradePanel.add(trade3,BorderLayout.SOUTH);
		
		label = new JLabel("");
		
		JPanel sc1 = new JPanel();
		sc1.add(new JLabel("보유 통화  :"));
		String[] currencies = {"","USD", "JPY", "THB", "AUD", "CAD", "CHF", "CNY", "EUR", "GBP", "HKD", "NZD", "SGD", "KRW"}; 
		currencyComboBox = new JComboBox<>(currencies);
		sc1.add(currencyComboBox);
		trade1.add(sc1);
		
		JPanel sc2 = new JPanel();
	    sc2.add(new JLabel("교환 통화 :"));
	    String[] exchangeCurrencies = {" ","USD", "JPY", "THB", "AUD", "CAD", "CHF", "CNY", "EUR", "GBP", "HKD", "NZD", "SGD", "KRW"};; // 교환 화폐 목록
	    exchangeComboBox = new JComboBox<>(exchangeCurrencies);
	    sc2.add(exchangeComboBox);
	    trade1.add(sc2);
	    
	    searchBtn = new JButton("검색");
	    newBtn = new JButton("새로고침");
	    
	    searchBtn.addActionListener(this);
	    newBtn.addActionListener(this);
	    
	    trade1.add(searchBtn);
	    trade1.add(newBtn);
		
		b1 = new JButton("게시글 작성");
		b2 = new JButton("수정");
		b3 = new JButton("삭제");
		b4 = new JButton("상세보기");
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		
		
		trade3.add(b1);
		trade3.add(b2);
		trade3.add(b3);
		trade3.add(b4);
		
		viewList(trade2,null, null);
		
	    add(menuPanel, BorderLayout.WEST);
		add(tradePanel, BorderLayout.CENTER);
		
		validate();
	}
	
	
	public void viewList(JPanel panel, String object, String object2) {
		
		vlist = mgr.selectAllDESC();
        DecimalFormat df = new DecimalFormat("#,###");

		list = new List(vlist.size(), false);
		
	    if ("".equals(object) && " ".equals(object2)) {
	        selectedCurrency = null;
	        selectedExchange = null;
	    } else {
	        selectedCurrency = object;
	        selectedExchange = object2;
	    }

	    for (int i = 0; i < vlist.size(); i++) {
	        TradeBean bean = vlist.get(i);
	        // 선택한 보유 통화와 교환 통화에 맞는 거래만 리스트에 추가
	        if (selectedCurrency == null && selectedExchange == null
	                || (bean.getSellCur().equals(selectedCurrency) && bean.getBuyCur().equals(selectedExchange))) {
	            String SellAmt = df.format(bean.getSellAmt());
	            String str = bean.getTradeNum() + "    "
	                    + bean.getTraderAcc1().trim() + "       "
	                    + bean.getSellCur().trim() + "       "
	                    + bean.getBuyCur().trim() + "       "
	                    + SellAmt + "       "
	                    + bean.getDay();
	            list.add(str);
	        }
	    }
		
		
		int len = list.getItemCount();
		if(len>0)
			list.select(0);
		panel.add(list, BorderLayout.CENTER);
	}		
	
	
			
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		String command = e.getActionCommand();
	        
	        if (obj == b1/*게시글작성 버튼*/) {
	            
				int i = list.getSelectedIndex();
				TradeBean bean = vlist.get(i);
				System.out.println("버튼 눌림");
				System.out.println(bean.getTradeNum());
				If = new InsertFrame(this,Integer.toString(bean.getTradeNum()));
				If.setVisible(true);
	            
	     
			
		}else if(obj==b2/*수정버튼*/) {

				int i = list.getSelectedIndex();
				TradeBean bean = vlist.get(i);
				System.out.println("버튼 눌림");
				System.out.println(bean.getTradeNum());
				Uf = new UpdateFrame(this, Integer.toString(bean.getTradeNum()));
				Uf.setVisible(true);
			
		}else if(obj==b3/*삭제버튼*/) {
					
					int i = list.getSelectedIndex();
					TradeBean bean = vlist.get(i);
					if(mgr.delete(bean.getTradeNum())) {
			            trade2.removeAll();
						viewList(trade2, null, null);
			            trade2.revalidate();
			            trade2.repaint();
					}
					
		}else if(obj==b4/*상세보기 버튼*/) {
			
			
						int i = list.getSelectedIndex();
						TradeBean bean = vlist.get(i);
						System.out.println("버튼 눌림");
						System.out.println(bean.getTradeNum());
						Sf = new ShowFrame(this, Integer.toString(bean.getTradeNum()),trade2);
						Sf.setVisible(true);

//		}else if(obj == AplBtn /*교환신청 버튼*/) {
//			
//					System.out.println("버튼 눌림");
//					tf0.getText();
//					System.out.println(tf0.getText());
//					af = new ApplyFrame3(this, tf0.getText());
//					af.setVisible(true);
						

		}else if("complete".equals(command)) {
			            trade2.removeAll();
						viewList(trade2,null,null);
			            trade2.revalidate();
					    viewList(trade2, null, null);
		    
	    }else if(obj== searchBtn/*검색*/) {
					
	    	
	        System.out.println("버튼 눌림");

	        String currency = (String) currencyComboBox.getSelectedItem();
	        String exchange = (String) exchangeComboBox.getSelectedItem();

	        if (currency.equals(exchange)) {
	            JOptionPane.showMessageDialog(null, "보유 외화와 교환 외화는 같을 수 없습니다. 수정하여 주십시오.");
	        } else {
	            System.out.println("검색 시작");

	            // 선택한 보유 통화와 교환 통화 저장
	            selectedCurrency = (String) currencyComboBox.getSelectedItem();
	            selectedExchange = (String) exchangeComboBox.getSelectedItem();

	            // 선택한 보유 통화로 거래 목록을 보여줌
	            if (selectedCurrency.equals("")||selectedExchange.equals(" ")) {
	                viewList(trade2, null, null); // 전체 보기 옵션일 때, selectedCurrency와 selectedExchange를 null로 전달
	            } else {
	                viewList(trade2, selectedCurrency, selectedExchange);
	            }
	        }
				    
		            trade2.removeAll();
					viewList(trade2, selectedCurrency , selectedExchange);
		            trade2.revalidate();
		            trade2.repaint();
		    
	    }else if(obj == newBtn/*새로고침*/) {
	    	
	    				System.out.println("버튼 눌림");
			            trade2.removeAll();
						viewList(trade2, null, null);
			            trade2.revalidate();
			            trade2.repaint();
	    }
		
		validate();
	}//--actionPerformed
	
	
	public static void main(String[] args) {
		new TestFrameTrade5();
	}
} 
