package main;

import server.ClientThread;
import server.ServerSender;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;

public class ServerMain {
	@SuppressWarnings("InfiniteLoopStatement")
	public static void main(String[] args) throws Exception {
		Properties p = new Properties();
		File propertiesFile = new File("./server.properties");
		if (propertiesFile.isFile()) {
			p.load(new FileInputStream(propertiesFile));
		} else {
			System.out.println("need file : server.properties");
			return;
		}
		String key = p.getProperty("pw");
		int port = Integer.parseInt(p.getProperty("port"));

		ServerSender ss = new ServerSender();
		ss.start();
		ServerSocket s_socket = new ServerSocket(port);
		StringBuilder sb = new StringBuilder();
		sb.append("ip : ").append(getMyIp()).append("\n");
		sb.append("port : ").append(port).append("\n");
		sb.append("password : ").append(key).append("\n");
		sb.append("\n");
		sb.append("waiting connections...").append("\n");
		System.out.print(sb);
		while (true) {
			try {
				Socket c_socket = s_socket.accept();
				ss.add(new ClientThread(ss, c_socket, key));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String getMyIp() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}
}
