package client;

import util.Aes128;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RecieveThread extends Thread {
	private final Aes128 aes;

	private final BufferedReader br;
	public RecieveThread(Socket _socket, String key) throws IOException {
		this.br = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		this.aes = new Aes128(key);
	}

	public void run() {
		super.run();
		try {
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(aes.decrypt(line));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

