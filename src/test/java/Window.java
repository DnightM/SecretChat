import javax.swing.*;
import java.awt.*;

public class Window {
	JFrame window = new JFrame();

	public Window() {
		window.setBounds(300, 300, 500, 400);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		JPanel p = new JPanel(new BorderLayout());
		window.add(p);
	}

	public static void main(String[] args) {
		new Window();
	}
}
