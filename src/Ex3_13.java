import java.util.Scanner;
public class Ex3_13 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dividend; // 나뉨수
        int divisor; // 나눗수
        
        System.out.print("제수를 입력하시오 : ");
        dividend = scanner.nextInt(); // 제수 입력
        System.out.print("피제수를 입력하시오 : ");
        divisor = scanner.nextInt(); // 피제수 입력
        
        try {
        System.out.printf("%d를 %d로 나누면 몫은 %d입니다.", dividend, divisor, dividend/divisor);
        }
        catch(ArithmeticException e) { // ArithmeticException 예외 처리 코드
        System.out.println("0으로 나눌 수 없습니다!");
        }
        finally {
        scanner.close(); // 정상적이든 예외가 발생하든 최종적으로 scanner를 닫는다.
        }
    }
}