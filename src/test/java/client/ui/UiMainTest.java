package client.ui;

import obj.Message;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class UiMainTest {
	private final static int VIEWER_WIDTH = 500;
	private final static int VIEWER_HEIGHT = 500;
	private final static int WRITER_WIDTH = 200;
	private final static int WRITER_HEIGHT = 70;
	private final static Dimension DIMEN = Toolkit.getDefaultToolkit().getScreenSize();

	public static void main(String[] args) throws IOException {
		int x = (int) DIMEN.getWidth() / 2 - VIEWER_WIDTH / 2;
		int y = (int) DIMEN.getHeight() / 2 - VIEWER_HEIGHT / 2;
		Rectangle viewerWindow = new Rectangle(x, y, VIEWER_WIDTH, VIEWER_HEIGHT);
		Rectangle writerWindow = new Rectangle(x, y + VIEWER_HEIGHT, WRITER_WIDTH, WRITER_HEIGHT);

		UiViewer view = new UiViewer(viewerWindow);
		UiWriter write = new UiWriter(writerWindow);

		/////////////////////////////////////////////////
		// write
		write.addActionListener(e -> {
			String msg = write.getText();
			write.setText("");
			view.append(msg + "\n");

		});
		write.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.setTop();
				write.setTop();
			}
		});
	}
}
