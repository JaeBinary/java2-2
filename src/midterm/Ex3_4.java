public class Ex3_4 {
    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                System.out.printf("%d x %d = %d", i, j, i*j);
                System.out.printf("\t");
            }
            System.out.println();
        }
    }
}
