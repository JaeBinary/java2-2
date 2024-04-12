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
# 12/04/2024 (6week)
### Scanner를 이용한 키 입력
```JAVA
import java.util.Scanner;
Scanner scanner = new Scanner(System.in);
```

# 05/04/2024 (5week)
제수(dividend) : <수학> 나눗셈에서, 어떤 수를 나누는 수.
피제수(divisor) : <수학> 어떤 수나 식을 다른 수나 식으로 나눌 때, 그 처음의 수나 식<br>

## 객체
객체란? 틀에 맞춰 찍어내는 것<br>
ex) 붕어빵틀(클래스), 붕어빵(객체)<br>

클래스 : 객체 모양을 선언한 틀(캡슐화하는 틀) / 객체의 속성과 행위 선언<br>
객체 : 생성된 실체<br>
필드(멤버변수) : 객체의 속성(state)<br>
메소드(멤버함수) : 객체의 행위(behavior)<br>

## 자바의 캡슐화
객체를 캡슐로 싸서 내부를 볼 수 없게 하는 것
### 객체의 가장 본질적인 특징
외부의 접근으로부터 객체 보호

## 자바 상속
상위클래스의 멤버를 하위 클래스가 물려받음
### 다형성
같은 이름의 메소드가 클래스 혹은 객체에 따라 다르게 구현되는 것

## 객체 지향 언어의 목적
1. 소프트웨어의 생산성 향상
2. 실세계에 대한 쉬운 모델링


# 29/03/2024 (4week)
## 22) 메모리 구조 (https://www.tcpschool.com/java/java_array_memory)
### 메모리 구조
- 모든 자바 프로그램은 자바 가상 머신(JVM)을 통해서 실행된다.
- 자바 프로그램이 실행되면 JVM은 운영 체제로부터 해당 프로그램을 수행할 수 있도록 필요한 메모리를 할당받는다.

# 22/03/2024 (3week)
## 7) 변수 (https://www.tcpschool.com/java/java_datatype_variable)
### 변수(variable)
- 데이터를 저장할 수 있는 메모리 공간이다.
- 저장된 값은 변경될 수 있다.

### 변수의 종류
#### 1. 기본형(primitive type) 변수
    실제 연산에 사용되는 변수이며, 다음과 같이 8가지 종류의 기본형 변수를 제공하고 있다.

    - 정수형 : byte, short, int, long
    - 실수형 : float, double
    - 문자형 : char
    - 논리형 : boolean
#### 2. 참조형(reference type) 변수
    8개의 기본형 변수를 사용하여 사용자가 직접 만들어 사용하는 변수를 의미한다.

### 변수의 선언
- 자바에서 변수를 사용하기 전에 반드시 먼저 변수를 선언하고 초기화해야 한다.
- 반드시 해당 타입의 데이터만을 저장해야 한다.

#### 1. 변수의 선언만 하는 방법
##### [문법]
    타입 변수이름;
##### [예제]
```java
int num;                 // 변수의 선언
System.out.println(num); // 오류 발생
num = 20;                // 변수의 초기화
System.out.println(num); // 20
```
#### 2. 변수의 선언과 동시에 초기화하는 방법
##### [문법]
    1. 타입 변수이름[, 변수이름];
    2. 타입 변수이름 = 초깃값[, 변수이름 = 초깃값];
##### [예제]
```java
int num1, num2;                  // 같은 타입의 변수를 동시에 선언함.
double num3 = 3.14;              // 선언과 동시에 초기화함.
double num4 = 1.23, num5 = 4.56; // 같은 타입의 변수를 동시에 선언하면서 초기화함.
```
##### [잘못된 예제]
```java
double num1, num2;        // 같은 타입의 변수를 동시에 선언함.
num1 = 1.23, num2 = 4.56; // 하지만 이미 선언된 여러 변수를 동시에 초기화할 수는 없음.
```

## 8) 상수 (https://www.tcpschool.com/java/java_datatype_constant)
### 상수(constant)
- 데이터를 정할 수 있는 메모리 공간이다.
- 변수와 다른 점은 프로그램이 실행되는 동안 메모리에 저장된 데이터를 변경할 수 없다.

### 상수의 선언
선언과 동시에 반드시 초기화해야 한다.
#### [예제]
```java
final int AGES = 30;
```
- 상수의 이름은 일반적으로 모두 대문자를 사용하여 선언한다.
- 여러 단어로 이루어진 이름의 경우에는 언더스코어(_)를 사용하여 구분한다.

