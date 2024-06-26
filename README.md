# JAEBIN KIM (202038112)
### Motto : STICK IT OUT

#### 자바 메인 메서드 (프로그램이 시작될 때 가장 먼저 실행!)
```JAVA
public class 클래스명 {
    public static void main(String[] args) {
        실행할 문장;
    }
}
```

# 14/06/2024 (14week)
# Chapter 14 - 자바 소켓 프로그래밍
## 1. 소켓(Socket)과 소켓통신이란?
### 1.1) 소켓
- TCP/IP 기반 네트워크 통신에서 데이터 송수신의 마지막 접접을 말한다.
- 클라이언트 소켓과 서버 소켓으로 구분되며, 소켓간 통신을 위해서는 네트워크상에서 클라이언트와 서버에 해당되는 컴퓨터를 식별하기 위한 IP주소와 해당 컴퓨터내에서 현재 통신에 사용되는 응용프로그램을 식별하기 위한 포트번호가 사용된다.

### 1.2) 소켓통신
- 소켓을 통해 서버-클라이언트간 데이터를 주고받는 양방향 연결 지향성 통신을 말한다.
- 보통 지속적으로 연결을 유지하면서 실시간으로 데이터를 주고받아야 하는 경우에 사용된다.

## 2. 서버와 클라이언트
- 소켓통신에서는 서버와 클라이언트가 존재한다.

### 2.1) 서버(Server)
- 데이터를 제공하는 쪽을 말한다.

### 2.2) 클라이언트(Client)
- 데이터를 요청하여 제공받는 쪽을 말한다.

## 3. 서버 소켓 구현하기
### 3.1) 서버소켓 생성
```java
ServerSocket serverSocket = new ServerSocket(8000);  // 포트번호
```

### 3.2) 클라이언트 접속 대기
```java
Socket socket = serverSocket.accept( );
```

### 3.3) 데이터 송수신을 위한 input/output 스트림 생성
```java
InputStream in = socket.getInputStream( );
OutputStream out = socket.getOutputStream( );
```

### 3.4) input 스트림을 통한 데이터 수신 (클라이언트 → 서버)
```java
byte[ ] inputData = new byte[100];
int length = in.read(inputData);
String inputMessage = new String(inputData, 0, length);
```

### 3.5) output 스트림을 통한 데이터 송신 (서버 → 클라이언트)
```java
String outputMessage = "보낼메시지";
out.write(outputMessage.getBytes( ));
out.flush( );
```

### 3.6) 통신 종료
```java
socket.close( );
serverSocket.close( );
```

## 4. 클라이언트 소켓 구현하기
### 4.1) 클라이언트 소켓 생성을 통한 서버접속
```java
Socket socket = new Socket("127.0.0.1", 8000);  // IP주소, 포트번호
```

### 4.2) 데이터 송수신을 위한 input/output 스트림 생성 
```java
InputStream in = socket.getInputStream( );
OutputStream out = socket.getOutputStream( );
```

### 4.3) output 스트림을 통한 데이터 송신 (클라이언트 → 서버)
```java
String outputMessage = "보낼메시지";
out.write(outputMessage.getBytes( ));
out.flush( );
```

### 4.4) input 스트림을 통한 데이터 수신 (서버 → 클라이언트)
```java
byte[ ] inputData = new byte[100];
int length = in.read(inputData);
String inputMessage = new String(inputData, 0, length);
```

### 4.5) 통신종료
```java
socket.close();
```

## 5. BufferedReader/BufferedWriter와 PrintWriter, PrintStream
- 데이터 전송의 기본 단위는 바이트(byte)로 문자의 경우 데이터 전송 시 문자를 바이트로, 수신 시 바이트를 문자로 변환이 필요하다.
- 자바에는 이 변환을 편리하게 해주는 클래스로 InputStreamReader와 OutputStreamWriter가 있으며 charset을 지정하여 사용할 수 있다.
- 또한, 데이터 입출력의 효율을 위해 바로 전달하지 않고 중간에 버퍼를 이용하기 위해서 BufferedReader와 BufferedWriter 클래스를 함께 사용할 수 있다.

```java
BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream( )));
BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream( )));
```

> 데이터 출력의 경우에는 출력 포맷을 편리하게 해주는 기능이 있는 PrintWriter 또는 PrintStream 클래스를 사용할 수 있다.

```java
PrintWriter out = new PrintWriter(socket.getOutputStream( ));
PrintStream out = new PrintStream(socket.getOutputStream( ));
```

## 6. 소켓통신 사용예제1 (1:1 채팅 프로그램)
### 6.1) 서버
```java
public class MyServer {
	public static void main(String[] args) {
		BufferedReader in = null;
		PrintWriter out = null;
		
		ServerSocket serverSocket = null;
		Socket socket = null;
		Scanner scanner = new Scanner(System.in);
		
		try {
			serverSocket = new ServerSocket(8000);
			
			System.out.println("[Server실행] Client연결대기중...");
			socket = serverSocket.accept();			// 연결대기

			System.out.println("Client 연결됨.");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
						
			while(true) {
				String inputMessage = in.readLine();	// 수신데이터 한줄씩 읽기	
				if ("quit".equalsIgnoreCase(inputMessage)) break;
				
				System.out.println("From Client: " + inputMessage);
				System.out.print("전송하기>>> ");
				
				String outputMessage = scanner.nextLine();
				out.println(outputMessage);
				out.flush();
				if ("quit".equalsIgnoreCase(outputMessage)) break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				scanner.close();		// Scanner 닫기
				socket.close();			// Socket 닫기
				serverSocket.close();		// ServerSocket 닫기
				System.out.println("연결종료");
			} catch (IOException e) {
				System.out.println("소켓통신에러");
			}
		}
	}
}
```

### 6.2) 클라이언트
```java
public class MyClient {
	public static void main(String[] args) {
		BufferedReader in = null;
		PrintWriter out = null;
		
		Socket socket = null;
		Scanner scanner = new Scanner(System.in);
		
		try {
			socket = new Socket("127.0.0.1", 8000);
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
			while(true) {
				System.out.print("전송하기>>> ");
				String outputMessage = scanner.nextLine();
				out.println(outputMessage);
				out.flush();
				if ("quit".equalsIgnoreCase(outputMessage)) break;
								
				String inputMessage = in.readLine();
				System.out.println("From Server: " + inputMessage);
				if ("quit".equalsIgnoreCase(inputMessage)) break;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				scanner.close();
				if (socket != null) socket.close();
				System.out.println("서버연결종료");
			} catch (IOException e) {
				System.out.println("소켓통신에러");
			}
		}
	}
}
```

## 7. 소켓통신 사용예제2 (멀티 채팅 프로그램)
### 7.1) 서버 구현
- 멀티 채팅 프로그램의 서버에서는 서버소켓이 루프를 돌면서 클라이언트가 연결될 때마다 새로운 스레드(Receive Thread)를 생성한다.
- 이렇게 생성된 스레드는 클라이언트에서 메시지가 들어올 때마다 전체 클라이언트에 해당 메시지를 전송한다.
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FoKODF%2FbtrJpftlM4Y%2FqT7LOEw3TQAWNi7mmzblRk%2Fimg.png)
```java
public class MultiServer {
	
	public static void main(String[] args) {
		MultiServer multiServer = new MultiServer();
		multiServer.start();
	}
	
	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(8000);
			while (true) {
				System.out.println("[클라이언트 연결대기중]");
				socket = serverSocket.accept();
				
				// client가 접속할때마다 새로운 스레드 생성
				ReceiveThread receiveThread = new ReceiveThread(socket);	
				receiveThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket!=null) {
				try {
					serverSocket.close();
					System.out.println("[서버종료]");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("[서버소켓통신에러]");
				}
			}
		}
	}
}

class ReceiveThread extends Thread {
	
	static List<PrintWriter> list = 
			Collections.synchronizedList(new ArrayList<PrintWriter>());
	
	Socket socket = null;
	BufferedReader in = null;
	PrintWriter out = null;
			
	public ReceiveThread (Socket socket) {
		this.socket = socket;
		try {
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			list.add(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	@Override
	public void run() {

		String name = "";
		try {
			// 최초1회는 client이름을 수신
			name = in.readLine();
			System.out.println("[" + name + " 새연결생성]");	
			sendAll("[" + name + "]님이 들어오셨습니다.");
			
			while (in != null) {
				String inputMsg = in.readLine();
				if("quit".equals(inputMsg)) break;
				sendAll(name + ">>" + inputMsg);
			}
		} catch (IOException e) {
			System.out.println("[" + name + " 접속끊김]");
		} finally {
			sendAll("[" + name + "]님이 나가셨습니다");
			list.remove(out);
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("[" + name + " 연결종료]");
	}
	
	private void sendAll (String s) {
		for (PrintWriter out: list) {
			out.println(s);
			out.flush();
		}
	}
}
```

### 7.2) 클라이언트 구현
- 서버로부터 메시지를 전송받아 화면에 출력한다.
- 메시지를 입력받아 서버에 전송하기 위한 스레드 하나를 생성하고, 첫전송은 클라이언트들의 구분을 위해서 클라이언트 이름을 생성해 전송하도록 한다.
```java
public class MultiClient {
	
	public static void main(String[] args) {
		MultiClient multiClient = new MultiClient();
		multiClient.start();
	}
	
	public void start() {
		Socket socket = null;
		BufferedReader in = null;
		try {
			socket = new Socket("localhost", 8000);
			System.out.println("[서버와 연결되었습니다]");

			String name = "user" + (int)(Math.random()*10);
			Thread sendThread = new SendThread(socket, name);
			sendThread.start();

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (in != null) {
				String inputMsg = in.readLine();
				if(("[" + name + "]님이 나가셨습니다").equals(inputMsg)) break;
				System.out.println("From:" + inputMsg);
			}
		} catch (IOException e) {
			System.out.println("[서버 접속끊김]");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("[서버 연결종료]");
	}
}

class SendThread extends Thread {
	Socket socket = null;
	String name;

	Scanner scanner = new Scanner(System.in);
	
	public SendThread(Socket socket, String name) {
		this.socket = socket;
		this.name = name;
	}

	@Override
	public void run() {
 		try {
			// 최초1회는 client의 name을 서버에 전송
			PrintStream out = new PrintStream(socket.getOutputStream());
			out.println(name);
			out.flush();
			
			while (true) {
				String outputMsg = scanner.nextLine();
				out.println(outputMsg);
				out.flush();
				if("quit".equals(outputMsg)) break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
```

# Chapter 13 - 입출력 스트림과 파일 입출력
## 1. 스트림
![스트림](https://velog.velcdn.com/images/seochan99/post/5e7fecb8-2549-4b06-9ccb-18f9d4096f0e/image.png)

### 1.1) 스트림 입출력
- 버퍼를 가지고 순차적으로 이루어지는 입출력

### 1.2) 자바의 입출력 스트림
- 응용 프로그램과 입출력 장치를 연결하는 소프트웨어 모듈
    - 입력 스트림 : 입력 장치로부터 자바 프로그램으로 데이터 전달
    - 출력 스트림 : 출력 장치로 데이터 출력

### 1.3) 자바의 입출력 스트림 특징
- 스트림의 양끝에 입출력 장치와 자바 응용프로그램 연결
- 스트림은 단방향
    - 입력과 출력을 동시에 하는 스트림 없다.
- 입출력 스트림기본 단위
    - 바이트 스트림 : 바이트
    - 문자 스트림 : 문자(자바 문자 1개 : 2byte)
- 선입선출 구조

### 1.4) 자바의 입출력 스트림 종류
#### 1.4.1) 바이트 스트림
- 입출력되는 데이터를 단순 바이트로 처리
    - ex) 바이너리 파일을 읽는 입력 스트림

#### 1.4.2) 문자 스트림
- 문자만 입출력하는 스트림
- 문자가 아닌 바이너리 데이터는 스트림에서 처리하지 못한다.
    - ex) 텍스트 파일을 읽는 입력 스트림

