package client.ui.component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;

// https://banana-media-lab.tistory.com/entry/%ED%88%AC%EB%AA%85%ED%95%9C-JPanel-%EB%A7%8C%EB%93%A4%EA%B8%B0
public class UiViewer extends JTextArea {
	private final ViewWindow window;

	public UiViewer(Rectangle r) {
		super();
		DefaultCaret caret = (DefaultCaret) this.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		this.setEditable(false);
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.setOpaque(false);
		this.setFont(new Font("고딕", Font.BOLD, 15));
		this.setForeground(Color.BLACK);
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		window = new ViewWindow(r);
		ViewScroll scrollPane = new ViewScroll(this);
		window.add(scrollPane);
		window.pack();
		window.setVisible(true);
	}


	public void setTop() {
		window.setAlwaysOnTop(true);
		window.setAlwaysOnTop(false);
	}

	public JFrame getWindow() {
		return window;
	}

	private static class ViewWindow extends JFrame {
		public ViewWindow(Rectangle r) {
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setBounds(r);
			this.getContentPane().setLayout(new BorderLayout(0, 0));
			this.setUndecorated(true);
			this.setBackground(new Color(0, 0, 0, 0));
			this.setType(JFrame.Type.UTILITY);
			this.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);

		}
	}

	private static class ViewScroll extends JScrollPane {
		public ViewScroll(JTextArea area) {
			JViewport port = new JViewport();
			port.setOpaque(false);
			port.setBackground(new Color(0, 0, 0, 0));
			port.setPreferredSize(new Dimension(300, 300));
			port.setView(area);

			this.setOpaque(false);
			this.setViewport(port);
			this.setBorder(new EmptyBorder(0, 0, 0, 0)); // 테두리 숨기기
		}

		@Override
		public JScrollBar createVerticalScrollBar() { // 스크롤 숨기기
			JScrollBar verticalScrollBar = new JScrollPane.ScrollBar(1);
			verticalScrollBar.setPreferredSize(new Dimension(0, 0));
			return verticalScrollBar;
		}
	}
}