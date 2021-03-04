package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RecieveThread extends Thread {
	private final Socket m_Socket;

	public RecieveThread(Socket _socket) {
		this.m_Socket = _socket;
	}

	public void run() {
		super.run();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(m_Socket.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			br.close();
			System.out.println("상대방의 연결이 끊어짐");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}

