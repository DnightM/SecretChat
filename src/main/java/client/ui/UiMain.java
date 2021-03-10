package client.ui;

import obj.Message;
import util.Aes128;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UiMain {
	private final static int VIEWER_WIDTH = 500;
	private final static int VIEWER_HEIGHT = 500;
	private final static int WRITER_WIDTH = 200;
	private final static int WRITER_HEIGHT = 70;
	private final static Dimension DIMEN = Toolkit.getDefaultToolkit().getScreenSize();

	private final Aes128 aes;
	private final BufferedReader br;
	private final PrintWriter pw;
	private final String name;

	public UiMain(Socket _socket, String key, String name) throws IOException {
		this.aes = new Aes128(key);
		this.br = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		this.pw = new PrintWriter(_socket.getOutputStream());
		this.name = name;
	}

	public void open() throws IOException {
		int x = (int) DIMEN.getWidth() / 2 - VIEWER_WIDTH / 2;
		int y = (int) DIMEN.getHeight() / 2 - VIEWER_HEIGHT / 2;
		Rectangle viewerWindow = new Rectangle(x, y, VIEWER_WIDTH, VIEWER_HEIGHT);
		Rectangle writerWindow = new Rectangle(x, y + VIEWER_HEIGHT, WRITER_WIDTH, WRITER_HEIGHT);

		UiViewer view = new UiViewer(viewerWindow);
		UiWriter write = new UiWriter(writerWindow);

		// id 전송
		pw.println(aes.encrypt(name));
		pw.flush();

		/////////////////////////////////////////////////
		// write
		write.addActionListener(e -> {
			String msg = write.getText();
			write.setText("");

			Message m = new Message(name, msg);
			pw.println(aes.encrypt(m.send()));
			pw.flush();
		});
		write.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { // 입력창 클릭시 view화면이 뜨게 하기 위한 장치
				view.setTop();
				write.setTop();
			}
		});

		/////////////////////////////////////////////////
		// read
		String line;
		while ((line = br.readLine()) != null) {
			Message msg = new Message(aes.decrypt(line), false);
			view.append(msg.send() + "\n");
			System.out.println(msg.send());
		}
	}
}
