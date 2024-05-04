import java.util.*;

public class IteratorEX {
    public static void main(String[] args) {
        // 벡터 v 생성 및 선언
        Vector<Integer> v = new Vector<Integer>();
        // 요소 5 4 -1 후 2번 인덱스에 100 넣어서 결과는 5 4 100 -1
        v.add(5);
        v.add(4);
        v.add(-1);
        v.add(2, 100);

        // Iterator 생성 후 v의 iterator()로 선언
        Iterator<Integer> it = v.iterator();
        // hasNext()는 다음 요소가 남아있으면 true 리턴. next()와의 시너지로 요소 마지막까지 반복.
        while(it.hasNext()) {
            // next()는 다음 요소 리턴. 요소를 뽑기 때문에 다음 요소로 넘어감
            int n = it.next();
            System.out.println(n);
        }

        // 합 구하기
        int sum = 0;
        // 위에서 이미 끝까지 뽑았기 때문에 다시 선언해주어야함.
        it = v.iterator();
        // 같은 방식
        while(it.hasNext()) {
            int n = it.next();
            sum += n;
        }
        System.out.println("벡터에 있는 정수의 합: " + sum);
    }
}
