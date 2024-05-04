import java.util.*;

public class HashMapDicEx {
    public static void main(String[] args) {
        // dictionary의 HashMap 생성 및 선언
        HashMap<String, String> dic = new HashMap<String, String>();
        
        // 키 값과 밸류 값 넣기
        dic.put("baby", "아기");
        dic.put("love", "사랑");
        dic.put("apple", "사과");

        // Set 컬렉션(Vector, Iterator와 비슷한 컬렉션) 생성 및 dic의 keySet()으로 선언. KeySet() 메소드는 모든 키를 Set 컬렉션 객체로 만들어 리턴.
        Set<String> keys = dic.keySet();
        // Iterator 컬렉션 생성 및 keys의 iterator()로 선언
        Iterator<String> it = keys.iterator();

        // Iterator 요소 반복문
        while(it.hasNext()) {
            // Iterator로 요소를 뽑으면 keySet()으로 선언했기에 키 값이 뽑힘
            String key = it.next();
            String value = dic.get(key);
            System.out.print("(" + key + "," + value + ")");
        }
        // 엔터 한번
        System.out.println();
        
        // 스캐너 생성 및 선언
        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i < 3; i++) {
            System.out.print("번역하고 싶은 영단어를 입력하십시오:");
            // 입력한 키값은 eng에 키값을 통한 밸류 값은 kor에 넣기
            String eng = scanner.next();
            String kor = dic.get(eng);
            if(kor == null) {
                // 밸류 값 없으면 null이므로
                System.out.println(eng + "라는 단어는 사전에 등록되어 있지 않습니다.");
            } else {
                // 그게 아니면 출력
                System.out.println("번역된 단어:" + kor);
            }
        }

        // 스캐너 닫기
        scanner.close();
    }
}
