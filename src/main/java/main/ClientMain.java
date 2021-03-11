package main;

import client.ui.UiMain;

import javax.swing.*;
import java.net.Socket;

public class ClientMain {
	public static void main(String[] args) throws Exception {
		String key = "test";
		Socket c_socket = new Socket("192.168.0.58", 24567);
		UiMain ui = new UiMain(c_socket, key, "test" + System.currentTimeMillis());
		ui.open();
	}
}
