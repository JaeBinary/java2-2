public class Ex4_4 {
    String title;
    String author;

    public Ex4_4(){
        title = "해리포터";
        author = "몰라";
    }
    public Ex4_4(String t) { // 생성자
        title = t; author = "작자미상";
    }
    public Ex4_4(String t, String a) { // 생성자
        title = t; author = a;
    }
    public static void main(String [] args) {
        Ex4_4 littlePrince = new Ex4_4("어린왕자", "생텍쥐페리"); 
        Ex4_4 loveStory = new Ex4_4("춘향전"); 
        System.out.println(littlePrince.title + " " + littlePrince.author);
        System.out.println(loveStory.title + " " + loveStory.author);
    }
}