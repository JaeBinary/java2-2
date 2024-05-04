import java.util.Scanner;

public class Ex3_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = 0, n = 0;
        double sum = 0;

        System.out.print("정수를 입력하고 마지막에 0을 입력하시오. : ");

        do {
            n = scanner.nextInt();
            sum += n;
            count++;
        } while (n != 0);
        /*
        while ((n = scanner.nextInt()) != 0) {
            sum += n;
            count++;
        }
        */

        System.out.printf("수의 개수 : %d\n", count);
        System.out.printf("평균 : %f\n", sum / count);

        scanner.close();
    }
}
