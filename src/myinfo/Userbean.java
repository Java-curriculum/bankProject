package myinfo;

//이름, 아이디, 비밀번호, 가상계좌, 휴대번호, 통신사 값

public class Userbean {
	String name, id, pw, account, phone, telecom;
	
	public String getName() { //이름
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() { //아이디
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() { //비밀번호
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getAccount() { //가상계좌
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPhone() { //휴대번호
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTelecom() { //통신사
		return telecom;
	}
	public void setTelecom(String telecom) {
		this.telecom = telecom;
	}
	public void show() {
		System.out.println(name+ id+ pw+ account+ phone+ telecom);
	}
}
