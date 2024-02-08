package testJava01;

public class Index {
	
	
	
	public static void main(String[] args) {
        // 싱글톤 매니저 인스턴스를 가져옴
        SceneManager sceneManager = SceneManager.getInstance();
        TestSocket testSocket = TestSocket.getInstance();
        
        // 각각의 씬 인스턴스를 가져옴
        Scene1 scene1 = Scene1.getInstance();
        Scene2 scene2 = Scene2.getInstance();
        
        LogInScene loginScene = LogInScene.getInstance();
        HomeScene homeScene = HomeScene.getInstance();
        
        
        // 씬 전환 매니저에 씬을 추가
        sceneManager.frame.add(scene1, Scene1.class.getName());
        sceneManager.frame.add(scene2, Scene2.class.getName());
        
        sceneManager.frame.add(loginScene,LogInScene.class.getName());
        sceneManager.frame.add(homeScene,HomeScene.class.getName());
        // 초기에 보여질 씬 설정
        sceneManager.switchToScene(loginScene);

        // 어떤 상황에서든 필요한 경우 씬 전환 매니저를 통해 씬을 전환할 수 있음
        // 예를 들어, 버튼 클릭 등의 이벤트에서 아래와 같이 사용할 수 있습니다.
        // sceneManager.switchToScene(scene2);
        
        //-------------------------------------------
        // 싱글톤 인스턴스 얻기
        MemberManager manager1 = MemberManager.getInstance();
        MemberManager manager2 = MemberManager.getInstance();

        // 회원 추가
        manager1.addMember(1, "John Doe");
        manager2.addMember(2, "Jane Doe");

        // 회원 정보 출력
        manager1.printMembers();
        manager2.printMembers();

        // 두 인스턴스가 동일한 객체를 참조하는지 확인
        System.out.println(manager1 == manager2);
        
    }
}

