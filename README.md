# JAEBIN KIM (202038112)
### Motto : STICK IT OUT
자바 형태 (기본)
```JAVA
public class 클래스명 {
    public static void main(String[] args) {
        실행할 문장;
    }
}
```

# 29/03/2024 (4week)
## 리터럴이란?
프로그램에 직접 표현한 값

### 정수 리터럴<br>
```JAVA
int n = 15;     // 10진수 10
int m = 015;    // 8진수 10 -> 10진수 13
int k = 0x15;   // 16진수 15 -> 10진수 21
int b = 0b0101; // 2진수 0101 -> 10진수 5
```

### 실수 리터럴
```JAVA
float d = 0.1234;
```

### 문자 리터럴
```JAVA
char a = 'a';
char b = '글';
char c = \uae00;    // '글'의 유니코드 값(ae00) 사용
```
### var 키워드
변수의 타입을 자동으로 결정한다.<br>
but, 많이 사용하지 않는다.<br>

```JAVA
var price = 200;    // 지역변수 안에서만, 선언 시 초기화

var name;           // 컴파일 오류.
                    // 초깃값이 주어지지 않아 변수 name의 타입을 추론할 수 없다.
```

### 상수
```JAVA
static final double PI = 3.141592;  // static 키워드를 붙이는 것이 바람직!
```

### 타입 변환
```JAVA
// 자동 타입 변환
long m = 25;
double d = 3.14 * 10;

// 강제 타입 변환
int n = 300;
byte b = (byte)n;
```
### Scanner를 이용한 키 입력
```JAVA
import java.util.Scanner;
Scanner scanner = new Scanner(System.in);

```

# 22/03/2024 (3week)
.exe : 실행파일 (Binary 파일)<br>
.class : Jave (컴파일된 파일은 아님)    //인터프리팅

# 15/03/2024 (2week)
### 네이밍 컨벤션<br>
    Coding Conventions 중 하나로, 소스 코드와 문서에 있는 변수 이름, 타입, 함수 등의 식별자에 사용되는 문자열을 선택하기 위한 여러가지 규칙이다. 프로젝트, 프로그래밍 언어, 개발 도구 등에 따라 사용되는 종류가 다르다.

ClassName : 파스칼케이스<br>
functionName : 카멜케이스<br>
java2-2 : 케밥케이스<br>
*_* : 스네이크케이스

# 08/03/2024 (1week)
```
# -> h1 태그
## -> h2 태그
### -> h3 태그
#### -> h4 태그
##### -> h5 태그
###### -> h6 태그


1. 첫 번째 순서 리스트
2. 두 번째 순서 리스트
3. 세 번째 순서 리스트(숫자가 틀려도 적용)

* 첫 번째 순서 없는 리스트
* 두 번째 순서 없는 리스트
- 첫번째 순서 없는 리스트(하이폰 사용)

*이탤릭체(스페이스 없이 사용)*

**볼드체(스페이스 없이 사용)**

스페이스를 두번 치면  
엔터 적용<br>
br태그도 적용 가능

```c++
#include <stdio.h>

int main()
{
    printf("Hello, world!\n");

    return 0;
}
```
