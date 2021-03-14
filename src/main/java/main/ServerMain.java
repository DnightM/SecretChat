package main;

import server.ClientThread;
import server.ServerSender;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerMain {
	@SuppressWarnings("InfiniteLoopStatement")
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		String key = getString("비밀번호를 입력하세요 : ", sc);
		int port = getInt("포트 번호를 입력하세요 : ", sc);

		ServerSender ss = new ServerSender();
		ss.start();
		ServerSocket s_socket = new ServerSocket(port);
		System.out.println("ip : " + getMyIp());
		System.out.println("port : " + port);
		System.out.println("password : " + key);
		System.out.println();
		System.out.println("waiting connections...");
		while (true) {
			try {
				Socket c_socket = s_socket.accept();
				ss.add(new ClientThread(ss, c_socket, key));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static int getInt(String msg, Scanner sc) {
		System.out.print(new String(msg.getBytes(), StandardCharsets.UTF_8));
		int t = sc.nextInt();
		System.out.println();
		return t;
	}

	private static String getString(String msg, Scanner sc) {
		System.out.print(new String(msg.getBytes(), StandardCharsets.UTF_8));
		String t = sc.nextLine();
		System.out.println();
		return t;
	}


	public static String getMyIp() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}
}
