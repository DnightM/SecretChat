package client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class UiWriter extends JTextField {
	private final JFrame window;

	public UiWriter(Rectangle r) {
		super();
		window = new JFrame();
		window.setBounds(r);
		window.getContentPane().setLayout(new java.awt.BorderLayout(0, 0));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);

		WriterPanel panel = new WriterPanel(r.width, r.height);
		panel.add(this, BorderLayout.CENTER);

		window.add(panel);
	}

	public void setTop() {
		window.setAlwaysOnTop(true);
		window.setAlwaysOnTop(false);
	}

	private static class WriterPanel extends JPanel {
		public WriterPanel(int width, int height) {
			this.setPreferredSize(new Dimension(width, height));
			this.setLayout(new BorderLayout());
		}
	}

	public void setWindowComponentListener(ComponentAdapter ca) {
		window.addComponentListener(ca);
	}

	public JFrame getWindow() {
		return window;
	}
}


