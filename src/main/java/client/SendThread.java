package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SendThread extends Thread {
	private final BufferedReader br;
	private final PrintWriter pw;
	private final String name;

	public SendThread(Socket _socket, String name) throws Exception {
		this.br = new BufferedReader(new InputStreamReader(System.in));
		this.pw = new PrintWriter(_socket.getOutputStream());
		this.name = name;

		pw.println(name);
		pw.flush();
	}

	public void run() {
		super.run();
		try {
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(name + " : " + line);
				pw.println(line);
				pw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}