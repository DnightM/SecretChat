package main;

import server.ClientThread;
import server.ServerSender;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	@SuppressWarnings("InfiniteLoopStatement")
	public static void main(String[] args) throws IOException {
		String key = "test";

		ServerSender ss = new ServerSender(key);
		ss.start();

		ServerSocket s_socket = new ServerSocket(24567);
		System.out.println("waiting connections");
		while (true) {
			try {
				Socket c_socket = s_socket.accept();
				ss.add(new ClientThread(ss, c_socket));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
