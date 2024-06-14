import java.io.*;
import java.net.*;
import java.util.*;

public class ServerEx {
    public static void main(String[] args) {
        BufferedReader in = null; 														 // Ŭ���̾�Ʈ�κ����� �Է��� �ޱ� ���� BufferedReader
        BufferedWriter out = null; 														 // Ŭ���̾�Ʈ���� ����� ���� BufferedWriter
        ServerSocket listener = null; 													 // Ŭ���̾�Ʈ�� ������ �ޱ� ���� ServerSocket
        Socket socket = null; 															 // ����� ���� Socket
        Scanner scanner = new Scanner(System.in); 										 // Ű���� �Է��� �ޱ� ���� Scanner ��ü ����
        try {
            listener = new ServerSocket(9999); 									 // ��Ʈ 9999���� Ŭ���̾�Ʈ�� ���� ��û�� ���� ServerSocket ����
            System.out.println("������ ��ٸ��� �ֽ��ϴ�.....");
            socket = listener.accept(); 												 // Ŭ���̾�Ʈ�� ���� ��û�� ��ٸ���, ������ �����Ͽ� Socket ����
            System.out.println("����Ǿ����ϴ�.");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 	 // ���� �Է� ��Ʈ��
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));  // ���� ��� ��Ʈ��
            while (true) {
                String inputMessage = in.readLine(); 									 // Ŭ���̾�Ʈ�κ��� �� �� �б�
                if (inputMessage.equalsIgnoreCase("bye")) {
                    System.out.println("Ŭ���̾�Ʈ���� bye�� ������ �����Ͽ���"); 		  // "bye"�� ������ ���� ���� �޽��� ���
                    break; 																 // ���� ����
                }
                System.out.println("Ŭ���̾�Ʈ: " + inputMessage); 						   // Ŭ���̾�Ʈ�� ���� �޽����� �ֿܼ� ���
                System.out.print("������>>"); 										  // ����ڿ��� �Է� ������Ʈ ����
                String outputMessage = scanner.nextLine(); 								 // ����ڷκ��� �޽��� �Է¹���
                out.write(outputMessage + "\n"); 										 // Ŭ���̾�Ʈ�� �޽��� ����
                out.flush(); 															 // ���� ����
            }
        } catch (IOException e) { 														 // ����� ���� ó��
            System.out.println(e.getMessage()); 										 // ���� �޽��� ���
        } finally {
            try {
                scanner.close(); 														 // Scanner ��ü �ݱ�
                if (socket != null) socket.close(); 									 // ���� �ݱ�
                if (listener != null) listener.close(); 								 // ���� ���� �ݱ�
            } catch (IOException e) {
                System.out.println("Ŭ���̾�Ʈ���� ���� ���� �� ������ �߻��߽��ϴ�."); 		// ���� �߻� �� �޽��� ���
            }
        }
    }
}
