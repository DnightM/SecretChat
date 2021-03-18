package client.ui.controller;

import client.ui.component.UiViewer;
import client.ui.component.UiWriter;
import net.mariottini.swing.JFontChooser;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class UiController {
	public static boolean USE_HOTKEY = true;
	public static boolean VIEW_MOVE_WINDOW = false;

	private final UiViewer view;

	private final JFrame writeWindow;
	private final JFrame viewWindow;
	private int mx, my, wx, wy;


	static {
		LogManager.getLogManager().reset();
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);

		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}
	}

	public UiController(UiViewer view, UiWriter write) {
		this.view = view;
		this.viewWindow = view.getWindow();
		this.writeWindow = write.getWindow();
	}

	public void run() {
		hotKey();
		moveViewWindow();
	}

	private void hotKey() {
		GlobalScreen.addNativeKeyListener(new NativeKeyAdapter() {

			@Override
			public void nativeKeyReleased(NativeKeyEvent e) {
				if (!USE_HOTKEY) {
					return;
				}
				switch (e.getKeyCode()) {
					// 옵션 여는 단축키
					case NativeKeyEvent.VC_CONTROL:
						// ctrl 누르고 있는 동안에는 view 창이 움직임
						VIEW_MOVE_WINDOW = false;
						break;
					case 3662: // 키보드 우측 키패드의 + 키
						// 키보드 우측 키패드의 + 누르면 모두 숨기기
						viewWindow.setVisible(!viewWindow.isVisible());
						writeWindow.setVisible(!writeWindow.isVisible());
						break;
					case NativeKeyEvent.VC_F11:
						// F11 강제 종료
						System.exit(0);
						break;
					case 3658: // 키보드 우측 키패드의 - 키
						// 폰트 설정 옵션
						openFontChooser();
						break;
					case 3639: // 키보드 우측 키패드의 * 키
						// view 창 휠 맨 아래로
						break;

				}
			}

			@Override
			public void nativeKeyPressed(NativeKeyEvent e) {
				if (!USE_HOTKEY) {
					return;
				}
				switch (e.getKeyCode()) {
					case NativeKeyEvent.VC_CONTROL: // view move
						Point p = MouseInfo.getPointerInfo().getLocation();
						mx = p.x;
						my = p.y;
						p = viewWindow.getLocation();
						wx = p.x;
						wy = p.y;
						VIEW_MOVE_WINDOW = true;
						break;
				}
			}
		});
	}

	private void openFontChooser() {
		JFontChooser fontChooser = new JFontChooser();
		fontChooser.setFont(view.getFont());
		int result = fontChooser.showDialog(viewWindow);
		if (result == JFontChooser.APPROVE_OPTION) {
			view.setFont(fontChooser.getSelectedFont());
		}
	}


	private void moveViewWindow() {
		GlobalScreen.addNativeMouseMotionListener(new NativeMouseMotionAdapter() {
			@Override
			public void nativeMouseMoved(NativeMouseEvent e) {
				if (UiController.VIEW_MOVE_WINDOW) {
					int x = (int) (mx - e.getPoint().getX());
					int y = (int) (my - e.getPoint().getY());
					viewWindow.setLocation(wx - x, wy - y);
				}
			}
		});
	}
}
