package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Window {
	private final JFrame window = new JFrame();
	private final int windowWidth = 500;
	private final int windowHeight = 500;

	public void run() {

		JPanel mainPanel = mainPanel();
		window.add(mainPanel);

		Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
		window.setBounds(dimen.width / 2 - windowWidth / 2, dimen.height / 2 - windowHeight / 2, 500, 500);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		window.dispose();
		window.setUndecorated(true);
		window.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
		window.getContentPane().setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));

		window.setVisible(true);

	}

	private JPanel mainPanel() {
		JPanel jp = new JPanel(new BorderLayout()) {
			@Override
			public void paintComponents(Graphics g) {
				g.setColor(getBackground());
				Rectangle r = g.getClipBounds();
				g.fillRect(r.x, r.y, r.width, r.height);
				super.paintComponent(g);
			}
		};
		JPanel up = upPanel();
		JPanel down = downPanel();
		down.setPreferredSize(new Dimension(windowWidth, windowHeight / 10));

		jp.add(up, BorderLayout.CENTER);
		jp.add(down, BorderLayout.SOUTH);
		jp.setOpaque(false);
		return jp;
	}

	private JPanel upPanel() {
		JPanel jp = new JPanel(new GridLayout());
		JLabel jl = new JLabel();
		jp.add(jl);
		return jp;
	}

	private JPanel downPanel() {
		JPanel jp = new JPanel(new GridLayout());
		JTextField jt = new JTextField() {
			@Override
			public void setBorder(Border border) {
			}

			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(Color.red);
				g.fillRect(0, 0, jp.getWidth(), jp.getHeight());
			}
		};

		jt.addActionListener(e -> {
			JTextField t = (JTextField) e.getSource();
			System.out.println(t.getText());
			t.setText("");
		});
		jp.add(jt);
		return jp;
	}


	public static void main(String[] args) {
		new Window().run();
	}
}
