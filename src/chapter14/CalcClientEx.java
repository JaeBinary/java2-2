package chapter14;
import java.io.*;
import java.net.*;
import java.util.*;

public class CalcClientEx {
    public static void main(String[] args) {
        BufferedReader in = null; 																// �����κ����� �Է��� �ޱ� ���� BufferedReader
        BufferedWriter out = null; 																// �������� ����� ���� BufferedWriter
        Socket socket = null; 																	// Ŭ���̾�Ʈ ����
        Scanner scanner = new Scanner(System.in); 												// Ű���� �Է��� �ޱ� ���� Scanner ��ü ����
        try {
            socket = new Socket("localhost", 9999); 									// ������ ������ �õ��ϴ� ���� ����
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 			// ���� �Է� ��Ʈ��
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 		// ���� ��� ��Ʈ��
            while (true) {
                System.out.print("����(��ĭ���� ��� �Է�,��:24 + 42)>>"); 						// ����ڿ��� �Է� ������Ʈ ����
                String outputMessage = scanner.nextLine(); 										// ����ڷκ��� ������ �Է¹���
                if (outputMessage.equalsIgnoreCase("bye")) { 						// ����ڰ� "bye"�� �Է��� ���
                    out.write(outputMessage + "\n"); 											// ������ "bye"�� ����
                    out.flush();
                    break; 																		// ���� ����
                }
                out.write(outputMessage + "\n"); 												// ������ ������ ����
                out.flush();
                String inputMessage = in.readLine(); 											// �����κ��� ��� ����� �о��
                System.out.println("��� ���: " + inputMessage); 								 // ��� ��� ���
            }
        } catch (IOException e) { 																// ����� ���� ó��
            System.out.println(e.getMessage()); 												// ���� �޽��� ���
        } finally {
            try {
                scanner.close(); 																// Scanner ��ü �ݱ�
                if (socket != null) socket.close(); 											// ���� �ݱ�
            } catch (IOException e) {
                System.out.println("�������� ���� ���� �� ������ �߻��߽��ϴ�."); 				  // ���� �߻� �� �޽��� ���
            }
        }
    }
}
