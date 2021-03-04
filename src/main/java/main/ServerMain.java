package main;

import server.ClientManager;
import server.ClientThread;
import server.ServerSender;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {
	public static void main(String[] args) throws Exception {
		ServerSender st = new ServerSender();
		st.start();

		ServerSocket s_socket = new ServerSocket(24567);
		System.out.println("waiting connections");
		while (true) {
			Socket c_socket = s_socket.accept();
			ClientManager.add(new ClientThread(c_socket));
		}
	}
}
