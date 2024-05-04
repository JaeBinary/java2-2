import java.util.Vector;

public class VectorEX {
    public static void main(String[] args) {
        
        // 컬렉션 벡터 생성 및 선언
        Vector<Integer> v = new Vector<Integer>();
        // v 벡터의 맨 뒤에 요소 삽입 결과는 5 4 -1로 됨
        // 원래는 Wrapper 클래스만 다루기 때문에 Integer.valueOf(5)로 넣어야 하지만 자동 박싱을 해주므로 그냥 5로 넣음
        v.add(5);
        v.add(4);
        v.add(-1);

        // index 2에 100을 삽입하므로 결과는 5 4 100 -1이 됨.
        v.add(2, 100);

        // size()는 벡터에 요소가 들어있는 수. capacity()는 벡터의 전체 크기(선언 할 때 ()로 비워뒀으므로 10 예상)
        System.out.println("벡터 내의 요소 객체 수: " + v.size());
        System.out.println("벡터의 현재 용량: " + v.capacity());

        // 요소 모두 출력
        for(int i = 0; i < v.size(); i++){
            // v.get(i)은 i의 값을 받아옴. 원래는 Wrapper 클래스 Integer로 받아오지만 자동 언박싱으로 int에 넣을 수 있음.
            int n = v.get(i);
            System.out.println(n);
        }

        // 같은 방식으로 요소 전부 더하기
        int sum = 0;
        for(int i = 0; i < v.size(); i++) {
            int n = v.get(i);
            sum += n;
        }
        System.out.println("백터에 있는 정수의 합: " + sum);

        // 설명 지렸다, 재빈. 인정?
    }
}