### 1.5) JDK의 바이트 스트림 클래스 계층 구조
![](https://velog.velcdn.com/images/seochan99/post/037c8ae7-3e76-4ca5-9f2e-abe500f1422e/image.png)

![](https://velog.velcdn.com/images/seochan99/post/22743882-260b-4c66-98d9-1eed1ba47fc5/image.png)

> ※ 스트림은 연결될 수 있다!

![](https://velog.velcdn.com/images/seochan99/post/13fef17b-937e-424e-acbe-1f8b63449645/image.png)

#### 1.5.1) 표준 입력 스트림 System.in에 InputStreamReader stream을 연결한 사례
![](https://velog.velcdn.com/images/seochan99/post/6959c0ac-2f17-4070-898c-7c1c67cddd9d/image.png)

## 2. 문자 스트림
- 유니 코드(2byte) 문자를 입출력 하는 스트림
    - 문자로 표현되지 않는 데이터는 다루지 못한다.
    - 이미지, 동영상과 같은 바이너리 데이터 입출력 불과
- 문자 스트림을 다루는 클래스
    - Reader/Writer
    - InputStreamReader/OutputStreamWriter
    - FileReader/FileWriter

## 3. FileReader을 이용한 파일 읽기
- 파일 전체를 읽어 화면에 출력하는 코드
```java
FileReader fin = new FileReader("txt 주소");     // 파일 열고 파일과 입력 바이트 스트림 객체 fin 연결
int c;
while((c = fin.read() != -1 )) {                // 파일의 끝까지 바이트씩 c에 읽어 들인다. 파일의 끝을 만나면 read()는 -1리턴			
	System.out.print((char)c);                  // 바이트 C를 문자로 변환하여 화면에 출력
}
fin.close();                                    // 스트림 닫기, 파일도 닫힘 => 스트림과 파일의 연결을 끊으로서 더이상 스트림으로부터 읽을 수 없다.
```

## 4. 문자 집합과 InputStreamReader를 이용한 텍스트 파일 읽기
```java
FileInputStream fin = new FileInputStream("c:\\Temp\\hangul.txt");
InputStreamReader in = new InputStreamReader(fin, "MS949");         // MS949 : 한글 완성형 확장형 문자 집합 

 while ((c = in.read()) != -1) { 
 System.out.print((char)c);
 }
```
![](https://velog.velcdn.com/images/seochan99/post/4e4b9b5c-0564-4a39-9a3b-45784330f9d3/image.png)

## 5. FileWriter 사용 예
```java
FileWriter fout = new FileWriter("c:\\Temp\\test.txt");             // txt로의 문자 출력 스트림 생성

// 파일 쓰기
// 문자 단위 쓰기
fout.write(‘A’);                                                    // 문자 ‘A’ 쓰기
fout.close();

// 블록 단위 쓰기
char [] buf new char[1024];


// buf[] 배열의 처음부터 배열 크기(1024개 문자)만큼 쓰기
fout.write(buf, 0, buf.length);
```

## 6. 바이트 스트림 클래스
### 6.1) 바이트 스트림
- 바이트 단위의 바이너리 값을 읽고 쓰는 스트림

### 6.2) 바이트 스트림 클래스
#### 6.2.1) InputStream/OutputStream
- 추상 클래스
- 바이트 스트림을 다루는 모든 클래스의 슈퍼 클래스

#### 6.2.2) FileInputStream/FileOutputStream
- 파일로부터 바이트 단위로 읽거나 저장하는 클래스
- 바이너리 파일의 입출력 용도

#### 6.2.3) DataInputStream/DataOutputStream
- 자바의 기본 데이터 타입의 값(변수)을 바이너리 값 그대로 입출력
- 문자열도 바이너리 형태로 입출력

## 7. FileOutputStream을 이용한 파일 쓰기
![](https://velog.velcdn.com/images/seochan99/post/f7c40aef-dd5f-4d03-9e10-e873c51f40a6/image.png)

## 8. 버퍼 입출력 스트림과 버퍼 입출력의 특징
### 8.1) 버퍼 스트림
- 버퍼를 가진 스트림
- 입출력 데이터를 일시적으로 저장하는 버퍼를 이용하여 입출력 효율 개선

### 8.2) 버퍼 입출력의 목적
- 입출력 시 운영체제의 API 호출 횟수를 줄여 입출력 성능 개선
    - 출력 시 여러 번 반복되는 데이터를 버퍼에 모아두고 한 번에 장치로 출력
![](https://velog.velcdn.com/images/seochan99/post/463ac0cf-859f-4c23-971c-03ef54359005/image.png)

### 8.3) 버퍼 스트림의 종류
#### 8.3.1) 바이트 버퍼 스트림
- 바이트 단위의 바이너리 데이터를 처리하는 버퍼 스트림
- BufferedInputStream와 BufferedOutputStream

#### 8.3.2) 문자 버퍼 스트림
- 유니코드의 문자 데이터만 처리하는 버퍼 스트림
- BufferedReader와 BufferedWriter

### 8.4) 20byte 버퍼를 가진 BufferedOutputStream
![](https://velog.velcdn.com/images/seochan99/post/df80081f-0434-4ee1-9209-df0f9f7f5f51/image.png)


## 9. File 클래스
- 파일의 경로명을 다루는 클래스
- 파일 관리 기능

### 사용 예시
```java
// 파일 객체 생성
	File f = new File("c:\\windows\\system.ini");

// 파일의 경로명 
	String filename = f.getName();                                      // "system.ini"
	String path = f.getPath();                                          // "c:\\windows\\system.ini" 
	String parent = f.getParent();                                      // "c:\\windows"

// 파일인지 디렉터리인지 구분 
	if(f.isFile())                                                      // 파일인 경우 
		System.out.println(f.getPath() + "는 파일입니다.");
	else if(f.isDirectory())                                            // 디렉터리인 경우 
		System.out.println(f.getPath() + "는 디렉터리입니다.");
	
// 서브 디렉터리 리스트 얻기 
	File f = new File("c:\\Temp");
	File[] subfiles = f.listFiles();                                    // c:\Temp 파일 및 서브디렉터리 리스트 얻기
	for(int i=0; i<filenames.length; i++) { 
		System.out.print(subfiles[i].getName());                        // 파일명 출력
		System.out.println("\t파일 크기: " + subfiles[i].length());      // 크기 출력 
		}
	}
```

# 07/06/2024 (13week)
# Chapter 12 - 자바 스레드 기초
## 1. 멀티태스킹(multi-tasking)
- 하나의 응용 프로그램이 여러 개의 작업(task)을 동시에 처리하는 것

## 2. 스레드와 멀티스레딩
### 2.1) 스레드
- 사용자가 작성한 코드
- JVM(자바 가상 기계)에 의해 스케쥴링되어 실행되는 단위
- JVM에 의한 실행 단위

### 2.2) 자바의 멀티태스킹
- 멀티스레딩만 가능하다.
- 하나의 응용프로그램은 여러 개의 스레드로 구성 가능하다.

### 2.3) 멀티스레딩 효과
- 한 스레드가 대기하는 동안 다른 스레드를 실행할 수 있다.
    - 프로그램 전체적으로 시간 지연을 줄일 수 있다.

## 3. 자바 Thread
- JVM에 의해 스케쥴되는 실행 단위의 코드 블럭
- 스레드의 주기는 JVM에 의해 관리된다.

### 3.1) JVM과 멀티 스레드의 관계
- 하나의 JVM은 하나의 응용프로그램만 실행한다.
- 하나의 응용 프로그램은 하나 이상의 스레드로 구성할 수 있다.
> ※ 두 개의 응용프로그램을 동시에 실행하고자 하면 두 개의 JVM을 이용

## 4. 스레드의 생성과 실행
### 4.1) 실행을 위해 개발자가 하는 작업
- 스레드 코드 작성
- 스레드를 생성하고 JVM에게 스레드 코드 실행을 요청

### 4.2) 스레드를 만들 때 주의사항
- run() 메소드 종료 시 스레드도 종료
- 한 번 종료한 스레드는 다시 시작시킬 수 없다.
- 한 스레드에서 다른 스레드를 가젱 종료시킬 수 있다.

### 4.3) 스레드 생성 방법
#### 4.3.1) Thread 클래스를 상속
- java.lang.Thread 클래스를 이용
```java
// 스레드 클래스 작성
// Thread 클래스 상속
class ThreadEx extends Thread {
    ......
    @Override
    public void run() { // run() 메소드 오버라이딩
        ......
    } // 스레드 코드라고 부름
      // 스레드 실행 시작
    ......
}
```

- 스레드 객체 생성 및 스레드 시작
```java
ThreadEx thread = new ThreadEx();   // 스레드 객체 생성
thread.start();                     // start() 메소드 호출
// 스레드로 작동 시작
// JVM에 의해 스케쥴되기 시작한다.
```
#### 4.3.2) Runnable 인터페이스를 구현
- java.lang.Runnable 인터페이스를 이용
```java
// Runnable 인터페이스 구현하여 클래스 작성
class ThreadEx implements Runnable {
    ......
    @Override
    public void run() { // run() 메소드 구현
        ......
    } // 스레드 코드, 스레드 실행 시작
    ......
}
```

- 스레드 객체 생성 및 스레드 시작
```java
Thread thread = new Thread(new ThreadEx());     // 스레드 객체 생성
thread.start();                                 // 스레드 시작
```

## 5. 스레드의 상태
- JVM에 의해 기록 관리된다.

### 5.1) NEW
- 스레드가 생성되었지만 아직 실행할 준비되지 않았다.

### 5.2) RUNNABLE
- 스레드가 실행 중 OR 실행 준비가 되어 스케쥴링을 대기하는 상태

### 5.3) WAITING
- wait() 메소드를 호출한 상태
- 스레드 동기화 시에 사용
- 다른 스레드가 notify(), notifyAll() 메소드를 호출하기를 기다리는 상태

### 5.4) TIMED_WAITING
- sleep() 메소드를 호출하여 대기 중인 상태

### 5.5) BLOCK
- 스레드가 입출력 작업을 요청하면 자바 가상 기계가 자동으로 BLOCK 상태로 만든다.

### 5.6) TERMINATED
- 스레드 종료된 상태

## 6. 스레드 우선순위
    static int MAX_PRIORITY : 최대 우선순위 (10)
    static int MIN_PRIORITY : 최소 우선순위 (1)
    static int NORMAL_PRIORITY : 기본 우선순위

- 스레드의 우선순위는 1 ~ 10까지 가질 수 있다.
- 숫자가 높을수록 우선순위가 높다.

> ※ 스레드 우선순위는 응용프로그램에서 변경 가능하다.

```java
void setPriority(int priority)
int getPriority()
```

- main() 스레드의 초기 우선순위는 5
- 스레드는 부모 스레드와 동일한 우선순위를 가진다.

## 7. main 스레드
- main()을 실행
- 응용프로그램을 실행할 때 JVM이 스레드 생성
    - main 스레드
- JVM이 main 스레드가 main() 메소드를 실행하게 한다.

### 7.1) main 스레드 정보
```java
public class MainThreadEx {
    public static void main(String[] args) {
        long id = Thread.currentThread().getId();                       // 현재 Thread의 ID 얻기
        String name = Thread.currentThread().getName();                 // 현재 Thread의 이름 얻기
        int priority = Thread.currentThread().getPriority();            // 현재 Thread의 우선순위 값 얻ㄱ이
        Thread.State state = Thread.currentThread().getState();         // 현재 Thread의 상태 값 얻기
        
        System.out.println("현재 스레드 ID : " + id);
        System.out.println("현재 스레드 이름 : " + name);
        System.out.println("현재 스레드 우선순위 : " + priority);
        System.out.println("현재 스레드 상태 값 : " + state);
    }
}
```

### 실행결과
    현재 스레드 ID : 1
    현재 스레드 이름 : main
    현재 스레드 우선순위 : 5
    현재 스레드 상태 값 : RUNNABLE

## 8. 스레드 종료
### 8.1) 스스로 종료
- run() 메소드 리턴
```java
class ThreadEx extends Thread {
    int n = 0;
    
    @Override
    public void run() {                 // run() 메소드 오버라이딩
        while(true) {
            System.out.print(n);        // 1초마다 n 값 출력
            n++
            
            try {
                sleep(1000);            // 1초 동안 대기
            } catch(InterruptedException e) {
                return;                 // 리턴하여 종료
            }
        }
    }
}
```

### 8.2) 강제 종료
- 다른 스레드에서 강제 종료
- interrupt() 메소드 사용
```java
public static void main(String[] args) {
    ThreadEx thread = new ThreadEx();       // 스레드 객체 생성
    thread.start();                         // 스레드 실행
    
    thread.interrupt();                     // 스레드 강제 종료
}
```

## 9. flag를 이용한 종료
- 다른 스레드에서 한 스레드의 flag를 true로 만들면 스레드 종료

## 10. 스레드 동기화
- Thread Stnchronization
- 공유 데이터에 대한 멀티스레드의 동시 접근 문제의 해결책
    - 한 스레드의 공유 데이터 작업이 끝날 때까지 다른 스레드는 대기
- 다수의 스레드가 공유 데이터를 배타적으로 접근하기 위해 상호 협력하는 것

> ※ 다수의 스레드가 공유 데이터에 동시에 접근할 경우 예상치 못한 결과가 발생할 수 있다.

### 10.1) 스레드 동기화 방법
#### 10.1.1) synchronized로 동기화 블록 지정
#### synchronized 키워드
- 한 스레드가 독점적으로 실행해야 하는 동기화 코드를 표시
- 메소드 전체 또는 코드 블록에 사용

#### synchronized 블록에 대한 컴파일러의 처리
1. 먼저 실행한 스레드가 해당 객체를 독점적으로 사용하는 권한을 소유한다.
2. 먼저 실행한 스레드가 사용 권한을 포기할 때까지 다른 스레드들은 대기한다.

#### 10.1.2) wait() - notify() 메소드로 실행 순서 제어
- 동기화 객체 : 두 개 이상의 스레드의 동기화에 사용되는 객체

#### 동기화 메소드
- Object 메소드이다.
    - 모든 객체가 동기화 객체로 가능
    - Thread 객체도 동기화 객체로 사용 가능
- wait() : 다른 스레드가 notify()를 호출할 때까지 대기
- notify() : 대기 중인 스레드를 RUNNABLE 상태로 만든다. (한 개의 스레드만 깨운다.)
- notifyAll() : 대기 중인 모든 스레드를 RUNNABLE 상태로 만든다. (synchronized 블록 내에서만 사용)

# Chapter 11 - 그래픽
## 1. 스윙 컴포넌트 그리기
### 1.1) 스윙 그리기의 기본
- 모든 컴포넌트는 자신의 모양을 스스로 그린다.
- 컨테이너는 자신을 그린 후, 자식들에게 그리기를 지시한다.

### 1.2) public void paintComponent(Graphics g)
- 스윙 컴포넌트가 자신의 모양을 그리는 메소드
- 컴포넌트가 그려져야 하는 시점마다 호출
    - 크기 변경, 위치 변경 등...
- JComponent 메소드 : 모든 스윙 컴포넌트가 이 메소드를 가진다.

## 2. Graphics 객체
- java.awt.Graphics
- 컴포넌트 그리기에 필요한 도구를 제공하는 객체

## 3. 그래픽 기반 GUI 프로그래밍
- 스윙 컴포넌트를 사용하지 않는다.
-  선, 원, 이미지 등을 직접 그려서 GUI 화면을 구성
- 자유롭고 빠르게 GUI 표현이 가능하다.

### 3.1) Graphics의 기능
- 색상 선택
- 문자열 출력
- 도형 그리기
- 도형 칠하기
- 이미지 출력
- 클리핑

### 3.2) 문자열 그리기
- void drawString(String str, int x, int y)
    - (x, y) 좌표에 str 문자열을 그린다.
    - 현재 컬러와 폰트로 출력한다.

## 4. Color & Font 클래스
### 4.1) Color
- 하나의 색을 표현하는 클래스
- 3가지 성분 : Red, Green, Blue
- 각 성분은 0 ~ 255 사이의 크기를 가진다.

#### 4.1.1) 생성자
```java
Color(int r, int g, int b)
new Color(128, 255, 0);
// r(red), g(green), b(blue)
// RGB 색상을 생성


Color(int rgb)
new Color(0x0000ff00); // 초록색
// rgb 정수 값은 32비트 중 하위 24비트만 유효
// 0x00rrggbb 형태로 표현
```

#### 4.1.2) 사용 방법
```java
Graphics g;
// 아래 세 방법 중 하나를 상황에 따라 사용
g.setColor(Color.RED); // 작성자는 해당 방법을 선호
g.setColor(new Color(128, 255, 0));
g.setColor(new Color(0x0000ff00);
```

### 4.2) Font
- 폰트를 표현하는 클래스

#### 4.2.1) 생성자
```java
Font(String fontFace, int style, int size)
// fontFace : 고딕체, Arial 등을 의미
// style : Font.BOLD, Font. ITALIC, Font,. PLAIN 중 하나
// size : 픽셀 단위 크기
```

#### 4.2.2) 색상과 폰트 설정
```java
void setColor(Color color)
// 설정할 색상을 color로 지정

void setFont(Font font)
// 설정할 폰트를 font로 지정
```

#### 4.2.3) 사용 방법
```java
Graphics g;
Font font = new Font("Arial", Font.ITALIC, 20);
g.setFont(font);
g.setColot(Color.RED);
g.drawString("Font&Color", 50, 50);
```

## 5. 도형 그리기
- 선, 사각형, 타원 등

### 5.1) Graphics의 메소드
```java
void drawLine(int x1, int y1, int x2, int y2)
// (x1, y1) 좌표부터 (x2, y2) 좌표까지 선을 그린다.

void drawRect(int x, int y, int width, int height)
// (x, y) 좌표에 width x height 크기인 사각형을 그린다.

void drawOval(int x, int y, int width, int height)
// (x, y) 좌표에 width x height 크기인 사각형에 내접하는 타원을 그린다.

void drawRoundRect(int x, int y, int width, int height, int acrWidth, int arcHeight)
// arcWidth : 모서리 원의 수평 반지름
// arcHeight : 모서리 원의 수직 반지름
// (x, y) 좌표에 width x height 크기인 사각형을 그리면서
// 사각형의 4개의 모서리는 arcWidth와 arcHeight를 이용하여 원호로 그린다.
```

## 6. 도형 칠하기
- 도형을 그리고 도형 내부를 칠하는 기능
- 도형의 외곽선과 내부를 따로 칠하는 기능은 없다.

### 6.1) 도형을 칠하기 위한 메소드
```java
void fillRect(int x, int y, int width, int height)

void fillOval(int x, int y, int width, int height)

void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight)

void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle)

void follPolygon(int[] x, int[] y, int n)
```

## 7. 이미지 그리기
### 7.1) JPanel에 Graphics 메소드를 사용
#### 장점
- 이미지의 원본 크기와 다르게 그리기 가능
- 이미지의 일부분 등을 그리는 것이 가능
#### 단점
- 컴포넌트로 관리되지 않는다.
    - 이미지에 이벤트가 발생하지 않는다.
    - 구현자가 이미지의 위치와 크기를 설정하여야 한다.

### 7.2) 메소드
- 이미지가 그려지면, 통보를 받는 객체를 지정하는 매개변수
- 주로 this를 사용하거나 null로 통보를 받지 않을 수 있다.

#### 7.2.1) 이미지 원본 크기로 그리기
```java
void drawImage(Image img, int x, int y, Color backgroundColor, ImageObserver ob)

void drawImage(Image img, int x, int y, ImageObserver ob)
```

#### 7.2.2) 이미지의 크기를 조절하여 그리기
```java
void drawImage(Image img, int x, int y, int width, int height, 
	Color backgroundColor, ImageObserver, ob)

void drawImage(Image img, int x, int y, int width, int height, ImageObserver ob)
```

#### 7.2.3) 이미지 일부분의 크기를 조절하여 그리기
```java
void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, 
	int sx1, int sy1, int sx2, int sy2, Color backgroundColor, ImageObserver)
    
void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1,
	int sx2, int sy2, ImageObserver ob)
```

### 7.3) 클리핑(Clipping)
- 클리핑 영역 : 하나의 사각형 영역
- 클리핑 영역에서만 그래픽이 이루어지도록 하는 기능
- 그리기, 칠하기 이미지 그리기, 문자열 출력 등에서 작동한다.

#### 7.3.1) 클리핑 메소드
```java
void setClip(int x, int y, int width, int height)
// 컴포넌트의 (x, y) 위치에서 width x height 크기의 사각형을 클리핑 영역으로 지정

void clipRect(int x, int y, int width, int height)
// 기존 클리핑 영역과 새로 지정된 영역의 교집합 영역을 클리핑 영역으로 지정
// 사용할수록 클리핑 영역이 줄어든다.
```

## 8. repaint() 메소드
- 강제로 컴포넌트를 다시 그리도록 하는 메소드
- 컴포넌트의 페인팅 과정을 진행
- 자바 플랫폼에게 다시 그리도록 지시
```java
component.repaint();
```

### 8.1) repaint()를 사용하는 경우
- 프로그램 내에서 모양 또는 위치를 변경한 경우
- repaint() 호출 시 paintComponent()가 호출된다.

#### 8.1.1) 부모 컴포넌트부터 다시 그리는 것이 좋은 이유
- 해당 컴포넌트에 repaint( ) 메소드를 호출하면 새로운 위치에 다시 그려진다.
- 그러나, 이전에 그려진 모양은 남아있다.
- 이를 해결하기 위해서는 부모 컴포넌트의 repaint( ) 메소드를 호출하는 것이 좋다.
```java
component.getParent().repaint();
```

## 9. revalidate() 메소드
- 컨테이너 배치관리자에게 자식 컴포넌트를 다시 배치하도록 지시하는 메소드
- 컨테이너에 변경이 생기는 경우 사용
    - 프로그램 내에서 컴포넌트 삽입 또는 삭제의 경우
```java
container.revalidate();     // 컴포넌트 재배치를 지시
container.repaint();        // 컨터이너 다시 그리기
```

# 31/05/2024 (12week)
# Chapter 10 - 스윙 컴포넌트 활용
## 1. GUI를 구성하는 2가지 방법
### 1.1) 컴포넌트 기반 GUI 프로그래밍
- GUI 구성이 쉽다.
- 스윙 패키지에 포함된 GUI 컴포넌트를 이용한다.
- 일반적인 GUI 프로그램에 적합하다.

### 1.2) 그래픽 기반 GUI 프로그래밍
- 작업 부담이 높다.
- 선, 원, 도형, 이미지 등을 직접 그려 그래픽 화면을 구성한다.
- 자유로운 GUI 프로그램에 적합하다.

## 2. 스윙 컴포넌트의 공통 메소드
### 2.1) 컴포넌트의 모양과 관련된 메소드
```java
void setForeground(Color)               // 전경색 설정(앞쪽색)
void setBackground(Color)               // 배경색 설정(뒤쪽색)
void setOpaque(boolean)                 // 불투명성 설정
void setFont(Font)                      // 폰트 설정
Font getFont()                          // 폰트를 리턴
```

### 2.2) 컴포넌트의 상태와 관련된 메소드
```java
void setEnabled(boolean)                // 컴포넌트 활성화 설정
void setVisible(boolean)                // 컴포넌트 보이기 설정
boolean isVisible()                     // 컴포넌트 보이기 상태를 리턴
```

### 2.3) 컴포넌트의 위치와 크기에 관련된 메소드
```java
int getWidth()                          // 가로 크기를 리턴
int getHeight()                         // 세로 크기를 리턴
int getX()                              // 컴포넌트의 x 좌표를 리턴
int getY()                              // 컴포넌트의 y 좌표를 리턴
void setLocation(int, int)              // 컴포넌트의 위치를 설정
void setSize(int, int)                  // 컴포넌트의 크기를 설정
```

### 2.4) 컨테이너에 대한 메소드
```java
Component add(Component)                // 자식 컴포넌트를 추가
void remove(Component)                  // 자식 컴포넌트를 제거
void removeAll(Component)               // 모든 자식 컴포넌트를 제거
Container getParent()                   // 부모 컨테이너를 리턴
Container getTopLevelAncestor()         // 최상위 부모 컨테이너를 리턴
Component[] getComponents()             // 자식 컴포넌트 배열을 리턴
```

## 3. JLable(레이블 컴포넌트)
- 문자열이나 이미지를 컴포넌트화하여 출력하기 위해서 사용

### 3.1) 생성자
```java
JLabel()                                                                // 빈 레이블을 생성

JLabel(Icon image)                                                      // 이미지 레이블을 생성
// 예제
ImageIcon img = new ImageIcon("이미지 경로");
JLabel jLabel = new JLabel(img);

JLabel(String text)                                                     // 문자열 레이블을 생성
// 예제
JLabel jLabel = new JLabel("문자열 레이블 생성 예제");

JLabel(String text, Icon image, int hAlign)                             // 문자열과 이미지를 모두 포함하는 레이블을 생성
// 예제
ImageIcon img = new ImageIcon("이미지 경로");
JLabel jLabel = new JLabel("텍스트 내용", img, SwingConstants.CENTER);   // CENTER, LEFT, RIGHT 중 하나로 설정
```

## 4. JButton(버튼 컴포넌트)
- 버튼 모양의 컴포넌트
- 버튼을 선택하면 Action 이벤트 발생

### 4.1) 생성자
```java
JButton()                               // 빈 버튼 생성

JButton(Icon image)                     // 이미지 버튼을 생성
// 예시
JButton("이미지 경로");                   // 실제 경로를 입력

JButton(String text)                    // 문자열 버튼을 생성
// 예시
JButton("문자열을 입력");

JButton(String text, Icon image)        // 문자열과 이미지 모두 포함하는 버튼을 생성
```

## 5. 이미지 버튼 만들기
### 5.1) 하나의 버튼에 3개의 이미지를 연결
- 마우스 접근에 따라서 서로 다른 3개의 이미지를 출력할 수 있다.
- 사용자의 버튼 조작에 따른 시각적 이미지를 부여할 수 있다.

#### 5.1.1) 버튼이 기본 상태일 경우 출력되는 이미지
```java
JButton.setlcon(image)
```

#### 5.1.2) 마우스가 버튼 위에 있을 경우 출력되는 이미지
```java
JButton.setRolloverIcon(image)
```

#### 5.1.3) 마우스가 버튼을 누르고 있는 동안 출력되는 이미지
```java
JButton.setPressedIcon(image)
```

### 5.2) 버튼과 레이블의 정렬
#### 5.2.1) 수평 정렬
- 컴포넌트 내에 이미지와 텍스트의 수평 위치를 정렬
```java
void setHorizontalAlignment(int align)
SwingConstants.(CENTER, LEFT, RIGHT) 중에 하나를 입력

```

#### 5.2.2) 수직 정렬
- 컴포넌트 내에 이미지와 텍스트의 수직 위치를 정렬
```java
void setVerticalAlignment(int align)
SwingConstants.(TOP, CENTER, BOTTOM) 중에 하나를 입력
```

## 6. JCheckBox
- 선택(selected)과 비선택(deselected) 두 가지 상태만 가진다.
- 이미지 아이콘을 가진 체크박스는 체크 박스 모양이 출력되지 않는다.

### 6.1) 생성자
```java
JCheckBox()                                             // 빈 체크박스를 생성
JCheckBox(String text)                                  // 문자열 체크박스를 생성
JCheckBox(String text, boolean selected)                // 문자열 체크박스를 생성, 체크 상태를 설정
// 예제
JCheckBox jCheckBox = new JCheckBox("문자열 입력");
JCheckbox jCheckBox = new JCheckBox("문자열 입력", true);


JCheckBox(Icon image)                                   // 이미지 체크박스를 생성
JCheckBox(Icon image, boolean selected)                 // 이미지 체크박스를 생성, 체크 상태를 설정
// 예제
JCheckBox("이미지경로");
JCheckBox("이미지경로", true);


JCheckBox(String text, Icon image)                      // 문자열과 이미지를 가지는 체크박스를 생성
JCheckBox(String text, Icon image, boolean selected)    // 문자열과 이미지를 가지는 체크박스, 체크 상태를 설정
```

### 6.2) 이미지 아이콘을 가지는 체크 박스 생성
- 체크 박스 모양이 출력되지 않는다.
- 선택 상태에 따라 표현하는 이미지 아이콘을 따로 설정해야 한다.
```java
// 실사용에서는 실제 이미지 경로를 입력
ImageIcon normalIcon = new ImageIcon("normalIcon 이미지 경로");
ImageIcon selectedIcon = new ImageIcon("selectIcon 이미지 경로");
JCheckBox jCheckBox = new JCheckBox("예제", normalIcon);
jCheckBox.setSelectedIcon(selectedIcon);
```

### 6.3) Item 이벤트 처리
#### 6.3.1) 체크박스 또는 라디오 버튼의 선택 상태가 바뀔 때 발생하는 이벤트
```java
JCheckBox jCheckBox = new JCheckBox("버튼");
jCheckBox.setSelected(true);
// 프로그램에서 선택 상태를 변경
```

#### 6.3.2) ItemListener 인터페이스의 추상 메소드
```java
void itemStateChanged(ItemEvent e)
// 선택 또는 해제 상태가 변경되는 경우에 호출된다.
```

#### 6.3.3) ItemEvent의 주요 메소드
```java
int getStateChange()
// 선택된 경우 ItemEvent.SELECTED를 리턴
// 해제된 경우 ItemEvent.DESELECTED를 리턴

Object getItem()
// 이벤트를 발생시킨 아이템 객체를 리턴
```

## 7. JRadioButton(라디오 버튼 컴포넌트)
- 여러 버튼으로 그룹을 형성하고, 하나만 선택되는 버튼
- 다른 버튼을 선택 시, 그 전에 선택한 버튼은 자동으로 선택 해제
- 이미지를 가진 라디오버튼의 생성 및 다루기는 체크박스와 완전히 동일한다.

### 7.1) 생성자
```java
JRadioButton()              // 빈 라디오버튼을 생성
JRadioButton(String text)   // 문자열 라디오버튼을 생성
JRadioButton(String text, boolean selected)
// 문자열 라디오버튼을 생성, 체크 상태를 설정

JRadioButton(Icon image)    // 이미지 라디오버튼을 생성
JRadioButton(Icon image, boolean selected)
// 이미지 라디오버튼을 생성, 체크 상태를 설정

JRadioButton(String text, Icon image)
// 문자열과 이미지를 포함하는 라디오버튼을 생성
JRadioButton(String text, Icon image, boolean selected)
// 문자열과 이미지를 포함하는 라디오버튼을 생성, 체크 상태를 설정
```

### 7.2) 라디오 버튼 생성 과정
```java
// 1. 버튼 그룹 객체를 생성한다.
ButtonGroup buttonGroup = new ButtonGroup();

// 2. 라디오버튼을 생성한다.
JRadioButton btn1 = new JRadioButton("btn1");
JRadioButton btn2 = new JRadioButton("btn2");
JRadioButton btn3 = new JRadioButton("btn3");

// 3. 생성한 라디오버튼을 버튼 그룹에 삽입한다.
buttonGroup.add(btn1);
buttonGroup.add(btn2);
buttonGroup.add(btn3);

// 4. 라디오 버튼을 컨테이너에 삽입
Container.add(btn1);
Container.add(btn2);
Container.add(btn3);
```

## 8. JTextField(텍스트 필드 컴포넌트)
- 한 줄짜리 문자열(텍스트) 입력창을 구현한 컴포넌트이다.
- ENTER 키가 입력되면 Action 이벤트가 발생
- 입력 가능한 문자 개수와 입력 창의 크기는 서로 다른다.

### 8.1) 생성자
```java
JTextField()                            // 빈 텍스트필드를 생성
JTextField(int column)                  // 열의 개수가 column개인 텍스트필드를 생성
JTextField(String text)                 // text가 입력되어있는 텍스트필드를 생성
JTextField(String text, int column)     // 열의 개수는 column개이며, text가 입력되어있는 텍스트필드를 생성
```

### 8.2) 주요 메소드
#### 8.2.1) 텍스트필드 내의 문자열을 편집하지 못하게 하는 메소드
```java
JTextField.setEditable(false)
```

#### 8.2.2) 텍스트필드의 입력 창에 문자열을 출력하는 메소드
```java
JTextField.setText(text)
```

#### 8.2.3) 텍스트필드 의 입력 창의 문자열의 폰트를 지정하는 메소드
```java
JTextField.setFont(Font)
```

## 9. TextArea(텍스트영역 컴포넌트)
- 여러 줄을 입력할 수 있는 텍스트 입력 창 컴포넌트
- JScrollPane 컴포넌트에 삽입 시 스크롤바를 지원

### 9.1) 생성자
```java
JTextArea()                             // 빈 텍스트영역을 생성
JTextArea(int rows, int columns)        // rows x columns 크기의 텍스트영역을 생성
JTextArea(String text)                  // text 문자열이 입력된 텍스트영역을 생성
JTextArea(String text, int rows, int columns)
// 입력 창에 text가 입력되어있으며, rows x columns 크기의 텍스트영역을 생성
```

## 10. JList< E >(리스트 컴포넌트)
- 여러 아이템들을 리스트 형태로 보여주고 선택하는 컴포넌트
- JComboBox< E >와 기본적으로는 같은 기능이다.
- JScrollPane에 삽입하여 스크롤 지원 가능
- < E >에 지정된 타입의 객체만 저장하는 리스트

### 10.1) 생성자
```java
JList<E>                        // 빈 리스트를 생성
JList<E>(Vector listData)       // 벡터로부터 아이템을 받는 리스트를 생성
JList<E>(Object[] listData)     // 배열로부터 아이템을 받는 리스트를 생성
```

### 10.2) 리스트 생성 방법
#### 10.2.1) 객체 배열로 아이템을 제공
```java
String[] numberList = {"one", "two", "three", "four", "five", "six", "seven"};
JList<String> numStrList = new JList<String>(numberList);
```

#### 10.2.2) Vector로 아이템을 제공
```java
Vector v = new Vector();
v.add("one");
v.add("two");
v.add("three");
JList<String> numStrList = new JList<String>(v);
```

#### 10.2.3) 빈 리스트 컴포넌트 생성 후, setListData() 메소드 사용
```java
ImageIcon[] imgs = {new ImageIcon("img1.png"), new ImageIcon("img2.png"), new ImageIcon("img3.png"), new ImageIcon("img4.png")};

JList<ImageIcon> imgList = new JList<ImageIcon>();
imgList.setListData(imgs);
```

#### 10.2.4) 스크롤 지원
```java
String[] numberList = {"one", "two", "three", "four", "five", "six", "seven"};
JList<String> scrollList = new JList<String>(numberList);
new JScrollPane(scrollList);
```

### 10.3) 리스트 아이템 변경
- 벡터 or 배열로 리스트 생성 후 벡터 or 배열을 수정해도 리스트는 수정이 되지 않는다.
- 벡터 or 배열 수정 후 setList() 메소드 사용
```java
// 벡터 수정 전
Vector<String> v = new Vector<String>();
v.add("one");
v.add("two");
v.add("three");
JList<String> vectorList = new JList<String>(v);

// 벡터 수정 후(요소 추가)
v.add("four");
v.add("five");
vectorList.setListData(v);          // setListData로 벡터를 새로 달기
```

## 11. JComboBox< E >(콤보박스)
- 텍스트 필드와 버튼 그리고 드롭다운 리스트로 구성
- JList< E >와 비슷하게 사용

### 11.1) 생성자
```java
JComboBox<E>()                      // 빈 콤보박스를 생성
JComboBox<E>(Vector listData)       // 벡터로부터 받은 아이템으로 콤보박스를 생성
JComboBox<E>(Object[] listData)     // 배열로부터 받은 아이템으로 콤보박스를 생성
```

### 11.2) Action 이벤트
- 콤보박스 아이템 선택시 Action 이벤트가 발생한다.
- 선택 상태인 아이템의 인덱스를 리턴
```java
getSelectedIndex()
```
- 선택 상태인 아이템 객체의 레퍼런스를 리턴
```java
 selectedItem()
```

## 12. JSlider(슬라이더)
- 마우스로 움직이면서 값을 선택하는 컴포넌트

### 12.1) 생성자
```java
JSlider()                               // 기본 슬라이더를 생성
JSlider(int orientation)                // orientation 방향 슬라이더를 생성
JSlider(int min, int max, int val)      // min, max, 초기값(val)을 가진 슬라이더를 생성
JSlider(int orietation, int min, int max, int val)
// orientation 방향이며, min, max, 초기값(val)을 가지는 슬라이더를 생성
```

### 12.2) 슬라이더의 모양 제어
#### 12.2.1) 방향제어
```java
void setOrientation(int orientation)
// HORIZONTAL, VERTICAL 둘 중 하나로 설정
```

#### 12.2.2) 최소/최대 값 설정
```java
void setMinimum(int min)    // 최소값 설정
void setMaximum(int max)    // 최대값 설정
```

#### 12.2.3) label 보이기 설정
```java
void setPaintLabels(true/false)
// true - 보이기, false - 감추기
```

#### 12.2.4) tick 보이기 설정
```java
void setPaintTicks(true/false)
// true - 눈금 보이기, false - 눈금 숨기기
```

#### 12.2.5) track 보이기 설정
```java
void setPaintTrack(true/false)
// true - track 보이기, false - track 숨기기
```

#### 12.2.6) 큰 눈금 간격 설정
```java
void setMajorTickSpacing(int space)
```

#### 12.2.7) 작은 눈금 간격 설정
```java
void setMinorTickSpacing(int space)
```

#### 12.2.8) 슬라이더 값 제어
```java
void setValue(int n)
// 슬라이더 값을 n으로 설정
// 슬라이더의 손잡이 위치가 변경된다.
```

## 13. Change 이벤트
- JSlider의 값이 변경되면 이벤트 발생
    - 손잡이를 움직이는 경우
    - setValue(int n) 메소드를 사용하는 경우
```java
void stateChanged(ChangeEvent e)
```

# 24/05/2024 (11week)
# Chapter 09 - 자바의 이벤트 처리
## 1. 이벤트 기반 프로그래밍(Event Driven Programming)
### 1.1) 이벤트의 발생에 의해 프로그램 흐름이 결정되는 방식
- 이벤트가 발생하면 이벤트 처리하는 루틴(이벤트 리스너) 실행
- 실행될 코드는 이벤트의 발생에 의해 전적으로 결정

### 1.2) 반대되는 개념 : 배치 실행(batch programming)
- 프로그램의 개발자가 프로그램의 흐름을 결정하는 방식

### 1.3) 이벤트 종류
- 사용자의 입력 : 마우스 드래그, 마우스 클릭, 키보드 누름 등
- 센서로부터의 입력, 네트워크로부터 데이터 송수신
- 다른 응용프로그램이나 다른 스레드로부터의 메시지

## 2. 이벤트 기반 응용 프로그램의 구조
### 2.1) 각 이벤트마다 처리하는 리스너 코드 보유

## 3. 이벤트 처리 순서
### 3.1) 이벤트 발생
- 마우스 드래그, 마우스 클릭, 키보드 입력등
### 3.2) 이벤트 객체 생성
- 현재 발생한 이벤트에 대한 정보를 가진 객체
### 3.3) 이벤트 리스너 찾기
### 3.4) 이벤트 리스너 호출
- 이벤트 객체가 리스너에 전달된다.
### 3.5) 이벤트 리스너 실행

### 예제) 이벤트 발생 사례
![이벤트 발생 사례](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcXYWKs%2Fbtsz3fPOguO%2F0HihhKLDIeKkxiw433cE30%2Fimg.png)

## 4. 이벤트 객체
- 이벤트가 발생할 때, 발생한 이벤트에 관한 정보를 가진 객체
- 이벤트 리스너에 전달된다. (이벤트 리스너 코드에서 이벤트가 발생한 상황을 파악할 수 있게 한다.)

### 4.1) 이벤트 객체의 종류
![이벤트 객체의 종류](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FckTUTf%2Fbtsz5o6xuSH%2FGuJPTUubqX1qz1BfYJE1ck%2Fimg.png)

### 4.2) 이벤트 객체가 포함하는 정보
- 이벤트 종류
- 이벤트 소스
- 이벤트가 발생한 화면 좌표
- 이벤트가 발생한 컴포넌트 좌표
- 버튼이나 메뉴 아이템에 이벤트가 발생한 경우, 버튼이나 메뉴 아이템의 문자열
- 클릭된 마우스 버튼 번호
- 키가 눌러졌다면, 키의 코드 값과 문자 값
- 체크박스, 라디오버튼 등과 같은 컴포넌트에 이벤트가 발상하였다면, 체크 상태

### 4.3) 이벤트에 따라 조금씩 다른 정보 포함
#### 4.3.1) ActionEvent 객체
- 액션 문자열
#### 4.3.2) MouseEvent 객체
- 마우스의 위치 정보, 마우스 버튼, 함께 눌러진 키 정보 등
#### 4.3.3) ItemEvent 객체
- 아이템의 체크 상태

## 5. 이벤트 객체의 메소드
- 이벤트 객체는 메소드를 통해 이벤트 정보를 제공

### 5.1) 이벤트 소스 알아내기
```java
Object EventObject.getSource()
```
- 발생한 이벤트의 소스 컴포넌트 리턴
- Object 타입으로 리턴하므로 캐스팅하여 사용
- 모튼 이벤트 객체에 대해 적용
![이벤트 소스 알아내기](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbgrZrZ%2Fbtsz41Rjvyl%2F1A5kjcKXAyiODUygphWtLK%2Fimg.png)

## 6. 이벤트 객체와 이벤트 소스
![이벤트 객체와 이벤트 소스](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbBe4uf%2Fbtsz3NS2IBh%2Fx100MqyIKrlKVUZMVTj2kK%2Fimg.png)

## 7. 이벤트 리스너
- 이벤트를 처리하는 코드, 클래스로 작성

### 7.1) JDK에서 이벤트 리스너 작성을 위한 인터페이스 제공
- 개발자가 리스너 인터페이스의 추상 메소드 구현
- 이벤트가 발생하면 자바 플랫폼은 리스너 인터페이스의 추상 메소드 호출

### ActionListener 인터페이스
```java
interface ActionListener {  // 아래 메소드를 개발자가 구현해야 한다.
    public void actionPerformed(ActionEvent e); // Action 이벤트 발생 시 호출된다.
}
```

### MouseListener
```java
interface MouseListener {   // 아래의 5개 메소드를 개발자가 구현해야 한다.
    public void mousePressed(MouseEvent e);     // 마우스 버튼이 눌러지는 순간 호출
    public void mouseReleased(MouseEvent e);    // 눌러진 마우스 버튼이 떼어지는 순간 호출
    public void mouseClicked(MouseEvent e);     // 마우스가 클릭되는 순간 호출
    public void mouseEntered(MouseEvent e);     // 마우스가 컴포넌트 위에 올라가는 순간 호출
    public void mouseExited(MouseEvent e);      // 마우스가 컴포넌트 위에서 내려오는 순간 호출
}
```

## 8. 리스너 인터페이스와 메소드
![리스너 인터페이스와 메소드](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FmQ766%2Fbtsz2mhmzpE%2FpaqGDTzA1VLoB4J8skaqV1%2Fimg.png)

## 9. 이벤트 리스너 작성 과정
### 9.1) 이벤트와 이벤트 리스너 선택
- 목적에 적합한 이벤트 리스너 인터페이스 선택
### 9.2) 이벤트 리스너 클래스 작성
- 리스너 인터페이스를 상속받는 클래스 작성, 추상 메소드 모두 구현
### 9.3) 이벤트 리스너 등록
- 이벤트를 받을 스윙 컴포넌트에 이벤트 리스너 등록

## 10. 리스너 등록 메소드가 addXXXListener인 이유
### 10.1) 컴포넌트는 다른 이벤트에 대한 리스너를 동시에 가질 수 있다.
```java
JButton.addActionListener();    // Action 리스너
JButton.addKeyListener();       // Key 리스너
JButton.addFocusListener();     // Focus 리스너
```

### 10.2) 컴포넌트는 한 이벤트에 대해 여러 개의 리스너를 동시에 가질 수 있다.
```java
JButton.addActionListener(new MyButtonListener1());
JButton.addActionListener(new MyButtonListener2());
```

## 11. 이벤트 리스너 작성 방법
### 11.1) 독립 클래스로 작성
- 이벤트 리스너를 완전한 클래스로 작성
- 이벤트 리스너를 여러 곳에서 사용할 때 적합

### 11.2) 내부 클래스로 작성
- 클래스 안에 멤버처럼 클래스 작성
- 이벤트 리스너를 특정 클래스에서만 사용할 때 적합

### 11.3) 익명 클래스로 작성
- 클래스의 이름 없이 간단한 리스너 작성
- 클래스 조차 만들 필요 없이 리스너 코드가 간단한 경우에 적합

## 12. 어댑터(Adapter) 클래스
- 리스너의 모든 메소드가 단순 리턴하도록 구현해놓은 클래스
- 리스너 인터페이스를 상속받아 이벤트 리스너를 구현할 때 리스너 인터페이스의 메소드를 모두 구현해야 하는 부담 존재
- 이벤트 리스너 구현에 따른 부담 해소를 위해, 자바의 JDK에서 리스너 인터페이스를 미리 구현해 놓은 클래스를 제공하는데 이것이 어댑터 클래스
- 추상 메소드가 하나뿐인 리스너는 어댑터 클래스 없다. ex) ActionAdapter, ItemAdapter 클래스는 존재하지 않는다.

### ex) MouseAdapter
```java
class MouseAdapter implements MouseListener, MouseMotionListener, MouseWheelListener {
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseWheelMoved(MouseEvent e) {}
}
```

## 13. JDK에서 제공하는 어댑터 클래스
![JDK에서 제공하는 어댑터 클래스](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb3oxCD%2Fbtsz6kRnugt%2FEvjoKGFOl2IdzKGDYhBaOk%2Fimg.png)

## 14. Key 이벤트와 포커스
### 14.1) 키 입력 시, 다음 3가지 경우에 Key 이벤트 발생
#### 14.1.1) 키를 누르는 순간
#### 14.1.2) 누른 키를 떼는 순간
#### 14.1.3) 누른 키를 떼는 순간(Unicode키의 경우에만)

### 14.2) 키 이벤트를 받을 수 있는 조건
- 모든 컴포넌트가 가능하지만, 현재 포커스(focus)를 가진 컴포넌트
- 어떤 컴포넌트에서 키를 입력하고자 하면 ```<Tab>```키나 마우스 클릭으로 포커스를 그 컴포넌트에게 이동해야 한다.

### 14.3) 포커스(focus)
- 컴포넌트나 응용프로그램이 키 이벤트를 독점하는 권한
- 컴포넌트 포커스 설정 방법 : 다음 2라인의 코드 필요
```java
component.setFocusable(true);   // component가 포커스를 받을 수 있도록 설정
component.requestFocus();       // coponen에 포커스 강제 지정
```
> 자바 플랫폼마다 실행 환경의 초기화가 서로 다를 수 있기 때문에 다음 코드가 필요하다!

## 15. 컴포넌트에 포커스 주기
- 키 입력을 받기 위해서 포커스를 주는 시점 또한 중요

### 15.1) 스윙 프레임이 만들어질 때, 포커스를 주고자 하는 경우
- JFrame의 settVisible(true) 이후에 포커스를 주어야 한다.
```java
setVisible(true);   // 스윙 프레임 출력
component.setFocusable(true);
component.requestFocus();
```

### 15.2) 마우스로 컴포넌트를 클릭하면, 그때 컴포넌트가 포커스를 얻도록 하는 경우
- 언제든지 필요할 때 포커스를 줄 수 있다.
```java
component.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        Component c = (Component)e.getSource(); // 클릭된 컴포넌트
        c.setFocusable(true);
        c.requestFocus();
    }
});
```

## 16. KeyListener의 메소드와 키
### 16.1) KeyListenr의 3개 메소드
![KeyListenr의 3개 메소드](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FAM4mr%2Fbtsz6ejt7Po%2F6SnALobrnTFKHCHObB9Su1%2Fimg.png)

### 16.2) 컴포넌트에 키 이벤트 리스너 등록
```java
component.addKeyListener(myKeyListener);
```

### 16.3) 키는 2가지 종류
#### 16.3.1) 유니코드 키
- 유니코드는 전 세계의 문자에 대한 코드 체계
- 유니코드는 한글, 영어 등 세계 여러 나라들의 문자들에 대해 코드 값을 정의 ex) A~Z, a~z, 0~9, !, @, & 등...

#### 16.3.2) 유니코드가 아닌 키
- 문자 키가 아닌 제어 키 ex) ```<Function>```, ```<Home>```, ```<Up>```, ```<Delete>```, ```<Control>```, ```<Shift>```, ```<Alt>``` 등...
- 정의된 유니코드 값 없다.
- 키 마다 키 코드 값(가상 키 코드 값)이 정의되어 있다.
- 유니코드 키가 아닌 경우 키 이벤트 호출 순서 : keyPressed(), keyReleased()만 호출된다.

### 16.4) 가상 키(Virtual Key)
- 유니코드 키든 아니든 모든 키에 자바의 가상 키 코드가 정의되어 있다.
- 가상 키 값으로 어떤 키인지 비교 판단 기능
- 가상 키 코드는 KeyEvent 클래스에 상수로 선언
![가상 키](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbvTGcX%2Fbtsz9yBe3IT%2FWua8SzR1zNGo3vKkYGNTdK%2Fimg.png)

## 17. 입력된 키 판별
- 키가 입력되면 키 정보를 가진 이벤트 객체 생성(KeyEvent 객체)
- KeyEvent 객체가 리스너에 전달됨

### 17.1) 키의 문자 코드(유니코드) 알아내기 - char KeyEvent.getKeyChar()
- 눌러진 키에 해당하는 문자 코드(유니코드) 리턴
- 눌러진 키가 문자 키인 경우에만 작동

### 17.2) 입력된 키의 가상 키 값 알아내기 -  int KeyEvent.getKeyCode()
- 모든 키에 대해 작동
- 키 코드는 운영체제나 하드웨어에 따라 서로 다를 수 있기 때문에
- 입력된 키를 판별하기 위해 getKeyCode()가 리턴한 키 코드와 가상 키 값을 비교 (가상 키 값은 KeyEvent 클래스의 상수로 정의된다.)

### 17.3) 키 이름 문자열 리턴 - String KeyEvent.getKeyText(int keyCode)
- Static 메소드
- 매개변수 keyCode의 코드 값(가상 키)에 해당하는 키의 이름 문자열 리턴

### ex) F1키의 경우 "F1", Shift 키의 경우 "SHIFT" 등의 문자열 리턴
![F1키의 경우](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F7lgc3%2Fbtsz7TMIsIX%2FY5Y3klAK8DUJv3UMK25IC0%2Fimg.png)

## 18. 마우스 이벤트와 마우스 관련 리스너
### 18.1) 마우스 이벤트
- 사용자의 마우스 조작에 따라 발생하는 이벤트, 8가지 경우
![마우스 이벤트 8가지 경우](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fc1fWxy%2Fbtsz84nbOx8%2F2eSKXNT78H6dqQM6r8nmZ1%2Fimg.png)

### 18.2) 마우스가 눌러진 위치에서 떼어지는 경우 메소드 호출 순서
```java
mousePressed(), mouseReleased(), mouseClicked()
```

### 18.3) 마우스가 드래그될 때 호출되는 경우 메소드 호출 순서
```java
mousePressed(), mouseDragged(), ..., mouseDragged(), mouseReleased()
```

## 19. 마우스 리스너 달기
### 19.1) MouseListener의 5개 이벤트를 처리하는 경우
- mouseEntered(), mouseExited(), mousePressed(), mouseReleased(), mouseClicked()

#### 19.1.1) 마우스 리스너 등록
- componet.addMouseListener(myMouseListener);

### 19.2) MouseListener의 이벤트도 함께 처리하고자 하는 경우
- mouseDragged(), mouseMoved()

#### 19.2.1) 마우스 모션 리스너 등록 필요
- component.addMouseMotionListener(myMouseMotionListener);

## 20. MouseEvent 객체로부터 얻을 수 있는 정보
### 20.1) 마우스 포인터의 위치
- int getX(), int getY()
- Point getPoint()
```java
public void mousePressed(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
}
```

### 20.2) 입력된 마우스 버튼
- int getButton()
```java
public void mousePressed(MouseEvent e) {
    if(e.getButton() == MouseEvent.BUTTON1)
        System.out.println("Left Button Pressed");
}
```

### 20.3) 마우스 클릭 횟수
- int getClickCount()
```java
public void mouseClicked(MouseEvent e) {
    if(e.getClickCount() == 2) {
        // 더블클릭을 처리하는 루틴
    }
}
```

# 17/05/2024 (10week)
# Chapter 08 - 자바 GUI 스윙 기초
## 1. GUI(Graphical User Interface)
### 1.1) GUI의 목적
- 그래픽을 이용하기 위함.
    - 사용자에게 이해하기 쉬운 모양의 정보를 제공

### 1.2) 자바 GUI 특징
- 강력한 GUI 컴포넌트를 제공한다.
- GUI 프로그래밍이 쉽다.

### 1.3) 자바 GUI 프로그래밍 방법
- GUI 컴포넌트와 그래픽을 이용
    - AWT 패키지와 Swing 패키지 이용
- AWT : java.awt 패키지 사용
- Swing : javax.swing 패키지 사용

## 2. AWT(Abstract Windowing Toolkit)
- 자바가 처음 나온 시기에 함께 배포된 GUI 라이브러리
- java.awt 패키지를 사용
- AWT 컴포넌트는 중량 컴포넌트이다.
    - 처리 속도는 빠르지만 운양체제에 부담이 많이 간다.
    - native 운영체제의 GUI 컴포넌트의 도움을 받기 때문이다.

## 3. Swing(스윙)
- AWT 기술 기반으로 순수 자바 언어로 만들어진 라이브러리
- AWT 기능 + 고급 컴포넌트
- AWT 컴포넌트에 J를 붙인 이름의 클래스
- 그 외 J로 시작하는 클래스
- javax.swing 패키지를 사용
- Swing 컴포넌트는 경량 컴포넌트
    - native 운영체제에 의존하지 않는다.

### 3.1) Swing 클래스의 특징
- 클래스 이름이 J로 시작
- 쉽게 GUI 프로그래밍이 가능
- JComponent를 상속받는 클래스
    - 공통적인 속성을 구현한 추상 클래스 (인스턴스 생성하지 않는다.)
- AWT의 Container를 상속받는 클래스
    - JFrame, JDialog 등...

## 4. Component(컴포넌트)
- 컨테이너에 포함되어야 화면에 출력할 수 있는 순수 컴포넌트
- 모든 컴포넌트는 java.awt.Component를 상속 받는다.
- 모든 스윙 컴포넌트는 javax.swing.JComponent를 상속 받는다.

## 5. Container(컨테이너)
- 다른 GUI 컴포넌트를 포함할 수 있는 컴포넌트
- java.awt.Container를 상속 받는다.
- 다른 컨테이너에 포함 가능
- AWT 컨테이너와 Swing 컨테이너가 존재

### 5.1) 최상위 컨테이너
- 다른 컨테이너에 속하지 않고도 독립적으로 출력이 가능한 컨테이너
- 모든 컴포넌트는 컨테이너에 포함되어야 화면에 출력이 가능
- JFrame, JDialog, JApplet

## 6. JFrame(스윙 프레임)
- 모든 스윙 컴포넌트를 포함하는 최상위 GUI 컨테이너
- JFrame을 상속
- 컴포넌트를 화면에 출력하기 위해서는 스윙 프레임에 포함

### 6.1) 스윙 프레임의 기본 구성
- 프레임 : 스윙 프로그램의 기본 틀
- 메뉴바 : 메뉴들이 포함되는 공간
- 컨텐트 팬 : GUI 컴포넌트들이 포함되는 공간

### 6.2) 프레임에 컴포넌트 붙이기
```java
// 프레임의 타이틀을 다는 방법
public SwingFrameEx() {
    // 1.메소드를 호출하여 설정
    setTitle("타이틀 이름");
    // 2. Jframe의 생성자를 호출하는 방법
    super("타이틀 이름");
}

// 컨텐트팬 알아내기
Container contentPane = frame.getContentPane();

// 컴포넌트 달기
Container contentPane = frame.getContentPane();
JButton jb = new JButton("OK");
contentPane.add(jb);

// 컨텐트팬 변경하기
JPanel jp = new JPanel();
frame.setContentPane(jp);
```

## 7. 컨테이너는 생성 시 디폴트 배치관리자를 설정
- Window, JWindow : BorderLayout
- Frame, JFrame : BorderLayout
- Dialog, JDialog : BorderLayout
- Panel, JPanel : FlowLayout
- Applet, JApplet : FlowLayout

## 8. 새로운 배치관리자 설정
- Container.setLayout(LayoutManager lm)
    - lm을 새로운 배치관리자로 설정
```java
// JWindow에 FlowLayout로 설정
JWindow jw = new JWindow();
jw.setLayout(mew FlowLayout());

// 컨텐트팬의 배치관리자를 FlowLayout으로 변경
Container c = frame.getContentPane();
c.setLayout(new FlowLayout());
```

### 8.1) FlowLayout
- 컴포넌트를 왼쪽에서 오른쪽으로 배치
- 그 후, 위에서 아래로 컴포넌트를 배치
- 컨테이너 크기 변경 시 컴포넌트를 재배치
 
### 8.2) BorderLayout
- 컨테이너의 공간을 5개로 분할, 배치
    - East, West, Center, South, North
- 배치 방법 : add(Component comp, int index)
- 컨테이너 크기 변경 시, 재배치

### 8.3) GridLayout
- 동일한 그리드(격자)로 분할하고 각 셀에 컴포넌트를 배치
- 그리드는 생성자에 행과 열의 수를 지정
- 컨테이너 크기 변경 시, 재배치

### 8.4) 배치관리자가 없는 컨테이너
- 컴포넌트의 절대 크기가와 절대 위치를 결정
    - 컨테이너 크기를 변경하더라도 재배치하지 않는다.
- 컴포넌트의 위치 또는 크기를 임의로 설정하고자 할 경우 사용
- 컴포넌트를 겹쳐서 출력하고자 하는 경우 사용

# 03/05/2024 (9week)
# 13. 제네릭
## 55) 제네릭의 개념
### 제네릭(generic)이란?
> 데이터의 타입을 일반화한다는 것

제네릭은 클래스나 메소드에서 사용할 내부 데이터 타입을 컴파일 시에 미리 지정하는 방법이다.

#### 장점
1. 클래스나 메소드 내부에서 사용되는 객체의 타입 안정성을 높일 수 있다.
2. 반환 값에 대한 타입 변환 및 타입 검사에 들어가는 노력을 줄일 수 있다.

### 제네릭의 선언 및 생성
```java
class MyArray<T> {
    T element;
    void setElement(T element) {
        this.element = element;
    }
    T getElement() {
        return element;
    }
}
```


# 14. 컬렉션 프레임워크
## 57) 컬렉션 프레임워크의 개념
### 컬렉션 프레임워크(collection framework)란?
> 다수의 데이터를 쉽고 효과적으로 처리할 수 있는 표준화된 방법을 제공하는 클래스의 집합을 의미한다.

즉, 데이터를 저장하는 자료 구조와 데이터를 처리하는 알고리즘을 구조화하여 클래스로 구현해 높은 것이다.

### 컬렉션 클래스
> 컬렉션 프레임워크에 속하는 인터페이스를 구현한 클래스

Vector나 Hashtable과 같은 컬렉션 클래스는 예전부터 사용해 왔으므로, 기존 코드와의 호환을 위해 아직도 남아 있다.<br>
하지만, 기존에 사용하던 컬렉션 클래스를 사용하는 것보다 새로 추가된 ArrayList나 HashMap 클래스를 사용하는 것이 성능 면에서도 더 나은 결과를 얻을 수 있다.

#### ArrayList 클래스를 이용하여 리스트를 생성하고 조작하는 예제
```java
import java.util.*;

public class Collection01 {
    public static void main(String[] args) {
        //리스트 생성
        ArrayList<String> arrList = new ArrayList<String>();

        //리스트 요소의 저장
        arrList.add("넷");
        arrList.add("둘");
        arrList.add("셋");
        arrList.add("하나");

        //리스트 요소의 출력
        for(int i = 0; i < arrList.size(); i++) {
            System.out.println(arrList.get(i));
        }
    }
}
```
#### 출력
    넷
    둘
    셋
    하나

### 중간고사 대비 (아직 더 공부해야될 개념)
    7. 메소드와 생성자
        31) this와 this()
        32) 메소드 오버로딩
    
    9. 클래스 멤버
        37) 필드의 구분
        38) 메소드의 구분
        39) 초기화 블록
    
    10. 상속
        41) super와 super()
        42) 메소드 오버라이딩
    
    11. 다형성
        44) 추상 클래스
        45) 인터페이스
        46) 내부 클래스
    
    12. 자바 API클래스
        47) Object클래스
        48) String클래스
        51) Wrapper클래스