### 리터럴(literal)
- 그 자체로 값을 의미한다.
- 즉, 변수와 상수와는 달리 데이터가 저장된 메모리 공간을 가리키는 이름을 가지고 있지 않다.
#### [예제]
```java
int var = 30;         // 30이 바로 리터럴임.
final int AGES = 100; // 100이 바로 리터럴임.
```

# 15/03/2024 (2week)
## 4) 자바 프로그램 (https://www.tcpschool.com/java/java_intro_program)
### 간단한 자바 프로그램
- 1개 이상의 클래스로 구성된다.
- 클래스는 1개 이상의 필드(field)나 메소드(method)로 구성된다.

#### [원형]
```java
class 클래스이름 {

    필드의 선언

    필드의 선언

    ...

    메소드의 선언

    메소드의 선언

    ...

}
```
#### [예제]
```java
class Test {

    int field1;

    String field2;

 

    public void method1() {

        System.out.println("자바 프로그래밍!!");

    }

}
```
#### [실행결과]
    자바 프로그래밍!!

### main() 메소드
- 자바 프로그램이 실행되면 맨 먼저 main() 메소드를 찾아 그 안의 모든 명령문을 차례대로 실행한다.
- 따라서, 하나의 자바 프로그램에는 main() 메소드를 가지는 클래스가 반드시 하나는 존재해야 한다.

#### [원형]
```java
public static void main(String[] args) {

    ...

}
```
위의 원형처럼 main() 메소드는 반드시 ***public static void***로 선언되어야 한다.

자바 클래스 파일(*.java)에 public클래스가 존재하면 소스 파일의 이름은 반드시 해당 public클래스의 이름과 같아야 한다.<br>
이러한 public 클래스는 자바 클래스 파일마다 단 1개만 가질 수 있다.

### 명령문(statement)
자바의 모든 명령문의 반드시 세미콜론(;)으로 끝나야 한다.

### 주석(comment)
#### [문법]
```java
1. // 한 줄 주석

2. /* 여러

   줄

   주석 */

3. /* 여러 줄

     // 이렇게 두 줄 주석 안에 또 다른 한 줄 주석을 삽입할 수 있습니다.

주석입니다. */
```
여러 줄 주석은 중첩해서 사용하지 않는다.

## 5) System.out.println() (https://www.tcpschool.com/java/java_intro_systemout)
### 자바 표준 입출력 클래스
    1. System.in (입력)
    2. System.out (출력)
    3. System.err (출력)
- 사용자가 프로그램과 대화하기 위해서는 사용자와 프로그램 사이의 입출력을 담당하는 수단이 필요하다.
- 자바에서 모든 것이 객체로 표현되므로 입출력을 담당하는 수단 또한 모두 객체이다.

C언어의 printf() 함수나 scanf() 함수처럼 자바에서는 System이라는 표준 입출력 클래스를 정의하여 제공하고 있습니다.<br><br>
이러한 System 클래스는 java.lang 패키지에 포함되어 제공됩니다.<br><br>
System 클래스에는 표준 입출력을 위해 다음과 같은 클래스 변수(static variable)가 정의되어 있습니다.

### System.out.println()
#### [문법]
```java
System.out.println(출력할 데이터);
```

#### [예제]
```java
System.out.print(7);            // print() 메소드는 줄 바꿈 X
System.out.println(3);          // 정수 출력
System.out.println(3.14);       // 실수 출력
System.out.println("자바!");    // 문자열 출력
System.out.println("문자열끼리의 " + "연결도 가능합니다.");
System.out.println("숫자" + 3 + "과 문자열의 연결도 가능합니다.");
```

#### [실행 결과]
    73
    3.14
    자바!!
    문자열끼리의 연결도 가능합니다.
    숫자3과 문자열의 연결도 가능합니다.

println() 메소드와는 달리 print() 메소드는 전달된 데이터를 출력한 후에 줄 바꿈을 하지 않는다.


# 08/03/2024 (1week)
## 2) 자바 기초 (https://www.tcpschool.com/java/java_intro_basic)
### 자바란?
- 자바는 C++과는 달리 처음부터 객체 지향 언어로 개발된 프로그래밍 언어이다.
- 자바는 자바 가상 머신(JVM)을 사용하여 어느 운영체제에서나 같은 형태로 실행될 수 있다.

### 자바의 역사
- 자바는 가전제품 내에서 동작하는 임베디드 프로그램을 위한 언어로써 마이크로시스템즈사의 제임스 고슬링 팀에 의해 개발되었다.
- 1991년에 오크(Oak)라는 이름으로 시작하여, 1996년에 발표된 1.0.2 버전부터 자바(Java)라는 이름을 사용하게 된다.
- 1998년 발표된 J2SE 1.2에서는 웹에서도 자바를 돌릴 수 있게 해 주는 자바 애플릿(Java Applet)이 추가되며, 자바의 인기는 급상승하게 된다.
- 2009년에 썬 마이크로시스템즈사가 오라클과 인수 합병됨에 따라 자바 또한 오라클로 소유권이 넘어간다.

