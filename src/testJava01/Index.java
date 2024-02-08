package testJava01;
//오늘 해야할것 -> 공유할수 있을 정도로 코드를 가다듬고 정리해서 업로드 하자
public class Index {
	
	
	
	public static void main(String[] args) {
        // 싱글톤 매니저 인스턴스를 가져옴
        SceneManager sceneManager = SceneManager.getInstance();
        
        
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
        
        
    }
}

