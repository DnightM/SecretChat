package ui;

import javax.swing.*;
import java.awt.*;

// https://banana-media-lab.tistory.com/entry/%ED%88%AC%EB%AA%85%ED%95%9C-JPanel-%EB%A7%8C%EB%93%A4%EA%B8%B0
public class Window {
	private final JFrame window = new JFrame();
	private int windowWidth = 500;
	private int windowHeight = 500;
	public void initial() {
		window.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		window.setUndecorated(true);
		window.setType(javax.swing.JFrame.Type.UTILITY);
		window.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);

		Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
		window.setBounds(dimen.width / 2 - windowWidth/2, dimen.height / 2 - windowHeight/2, windowWidth, windowHeight);
		window.getContentPane().setLayout(new java.awt.BorderLayout(0, 0));
		window.setBackground(new Color(0, 0, 0, 0));
	}

	public void drawString(String sentence, int size, int y_coord, int x_coord, float h, float s, float b) {
		JLabel key = new JLabel();
		key.setText(sentence);
		Font myFont = new Font("고딕", Font.BOLD, size);
		key.setFont(myFont);
		key.setForeground(Color.getHSBColor(h, s, b));
		key.setBorder(BorderFactory.createEmptyBorder(y_coord, x_coord, 0, 0));
		window.getContentPane().add(key, 0);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}


	public static void main(String[] args) {
		Window kk = new Window();
		kk.initial();
		kk.drawString("강이사 이거 어때?", 15, 0, 0, 0, 0, 0);
	}
}


