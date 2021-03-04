package main;

import client.RecieveThread;
import client.SendThread;

import java.net.Socket;

public class ClientMain {
	public static void main(String[] args) throws Exception {
		Socket c_socket = new Socket("127.0.0.1", 24567);
		RecieveThread rec_thread = new RecieveThread(c_socket);
		SendThread send_thread = new SendThread(c_socket, "test" + System.currentTimeMillis());
		send_thread.start();
		rec_thread.start();
	}
}
