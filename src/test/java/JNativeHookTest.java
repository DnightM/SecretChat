import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JNativeHookTest {
	public static void main(String[] args) {
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);

		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}
		GlobalScreen.addNativeKeyListener(new NativeKeyAdapter() {
			@Override
			public void nativeKeyReleased(NativeKeyEvent e) {
				System.out.println(e.getKeyCode());
			}
		});
		System.out.println(MouseInfo.getPointerInfo().getLocation());
	}
}