# 19/04/2024 (7week)
# 11. 다형성
## 43) 다형성의 개념
### 다형성(polymorphism)이란?
    하나의 객체가 여러 가지 타입을 가질 수 있는 것을 의미한다.
- 부모 클래스 타입의 참조 변수로 자식 클래스 타입의 인스턴스를 참조할 수 있도록 하여 구현하고 있다.
- 다형성은 상속과 추상화와 더불어 객체지향 프로그래밍을 구성하는데 중요한 특징 중 하나이다.

### 참조 변수의 다형성
- 부모 클래스 타입의 참조 변수로 자식 클래스 타입의 인스턴스를 참조할 수 있도록 하고 있다.
- 참조 변수가 사용할 수 있는 멤버의 개수가 실제 인스턴스의 멤버 개수보다 같거나 적어야 참조할 수 있다.

클래스는 상속을 통해 확장될 수는 있어도 축소될 수는 없으므로, 자식 클래스에서 사용할 수 있는 멤버의 개수가 언제나 부모의 클래스와 같거나 많게 된다.

### 참조 변수의 타입 변환
1. 서로 상속 관계에 있는 클래스 사이에만 타입 변환을 할 수 있다.
2. 자식 클래스 타입에서 부모 클래스 타입으로의 타입 변환은 생략할 수 있다.
3. 하지만, 부모 클래스 타입에서 자식 클래스 타입으로의 타입 변환은 반드시, 명시해야 한다.
#### 문법
    (변환할타입의클래스명) 변환할참조변수

