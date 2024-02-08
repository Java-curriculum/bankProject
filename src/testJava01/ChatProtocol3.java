package testJava01;

public class ChatProtocol3 {
	
	//ID, CHAT, CHATALL, MESSAGE, CHATLIST
	
	//(C->S) ID:aaa;1234
	//(S->C) ID:T (����), ID:F(����), ID:C(��������)
	//(S->C) CHATLIST:aaa;bbb;ccc;ddd;
	public  static final String ID = "ID";
	
	//(C->S) CHAT:�޴¾��̵�;�޼��� ex) CHAT:bbb;�����
	//(S->C) CHAT:�������̵�;�޼��� ex) CHAT:aaa;�����
	public  static final String CHAT = "CHAT";
	
	//(C->S) CHATALL:�޼���
	//(S->C) CHATALL:[�������̵�]�޼���
	public  static final String CHATALL = "CHATALL";
	
	//(C->S) MESSAGE:�޴¾��̵�;�������� ex) MESSAGE:bbb;�����
	//(S->C) MESSAGE:�������̵�;�������� ex) MESSAGE:aaa;�����
	public  static final String MESSAGE = "MESSAGE";
	
	//(C->S) MSGLIST:id
	//(S->C) MSGLIST:fid,tid,msg;fid,tid,msg;...
	//(S->C) MSGLIST:aaa,bbb,�����;bbb,ccc,����...
	public  static final String MSGLIST = "MSGLIST";
	
	//(S->C) CHATLIST:aaa;bbb;ccc;ddd;
	public  static final String CHATLIST = "CHATLIST";
	
	public static final String MODE = ":";
	//-----------------------------------
	public static final String NAME = "name";
	public static final String LOGIN = "login";
	
}





