package client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class JfreamTest {
	public static void main(String[] args) {
		JFrame j = new JFrame();
		j.setBounds(new Rectangle(300, 300, 500 , 500));
		j.setVisible(true);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		j.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				System.out.println(e.getComponent().getLocation());
			}
		});
	}
}