### instanceof 연산자
    참조 변수가 참조하고 있는 인스턴스의 실제 타입을 확인할 수 있도록 해준다.
#### 문법
```java
참조변수 instanceof 클래스명
```
왼쪽에 전달된 참조 변수가 실제로 참조하고 있는 인스턴스의 타입이 오른쪽에 전달된 클래스 타입이면 true를 반환하고, 아니면 false를 반환합니다. 만약에 참조 변수가 null을 가리키고 있으면 false를 반환합니다.

# 12/04/2024 (6week)
# 8. 제어자
## 34) 패키지
### 패키지(package)
    클래스와 인터페이스의 집합을 의미한다.
    즉, 물리적으로 하나의 디렉터리를 의미한다.
- 파일을 효율적으로 관리할 수 있다.
- 협업 시 이름 충동 문제까지도 패키지를 이용하면 피할 수 있다.
- 하나의 패키지에 속한 클래스나 인터페이스 파일은 모두 해당 패키지 이름의 디렉터리에 포함되어 있다.
- 패키지는 다른 패키지를 포함할 수 있으며, 디렉터리의 계층 구조는 점(.)으로 구분한다.

### 패키지의 선언
#### 문법
```java
package 패키지명;
```
- 클래스나 인터페이스의 소스 파일에 추가하기만 하면 된다.
- 패키지 이름에는 패키지의 경로까지 포함한 풀 네임을 명시해야 한다.

