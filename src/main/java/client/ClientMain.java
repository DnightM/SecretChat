package client;

import network.SendThread;
import network.RecieveThread;

import java.net.Socket;

public class ClientMain {
	public static void main(String[] args) throws Exception {
		Socket c_socket = new Socket("127.0.0.1", 24567);
		RecieveThread rec_thread = new RecieveThread(c_socket);
		SendThread send_thread = new SendThread(c_socket);
		send_thread.start();
		rec_thread.start();
		System.out.println("접속 완료");
	}
}
