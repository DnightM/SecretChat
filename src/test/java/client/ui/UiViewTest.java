package client.ui;

import client.ui.component.UiViewer;

import java.awt.*;

public class UiViewTest {
	private final static int VIEWER_WIDTH = 500;
	private final static int VIEWER_HEIGHT = 300;
	private final static Dimension DIMEN = Toolkit.getDefaultToolkit().getScreenSize();

	public static void main(String[] args) {
		int x = (int) DIMEN.getWidth() / 2 - VIEWER_WIDTH / 2;
		int y = (int) DIMEN.getHeight() / 2 - VIEWER_HEIGHT / 2;
		Rectangle viewerWindow = new Rectangle(x, y, VIEWER_WIDTH, VIEWER_HEIGHT);

		UiViewer view = new UiViewer(viewerWindow);
		int idx = 0;
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			view.append("test" + idx++ + "\n");
			System.out.println(idx);
			if (idx > 10000) {
				break;
			}
		}


	}
}