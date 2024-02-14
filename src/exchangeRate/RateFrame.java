
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultHighLowDataset;

public class RateFrame extends JFrame implements ActionListener{
	
	JButton logo, logout;
	JButton menu_btn[] = new JButton[5];
	JPanel menu_pan, rate_pan, major_rate_pan, all_rate_pan, p1;
	JTextArea major_rate_header, all_rate_header, major_rate[];
	JTable all_rate_table; 

	public RateFrame() {
		setSize(1200,800);
		setBackground(Color.white);
		
        RateDAO dao = new RateDAO();
        RateInfo todayRate = dao.getTodayRate();
		
		// �޴� �г� ����
        menu_pan = new JPanel();
        menu_pan.setLayout(new GridLayout(7, 1));
        
        logo = new JButton("logo");
        logout = new JButton("�α׾ƿ�");
        
        menu_pan.add(logo);
        
        for (int i = 0; i < menu_btn.length; i++) {
            menu_btn[i] = new JButton("Menu " + (i + 1));
            menu_pan.add(menu_btn[i]);
        }
        menu_pan.add(logout);
        
        // ���� �г� ����
        rate_pan = new JPanel();
        rate_pan.setLayout(new GridLayout(2, 1));

        major_rate_pan = new JPanel();
        all_rate_pan = new JPanel();
        
        major_rate_pan.setLayout(new BorderLayout());
        all_rate_pan.setLayout(new BorderLayout());
        
        major_rate_header = new JTextArea("�ֿ� ���� ȯ�� ��Ȳ");
        major_rate_header.setEditable(false);
        
        p1 = new JPanel();
        p1.setLayout(new GridLayout(2,3));
        
        major_rate = new JTextArea[6];
        
        major_rate[0] = new JTextArea(String.valueOf(todayRate.getUSD()));
		major_rate[1] = new JTextArea(String.valueOf(todayRate.getEUR()));
		major_rate[2] = new JTextArea(String.valueOf(todayRate.getJPY()));
		major_rate[3] = new JTextArea(String.valueOf(todayRate.getGBP()));
		major_rate[4] = new JTextArea(String.valueOf(todayRate.getCNY()));
		major_rate[5] = new JTextArea(String.valueOf(todayRate.getAUD()));

        for (int i = 0; i < major_rate.length; i++) {
			major_rate[i].setEditable(false);
			p1.add(major_rate[i]);
		}
        
        major_rate_pan.add(new JLabel(),BorderLayout.EAST);
        major_rate_pan.add(new JLabel(),BorderLayout.WEST);
        major_rate_pan.add(new JLabel(),BorderLayout.SOUTH);
        major_rate_pan.add(p1,BorderLayout.CENTER);
		major_rate_pan.add(major_rate_header, BorderLayout.NORTH);
        
        all_rate_header = new JTextArea("���� �� ȯ�� ����");
        all_rate_header.setEditable(false);
      
        if(todayRate != null) {
        	
        	//
        	String header[] = {"��ȭ�ڵ�", "�����", "��ȭ��", "������ȭ��"};
            String contents[][] = {        
            		{"USD", "�̱�", "�̱� �޷�", String.valueOf(todayRate.getUSD())},
            		{"JPY","�Ϻ�","�Ϻ� ��",String.valueOf(todayRate.getJPY())},
            		{"THB","�±�","�±� ��Ʈ",String.valueOf(todayRate.getTHB())},
            		{"AUD","ȣ��","ȣ�� �޷�",String.valueOf(todayRate.getAUD())},
            		{"CAD","ĳ����","ĳ���� �޷�",String.valueOf(todayRate.getCAD())},
            		{"CHF","������","������ ����",String.valueOf(todayRate.getCHF())},
            		{"CNY","�߱�","�߱� ����",String.valueOf(todayRate.getCNY())},
            		{"EUR","����","���� ����",String.valueOf(todayRate.getEUR())},
            		{"GBP","����","���� �Ŀ��",String.valueOf(todayRate.getGBP())},
            		{"HKD","ȫ��","ȫ�� �޷�",String.valueOf(todayRate.getHKD())},
            		{"NZD","��������","�������� �޷�",String.valueOf(todayRate.getNZD())},
            		{"SGD","�̰�����","�̰����� �޷�",String.valueOf(todayRate.getSGD())},
            		{"KRW","�ѱ�","�ѱ� ��",String.valueOf(todayRate.getKRW())},
            };
            
            
            DefaultTableModel model = new DefaultTableModel(contents,header) {
            	@Override
                public boolean isCellEditable(int row, int column) {
                   //all cells false
                   return false;
                }

            };
            all_rate_table = new JTable(model);
        }
        
        all_rate_table.setGridColor(Color.white);
        all_rate_table.setShowHorizontalLines(true);
        
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        TableColumnModel tcm = all_rate_table.getColumnModel();
        
        tcm.getColumn(0).setCellRenderer(dtcr);
        tcm.getColumn(1).setCellRenderer(dtcr);
        tcm.getColumn(2).setCellRenderer(dtcr);
        tcm.getColumn(3).setCellRenderer(dtcr);
        
        all_rate_table.setRowHeight(30);
        
        rate_pan.add(major_rate_pan);
        rate_pan.add(all_rate_pan, BorderLayout.CENTER);
        
        all_rate_pan.add(new JScrollPane(all_rate_table),BorderLayout.CENTER);
        all_rate_pan.add(all_rate_header,BorderLayout.NORTH);

        add(menu_pan, BorderLayout.WEST);
        add(rate_pan, BorderLayout.CENTER);

        setVisible(true);
        
        //���̺��� �ο� Ŭ���� ���λ��� ǥ��
        all_rate_table.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if(e.getClickCount() == 1) {
        			JPanel detail_p1 = new JPanel(new GridLayout(1,2));
        			JPanel detail_p2 = new JPanel(new GridLayout(2,1));
        			JButton move_btn = new JButton("ȯ��");
        			JButton exit_btn = new JButton("���");
        			JTable target = (JTable)e.getSource();
        			int row = target.getSelectedRow();
        			Date date = new Date(System.currentTimeMillis());
        			//Ŭ���� ���� ������ �޾ƿ�
        			String cur_unit = (String)target.getValueAt(row, 0);
        			String contury = (String)target.getValueAt(row, 1);
        			String cur_name = (String)target.getValueAt(row, 2);
        			double rate = Double.parseDouble((String)target.getValueAt(row, 3));
        			
        			List<RateInfo> weekRates = dao.getWeekRates(date);
        			
        			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        			
        			
        			for (RateInfo rateInfo : weekRates) {
                        // �� ��¥�� ȯ���� �����ͼ¿� �߰��մϴ�.    
        				dataset.addValue(rateInfo.getRate(cur_unit), cur_unit, new SimpleDateFormat("MM-dd").format(rateInfo.getDAY()));

                    }

                    // �� �׷����� �����մϴ�.
                    JFreeChart lineChart = ChartFactory.createLineChart(
                            "", // ��Ʈ ����
                            cur_unit, // x�� ��
                            "rate", // y�� ��
                            dataset // �����ͼ�
                    );
                    
                    CategoryPlot plot = lineChart.getCategoryPlot();

                    double min = Double.MAX_VALUE;
                    double max = Double.MIN_VALUE;
                    for (RateInfo rateInfo : weekRates) {
                        double rate1 = rateInfo.getRate(cur_unit);
                        min = Math.min(min-0.1, rate1);
                        max = Math.max(max+0.1, rate1);
                    }
                    
                    ValueAxis yAxis = plot.getRangeAxis();
                    yAxis.setRange(min, max);
                    
                    String[] columnNames = {"��¥", "ȯ��"};
                    Object[][] data = new Object[weekRates.size()][2];
                    int i = 0;
                    for (RateInfo rateInfo : weekRates) {
                        data[i][0] = new SimpleDateFormat("MM-dd").format(rateInfo.getDAY());
                        data[i][1] = rateInfo.getRate(cur_unit);
                        i++;
                    }
                    TableModel model = new DefaultTableModel(data, columnNames) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;  // ��� ���� ���� �Ұ����ϰ� ����ϴ�.
                        }
                    };
                    ChartPanel chartPanel = new ChartPanel(lineChart);
                    chartPanel.setDomainZoomable(false);  // x�� ���� ��Ȱ��ȭ�մϴ�.
                    chartPanel.setRangeZoomable(false);   // y�� ���� ��Ȱ��ȭ�մϴ�.
                    chartPanel.setEnabled(false);      // �д��� ��Ȱ��ȭ�մϴ�.

                    
                    
                    // TableModel�� ����Ͽ� JTable�� ����ϴ�.
                    JTable table = new JTable(model);
                    table.setRowHeight(30);

                    // ���� ������ ������ ���ο� JFrame�� �����ϰ� ǥ���մϴ�.
                    JFrame detailFrame = new JFrame(contury);
                    
                    exit_btn.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							detailFrame.dispose();
						}
					});
                    detailFrame.setLayout(new GridLayout(2, 1));
                    detailFrame.add(chartPanel); // �� �׷����� �߰��մϴ�.
                    detailFrame.add(detail_p1);
                    detail_p1.add(table);
                    detail_p1.add(detail_p2);
                    detail_p2.add(move_btn);
                    detail_p2.add(exit_btn);
                    detailFrame.setSize(800, 510);
                    detailFrame.setLocationRelativeTo(RateFrame.this); // �θ� â�� �߾ӿ� ��ġ��ŵ�ϴ�.
                    detailFrame.setVisible(true);
        		}
        	}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	public static void main(String[] args) {
		RateAPI ra = new RateAPI();
//		ra.main(args);
		new RateFrame();
	}
}