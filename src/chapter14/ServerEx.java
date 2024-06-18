package chapter14;
import java.io.*;
import java.net.*;
import java.util.*;

public class ServerEx {
    public static void main(String[] args) {
        BufferedReader in = null; 														 // 클라이언트로부터의 입력을 받기 위한 BufferedReader
        BufferedWriter out = null; 														 // 클라이언트로의 출력을 위한 BufferedWriter
        ServerSocket listener = null; 													 // 클라이언트의 연결을 받기 위한 ServerSocket
        Socket socket = null; 															 // 통신을 위한 Socket
        Scanner scanner = new Scanner(System.in); 										 // 키보드 입력을 받기 위한 Scanner 객체 생성
        try {
            listener = new ServerSocket(9999); 									 // 포트 9999에서 클라이언트의 연결 요청을 받을 ServerSocket 생성
            System.out.println("연결을 기다리고 있습니다.....");
            socket = listener.accept(); 												 // 클라이언트의 연결 요청을 기다리고, 연결을 수락하여 Socket 생성
            System.out.println("연결되었습니다.");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 	 // 소켓 입력 스트림
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));  // 소켓 출력 스트림
            while (true) {
                String inputMessage = in.readLine(); 									 // 클라이언트로부터 한 행 읽기
                if (inputMessage.equalsIgnoreCase("bye")) {
                    System.out.println("클라이언트에서 bye로 연결을 종료하였음"); 		  // "bye"를 받으면 연결 종료 메시지 출력
                    break; 																 // 연결 종료
                }
                System.out.println("클라이언트: " + inputMessage); 						   // 클라이언트가 보낸 메시지를 콘솔에 출력
                System.out.print("보내기>>"); 										  // 사용자에게 입력 프롬프트 제공
                String outputMessage = scanner.nextLine(); 								 // 사용자로부터 메시지 입력받음
                out.write(outputMessage + "\n"); 										 // 클라이언트로 메시지 전송
                out.flush(); 															 // 버퍼 비우기
            }
        } catch (IOException e) { 														 // 입출력 예외 처리
            System.out.println(e.getMessage()); 										 // 예외 메시지 출력
        } finally {
            try {
                scanner.close(); 														 // Scanner 객체 닫기
                if (socket != null) socket.close(); 									 // 소켓 닫기
                if (listener != null) listener.close(); 								 // 서버 소켓 닫기
            } catch (IOException e) {
                System.out.println("클라이언트와의 연결 해제 중 오류가 발생했습니다."); 		// 예외 발생 시 메시지 출력
            }
        }
    }
}
