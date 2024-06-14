/*
 * 일자 : 24.06.14 (14week)
 * 범위 : Chapter 13. 입출력 스트림과 파일 입출력
 * 예제 : 13-5. File 클래스를 활용한 파일 관리
 */

 package chapter13;                // chapter13 패키지에 속하는 클래스임을 명시
 import java.io.File;              // 자바 파일 입출력 관련 클래스를 포함하는 패키지를 임포트
 
 public class FileClassExample {   																// FileClassExample 클래스 선언
	
	 // 디렉토리에 포함된 파일과 서브 디렉토리의 이름, 크기, 수정 시간을 출력하는 메소드
	 public static void listDirectory(File dir) {
		 System.out.println("-----" + dir.getPath() + "의 서브 리스트 입니다.-----");
		 
		 File[] subFiles = dir.listFiles(); 													// 디렉토리에 포함된 파일과 디렉토리 이름의 리스트 얻기
		 for (int i = 0; i < subFiles.length; i++) { 											// subFiles 배열의 각 File에 대해 루프
			 File f = subFiles[i];
			 long t = f.lastModified(); 														// 마지막으로 수정된 시간
			 System.out.print(f.getName());
			 System.out.print("\t파일 크기: " + f.length()); 									 // 파일 크기 출력
			 System.out.printf("\t수정한 시간: %tb %td %ta %tT\n", t, t, t, t); 			 // 포맷 출력
		 }        
	 }
	 
	 public static void main(String[] args) {
		 File f1 = new File("c:\\windows\\system.ini"); 								// File 객체 생성
		 System.out.println(f1.getPath() + ", " + f1.getParent() + ", " + f1.getName()); 		// 파일의 경로, 부모 디렉토리, 이름 출력
 
		 String res = "";
		 if (f1.isFile()) res = "파일"; 														// 파일 타입이면
		 else if (f1.isDirectory()) res = "디렉토리"; 											 // 디렉토리 타입이면
		 System.out.println(f1.getPath() + "은 " + res + "입니다."); 							 // 파일 또는 디렉토리 타입 출력
 
		 File f2 = new File("c:\\Temp\\java_sample"); 									// 새로 만들고자 하는 디렉터리
		 if (!f2.exists()) { 																	// f2 디렉터리가 존재하는지 검사
			 f2.mkdir(); 																		// 존재하지 않으면 디렉토리 생성
		 }
		 
		 listDirectory(new File("c:\\Temp")); 											// c:\Temp에 있는 파일 리스트 출력
		 f2.renameTo(new File("c:\\Temp\\javasample")); 								// java_sample -> javasample로 이름 변경
		 listDirectory(new File("c:\\Temp")); 											// javasample로 변경한 후 리스트 출력
	 }
 }
 