/*
 * 일자 : 24.06.07 (13주차)
 * 범위 : Chapter 11. 그래픽
 * 예제 : 11-5. 원본 크기로 이미지 그리기
 */

 package chapter11; 				// chapter11 패키지에 속하는 클래스임을 명시
 import javax.swing.*; 				// javax.swing 패키지의 모든 클래스를 임포트
 import java.awt.*; 				// java.awt 패키지의 모든 클래스를 임포트
 
 // GraphicsDrawImageEx1 클래스는 JFrame을 상속받는다.
 public class GraphicsDrawImageEx1 extends JFrame {
	 
	 // GraphicsDrawImageEx1 생성자
	 public GraphicsDrawImageEx1() {
		 setTitle("원본 크기로 원하는 위치에 이미지 그리기"); 			  // 프레임의 제목 설정
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);					// 프레임의 닫기 버튼을 클릭했을 때 프로그램이 종료되도록 설정
		 setContentPane(new MyPanel()); 									// 새로운 패널(MyPanel)을 생성하여 컨텐트팬으로 설정
		 
		 setSize(300, 400); 									// 프레임의 크기를 가로 300, 세로 400으로 설정
		 setVisible(true); 												// 프레임을 화면에 보이도록 설정
	 }
 
	 // JPanel을 상속받아 MyPanel 클래스 선언
	 class MyPanel extends JPanel {
		 
		 // ImageIcon을 이용하여 이미지 로딩
		 private ImageIcon icon = new ImageIcon("images/11-5/image0.jpg");		// 이미지 로딩
		 private Image img = icon.getImage(); 											// Image 객체로 변환
 
		 // paintComponent 메서드 오버라이딩
		 public void paintComponent(Graphics g) {
			 super.paintComponent(g); 													// 부모 클래스(JPanel)의 paintComponent 메서드 호출
			 
			 // 이미지를 패널의 (20, 20) 위치에 원래 크기로 그린다
			 g.drawImage(img, 20, 20, this);
		 }
	 }
	 
	 // main 메서드: 프로그램의 시작점
	 public static void main(String[] args) {
		 new GraphicsDrawImageEx1(); 		// GraphicsDrawImageEx1 객체를 생성하여 프로그램을 실행
	 }
 }
 