### 이름 없는 패키지(unnamed package)
- 모든 클래스는 반드시, 하나 이상의 패키지에 포함되어야 한다.
- 기본적으로 이름 없는 패키지에 포함해 컴파일한다.
- 패키지를 명시하지 않은 모든 클래스와 인터페이스는 모두 같은 패키지에 포함되게 된다.

### import문
    코드에서 사용할 클래스의 패키지에 대한 정보를 미리 제공하는 역할을 한다.
- import문을 사용하면 다른 패키지에 속한 클래스를 패키지 이름을 제외한 클래스 이름만으로 사용할 수 있게 된다.

### import문의 선언
#### 문법
```java
import 패키지명.클래스명;      // 해당 패키지의 특정 클래스만을 사용하고자 할 때
import 패키지명.*;             // 해당 패키지의 모든 클래스를 클래스 이름만으로 사용하고 싶을 때    
```
- 패키지 이름에는 패키지의 경로까지 포함한 풀 네임을 명시해야 한다.

### import문의 특징
    별표(*)를 사용하는 것이 해당 패키지에 포함된 다른 모든 하위 패키지의 클래스까지 포함해 주는 것은 아니다.

## 35) 접근 제어자
### 제어자(modifier)
    클래스와 클래스 멤버의 선언 시 사용하여 부가적인 의미를 부여하는 키워드를 의미한다.
