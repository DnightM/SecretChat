import java.awt.*;
import javax.swing.*;

public class sample1 extends JFrame {
	private final static int VIEWER_WIDTH = 500;
	private final static int VIEWER_HEIGHT = 300;
	private final static Dimension DIMEN = Toolkit.getDefaultToolkit().getScreenSize();


	public sample1() {
		JFrame window = new JFrame();
		int x = (int) DIMEN.getWidth() / 2 - VIEWER_WIDTH / 2;
		int y = (int) DIMEN.getHeight() / 2 - VIEWER_HEIGHT / 2;
		Rectangle viewerWindow = new Rectangle(x, y, VIEWER_WIDTH, VIEWER_HEIGHT);
		window.setUndecorated(true);
		window.setBounds(viewerWindow);
		window.setType(JFrame.Type.UTILITY);
		window.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);
		window.setBackground(new Color(0, 0, 0, 0));
		window.getContentPane().setLayout(new BorderLayout(0, 0));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTextArea jt = new JTextArea("test");
		jt.setOpaque(false);

		JViewport viewport = new MyViewport();
		viewport.setView(jt);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewport(viewport);
		scrollPane.setOpaque(false);

		window.add(scrollPane);
		window.pack();
		window.setVisible(true);
	}

	public static void main(String[] args) {
		new sample1();
	}
}

class MyViewport extends JViewport {
	int TILE = 64;

	public MyViewport() {
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
	}
}