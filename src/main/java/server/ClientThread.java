package server;

import util.io.AesReader;
import util.io.AesWriter;

import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {
	private final AesReader br;
	private final AesWriter bw;
	private final String name;
	private final ServerSender ss;

	public ClientThread(ServerSender ss, Socket sc, String key) throws Exception {
		this.ss = ss;
		this.br = new AesReader(sc.getInputStream(), key);
		this.bw = new AesWriter(sc.getOutputStream(), key);
		this.name = br.aesReadLine().replaceAll("[^!-~ㄱ-힣A-Za-z ]", "").trim();
		ss.sendLogin(this.name);
	}

	@Override
	public void run() {
		super.run();
		try {
			String line;
			while ((line = br.aesReadLine()) != null) {
				ss.sendClientMsg(line);
			}
		} catch (IOException e) {
			ss.sendLogout(name);
			ss.remove(this);
		}
	}

	public void write(String msg) throws IOException {
		if (msg == null) {
			return;
		}
		bw.aesWrite(msg);
	}
}
