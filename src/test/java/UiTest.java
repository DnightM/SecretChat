import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class UiTest extends JPanel implements
		MouseListener,
		ActionListener,
		ComponentListener,
		Runnable
{

	JFrame f;
	Insets insets;
	private Timer t = new Timer(20, this);
	BufferedImage buffer1;
	boolean repaintBuffer1 = true;
	int initWidth = 640;
	int initHeight = 480;
	Rectangle rect;

	public static void main(String[] args) {
		EventQueue.invokeLater(new UiTest());
	}

	@Override
	public void run() {
		f = new JFrame("NewTest");
		f.addComponentListener(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		createBuffers();
		insets = f.getInsets();
		t.start();
	}

	public UiTest() {
		super(true);
		this.setPreferredSize(new Dimension(initWidth, initHeight));
		this.setLayout(null);
		this.addMouseListener(this);
	}

	void createBuffers() {
		int width = this.getWidth();
		int height = this.getHeight();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gs = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gs.getDefaultConfiguration();

		buffer1 = gc.createCompatibleImage(width, height, Transparency.OPAQUE);

		repaintBuffer1 = true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		int width = this.getWidth();
		int height = this.getHeight();
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		if (repaintBuffer1) {
			Graphics g1 = buffer1.getGraphics();
			g1.clearRect(0, 0, width, height);
			g1.setColor(Color.green);
			g1.drawRect(0, 0, width - 1, height - 1);
			g.drawImage(buffer1, 0, 0, null);
			repaintBuffer1 = false;
		}

		double time = 2* Math.PI * (System.currentTimeMillis() % 5000) / 5000.;
		g.setColor(Color.RED);
		if (rect != null) {
			g.drawImage(buffer1, rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, this);
		}
		rect = new Rectangle((int)(Math.sin(time) * width/3 + width/2 - 20), (int)(Math.cos(time) * height/3 + height/2) - 20, 40, 40);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.repaint();
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		int width = e.getComponent().getWidth() - (insets.left + insets.right);
		int height = e.getComponent().getHeight() - (insets.top + insets.bottom);
		this.setSize(width, height);
		createBuffers();
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JTextField field = new JTextField("test");
		field.setBounds(new Rectangle(e.getX(), e.getY(), 100, 20));
		this.add(field);
		repaintBuffer1 = true;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}