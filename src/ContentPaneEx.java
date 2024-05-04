import javax.swing.*;
import java.awt.*;

public class ContentPaneEx extends JFrame {
    public ContentPaneEx() {
        // 제목 달기
        setTitle("ContentPane과 JFrame 예제");
        // 창 닫을 때 스레드 종료(Run 종료)하기
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 컨텐트팬 객체 생성
        Container contentPane = getContentPane();
        // 컨텐트팬의 메서드 활용(배경색, 레이아웃 생성)
        contentPane.setBackground(Color.BLUE);
        contentPane.setLayout(new FlowLayout());

        // 컨텐트 팬에다 컴포넌트 부착(버튼 3개)
        contentPane.add(new JButton("OK"));
        contentPane.add(new JButton("Cancel"));
        contentPane.add(new JButton("Ignore"));

        // 사이즈 조정 및 보이기 설정
        setSize(300, 150);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ContentPaneEx();
    }
}
