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

	public ClientThread(Socket sc) throws Exception {
		this.br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
		this.pw = new PrintWriter(sc.getOutputStream());

		this.name = br.readLine().replaceAll("[^!-~ㄱ-힣]", "").trim();
		this.write("[" + this.name + "] Connect");
	}

	@Override
	public void run() {
		super.run();
		try {
			String line;
			while ((line = br.readLine()) != null) {
				ServerThread.MSG_QUEUE.add(new ClientMsgVo(this, name + " : " + line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(String msg) {
		pw.println(msg);
		pw.flush();
	}

	public String getClientName() {
		return name;
	}
}
