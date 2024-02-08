package testJava01;

import java.util.HashMap;
import java.util.Map;
//이곳은 회원들의 정보를 일괄적으로 모아서 관리해주는 클래스이다.
//없는 정보는 testSocket객체에게 요청하여서 서버와의 통신한뒤에 정보값을 리턴받는다.
public class MemberManager {
	TestSocket testSocket = TestSocket.getInstance();
    private static MemberManager instance;
    private Map<Integer, String> members;
    //회원 정보(임시)
    private int id;
    private String name;
    private int password;

    // private 생성자로 외부에서 직접 인스턴스를 생성하지 못하도록 함
    private MemberManager() {
        members = new HashMap<>();
    }

    // 인스턴스를 얻는 정적 메서드
    public static MemberManager getInstance() {
    	if (instance == null) {
    		instance = new MemberManager();
    	}
    	return instance;
    }
    
    public void addMember(int memberId, String memberInfo) {
        members.put(memberId, memberInfo);
    }

    public String getMember(int memberId) {
        return members.get(memberId);
    }

    public void printMembers() {
        for (Map.Entry<Integer, String> entry : members.entrySet()) {
            System.out.println("회원 ID: " + entry.getKey() + ", 회원 정보: " + entry.getValue());
        }
    }

    
    

}