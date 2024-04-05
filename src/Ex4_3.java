import java.util.Scanner;

int width;
int height;

public int getArea() {
    return width*height;
}

public class Ex4_3 {
    int radius;
    String name;

    public Ex4_3() {
        radius = 1;
        name = "";
    }
    public Ex4_3() {
        width = 10;
    }
    public Ex4_3(int x, int y) {
        width = x;
        height = y;
    }
    public static void main(String[] args) {
        Ex4_3 foo = new Ex4_3();
        foo.height = 3;
        System.out.println("면적은" + );
    }
}