### 자바의 특징
#### [장점]
    1. 운영체제와는 독립적으로 실행할 수 있다.
    2. 불필요한 기능을 과감히 제거하여 다른 언어에 비해 배우기 쉽다.
    3. 자동 메모리 관리 등을 지원하여 다른 언어에 비해 안정성이 높다.
    4. 연산자 오버로딩을 금지하고 제네릭을 도입함으로써 코드의 가독성을 높였다.
#### [단점]
    1. 실행하기 위해선 자바 가상 머신을 거쳐야 하므로, 다른 언어에 비해 실행 속도가 느리다.
    2. 예외 처리가 잘 되어 있지만, 개발자가 일일이 처리를 지정해 줘야 한다.
    3. 다른 언어에 비해 작성해야 하는 코드의 길이가 긴 편이다.

## 3) 자바 프로그래밍 (https://www.tcpschool.com/java/java_intro_programming)
### 자바 프로그램의 실행 과정
자바로 작성된 프로그램은 다음과 같은 순서로 실행된다.
![실행 과정](https://www.tcpschool.com/lectures/img_java_programming.png)<br>

### 자바 컴파일러 (Java compiler)
- 자바를 가지고 작성한 자바 소스 코드를 자바 가상 머신이 이해할 수 있는 **자바 바이트코드로 변환**한다.
- 자바를 설치하면 *javac.exe*라는 실행 파일 형태로 설치된다.

### 자바 바이트코드 (Java bytecode)
- 자바 가상 머신이 이해할 수 있는 언어로 변환된 자바 소스 코드를 의미한다.
- 자바 컴파일러에 의해 변환되는 코드의 명령어 크기가 1바이트라서 자바 바이트 코드라고 불린다.
- 확장자는 *.class*이다.
- 자바 가상 머신만 설치되어 있으면, 어떤 운영체제에서라도 실행될 수 있다.

### 자바 가상 머신(JVM, Java Virtual Machine)
- **자바 바이트코드를 실행**시키기 위한 **가상의 기계**라고 한다.
- 자바로 작성된 모든 프로그램은 **자바 가상 머신에서만 실행**될 수 있으므로, 자바 프로그램을 실행하기 위해서는 **반드시 자바 가상 머신이 설치**되어 있어야 한다.
- 오라클은 대부분의 주요 운영체제뿐만 아니라 웹 브라우저, 스마트폰, 가전기기 등에서도 **자바 가상 머신을 손쉽게 설치**할 수 있도록 지원한다.
![JVM](https://www.tcpschool.com/lectures/img_java_jvm.png)<br>
위 그림처럼 서로 다른 운영체제라도 자바 가상 머신만 설치되어 있다면, 같은 자바 프로그램이 아무런 추가 조치 없이 동작할 수 있다.<br>
따라서, 개발자는 한 번만 프로그램을 작성하면 모든 운영체제에서 같이 사용할 수 있는 장점이 있다.<br>
<br>
단, 자바 프로그램과는 달리 자바 가상 머신은 운영체제에 종속적이므로 각 운영체제에 맞는 자바 가상 머신을 설치해야 한다.<br>
또한, 자바 프로그램은 일반 프로그램보다 자바 가상 머신이라는 한 단계를 더 거쳐야 하므로 상대적으로 실행 속도가 느리다는 단점을 가지고 있다.

### 자바 가상 머신의 구성
#### 1. 자바 인터프리터(interpreter)
    자바 컴파일러에 의해 변환된 자바 바이트코드를 읽고 해석하는 역할이다.
#### 2. 클래스 로더(class loader)
    동적으로 클래스를 로딩해주는 역할이다.
#### 3. JIT컴파일러(Just-In-Time compiler)
    프로그램이 실행 중인 런타임에 실제 기계어로 변환해 주는 컴파일러이다.

    즉, 자바 컴파일러가 생성한 자바 바이트코드를 런타임에 바로 기계어로 변환하는데 사용한다.
#### 4. 가비지 컬렉터(garbage collector)
    더는 사용하지 않는 메모리를 자동으로 회수해 준다.

    따라서, 개발자가 따로 메모리를 관리하지 않아도 되므로 더욱 손쉽게 프로그래밍을 할 수 있도록 도와준다.


# README
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
