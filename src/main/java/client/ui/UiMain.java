package client.ui;

import client.ui.component.UiViewer;
import client.ui.component.UiWriter;
import client.ui.controller.UiController;
import obj.Message;
import util.io.AesReader;
import util.io.AesWriter;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;

public class UiMain {
	private final static int VIEWER_WIDTH = 500;
	private final static int VIEWER_HEIGHT = 500;
	private final static int WRITER_WIDTH = 200;
	private final static int WRITER_HEIGHT = 70;
	private final static Dimension DIMEN = Toolkit.getDefaultToolkit().getScreenSize();

	private final AesReader br;
	private final AesWriter bw;
	private final String name;

	public UiMain(Socket _socket, String key, String name) throws IOException {
		this.br = new AesReader(_socket.getInputStream(), key);
		this.bw = new AesWriter(_socket.getOutputStream(), key);
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
		bw.aesWrite(name);
		System.out.println("sended : " + name);
		write(view, write);
		read(view);

		UiController controller = new UiController(view, write);
		controller.run();
	}

	private void read(UiViewer view) {
		new Thread(() -> {
			try {
				String line;
				while ((line = br.aesReadLine()) != null) {
					Message msg = new Message(line, false);
					view.append(msg.send() + "\n");
					System.out.println(msg.send());
				}
			} catch (Exception ignored) {
			}

		}).start();
	}

	private void write(UiViewer view, UiWriter write) {
		write.addActionListener(e -> {
			String msg = write.getText();
			write.setText("");

			Message m = new Message(name, msg);
			try {
				bw.aesWrite(m.send());
			} catch (IOException ioException) {
				view.append("Server closed. Exit this process.");
				write.setEditable(false);
				ioException.printStackTrace();
			}
		});
		write.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { // 입력창 클릭시 view화면이 뜨게 하기 위한 장치
				view.setTop();
				write.setTop();
			}
		});
	}
}
