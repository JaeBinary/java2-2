import javax.swing.*;

// 프레임을 만들기 때문에 JFrame 상속
public class MyFrame extends JFrame {
    public MyFrame() {
        setTitle("300x300 스윙 프레임 만들기");
        setSize(300, 300);
        setVisible(true);
    }

    // 너무 초라하고..
    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
    }
    // 창을 닫아도 스레드는 살아있음.(계속 Run이 되고 있음.) 따로 설정해줘야됨.
}
    