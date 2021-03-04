package network;

import java.io.*;
import java.net.Socket;

public class SendThread extends Thread {
	private final Socket m_Socket; //Socket 타입 변수 m_Socket 선언

	public SendThread(Socket _socket) {
		this.m_Socket = _socket;
	}

	public void run() {
		super.run(); // 부모 thread에 run 함수 호출
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter pw = new PrintWriter(m_Socket.getOutputStream());
			String line;
			while ((line = br.readLine()) != null) {
				pw.println(line);
				pw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}