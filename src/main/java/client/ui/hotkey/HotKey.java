package client.ui.hotkey;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HotKey {
	public static boolean useHotkey = true;

	static {
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

	public void run() {
		GlobalScreen.addNativeKeyListener(new NativeKeyAdapter() {
			@Override
			public void nativeKeyReleased(NativeKeyEvent e) {
				if (!useHotkey) {
					return;
				}
				switch (e.getKeyCode()) {
					// ctrl 누르고 있는 동안에는 view 창이 움직임
					// + 누르면 모두 숨기기
					// F11 강제 종료
					// 옵션 여는 단축키
					case NativeKeyEvent.VC_F2:

						break;
					case NativeKeyEvent.VC_F3:

						break;
					case NativeKeyEvent.VC_F4:

						break;
				}
			}
		});
	}
}