- 접근 제어자와 기타 제어자로 구분할 수 있다.
- 기타 제어자는 경우에 따라 여러 개를 사용할 수도 있지만, 접근 제어자를 2개 이상 같이 사용할 수는 없다.

### 접근 제어자(access modifier)
    사용자가 굳이 알 필요가 없는 정보는 사용자로부터 숨겨야 한다는 개념이다.
- 사용자는 언제나 최소한의 정보만으로 프로그램을 손쉽게 사용할 수 있다.
- 정보 은닉을 위해 접근 제어자라는 기능을 제공한다.

### private 접근 제어자
    개인적인 것이라 외부에서 사용될 수 없도록 한다.
- 클래스 멤버는 외부에 공개되지 않으며, 외부에서는 직접 접근할 수 없다.
- private 멤법에 직접 접근할 수 없으며, 해당 객체의 public 메소드를 통해서만 접근할 수 있다.
- 클래스 내부의 세부적인 동작을 구현하는데 사용된다.
![private 멤버에 접근할 수 있는 영역](https://www.tcpschool.com/lectures/img_java_access_private.png)

### public 접근 제어자
    외부 클래스가 자유롭게 사용할 수 있도록 한다.
- 클래스 멤버는 외부로 공개되며, 해당 객체를 사용하는 프로그램 어디에서나 직접 접근할 수 있다.
- public 메소드를 통해서만 해당 객체의 private 멤버에 접근할 수 있다.
- public 메소도는 private 멤버와 프로그램 사이의 인터페이스 역할을 수행한다.
![public 멤버에 접근할 수 있는 영역](https://www.tcpschool.com/lectures/img_java_access_public.png)

### default 접근 제어자
    같은 패키지에 소속된 클래스에서만 사용할 수 있도록 한다.
- 접근 제어자가 지정되지 않으면 자동적으로 default 접근 제어를 가지게 된다.
- 멤버는 같은 클래스의 멤버와 같은 패키지에 속하는 멤버에서만 접근할 수 있다.
![default 멤버에 접근할 수 있는 영역](https://www.tcpschool.com/lectures/img_java_access_default.png)

### protected 접근 제어자
    같은 패키지 또는 자식 클래스에서 사용할 수 있도록 한다.
- protected 멤버는 부모 클래스에 대해서는 public 멤버처럼 취급되며, 외부에서는 private 멤버처럼 취급된다.
![protected 멤버에 접근할 수 있는 영역](https://www.tcpschool.com/lectures/img_java_access_protected.png)


|접근 제한|적용 대상|접근할 수 없는 클래스|
|-----|----------------------|-------------------|
|public|클래스, 필드, 생성자, 메소드|없음|
|protected|필드, 생성자, 메소드|자식 클래스가 아닌 다른 패키지에 소속된 클래스|
|default|클래스, 필드, 생성자, 메소드|다른 패키지에 소속된 클래스|
|private|필드, 생성자, 메소드|모든 외부 클래스|

![자바 접근 제어자](https://hongong.hanbit.co.kr/wp-content/uploads/2021/09/01-%EC%9E%90%EB%B0%94%EC%9D%98-%EC%A0%91%EA%B7%BC-%EC%A0%9C%ED%95%9C%EC%9E%90_public_private.png)

## 36) 기타 제어자
### final 제어자
    '변경할 수 없다'는 의미로 사용된다.      //클래스, 메소드, 필드, 지역 변수
- 필드나 지역 변수에 사용하면 값을 변경할 수 없는 상수가 된다.
- 클래스는 다른 클래스가 상속받을 수 없게 된다.
- 메소드는 오버라이딩을 통한 재정의를 할 수 없게 된다.

### static 제어자
    '공통적인'이라는 의미로 사용된다.        //메소드, 필드, 초기화 블록
- 해당 변수를 클래스 변수로 만들어 준다.
- 해당 메소드를 클래스 메소드로 만들어 준다.
- 초기화 블록에도 사용할 수 있다.

### abstract 제어자
    '추상적인'이라는 의미로 사용된다.       //클래스, 메소드
- 추상 메소드
    - 선언부만 있고 구현부가 없는 메소드
- 추상 클래스
    - 하나 이상의 추상 메소드를 포함하고 있는 클래스
주의! 반드시, ***abstract*** 제어자로!



# 05/04/2024 (5week)
# 7. 메소드와 생성자
## 29) 메소드의 개념
### 메소드(method)
    메소드란? 어떠한 특정 작업을 수행하기 위한 명령문의 집합이다.
자바에서 클래스는 멤버로 속성을 표현하는 필드와 기능을 표현하는 메소드를 가진다.

### 메소드의 사용 목적
클래스에서 메소드를 작성하여 사용하는 이유는 중복되는 코드의 반복적인 프로그래밍을 피할 수 있기 때문이다.
또한, 모듈화로 인해 코드의 가독성도 좋아진다. 그리고 프로그램에 문제가 발생하거나 기능의 변경이 필요할 때도 손쉽게 유지보수를 할 수 있게 된다.

> ※ 메소드를 작성할 때는 되도록 하나의 메소드가 하나의 기능만을 수행하도록 작성하는 것이 좋다.

### 메소드 정의
```java
접근제어자 반환타입 메소드명(매개변수목록) {    //선언부
    //구현부
}
```
1. 접근제어자: 해당 메소드에 접근할 수 있는 범위를 명시한다.
2. 반환 타입(return type): 메소드가 모든 작업을 마치고 반환하는 데이터의 타입을 명시한다.
3. 메소드명 : 메소드를 호출하기 위한 이름을 명시한다.
4. 매개변수목록(parameters): 메소드 호출 시에 전달되는 인수의 값을 저장할 변수들을 명시한다.
5. 구현부: 메소드의 고유 기능을 수행하는 명령문의 집합이다.

> ※ 메소드 시그니처란? 메소드의 선언부에 명시되는 매개변수의 리스트를 가리킨다.
만약, 두 메소드가 매개변수의 개수와 티입, 그 순서까지 모두 같다면, 이 두 메소드의 시그니처는 같다고 할 수 있다.

### 메소드 호출
```java
객체참조변수명.메소드명();                  //매개변수가 없는 메소드의 호출
객체참조변수명.메소드명(인수1, 인수2, ...)   //매개변수가 있는 메소드의 호출
```

## 30) 생성자

# 29/03/2024 (4week)
# 6. 클래스
## 26) 클래스의 개념
### 객체 지향 프로그래밍(OOP, Object-Oriented Programming)
    객체의 상태(state)와 행동(behavior)을 구체화하는 형태의 프로그래밍
객체 지향 프로그래밍에서는 모든 데이터를 객체(object)로 취급하며 이러한 객체가 바로 프로그래밍의 중심이 된다.

### 클래스(Class)란?
- Java에서 객체를 생성하기 위한 일종의 설계도이다.
    - 흔히 붕어빵(객체)를 만들기 위한 붕어빵 틀(클래스)이라고 비유를 한다.
- 객체가 가지는 속성(필드)와 동작(메서드)으로 이루어져 있다.
    - 이들은 생략될 수도 있고 하나 이상 작성될 수도 있다.
- Java를 실행 시 클래스는 JVM 메모리의 클래스 영역(Class Area)에 로드된다.
### 객체(Object)란?
- 물리적으로 존재하거나 추상적으로 생각할 수 있는 것 중에서
    - 자신의 속성을 가지고 있고, 다른 것과 식별 가능한 것을 말한다.
### 인스턴스(Instance)란?
- 클래스가 붕어빵 틀이라면, 그 틀을 통해 생성된 객체 하나하나를 해당 클래스의 인스턴스라 한다.
- 클래스를 사용하여 힙 영역(Heap Area)에 새로운 인스턴스(객체)를 생성할 수 있다.
    - 즉, 인스턴스란? 현실의 객체를 소프트웨어 내에서 구현한 실체라고 볼 수 있다.
### 요약
- 객체(Object)란 자신 고유의 속성을 가지는 물리적, 추상적인 모든 대상을 일컫는다.
- 클래스(Class)란 객체들을 소프트웨어 내에서 구현하기 위해 만든 설계도이다.
    - 이를 통해 생성된 객체 하나하나를 인스턴스(Instance)라고 부른다.
    - 대체로 객체와 인스턴스는 혼용해서 표현한다.

## 27) 클래스의 구성
### 클래스의 구성 요소
클래스는 멤버(member)로 속성을 표현하는 필드(field)와 기능을 표현하는 메소드(method)를 가진다.<br>
또한, 클래스는 생성된 객체의 필드를 초기화해주는 특별한 메소드인 생성자(constructor)를 가진다.

### 필드(field)
    클래스에 포함된 변수(variable)를 의미한다.
1. 클래스 변수(static variable)
2. 인스턴스 변수(instance variable)
3. 지역 변수(local variable)

### 메소드(method)
    어떠한 특정 작업을 수행하기 위한 명령문의 집합이라 할 수 있다.
- 메소드를 사용하면 **중복되는 코드의 반복적인 프로그래밍을 피할 수 있다.**
- 모듈화로 인해 **전체적인 코드의 가독성이 좋아진다.**
- 프로그램에 문제가 발생하거나 기능의 변경이 필요할 때도 **손쉽게 유지보수를 할 수 있다.**

### 생성자(constructor)
    객체의 생성과 동시에 인스턴스 변수를 원하는 값으로 초기화할 수 있는 생성자라는 메소드라 할 수 있다.
- 클래스를 가지고 객체를 생성하면 해당 객체는 메모리에 즉시 생성된다.
- 주의! 생성자의 이름은 해당 클래스의 이름과 같아야 한다.


# 22/03/2024 (3week)
# 5. 배열
## 22) 메모리 구조
### 메모리 구조
- 모든 자바 프로그램은 자바 가상 머신(JVM)을 통해서 실행된다.
- 자바 프로그램이 실행되면 JVM은 운영 체제로부터 해당 프로그램을 수행할 수 있도록 필요한 메모리를 할당받는다.<br>
![메모리 구조](https://www.tcpschool.com/lectures/img_java_memory_structure.png)<br>

### 메소드(method) 영역
    자바 프로그램에서 사용되는 클래스에 대한 정보와 함께 클래스 변수(static variable)가 저장되는 영역이다.

- JVM은 자바 프로그램에서 특정 클래스가 사용되면 해당 클래스의 클래스 파일(*.class)를 읽어들여, 해당 클래스에 대한 정보를 메소드 영역에 저장한다.

### 힙(heap) 영역
    자바 프로그램에서 사용되는 모든 인스턴스 변수가 저장되는 영역이다.

- JVM은 자바 프로그램에서 new키워드를 사용하여 인스턴스가 생성되면, 해당 인스턴스의 정보를 힙 영역에 저장한다.

- 힙 영역은 메모리의 낮은 주소에서 높은 주소의 방향으로 할당된다.

### 스택(stack) 영역
    자바 프로그램에서 메소드가 호출될 때 메소드의 스택 프레임이 저장되는 영역이다.

- JVM은 자바 프로그램에서 메소드가 호출되면 메소드의 호출과 관계되는 지역 변수와 매개변수를 스택 영역에 저장한다.

- 메소드의 호출과 함께 할당되며 메소드의 호출이 완료되면 소멸한다.

- 메소드의 호출정보를 스택 프레임(stack frame)이라고 한다.

- 푸시(push) 동작으로 데이터를 저장하고 팝(pop) 동작으로 데이터를 인출한다.

## 23) 1차원 배열
### 1차원 배열
#### [문법]
```java
1. 타입[] 배열명;     // 되도록 1번 사용
2. 타입 배열명[];
```
- 타입은 배열 요소로 저장되는 변수의 타입을 명시한다.
- 배열명은 배열이 선언된 후에 배열에 접근하기 위해 사용된다.

#### [문법]
```java
타입[] 배열명 = new 타입[배열길이];         // 배열의 선언과 생성을 동시에
```
- 자바에서는 이러한 배열도 모두 객체이므로 각각의 배열은 모두 자신만의 필드와 메소드를 가지고 있다.

#### [예제]
```java
int[] grade1 = new int[3]; // 길이가 3인 int형 배열의 선언 및 생성
int[] grade2 = new int[3]; // 길이가 3인 int형 배열의 선언 및 생성

grade1[0] = 85; // 인덱스를 이용한 배열의 초기화
grade1[1] = 65;
grade1[2] = 90;

grade2[0] = 85; // 배열의 길이보다 적은 수의 배열 요소만 초기화 -> 나머지 자동 초기화 (0)

for(int i = 0; i < grade1.length; i++) {
    System.out.print(grade1[i] + " "); // 인덱스를 이용한 배열로의 접근
}

for(int i = 0; i < grade2.length; i++) {
    System.out.print(grade2[i] + " "); // 인덱스를 이용한 배열로의 접근
}
```
- 위의 예제에서 사용한 ***length***는 배열의 길이를 저장하고 있는 배열 객체의 필드이다.

#### [실행 결과]
    85 65 90
    85 0 0

### 1차원 배열의 초기화
```java
1. 타입[] 배열이름 = {배열요소1, 배열요소2, ...};
2. 타입[] 배열이름 = new 타입[]{배열요소1, 배열요소2, ...};
```
- 초기화 블록에 맞춰 자동으로 배열의 길이 설정된다.
- 하지만, 다음과 같은 경우에는 2번째 방법만을 사용하여 초기화해야 한다.
    1. 배열의 선언과 초기화를 따로 진행해야 할 경우
    2. 메소드의 인수로 배열을 전달하면서 초기화해야 할 경우

#### [예제]
```java
int[] grade1 = {70, 90, 80};            // 배열의 선언과 동시에 초기화할 수 있음.
int[] grade2 = new int[]{70, 90, 80};   // 배열의 선언과 동시에 초기화할 수 있음.

int[] grade3;
grade3 = {70, 90, 80};                  // 이미 선언된 배열을 이 방법으로 초기화하면 오류가 발생함.

int[] grade4;
grade4 = new int[]{70, 90, 80};         // 이미 선언된 배열은 이 방법으로만 초기화할 수 있음.
```
위의 예제처럼 초기화 블록의 타입과 배열의 타입은 반드시 일치해야 한다.<br>

다음 예제는 앞선 예제와 같은 배열을 선언과 동시에 초기화 블록으로 초기화하는 예제이다.
#### [예제]
```java
int[] grade = new int[]{85, 65, 90};  // 길이가 3인 int형 배열을 선언과 동시에 초기화

for (int i = 0; i < grade.length; i++) {
    System.out.print(grade[i] + " "); // 인덱스를 이용한 배열로의 접근
}
```
#### [실행 결과]
    85 65 90

다음 예제는 배열 요소의 합과 평균을 구하는 예제이다.
#### [예제]
```java
int[] grade = new int[]{85, 65, 90}; // 길이가 3인 int형 배열을 선언과 동시에 초기화
int sum = 0;

for (int i = 0; i < grade.length; i++) {
    sum += grade[i];
}

System.out.println("모든 과목에서 받은 점수의 합은 " + sum + "입니다.");
System.out.println("이 학생의 평균은 " + (sum / grade.length) + "입니다.");
```
#### [실행 결과]
    모든 과목에서 받은 점수의 합은 240입니다.
    이 학생의 평균은 80입니다.

## 24) 다차원 배열
### 2차원 배열
- 배열의 요소로 1차원 배열을 가지는 배열이다.
- 자바에서는 2차원 배열을 나타내는 타입을 따로 제공하지 않는다.
- 대신에 1차원 배열의 배열요소로 또 다른 1차원 배열을 사용하여 2차원 배열을 나타낼 수 있다.<br>
#### [문법]
```java
1. 타입[][] 배열이름;
2. 타입 배열이름[][];
3. 타입[] 배열이름[];
```

![2차원 배열](https://www.tcpschool.com/lectures/img_java_array23.png)<br>

#### [예제]
```java
int[][] arr = new int[2][3];

int k = 10;
for (int i = 0; i < arr.length; i++) {
    for (int j = 0; j < arr[i].length; j++) {
        arr[i][j] = k; // 인덱스를 이용한 초기화
        k += 10;
    }
}

for (int i = 0; i < arr.length; i++) {
    for (int j = 0; j < arr[i].length; j++) {
        System.out.print(arr[i][j] + " ");
    }
    System.out.println();
}
```
#### [실행결과]
    10 20 30
    40 50 60
위의 예제에서 사용된 arr는 2차원 배열이며 arr[i]는 arr의 각 배열 요소로 1차원 배열이 된다.
- ***arr.length***는 2차원 배열인 arr의 배열 요소의 총 개수를 반환한다.
- ***arr[i].length***는 arr의 각 배열 요소인 1차원 배열이 가지고 있는 배열 요소의 총 개수를 반환한다.

### 2차원 배열의 초기화
#### [문법]
```java
타입 배열이름[행의길이][열의길이] = {
    {배열요소[0][0], 배열요소[0][1], ...},
    {배열요소[1][0], 배열요소[1][1], ...},
    {배열요소[2][0], 배열요소[2][1], ...},
    ...
};
```

### 가변 배열
    행마다 다른 길이의 배열을 저장할 수 있는 배열
자바에서는 2차원 배열을 생성할 때 열의 길이를 명시하지 않음으로써 행마다 다른 길이의 배열을 요소로 저장할 수 있다.

#### [예제]
```java
int[][] arr = new int[3][];

arr[0] = new int[2];
arr[1] = new int[4];
arr[2] = new int[1];
```
#### [예제]
```java
int[][] arr = {
    {10, 20},
    {10, 20, 30, 40},
    {10}
};
```

## 25) 배열의 활용
### 배열의 복사
자바에서 배열은 한 번 생성하면 그 길이를 변경할 수 없다.<br>
따라서, 더 많은 데이터를 저장하기 위해서는 더욱 큰 배열을 만들고 이전 배열의 데이터를 새로 만든 배열로 복사해야 한다.<br>

```
1. System 클래스의 arraycopy() 메소드
2. Arrays 클래스의 copyOf() 메소드
3. Object 클래스의 clone() 메소드
4. for문과 인덱스를 이용한 복사
```

이 중에서 가장 좋은 성능을 보이는 것은 배열의 복사만을 위해 만들어진 arraycopy() 메소드이다.<br>
하지만, 현재 배열의 복사를 위해 가장 많이 사용되는 메소드는 좀 더 유연한 방식의 copyOf() 메소드이다.

arraycopy(), copyOf() 메소드와 for 문을 이용한 복사는 배열의 길이를 마음대로 늘일 수 있다.<br>
하지만, clone() 메소드는 이전 배열과 같은 길이의 배열밖에 만들 수 없다.

#### [예제]
```java
int[] arr1 = new int[]{1, 2, 3, 4, 5};
int newLen = 10;

// 1. System 클래스의 arraycopy() 메소드
int[] arr2 = new int[newLen];
System.arraycopy(arr1, 0, arr2, 0, arr1.length);

// 2. Arrays 클래스의 copyOf() 메소드
int[] arr3 = Arrays.copyOf(arr1, 10);

// 3. Object 클래스의 clone() 메소드
int[] arr4 = (int[])arr1.clone();
 
// 4. for문과 인덱스를 이용한 복사
int[] arr5 = new int[newLen];
```

#### [실행 결과]
    1 2 3 4 5 0 0 0 0 0 
    1 2 3 4 5 0 0 0 0 0 
    1 2 3 4 5 
    1 2 3 4 5 0 0 0 0 0 



# 15/03/2024 (2week)
# 2. 타입

## 7) 변수
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

## 8) 상수
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

# 08/03/2024 (1week)
# 1. 자바 시작

## 2) 자바 기초
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

## 3) 자바 프로그래밍
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
- 오라클은 대부분의 주요 운영체제뿐만 아니라 웹 브라우저, 스마트폰, 가전기기 등에서도 **자바 가상 머신을 손쉽게 설치**할 수 있도록 지원한다.<br>
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

## 4) 자바 프로그램
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

## 5) System.out.println()
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
