package main;

import client.ui.UiMain;

import java.net.Socket;

public class ClientMain {
	public static void main(String[] args) throws Exception {
		String key = "test";
		Socket c_socket = new Socket("127.0.0.1", 24567);
		UiMain ui = new UiMain(c_socket, key, "test" + System.currentTimeMillis());
		ui.open();
	}
}
