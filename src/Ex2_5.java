import java.util.Scanner;

public class Ex2_5{
    public static void main(String args[]) {
        System.out.print("이름, 도시, 나이, 체중, 독신 여부를 빈칸으로 분리하여 입력하시오. : ");
    
        Scanner scanner = new Scanner(System.in);
        
        String name = scanner.next();               // 문자열 토큰 읽기
        System.out.println("이름" + name);
        
        String city = scanner.next();               // 문자열 토큰  읽기
        System.out.println("도시" + city);
        
        int age = scanner.nextInt();                // 정수 토큰 읽기
        System.out.println("나이" + (age + age));

        double weight = scanner.nextDouble();       // 실수 토큰 읽기
        System.out.println("체중" + weight);

        boolean single = scanner.nextBoolean();     // 논리 토큰 읽기
        System.out.println("독신" + single);

        scanner.close();    // scanner 스트림 닫기
    }
}