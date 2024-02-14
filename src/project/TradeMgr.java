package project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class TradeMgr {
	
	private DBConnectionMgr pool;
	
	public TradeMgr() {
	try { //DBConnectionMgr 생성코드 - Connection 객체 10개
		pool = DBConnectionMgr.getInstance();
//		Connection con = pool.getConnection(); 
//		System.out.println("성공");
	} catch (Exception e) {
		e.printStackTrace();
		}
	
	}

	//모든 리스트 : select
			public Vector<TradeBean> selectAll(){
				Connection con = null;
				PreparedStatement  pstmt = null;
				ResultSet rs = null;
				String sql = null;
				Vector<TradeBean> vlist = new Vector<TradeBean>();
				try { 
					con = pool.getConnection();
					sql = "select * from trade_history";
					pstmt = con.prepareStatement(sql);
					rs =pstmt.executeQuery();
					while(rs.next()) {
						TradeBean bean = new TradeBean();
						bean.setTradeNum(rs.getInt("trade_num"));
						bean.setTraderAcc1(rs.getString("trader_account_1"));
						bean.setTraderAcc2(rs.getString("trader_account_2"));
						bean.setDay(rs.getDate("day"));
						bean.setSellCur(rs.getString("sell_cur"));
						bean.setSellAmt(rs.getDouble("sell_amt"));
						bean.setBuyCur(rs.getString("buy_cur"));
						bean.setBuyAmt(rs.getDouble("buy_amt"));
						bean.setRate(rs.getDouble("rate"));
						bean.setCheck1(rs.getBoolean("check1"));
						bean.setCheck2(rs.getBoolean("check2"));
						
						vlist.addElement(bean);
					}
					System.out.println("size : " + vlist.size());
					} catch(Exception e) {
					e.printStackTrace();
				}finally {
					pool.freeConnection(con,pstmt,rs);
				
				}
				return vlist;
			}
			
			
			// TradeMgr 클래스의 selectAll 메서드 내에 정렬 기능을 추가합니다.
			public Vector<TradeBean> selectAllDESC() {
			    Vector<TradeBean> list = new Vector<>();
				Connection con = null;
				PreparedStatement  pstmt = null;
				ResultSet rs = null;
				String sql = null;
				
			    try {
			        sql = "SELECT * FROM trade_history WHERE Check1 = false ORDER BY trade_num DESC"; // TradeNum을 기준으로 오름차순으로 정렬
			        con = pool.getConnection();
			        pstmt = con.prepareStatement(sql);
			        rs = pstmt.executeQuery();
			        while (rs.next()) {
			            TradeBean bean = new TradeBean();
			            bean.setTradeNum(rs.getInt("trade_num"));
			            bean.setTraderAcc1(rs.getString("trader_account_1"));
			            bean.setSellCur(rs.getString("sell_cur"));
			            bean.setBuyCur(rs.getString("buy_cur"));
			            bean.setSellAmt(rs.getInt("sell_amt"));
			            bean.setBuyAmt(rs.getInt("buy_amt"));
			            bean.setDay(rs.getDate("day"));
			            bean.setCheck1(rs.getBoolean("check1"));
			            list.add(bean);
			        }
			        rs.close();
			    } catch (Exception e) {
			        e.printStackTrace();
			    }finally {
					pool.freeConnection(con,pstmt,rs);
				
				}
			    return list;
			}
			
	
//			bean.setSellCur(rs.getString("sell_cur"));
//			bean.setSellAmt(rs.getInt("sell_amt"));
//			bean.setBuyCur(rs.getString("buy_cur"));
//			bean.setBuyAmt(rs.getInt("buy_amt"));
//			bean.setRate(rs.getDouble("rate"));
//			bean.setCheck1(rs.getBoolean("check1"));
//			bean.setCheck2(rs.getBoolean("check2"));
			
			
			public boolean insert(TradeBean bean){
				Connection con = null;
				PreparedStatement  pstmt = null;
				String sql = null;
				boolean flag = false;
					try { 
					con = pool.getConnection();
					sql = "insert trade_history values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
					pstmt = con.prepareStatement(sql);
					//setString -> 'aaa'
					pstmt.setInt(1, bean.getTradeNum());
					pstmt.setString(2, bean.getTraderAcc1());
					pstmt.setString(3, bean.getTraderAcc2());
					pstmt.setDate(4, bean.getDay());
					pstmt.setString(5, bean.getSellCur());
					pstmt.setDouble(6, bean.getSellAmt());
					pstmt.setString(7, bean.getBuyCur());
					pstmt.setDouble(8, bean.getBuyAmt());
					pstmt.setDouble(9, bean.getRate());
					pstmt.setBoolean(10, bean.getCheck1());
					pstmt.setBoolean(11, bean.getCheck2());

					int cnt = pstmt.executeUpdate(); 
					if(cnt ==  1)flag = true; 
					} catch(Exception e) {
					e.printStackTrace();
					
				}finally {
					pool.freeConnection(con,pstmt);	
				}
				return flag;
			}
					
			
			
			//거래 테이블 id 검색 select	
			public TradeBean selectID(String id) {
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String sql = null;
				TradeBean bean = new TradeBean();
				try {
					con = pool.getConnection();
					sql = "select * from trade_history where trader_account_1 = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, id); 
					rs = pstmt.executeQuery();
					if(rs.next()) {
						
						bean.setTradeNum(rs.getInt(1));
						bean.setTraderAcc1(rs.getString(2));
						bean.setTraderAcc2(rs.getString(3));
						bean.setDay(rs.getDate(4));
						bean.setSellCur(rs.getString(5));
						bean.setSellAmt(rs.getInt(6));
						bean.setBuyCur(rs.getString(7));
						bean.setBuyAmt(rs.getInt(8));
						bean.setRate(rs.getDouble(9));
						bean.setCheck1(rs.getBoolean(10));
						bean.setCheck2(rs.getBoolean(11));

						
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					pool.freeConnection(con, pstmt, rs);
				}
				return bean;
			}
			
			
			//환율 테이블 검색 select	
			public Double selectRate(String id) {
			    Connection con = null;
			    PreparedStatement pstmt = null;
			    ResultSet rs = null;
			    String sql = null;
			    Double rate = null; // rate 변수를 초기화
			    ExchangeRateBean bean = new ExchangeRateBean();
			    try {
			        con = pool.getConnection();
			        sql = "SELECT * FROM exchange_rate";
			        pstmt = con.prepareStatement(sql);
			        rs = pstmt.executeQuery();
			        if (rs.next()) {
			            if (id.equals("DAY")) {
			                rate = (double) rs.getDate(1).getTime(); // ms로 변환하여 rate에 저장
			            } else if (id.equals("USD")) {
			                rate = rs.getDouble(2);
			            } else if (id.equals("JPY")) {
			                rate = rs.getDouble(3);
			            } else if (id.equals("THB")) {
			                rate = rs.getDouble(4);
			            } else if (id.equals("AUD")) {
			                rate = rs.getDouble(5);
			            } else if (id.equals("CAD")) {
			                rate = rs.getDouble(6);
			            } else if (id.equals("CHF")) {
			                rate = rs.getDouble(7);
			            } else if (id.equals("CNY")) {
			                rate = rs.getDouble(8);
			            } else if (id.equals("EUR")) {
			                rate = rs.getDouble(9);
			            } else if (id.equals("GBP")) {
			                rate = rs.getDouble(10);
			            } else if (id.equals("HKD")) {
			                rate = rs.getDouble(11);
			            } else if (id.equals("NZD")) {
			                rate = rs.getDouble(12);
			            } else if (id.equals("SGD")) {
			                rate = rs.getDouble(13);
			            } else if (id.equals("KRW")) {
			                rate = rs.getDouble(14);
			            }
			        }
			    } catch (Exception e) {
			        e.printStackTrace();
			    } finally {
			        pool.freeConnection(con, pstmt, rs);
			    }

			    return rate;
			}
			
			
			// 거래 테이블  번호로 검색 select	
			public TradeBean selectNO(String no) {
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String sql = null;
				TradeBean bean = new TradeBean();
				try {
					con = pool.getConnection();
					sql = "select * from trade_history where trade_num = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, no); 
					rs = pstmt.executeQuery();
					if(rs.next()) {
						
						bean.setTradeNum(rs.getInt(1));
						bean.setTraderAcc1(rs.getString(2));
						bean.setTraderAcc2(rs.getString(3));
						bean.setDay(rs.getDate(4));
						bean.setSellCur(rs.getString(5));
						bean.setSellAmt(rs.getInt(6));
						bean.setBuyCur(rs.getString(7));
						bean.setBuyAmt(rs.getInt(8));
						bean.setRate(rs.getDouble(9));
						bean.setCheck1(rs.getBoolean(10));
						bean.setCheck2(rs.getBoolean(11));

						
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					pool.freeConnection(con, pstmt, rs);
				}
				return bean;
			}
			
			
			
			//계좌 테이블 검색 select	mo = money
			public Double selectAccMo(String id, String mo) {
			    Connection con = null;
			    PreparedStatement pstmt = null;
			    ResultSet rs = null;
			    String sql = null;
			    Double AccAmt = null; // rate 변수를 초기화
			    AccountBean bean = new AccountBean();
			    try {
			        con = pool.getConnection();
			        sql = "SELECT * FROM account where id = ?";
			        pstmt = con.prepareStatement(sql);
			        pstmt.setString(1, id);
			        rs = pstmt.executeQuery();
			        if (rs.next()) {
			            if (mo.equals("account_num")) {
			                AccAmt = (Double.parseDouble(rs.getString(1))); // ms로 변환하여 rate에 저장
			            } else if (mo.equals("id")) {
			            	AccAmt =(Double.parseDouble(rs.getString(2)));    
			            } else if (mo.equals("USD")) {
			            	AccAmt = rs.getDouble(3);
			            } else if (mo.equals("JPY")) {
			            	AccAmt = rs.getDouble(4);
			            } else if (mo.equals("THB")) {
			            	AccAmt = rs.getDouble(5);
			            } else if (mo.equals("AUD")) {
			            	AccAmt = rs.getDouble(6);
			            } else if (mo.equals("CAD")) {
			            	AccAmt = rs.getDouble(7);
			            } else if (mo.equals("CHF")) {
			            	AccAmt = rs.getDouble(8);
			            } else if (mo.equals("CNY")) {
			            	AccAmt = rs.getDouble(9);
			            } else if (mo.equals("EUR")) {
			            	AccAmt = rs.getDouble(10);
			            } else if (mo.equals("GBP")) {
			            	AccAmt = rs.getDouble(11);
			            } else if (mo.equals("HKD")) {
			            	AccAmt = rs.getDouble(12);
			            } else if (mo.equals("NZD")) {
			            	AccAmt = rs.getDouble(13);
			            } else if (mo.equals("SGD")) {
			            	AccAmt = rs.getDouble(14);
			            } else if (mo.equals("KRW")) {
			            	AccAmt = rs.getDouble(15);
			            }
			        }
			        
			    } catch (Exception e) {
			        e.printStackTrace();
			    } finally {
			        pool.freeConnection(con, pstmt, rs);
			    }
			    if(AccAmt == null) {
			    	return 0.0;
			    }else {
			    return AccAmt;
			    }
			}
			
			
			//  update
			
				public boolean update(TradeBean bean) {
					Connection con = null;
					PreparedStatement pstmt = null;
					String sql = null;
					boolean flag = false;
					try {
						con = pool.getConnection();
						sql = "update trade_history set sell_cur = ?, sell_amt = ?, buy_cur = ?, buy_amt = ?, check1 = ? where trade_num =?";
						pstmt = con.prepareStatement(sql);
						
//						pstmt.setInt(1, bean.getTradeNum());
//						pstmt.setString(1, bean.getTraderAcc1());
//						pstmt.setString(3, bean.getTraderAcc2());
//						pstmt.setDate(4, bean.getDay());
						pstmt.setString(1, bean.getSellCur());
						System.out.println(bean.getSellCur());
						pstmt.setDouble(2, bean.getSellAmt());
						System.out.println(bean.getSellAmt());
						pstmt.setString(3, bean.getBuyCur());
						System.out.println(bean.getBuyCur());
						pstmt.setDouble(4, bean.getBuyAmt());
						System.out.println(bean.getBuyAmt());
//						pstmt.setDouble(9, bean.getRate());
						pstmt.setBoolean(5, bean.getCheck1());
//						pstmt.setBoolean(11, false);
						pstmt.setInt(6, bean.getTradeNum());
						System.out.println(bean.getTradeNum());
						
//					    int rowsAffected = pstmt.executeUpdate();
//					    if(rowsAffected == 1) {
//					        System.out.println("1 row updated successfully.");
//					    } else {
//					        System.out.println(rowsAffected + " rows updated successfully.");
//					    }
//					    
//						flag = true;
		
						if(pstmt.executeUpdate()==1)flag = true; 
					} catch (Exception e) {				
						e.printStackTrace();
					} finally {
						pool.freeConnection(con, pstmt);
					}
					return flag;
					
				}
			
				
				//check update
				public boolean updateCheck(TradeBean bean) {
					Connection con = null;
					PreparedStatement pstmt = null;
					String sql = null;
					boolean flag = false;
					try {
						con = pool.getConnection();
						sql = "update trade_history set  check1 = ?, check2 = ? where trade_num =?";
						pstmt = con.prepareStatement(sql);
						
						pstmt.setBoolean(1, bean.getCheck1());
						pstmt.setBoolean(2, bean.getCheck2());
						pstmt.setInt(3, bean.getTradeNum());
						System.out.println(bean.getCheck1());
						System.out.println(bean.getTradeNum());
		
						if(pstmt.executeUpdate()==1)flag = true; 
					} catch (Exception e) {				
						e.printStackTrace();
					} finally {
						pool.freeConnection(con, pstmt);
					}
					return flag;
				}
				
				
				
				
				//  delete
				
				public boolean delete(int id) {
					Connection con = null;
					PreparedStatement pstmt = null;
					String sql = null;
					boolean flag = false;
					try {
						con = pool.getConnection();
						sql = "delete from trade_history where trade_num = ?";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, id);

						if(pstmt.executeUpdate()==1)flag = true;
						
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						pool.freeConnection(con, pstmt);
					}
					return flag;
					
				}
	

		
		
		
	public static void main(String[] args) {
		TradeMgr mgr = new TradeMgr();
		Vector<TradeBean> vlist = mgr.selectAll();
		for ( int i = 0 ; i < vlist.size(); i++) {
//			//벡터에 저장된 빈즈 객체를 리턴
//			MemberBean bean = vlist.get(i);
//			System.out.print(bean.getId() + "\t");
//			System.out.print(bean.getName() + "\t");
//			System.out.print(bean.getPhone() + "\t");
//			System.out.print(bean.getAddress() + "\t");
//			System.out.println(bean.getTeam());
		}
		TradeBean bean = new TradeBean();

	}

}
