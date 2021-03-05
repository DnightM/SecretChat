package main;

import server.ClientThread;
import server.ServerSender;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	@SuppressWarnings("InfiniteLoopStatement")
	public static void main(String[] args) throws Exception {
		String key = "test";

		ServerSender ss = new ServerSender(key);
		ss.start();

		ServerSocket s_socket = new ServerSocket(24567);
		System.out.println("waiting connections");
		while (true) {
			Socket c_socket = s_socket.accept();
			ss.add(new ClientThread(ss, c_socket));
		}
	}
}
