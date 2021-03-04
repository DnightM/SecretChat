package server;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	public static void main(String[] args) throws Exception {
		ServerSocket s_socket = new ServerSocket(24567);
		while (true) {
			Socket c_socket = s_socket.accept();
		}
	}
}
