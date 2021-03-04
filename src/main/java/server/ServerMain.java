package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {
	public static final ArrayList<ClientThread> CLIENT_LIST = new ArrayList<>();

	public static void main(String[] args) throws Exception {
		ServerThread st = new ServerThread();
		st.start();

		ServerSocket s_socket = new ServerSocket(24567);
		System.out.println("waiting connections");
		while (true) {
			Socket c_socket = s_socket.accept();
			ClientThread cl = new ClientThread(c_socket);
			CLIENT_LIST.add(cl);
			cl.start();
		}
	}
}
