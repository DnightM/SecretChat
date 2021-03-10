package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
	private final BufferedReader br;
	private final PrintWriter pw;
	private final String name;
	private final ServerSender ss;

	public ClientThread(ServerSender ss, Socket sc) throws Exception {
		this.ss = ss;

		this.br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
		this.pw = new PrintWriter(sc.getOutputStream());
		this.name = br.readLine().replaceAll("[^!-~ㄱ-힣]", "").trim();

		ss.sendLogin(this.name);
	}

	@Override
	public void run() {
		super.run();
		try {
			String line;
			while ((line = br.readLine()) != null) {
				ss.sendClientMsg(line);
			}
		} catch (IOException e) {
			ss.sendLogout(name);
			ss.remove(this);
		}
	}

	public void write(String msg) {
		if (msg == null) {
			return;
		}
		pw.println(msg);
		pw.flush();
	}
}
