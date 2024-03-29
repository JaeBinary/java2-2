import java.util.Scanner;

public class Ex2_14 {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("1 ~ 12월을 입력하시오. : ");

        int month = scanner.nextInt();

        if(3 <= month && month <= 5) {
            System.out.println("봄입니다.");
        }
        else if(6 <= month && month <= 8) {
            System.out.println("여름입니다.");
        }
        else if(9 <= month && month <= 11) {
            System.out.println("가을입니다.");
        }
        else if(month <= 2 || month == 12) {
            System.out.println("겨울입니다.");
        }
        else {
            System.err.println("1 ~ 12월 입력하세요.");
        }
        
        scanner.close();
    }
}
