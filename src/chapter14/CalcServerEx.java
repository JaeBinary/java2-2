package chapter14;
import java.io.*;
import java.net.*;
import java.util.*;

public class CalcServerEx {

    // ������ ����ϴ� �޼���
    public static String calc(String exp) {
        StringTokenizer st = new StringTokenizer(exp, " "); 								// ������ ������ �������� �и��ϴ� StringTokenizer ����
        if (st.countTokens() != 3) return "error"; 												// �ǿ����ڿ� �����ڸ� ����� �и����� ���� ��� ���� ��ȯ

        String res = "";
        int op1 = Integer.parseInt(st.nextToken()); 											// ù ��° �ǿ�����
        String opcode = st.nextToken(); 														// ������
        int op2 = Integer.parseInt(st.nextToken()); 											// �� ��° �ǿ�����
        switch (opcode) {
            case "+": res = Integer.toString(op1 + op2); 										// ���� ����
                break;
            case "-": res = Integer.toString(op1 - op2); 										// ���� ����
                break;
            case "*": res = Integer.toString(op1 * op2); 										// ���� ����
                break;
            default : res = "error"; 															// �� �� ���� �������� ��� ���� ��ȯ
        }
        return res; 																			// ��� ��� ��ȯ
    }

    // ���� ���� �޼���
    public static void main(String[] args) {
        BufferedReader in = null; 																// Ŭ���̾�Ʈ�κ����� �Է��� �ޱ� ���� BufferedReader
        BufferedWriter out = null; 																// Ŭ���̾�Ʈ���� ����� ���� BufferedWriter
        ServerSocket listener = null; 															// Ŭ���̾�Ʈ�� ������ �ޱ� ���� ServerSocket
        Socket socket = null; 																	// ����� ���� Socket
        try {
            listener = new ServerSocket(9999); 											// ��Ʈ 9999���� Ŭ���̾�Ʈ�� ���� ��û�� ���� ServerSocket ����
            System.out.println("������ ��ٸ��� �ֽ��ϴ�.....");
            socket = listener.accept(); 														// Ŭ���̾�Ʈ�� ���� ��û�� ��ٸ���, ������ �����Ͽ� Socket ����
            System.out.println("����Ǿ����ϴ�.");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 			// ���� �Է� ��Ʈ��
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 		// ���� ��� ��Ʈ��
            while (true) {
                String inputMessage = in.readLine(); 											// Ŭ���̾�Ʈ�κ��� ������ �Է¹���
                if (inputMessage.equalsIgnoreCase("bye")) {
                    System.out.println("Ŭ���̾�Ʈ���� ������ �����Ͽ���"); 						 // "bye" �޽����� ������ ���� ���� �޽��� ���
                    break; 																		// ���� ����
                }
                System.out.println(inputMessage); 												// ���� �޽����� �ֿܼ� ���
                String res = calc(inputMessage); 												// �Է¹��� ������ ����Ͽ� ����� ����
                out.write(res + "\n"); 															// ��� ����� Ŭ���̾�Ʈ���� ����
                out.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage()); 												// ����� ���� �߻� �� �޽��� ���
        } finally {
            try {
                if (socket != null) socket.close(); 											// ���� �ݱ�
                if (listener != null) listener.close(); 										// ���� ���� �ݱ�
            } catch (IOException e) {
                System.out.println("Ŭ���̾�Ʈ���� ���� ���� �� ������ �߻��߽��ϴ�."); 			   // ���� �߻� �� �޽��� ���
            }
        }
    }
}
